package dao;

import model.ChiTietDichVu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDichVuDAO {

    // Lấy toàn bộ chi tiết dịch vụ
    public List<ChiTietDichVu> getAll() {
        List<ChiTietDichVu> list = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETDICHVU";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ChiTietDichVu ct = new ChiTietDichVu(
                        rs.getInt("MaDichVu"),    // đổi sang int
                        rs.getInt("MaCTDP"),      // đổi sang int
                        rs.getInt("SoLuong"),
                        rs.getDouble("ThanhTien")
                );
                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm chi tiết dịch vụ
    public boolean insert(ChiTietDichVu ct) {
        String sql = "INSERT INTO CHITIETDICHVU (MaDichVu, MaCTDP, SoLuong, ThanhTien) VALUES (?, ?, ?, ?)";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ct.getMaDichVu());
            ps.setInt(2, ct.getMaCTDP());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getThanhTien());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật chi tiết dịch vụ
    public boolean update(ChiTietDichVu ct) {
        String sql = "UPDATE CHITIETDICHVU SET SoLuong=?, ThanhTien=? WHERE MaDichVu=? AND MaCTDP=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ct.getSoLuong());
            ps.setDouble(2, ct.getThanhTien());
            ps.setInt(3, ct.getMaDichVu());
            ps.setInt(4, ct.getMaCTDP());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa chi tiết dịch vụ
    public boolean delete(int maDichVu, int maCTDP) {
        String sql = "DELETE FROM CHITIETDICHVU WHERE MaDichVu=? AND MaCTDP=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDichVu);
            ps.setInt(2, maCTDP);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm chi tiết dịch vụ theo khóa kép
    public ChiTietDichVu findById(int maDichVu, int maCTDP) {
        String sql = "SELECT * FROM CHITIETDICHVU WHERE MaDichVu=? AND MaCTDP=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maDichVu);
            ps.setInt(2, maCTDP);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ChiTietDichVu(
                        rs.getInt("MaDichVu"),
                        rs.getInt("MaCTDP"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("ThanhTien")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
