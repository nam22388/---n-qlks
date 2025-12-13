package dao;

import model.HoaDon;
import java.sql.*;
import java.time.LocalDate;

public class HoaDonDAO {

    public HoaDon getByMaPhieuDatPhong(int maPhieu) {

        String sql = """
            SELECT hd.MaHoaDon, hd.MaPhieuDatPhong, hd.NgayTao,
                   hd.TongTienPhong, hd.TongTienDichVu, hd.TongCong,
                   kh.MaKhachHang, kh.HoTen
            FROM HOADON hd
            JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang
            WHERE hd.MaPhieuDatPhong = ?
        """;

        HoaDon hd = null;

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhieu);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Date d = rs.getDate("NgayTao");
                    LocalDate ngayTao = (d != null) ? d.toLocalDate() : null;

                    hd = new HoaDon(
                            rs.getInt("MaHoaDon"),
                            rs.getInt("MaPhieuDatPhong"),
                            ngayTao,
                            rs.getDouble("TongTienPhong"),
                            rs.getDouble("TongTienDichVu"),
                            rs.getDouble("TongCong"),
                            rs.getInt("MaKhachHang")
                    );

                    // tên khách hàng đọc đúng từ SELECT
                    hd.setTenKhachHang(rs.getString("HoTen"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hd;
    }

  
}
