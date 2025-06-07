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

public class Buku {

    private static String query;
    private static String query_c;
    private static ResultSet rs;
    private static Statement stmt;
    private static ResultSet rs_c;
    private static Statement stmt_c;

    //Insert Books
    public static boolean addBook(String title, String author, int price, int stock) {
        String sqlBook = "INSERT INTO books (title, author, price) VALUES (?, ?, ?)";
        String sqlStock = "INSERT INTO stock (book_id, quantity) VALUES (?, ?)";
        Connection conn = null;

        try {
            conn = Koneksi.getConnection();
            conn.setAutoCommit(false); // Mulai transaksi

            int bookId = -1;

            // Insert ke tabel books
            try (PreparedStatement pstmtBook = conn.prepareStatement(sqlBook, Statement.RETURN_GENERATED_KEYS)) {
                pstmtBook.setString(1, title);
                pstmtBook.setString(2, author);
                pstmtBook.setInt(3, price);
                pstmtBook.executeUpdate();

                try (ResultSet generatedKeys = pstmtBook.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        bookId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Gagal membuat buku, tidak ada ID yang didapat.");
                    }
                }
            }

            // Insert ke tabel stock
            try (PreparedStatement pstmtStock = conn.prepareStatement(sqlStock)) {
                pstmtStock.setInt(1, bookId);
                pstmtStock.setInt(2, stock);
                pstmtStock.executeUpdate();
            }

            conn.commit(); // Jika semua berhasil, commit
            return true;

        } catch (SQLException ex) {
            System.err.println("Error saat menambah buku: " + ex.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Search Book
    public static String[][] searchBook(String keyword) {
        String[][] data = new String[0][0];
        Connection connect = Koneksi.getConnection();

        try {
            stmt = connect.createStatement();
            query = "SELECT b.book_id, b.title, b.author, b.price, "
                    + "(SELECT quantity FROM stock s WHERE s.book_id = b.book_id) AS stock "
                    + "FROM books b WHERE b.title LIKE '%" + keyword + "%' OR b.author LIKE '%" + keyword + "%'";

            rs = stmt.executeQuery(query);

            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }

            if (rowCount > 0) {
                data = new String[rowCount][5];
                rs.beforeFirst();
                int i = 0;
                while (rs.next()) {
                    data[i][0] = rs.getString("book_id");
                    data[i][1] = rs.getString("title");
                    data[i][2] = rs.getString("author");
                    data[i][3] = rs.getString("price");
                    data[i][4] = rs.getString("stock") != null ? rs.getString("stock") : "0";
                    i++;
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Error saat mencari buku: " + ex);
        }

        System.out.println(data);

        return data;
    }

    //get list all book
    public static String[][] getAllBook() {
        String[][] data = new String[0][0]; // Default jika tidak ada data
        Connection connect = Koneksi.getConnection();

        try {
            Statement stmt = connect.createStatement();
            String query = "SELECT b.book_id, b.title, b.author, b.price, "
                    + "(SELECT quantity FROM stock s WHERE s.book_id = b.book_id) AS stock "
                    + "FROM books b ORDER BY b.book_id;";
            ResultSet rs = stmt.executeQuery(query);

            // Hitung jumlah baris terlebih dahulu
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }

            if (rowCount > 0) {
                data = new String[rowCount][5]; // kolom: id, title, author, price, stock
                rs.beforeFirst(); // Reset cursor ke awal

                int i = 0;
                while (rs.next()) {
                    data[i][0] = rs.getString("book_id");
                    data[i][1] = rs.getString("title");
                    data[i][2] = rs.getString("author");
                    data[i][3] = rs.getString("price");
                    data[i][4] = rs.getString("stock");

                    i++;
                }
            }

            rs.close();
            stmt.close();
            connect.close();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }

        return data;
    }

    // test print list book
    public static void printBooks(String[][] books) {
        if (books == null || books.length == 0) {
            System.out.println("Data buku kosong.");
            return;
        }

        for (int i = 0; i < books.length; i++) {
            for (int j = 0; j < books[i].length; j++) {
                System.out.print(books[i][j] + "\t");
            }
            System.out.println();
        }
    }

    //edit data book
     public static boolean editBook(int book_id, String newTitle, String newAuthor, int newPrice, int newStock) {
        String sqlBook = "UPDATE books SET title = ?, author = ?, price = ? WHERE book_id = ?";
        String sqlStock = "UPDATE stock SET quantity = ? WHERE book_id = ?";
        Connection conn = null;

        try {
            conn = Koneksi.getConnection();
            conn.setAutoCommit(false);

            // Update tabel books
            try (PreparedStatement pstmtBook = conn.prepareStatement(sqlBook)) {
                pstmtBook.setString(1, newTitle);
                pstmtBook.setString(2, newAuthor);
                pstmtBook.setDouble(3, newPrice);
                pstmtBook.setInt(4, book_id);
                pstmtBook.executeUpdate();
            }

            // Update tabel stock
            try (PreparedStatement pstmtStock = conn.prepareStatement(sqlStock)) {
                pstmtStock.setInt(1, newStock);
                pstmtStock.setInt(2, book_id);
                pstmtStock.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException ex) {
            System.err.println("Error saat mengupdate buku: " + ex.getMessage());
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException e) { e.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    //delete book
    public static void deleteBook(int book_id) {
        Connection connect = Koneksi.getConnection();

        try {
            //delete stock
            stmt_c = connect.createStatement();
            query_c = "DELETE FROM stock WHERE book_id =" + book_id;
            stmt_c.executeUpdate(query_c);

            //delete book
            stmt = connect.createStatement();
            query = "DELETE FROM books WHERE book_id = " + book_id;
            int result = stmt.executeUpdate(query);

            stmt.close();

            if (result > 0) {
                System.out.println("Berhasil menghapus buku dengan ID " + book_id);
            } else {
                System.out.println("Buku dengan ID " + book_id + " tidak ditemukan.");
            }
        } catch (SQLException ex) {
            System.out.println("Error saat menghapus buku: " + ex);
        }
    }

    //Delete all book   
    public static void deleteAllBooks() {
        Connection connect = Koneksi.getConnection();

        try {
            stmt = connect.createStatement();

            // Hapus semua stock dulu
            stmt.executeUpdate("DELETE FROM stock");

            // Hapus semua buku
            int result = stmt.executeUpdate("DELETE FROM books");

            System.out.println("Berhasil menghapus semua buku dan stock.");

            stmt.close();
            connect.close();
        } catch (SQLException ex) {
            System.out.println("Error saat menghapus semua buku: " + ex);
        }

    }

    // View book_id
    public static int getNextBookId() {
        int nextId = 1; // default 
        Connection connect = Koneksi.getConnection();

        try {
            stmt = connect.createStatement();
            query = "SELECT MAX(book_id) AS max_id FROM books";

            rs = stmt.executeQuery(query);

            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                if (maxId > 0) {
                    nextId = maxId + 1;
                }

            }
        } catch (SQLException e) {
            System.out.println("Error ambil next book_id: " + e.getMessage());
        }

        return nextId;
    }

}
