package dao;

import model.ChiTietDichVu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDichVuDAO {

    

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
    // Lấy danh sách chi tiết dịch vụ theo mã chi tiết đặt phòng
    public List<Object[]> getByMaCTDP(int maCTDP) {
        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT ct.MaDichVu, dv.TenDichVu, ct.SoLuong, ct.ThanhTien
            FROM CHITIETDICHVU ct
            JOIN DICHVU dv ON ct.MaDichVu = dv.MaDichVu
            WHERE ct.MaCTDP = ?
        """;

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maCTDP);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getInt("MaDichVu");
                row[1] = rs.getString("TenDichVu");
                row[2] = rs.getInt("SoLuong");
                row[3] = rs.getDouble("ThanhTien");

                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
