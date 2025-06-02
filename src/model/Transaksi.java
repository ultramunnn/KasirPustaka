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
import model.Koneksi;
public class Transaksi {
   
    ///get list all book
   public static String[][] getRiwayatTransaksi() {
    String[][] data = new String[0][0]; // Default jika tidak ada data
    Connection connect = Koneksi.getConnection();

    try {
    Statement stmt = connect.createStatement();
    String query = "SELECT pi.purchase_item_id, b.title, pi.quantity, (b.price * pi.quantity) AS subtotal, pi.book_id, b.price FROM purchase_items pi " + 
                   "JOIN books b ON pi.book_id = b.book_id";

    ResultSet rs = stmt.executeQuery(query);

    // Hitung jumlah baris terlebih dahulu
    int rowCount = 0;
    while (rs.next()) {
        rowCount++;
    }

    if (rowCount > 0) {
        data = new String[rowCount][6]; // Kolom: id, title, quantity, total, book_id, price
        rs.beforeFirst(); // Reset cursor ke awal

        int i = 0;
        while (rs.next()) {
            data[i][0] = rs.getString("purchase_item_id");  // ID transaksi
            data[i][1] = rs.getString("book_id");             // ID Buku
            data[i][2] = rs.getString("title");        //Judul
            data[i][3] = rs.getString("price");     // Total
            data[i][4] = rs.getString("quantity");          // Jumlah
            data[i][5] = rs.getString("subtotal");           // Total
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
    
}
