package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author HP
 */
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Koneksi;

public class Transaksi {

    // Menambahkan SATU ITEM ke keranjang di database
    public static String tambahItemKeKeranjang(int idItem, int bookId, int jumlah, double harga) {
        Connection conn = null;
        String checkStockSql = "SELECT title, quantity FROM stock s JOIN books b ON s.book_id = b.book_id WHERE s.book_id = ?";
        String insertItemSql = "INSERT INTO purchase_items (purchase_item_id, book_id, quantity, subtotal) VALUES (?, ?, ?, ?)";

        try {
            conn = Koneksi.getConnection();
            conn.setAutoCommit(false);

            // validasi Stok
            try (PreparedStatement psCheck = conn.prepareStatement(checkStockSql)) {
                psCheck.setInt(1, bookId);
                try (ResultSet rsCheck = psCheck.executeQuery()) {
                    if (rsCheck.next()) {
                        int stokTersedia = rsCheck.getInt("quantity");
                        if (stokTersedia < jumlah) {
                            String judulBuku = rsCheck.getString("title");
                            // tampikan error
                            String pesanError = "Stok untuk '" + judulBuku + "' tidak mencukupi.\nStok tersedia: " + stokTersedia + ", Anda ingin membeli: " + jumlah;

                            // batalkan transaksi dan keluar
                            conn.rollback();
                            return pesanError;
                        }
                    } else {
                        conn.rollback();
                        return "Buku dengan ID " + bookId + " tidak ditemukan di daftar stok.";
                    }
                }
            }

            // hanya jika stok aman, lakukan insert
            double subtotal = harga * jumlah;
            try (PreparedStatement psInsert = conn.prepareStatement(insertItemSql)) {
                psInsert.setInt(1, idItem);
                psInsert.setInt(2, bookId);
                psInsert.setInt(3, jumlah);
                psInsert.setDouble(4, subtotal);
                psInsert.executeUpdate();
            }

            conn.commit();
            return "OK";

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            e.printStackTrace();
            return "Terjadi error database: " + e.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    // Menyelesaikan seluruh transaksi
    public static long finalisasiTransaksi(int userIdKasir, String customerName, double totalHarga, List<Object[]> keranjang) {
        Connection conn = null;
        long newPurchaseId = -1;

        String sqlPurchase = "INSERT INTO purchases (user_id, customer_name, total_amount) VALUES (?, ?, ?)";
        String sqlUpdateStock = "UPDATE stock SET quantity = quantity - ? WHERE book_id = ?";
        List<Integer> itemIds = keranjang.stream().map(item -> Integer.parseInt(item[0].toString())).collect(Collectors.toList());
        String placeholders = itemIds.stream().map(id -> "?").collect(Collectors.joining(", "));
        String sqlUpdateItems = "UPDATE purchase_items SET purchase_id = ? WHERE purchase_item_id IN (" + placeholders + ")";

        try {
            conn = Koneksi.getConnection();
            conn.setAutoCommit(false);

            // Logika validasi stok sudah ada di `tambahItemKeKeranjang`, jadi tidak perlu di sini lagi
            // 1. INSERT ke purchases
            try (PreparedStatement pstmt = conn.prepareStatement(sqlPurchase, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, userIdKasir);
                pstmt.setString(2, customerName);
                pstmt.setDouble(3, totalHarga);
                pstmt.executeUpdate();
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        newPurchaseId = rs.getLong(1);
                    }
                }
            }

            // 2. UPDATE purchase_items
            try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateItems)) {
                pstmt.setLong(1, newPurchaseId);
                int paramIndex = 2;
                for (Integer id : itemIds) {
                    pstmt.setInt(paramIndex++, id);
                }
                pstmt.executeUpdate();
            }

