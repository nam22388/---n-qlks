/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author LEGION
 */
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;    
public class DBConnection {
    public static Connection getJDBCConnection() {

        String url = "jdbc:mysql://localhost:3306/hotel_management";
        String user = "dung"; // root
        String password = "12345"; // Các bạn tự đặt ở trong mysql workbench
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    public static void main(String[] args) {
        Connection conn = getJDBCConnection();

        if(conn != null) {
            System.out.println("Thanh cong");
        } else {
            System.out.println("That bai");
        }
    }
}
