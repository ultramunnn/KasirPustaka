package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author HP
 */
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

public class TransaksiDetail {

    public static long finalisasiTransaksi(int userIdKasir, String customerName, double totalHarga, List<Object[]> itemsUntukBayar) {
        Connection conn = null;
        long newPurchaseId = -1;

        String sqlPurchase = "INSERT INTO purchases (user_id, customer_name, total_amount) VALUES (?, ?, ?)";
        String sqlUpdateStock = "UPDATE stock SET quantity = quantity - ? WHERE book_id = ?";

        List<Integer> itemIds = itemsUntukBayar.stream()
                .map(item -> Integer.parseInt(item[0].toString()))
                .collect(Collectors.toList());
        String placeholders = itemIds.stream().map(id -> "?").collect(Collectors.joining(", "));
        String sqlUpdateItems = "UPDATE purchase_items SET purchase_id = ? WHERE purchase_item_id IN (" + placeholders + ")";

        try {
            conn = Koneksi.getConnection();
            conn.setAutoCommit(false); // mulai transaction

            // insert ke 'purchases'
            try (PreparedStatement pstmt = conn.prepareStatement(sqlPurchase, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, userIdKasir);
                pstmt.setString(2, customerName.isEmpty() ? "Umum" : customerName);
                pstmt.setDouble(3, totalHarga);
                pstmt.executeUpdate();

                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        newPurchaseId = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Gagal membuat purchase, tidak ada ID yang didapat.");
                    }
                }
            }

            // update 'purchase_items'
            try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateItems)) {
                pstmt.setLong(1, newPurchaseId);
                int paramIndex = 2;
                for (Integer id : itemIds) {
                    pstmt.setInt(paramIndex++, id);
                }
                pstmt.executeUpdate();
            }

            // update stok
            try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdateStock)) {
                for (Object[] item : itemsUntukBayar) {
                    int bookId = Integer.parseInt(item[1].toString());
                    int quantitySold = (Integer) item[4];
                    pstmt.setInt(1, quantitySold);
                    pstmt.setInt(2, bookId);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }

            conn.commit();
            System.out.println("MODEL: Transaksi & Stok berhasil diupdate. ID: " + newPurchaseId);
            return newPurchaseId;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return -1;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
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

}
