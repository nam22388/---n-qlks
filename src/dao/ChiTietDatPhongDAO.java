package dao;

import model.ChiTietDatPhong;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDatPhongDAO {

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

    // Lấy danh sách chi tiết theo mã phiếu đặt phòng
    public List<ChiTietDatPhong> getByMaPhieu(int maPhieu) {
        List<ChiTietDatPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETDATPHONG WHERE MaPhieuDatPhong = ?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhieu);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChiTietDatPhong ct = new ChiTietDatPhong(
                        rs.getInt("MaCTDP"),
                        rs.getInt("SoNgay"),
                        rs.getInt("MaPhong"),
                        rs.getInt("MaPhieuDatPhong"),
                        rs.getDouble("GiaDat")
                );
                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
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
