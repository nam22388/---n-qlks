package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.KhachHang;

public class KhachHangDAO {

    // Lấy danh sách tất cả khách hàng
    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KHACHHANG";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getInt("MaKhachHang"),   // đổi sang int
                        rs.getString("HoTen"),
                        rs.getString("CCCD"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("Email")
                );
                list.add(kh);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm khách hàng (không cần MaKhachHang vì tự tăng)
    public boolean insert(KhachHang kh) {
        String sql = "INSERT INTO KHACHHANG (HoTen, CCCD, SoDienThoai, DiaChi, Email) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getCccd());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getDiaChi());
            ps.setString(5, kh.getEmail());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật khách hàng
    public boolean update(KhachHang kh) {
        String sql = "UPDATE KHACHHANG SET HoTen=?, CCCD=?, SoDienThoai=?, DiaChi=?, Email=? "
                   + "WHERE MaKhachHang=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getCccd());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getDiaChi());
            ps.setString(5, kh.getEmail());
            ps.setInt(6, kh.getMaKhachHang()); // đổi sang int

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa khách hàng theo mã
    public boolean delete(int maKhachHang) {
        String sql = "DELETE FROM KHACHHANG WHERE MaKhachHang=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKhachHang);  // đổi sang int
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm khách hàng theo mã
    public KhachHang findById(int maKhachHang) {
        String sql = "SELECT * FROM KHACHHANG WHERE MaKhachHang=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKhachHang);  // đổi sang int
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new KhachHang(
                        rs.getInt("MaKhachHang"),
                        rs.getString("HoTen"),
                        rs.getString("CCCD"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("Email")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Tìm theo CCCD (dùng để check trùng)
    public KhachHang findByCCCD(String cccd) {
        String sql = "SELECT * FROM KHACHHANG WHERE CCCD=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cccd);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new KhachHang(
                        rs.getInt("MaKhachHang"),
                        rs.getString("HoTen"),
                        rs.getString("CCCD"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("Email")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
