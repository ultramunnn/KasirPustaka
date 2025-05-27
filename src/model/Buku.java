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
    private static ResultSet rs;
    private static Statement stmt;

    
    //Insert Books
    public static void addBook (String title, String author, int price){
        Connection connect = Koneksi.getConnection();

        
        try{
            stmt = connect.createStatement();
            query = "INSERT INTO books (title,author,price)"
                    + "VALUES ('"+ title + "', '"+ author +"','"+ price +"')";
           
            int rs = stmt.executeUpdate(query);

            stmt.close();
            
          if(rs > 0){
            System.out.println("Berhasil menambah buku.");
          } else {
            System.out.println("Gagal menambah buku.");
          }
              
        }catch(SQLException ex){
            System.out.println("Tidak berhasil menambah buku error: " + ex);
            
            
        }
    
    }
    
    //Search Book
    public static void searchBook(String title) {
    Connection connect = Koneksi.getConnection();

    try {
        stmt = connect.createStatement();
        query = "SELECT * FROM books WHERE title LIKE '%" + title + "%'";
        
        rs = stmt.executeQuery(query);
        System.out.println("Query yang dijalankan: " + query);

        boolean found = false;
        while(rs.next()) {
            found = true;
            System.out.println("ID: " + rs.getInt("book_id")
                + ", Title: " + rs.getString("title")
                + ", Author: " + rs.getString("author")
                + ", Price: " + rs.getInt("price"));
        }
        
        if(!found) {
            System.out.println("Buku tidak ditemukan.");
        }
        
        rs.close();
        stmt.close();
    } catch(SQLException ex) {
        System.out.println("Error saat mencari buku: " + ex);
    }
}

    
    //get list all book
   public static String[][] getAllBook() {
    String[][] data = new String[0][0]; // Default jika tidak ada data
    Connection connect = Koneksi.getConnection();

    try {
        Statement stmt = connect.createStatement();
        String query = "SELECT * FROM books ORDER BY book_id;";
        ResultSet rs = stmt.executeQuery(query);

        // Hitung jumlah baris terlebih dahulu
        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
        }

        if (rowCount > 0) {
            data = new String[rowCount][4]; // kolom: id, title, author, price, stock
            rs.beforeFirst(); // Reset cursor ke awal

            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("book_id");
                data[i][1] = rs.getString("title");
                data[i][2] = rs.getString("author");
                data[i][3] = rs.getString("price");
               
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
   public static void editBook(int book_id, String newTitle, String newAuthor, int newPrice){
       Connection connect = Koneksi.getConnection();
       
       try{
            stmt = connect.createStatement();
        String query = "UPDATE books SET "
                     + "title = '" + newTitle + "', "
                     + "author = '" + newAuthor + "', "
                     + "price = " + newPrice + " "
                     + "WHERE book_id = " + book_id;

        int result = stmt.executeUpdate(query);
        stmt.close();

        if(result > 0){
            System.out.println("Berhasil mengupdate buku dengan ID " + book_id);
        } else {
            System.out.println("Gagal mengupdate buku, ID " + book_id + " tidak ditemukan.");
        }
           
       }catch(SQLException ex){
             System.out.println("Error saat mengupdate buku: " + ex);
       }
       
   }
   
   public static void deleteBook(int book_id) {
    Connection connect = Koneksi.getConnection();

    try {
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


    
    
  
    public static void main(String[] args) {
      
        System.out.println("-------Tambah Buku-------");
        Buku.addBook("Mamah Kenapa aku TI", "Munir", 200000);
        Buku.addBook("AKuh LeMAS", "Joni", 300000);
        
//        
//          System.out.println("---------Edit Buku-----------\n");
//          Buku.editBook(3, "Java Anjay", "Yono", 500000);
//        
//        System.out.println("-----------Delete Buku----------\n");
//        Buku.deleteBook(6);
        
        
        System.out.println("----------List Buku----------\n");
        String[][] books = getAllBook();
        printBooks(books);
         
        
    }
}


