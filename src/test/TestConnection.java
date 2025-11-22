/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=QLKS;encrypt=false;";
        String user = "sa";
        String password = "123"; // đổi theo máy bạn
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Kết nối SQL Server thành công!");
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kết nối!");
            e.printStackTrace();
        }
    }
}

