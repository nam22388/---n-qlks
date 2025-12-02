package dao;

import model.HoaDon;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    // Lấy toàn bộ hóa đơn
    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HOADON";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Date d = rs.getDate("NgayTao");
                LocalDate ngayTao = (d != null) ? d.toLocalDate() : null;

                HoaDon hd = new HoaDon(
                        rs.getInt("MaHoaDon"),                // đổi sang int
                        rs.getInt("MaPhieu"),                 // đổi sang int
                        ngayTao,
                        rs.getDouble("TongTienPhong"),
                        rs.getDouble("TongTienDichVu"),
                        rs.getDouble("TongCong"),
                        rs.getInt("MaKhachHang")              // đổi sang int
                );
                list.add(hd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm hóa đơn (không cần MaHoaDon vì tự tăng)
    public boolean insert(HoaDon hd) {
        String sql = "INSERT INTO HOADON (MaPhieu, NgayTao, TongTienPhong, TongTienDichVu, TongCong, MaKhachHang) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hd.getMaPhieuDatPhong());
            if (hd.getNgayTao() != null)
                ps.setDate(2, Date.valueOf(hd.getNgayTao()));
            else
                ps.setNull(2, Types.DATE);

            ps.setDouble(3, hd.getTongTienPhong());
            ps.setDouble(4, hd.getTongTienDichVu());
            ps.setDouble(5, hd.getTongCong());
            ps.setInt(6, hd.getMaKhachHang());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm theo mã hóa đơn
    public HoaDon findById(int maHoaDon) {
        String sql = "SELECT * FROM HOADON WHERE MaHoaDon=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHoaDon);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Date d = rs.getDate("NgayTao");
                LocalDate ngayTao = (d != null) ? d.toLocalDate() : null;

                return new HoaDon(
                        rs.getInt("MaHoaDon"),
                        rs.getInt("MaPhieu"),
                        ngayTao,
                        rs.getDouble("TongTienPhong"),
                        rs.getDouble("TongTienDichVu"),
                        rs.getDouble("TongCong"),
                        rs.getInt("MaKhachHang")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
