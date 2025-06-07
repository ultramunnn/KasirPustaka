package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author HP
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class User {

    //variabel untuk nyimpan data user yang login
    private int userId;
    private String username;
    private String password;
    private String role;
    private Timestamp createdAt;

    private User(int userId, String username, String role, Timestamp createdAt) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public static User login(String inputUsername, String inputPassword) {
        Connection connect = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User loggedInUser = null;

        String query = "select * from users where username = ?";
        try {
            connect = Koneksi.getConnection();
            if (connect == null) {
                System.err.println("Gagal Mendapatkan Koneksi ke Database di User.login().");
                return null;
            }
            pstmt = connect.prepareStatement(query);
            pstmt.setString(1, inputUsername);

            rs = pstmt.executeQuery();

            //jika username di temukan
            if (rs.next()) {
                String passwordFromDB = rs.getString("password");

                if (inputPassword.equals(passwordFromDB)) {
                    //jika password user cocok dia buat object user
                    loggedInUser = new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("role"),
                            rs.getTimestamp("created_at")
                    );
                    System.out.println("Login berhasil " + inputUsername + "!");
                } else {
                    System.out.println("Password salah " + inputUsername);
                }
            } else {
                System.out.println("Username tidak ditemukan " + inputUsername + "!");
            }
        } catch (Exception e) {
            System.err.println("Error saat proses login: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch(SQLException ex) {
                System.err.println("Error saat menutup resources" + ex.getMessage());
            }
        }
        return loggedInUser;

    }

}
