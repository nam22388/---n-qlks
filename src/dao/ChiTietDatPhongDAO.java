package dao;

import model.ChiTietDatPhong;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDatPhongDAO {

    // Lấy toàn bộ chi tiết đặt phòng
    public List<ChiTietDatPhong> getAll() {
        List<ChiTietDatPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETDATPHONG";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ChiTietDatPhong ct = new ChiTietDatPhong(
                        rs.getInt("MaCTDP"),           // int
                        rs.getInt("SoNgay"),
                        rs.getInt("MaPhong"),          // int
                        rs.getInt("MaPhieuDatPhong"),  // int
                        rs.getDouble("GiaDat")
                );
                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm chi tiết đặt phòng
    public boolean insert(ChiTietDatPhong ct) {
        String sql = "INSERT INTO CHITIETDATPHONG (MaCTDP, SoNgay, MaPhong, MaPhieuDatPhong, GiaDat) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ct.getMaCTDP());
            ps.setInt(2, ct.getSoNgay());
            ps.setInt(3, ct.getMaPhong());
            ps.setInt(4, ct.getMaPhieuDatPhong());
            ps.setDouble(5, ct.getGiaDat());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    // Xóa chi tiết đặt phòng
    public boolean delete(int maCTDP) {
        String sql = "DELETE FROM CHITIETDATPHONG WHERE MaCTDP=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maCTDP);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
