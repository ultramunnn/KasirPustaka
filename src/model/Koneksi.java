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
public class Koneksi {
    public static Connection getConnection(){
        try {
            //untuk MySQL versi 8.0+
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/bookselfappdb?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
            String user = "root";
            String pass = "";

            return DriverManager.getConnection(url, user, pass);

               } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver JDBC tidak ditemukan!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error: Gagal koneksi ke database!");
            e.printStackTrace();
        }
        
        return null;

      
        }
    }

