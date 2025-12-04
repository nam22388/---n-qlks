package dao;

import model.DichVu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DichVuDAO {

    // Lấy toàn bộ dịch vụ
    public List<DichVu> getAll() {
        List<DichVu> list = new ArrayList<>();
        String sql = "SELECT * FROM DICHVU";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DichVu dv = new DichVu(
                        rs.getInt("MaDichVu"),           // đổi sang int
                        rs.getString("TenDichVu"),
                        rs.getDouble("GiaDichVu")
                );
                list.add(dv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm dịch vụ (không cần MaDichVu vì tự tăng)
    public boolean insert(DichVu dv) {
        String sql = "INSERT INTO DICHVU (TenDichVu, GiaDichVu) VALUES (?, ?)";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dv.getTenDichVu());
            ps.setDouble(2, dv.getGiaDichVu());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa dịch vụ
    public boolean delete(int maDichVu) {
        String sql = "DELETE FROM DICHVU WHERE MaDichVu=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDichVu);                  // đổi sang int
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm theo mã dịch vụ
    public DichVu findById(int maDichVu) {
        String sql = "SELECT * FROM DICHVU WHERE MaDichVu=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDichVu);                  // đổi sang int
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new DichVu(
                        rs.getInt("MaDichVu"),
                        rs.getString("TenDichVu"),
                        rs.getDouble("GiaDichVu")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    // Tìm danh sách dịch vụ theo tên (có thể nhập một phần tên)
    public List<DichVu> findByName(String keyword) {
        List<DichVu> list = new ArrayList<>();
        String sql = "SELECT * FROM DICHVU WHERE TenDichVu LIKE ?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");  // tìm chứa từ khóa
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DichVu dv = new DichVu(
                        rs.getInt("MaDichVu"),
                        rs.getString("TenDichVu"),
                        rs.getDouble("GiaDichVu")
                );
                list.add(dv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
