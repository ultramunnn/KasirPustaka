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
    public static void addBook(String title, String author, int price, int stock) {
    Connection connect = Koneksi.getConnection();
 

    try {
        stmt = connect.createStatement();
        String query = "INSERT INTO books (title, author, price) VALUES ('" + title + "', '" + author + "', " + price + ")";
        int res = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

        if (res > 0) {
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int bookId = rs.getInt(1);

                stmt_c = connect.createStatement();
                String query_c = "INSERT INTO stock (book_id, quantity) VALUES (" + bookId + ", " + stock + ")";
                int res_c = stmt_c.executeUpdate(query_c);

                if (res_c > 0) {
                    System.out.println("Berhasil menambah buku dan stock.");
                } else {
                    System.out.println("Gagal menambah stock.");
                }
            }
        } else {
            System.out.println("Gagal menambah buku.");
        }
    } catch (SQLException ex) {
        System.out.println("Error saat menambah buku: " + ex);
    } 
}

    //Search Book
    public static String[][] searchBook(String keyword) {
        String[][] data = new String[0][0];
        Connection connect = Koneksi.getConnection();

    try {
        stmt = connect.createStatement();
        query = "SELECT b.book_id, b.title, b.author, b.price, " +
                "(SELECT quantity FROM stock s WHERE s.book_id = b.book_id) AS stock " +
                "FROM books b WHERE b.title LIKE '%" + keyword + "%' OR b.author LIKE '%" + keyword + "%'";
        
        rs = stmt.executeQuery(query);
        
        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
        }

        if (rowCount > 0) {
            data = new String[rowCount][5];
            rs.beforeFirst();
            int i = 0;
            while(rs.next()) {
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
    } catch(SQLException ex) {
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
        String query = "SELECT b.book_id, b.title, b.author, b.price, " +
               "(SELECT quantity FROM stock s WHERE s.book_id = b.book_id) AS stock " +
               "FROM books b ORDER BY b.book_id;";
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
   public static void editBook(int book_id, String newTitle, String newAuthor, int newPrice, int stock){
       Connection connect = Koneksi.getConnection();
       
       try{
            stmt = connect.createStatement();
        String query = "UPDATE books SET "
                     + "title = '" + newTitle + "', "
                     + "author = '" + newAuthor + "', "
                     + "price = " + newPrice + " "
                     + "WHERE book_id = " + book_id;

        int result = stmt.executeUpdate(query);
        
        
        query_c = "SELECT COUNT(*) AS COUNT FROM stock WHERE book_id = " + book_id;
        rs_c = stmt.executeQuery(query_c);
        
      

        int count = 0;
        if (rs_c.next()) {
            count = rs_c.getInt("count");
        }
        rs_c.close();

        if (count > 0) {
            // Update stock
            String queryUpdateStock = "UPDATE stock SET quantity = " + stock + " WHERE book_id = " + book_id;
            stmt.executeUpdate(queryUpdateStock);
        } else {
            // Insert stock baru
            String queryInsertStock = "INSERT INTO stock (book_id, quantity) VALUES (" + book_id + ", " + stock + ")";
            stmt.executeUpdate(queryInsertStock);
        }

        stmt.close();

        if (result > 0) {
            System.out.println("Berhasil mengupdate buku dengan ID " + book_id);
        } else {
            System.out.println("Gagal mengupdate buku, ID " + book_id + " tidak ditemukan.");
        }

    } catch (SQLException ex) {
        System.out.println("Error saat mengupdate buku: " + ex);
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

        if(result > 0){
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


}