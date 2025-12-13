package dao;

import model.ChiTietDatPhong;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDatPhongDAO {

    // Thêm chi tiết đặt phòng
    public boolean insert(ChiTietDatPhong ct) {
        String sql = "INSERT INTO ChiTietDatPhong (MaPhieuDatPhong, MaPhong) VALUES (?, ?)";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ct.getMaPhieuDatPhong());
            ps.setInt(2, ct.getMaPhong());

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
    public List<Integer> getMaPhongByMaPhieu(int maPhieu) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT MaPhong FROM ChiTietDatPhong WHERE MaPhieuDatPhong=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhieu);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("MaPhong"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