            // 3. UPDATE stock
            try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateStock)) {
                for (Object[] item : keranjang) {
                    pstmt.setInt(1, Integer.parseInt(item[4].toString())); // quantity
                    pstmt.setInt(2, Integer.parseInt(item[1].toString())); // book_id
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            conn.commit();
            return newPurchaseId;

        } catch (SQLException e) {
            if (conn != null) try {
                conn.rollback();
            } catch (SQLException ex) {
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Transaksi Gagal: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            return -1;
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException ex) {
            }
        }
    }

    public static boolean hapusSatuTransaksi(long purchaseId) {
        String sql = "DELETE FROM purchases WHERE purchase_id = ?";

        try (Connection conn = Koneksi.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameter dengan aman
            pstmt.setLong(1, purchaseId);

            int rowsAffected = pstmt.executeUpdate();

            // Kembalikan true kalau ada baris yang dihapus
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error saat menghapus transaksi ID " + purchaseId);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean hapusSemuaTransaksi() {
        try (Connection conn = Koneksi.getConnection(); Statement stmt = (Statement) conn.createStatement()) {
            // set matikan pengecekan foreign kan
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            System.out.println("MODEL: Foreign key checks dinonaktifkan.");

            //kosongkan tabel detail (purchase_items)
            stmt.executeUpdate("TRUNCATE TABLE purchase_items");
            System.out.println("MODEL: Tabel purchase_items dikosongkan.");

            // kosongkan tabel master (purchases)
            stmt.executeUpdate("TRUNCATE TABLE purchases");
            System.out.println("MODEL: Tabel purchases dikosongkan.");

            // aktifkan kembali pengecekan foreign key
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            System.out.println("MODEL: Foreign key checks diaktifkan kembali.");

            // jika semua perintah di atas berhasil tanpa melempar exception,
            // kembalikan true
            return true;

        } catch (SQLException e) {
            System.err.println("Error saat menghapus semua transaksi: " + e.getMessage());
            e.printStackTrace();
            // jika terjadi error di mana pun di dalam blok try, kembalikan false
            return false;
        }
    }

    public static boolean DeleteItemTransaksi(long purchaseId) {
        String sql = "DELETE FROM purchases WHERE purchase_id = ?";

        try (Connection conn = Koneksi.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameter dengan aman
            pstmt.setLong(1, purchaseId);

            int rowsAffected = pstmt.executeUpdate();

            // Kembalikan true kalau ada baris yang dihapus
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error saat menghapus transaksi ID " + purchaseId);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean DeleteAllTransaksi() {
        try (Connection conn = Koneksi.getConnection(); com.mysql.jdbc.Statement stmt = (com.mysql.jdbc.Statement) conn.createStatement()) {
            // set matikan pengecekan foreign kan
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            System.out.println("MODEL: Foreign key checks dinonaktifkan.");

            //kosongkan tabel detail (purchase_items)
            stmt.executeUpdate("TRUNCATE TABLE purchase_items");
            System.out.println("MODEL: Tabel purchase_items dikosongkan.");

            // kosongkan tabel master (purchases)
            stmt.executeUpdate("TRUNCATE TABLE purchases");
            System.out.println("MODEL: Tabel purchases dikosongkan.");

            // aktifkan kembali pengecekan foreign key
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            System.out.println("MODEL: Foreign key checks diaktifkan kembali.");

            // jika semua perintah di atas berhasil tanpa melempar exception,
            // kembalikan true
            return true;

        } catch (SQLException e) {
            System.err.println("Error saat menghapus semua transaksi: " + e.getMessage());
            e.printStackTrace();
            // jika terjadi error di mana pun di dalam blok try, kembalikan false
            return false;
        }
    }

    // Method untuk mengambil riwayat
    public static DefaultTableModel getRiwayatPemasukan() {
        String[] columnNames = {"ID Transaksi", "Pelanggan", "Total", "Tanggal"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        String sql = "SELECT purchase_id, customer_name, total_amount, purchase_date FROM purchases ORDER BY purchase_date DESC LIMIT 10";
        try (Connection conn = Koneksi.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("purchase_id"),
                    rs.getString("customer_name"),
                    String.format("Rp %,.2f", rs.getDouble("total_amount")),
                    rs.getTimestamp("purchase_date").toString()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public static DefaultTableModel getRiwayatPembelianModel() {
        String[] columnNames = {"ID Transaksi", "Pelanggan", "Total", "Tanggal"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        String sql = "SELECT purchase_id, customer_name, total_amount, purchase_date FROM purchases WHERE purchase_id IS NOT NULL ORDER BY purchase_date DESC LIMIT 10";
        try (Connection conn = Koneksi.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("purchase_id"),
                    rs.getString("customer_name"),
                    String.format("Rp %,.2f", rs.getDouble("total_amount")),
                    rs.getTimestamp("purchase_date").toString()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public static DefaultTableModel getKeranjangSaatIni() {
        String[] columnNames = {"Id Item", "Kode Buku", "Judul", "Harga", "Jumlah", "Subtotal"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        String sql = "SELECT pi.purchase_item_id, pi.book_id, b.title, b.price, pi.quantity, pi.subtotal "
                + "FROM purchase_items pi JOIN books b ON pi.book_id = b.book_id "
                + "WHERE pi.purchase_id IS NULL ORDER BY pi.purchase_item_id";

        try (Connection conn = Koneksi.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("purchase_item_id"),
                    rs.getString("book_id"),
                    rs.getString("title"),
                    rs.getString("price"),
                    rs.getString("quantity"),
                    rs.getString("subtotal")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data keranjang: " + e.getMessage());
            e.printStackTrace();
        }
        return model;
    }

    public static String editItemDiKeranjang(int idItem, int bookId, int jumlahBaru, double harga) {
        Connection conn = null;
        String checkStockSql = "SELECT title, quantity FROM stock s JOIN books b ON s.book_id = b.book_id WHERE s.book_id = ?";
        String updateItemSql = "UPDATE purchase_items SET book_id = ?, quantity = ?, subtotal = ? WHERE purchase_item_id = ?";

        try {
            conn = Koneksi.getConnection();
            conn.setAutoCommit(false); // mulai transaction

            // vlidasi Stok
            try (PreparedStatement psCheck = conn.prepareStatement(checkStockSql)) {
                psCheck.setInt(1, bookId);
                try (ResultSet rsCheck = psCheck.executeQuery()) {
                    if (rsCheck.next()) {
                        int stokTersedia = rsCheck.getInt("quantity");
                        if (stokTersedia < jumlahBaru) {
                            String judulBuku = rsCheck.getString("title");
                            String pesanError = "Stok untuk '" + judulBuku + "' tidak mencukupi.\nStok tersedia: " + stokTersedia + ", Anda ingin mengubah menjadi: " + jumlahBaru;
                            conn.rollback();
                            return pesanError;
                        }
                    } else {
                        conn.rollback();
                        return "Buku dengan ID " + bookId + " tidak ditemukan di daftar stok.";
                    }
                }
            }

            // jika stok aman update
            double subtotal = harga * jumlahBaru;
            try (PreparedStatement psUpdate = conn.prepareStatement(updateItemSql)) {
                psUpdate.setInt(1, bookId);
                psUpdate.setInt(2, jumlahBaru);
                psUpdate.setDouble(3, subtotal);
                psUpdate.setInt(4, idItem);

                int rowsAffected = psUpdate.executeUpdate();
                if (rowsAffected == 0) {
                   //jika item tidak di temukan
                    conn.rollback();
                    return "Gagal mengedit, item dengan ID " + idItem + " tidak ditemukan.";
                }
            }

            conn.commit();
            return "OK";

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            e.printStackTrace();
            return "Terjadi error database: " + e.getMessage();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
