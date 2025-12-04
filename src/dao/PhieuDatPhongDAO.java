package dao;

import model.PhieuDatPhong;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PhieuDatPhongDAO {

    // Hàm lấy danh sách phiếu kèm thông tin khách hàng
    public List<Object[]> getAllWithKhachHang() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT p.MaPhieuDatPhong, p.MaKhachHang, kh.HoTen, kh.CCCD,
                   p.TrangThai, p.NgayDen, p.NgayDi
            FROM PhieuDatPhong p
            JOIN KhachHang kh ON p.MaKhachHang = kh.MaKhachHang
            """;

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("MaPhieuDatPhong");
                row[1] = rs.getInt("MaKhachHang");
                row[2] = rs.getString("HoTen");
                row[3] = rs.getString("CCCD");
                row[4] = rs.getString("TrangThai");
                
                Date dDen = rs.getDate("NgayDen");
                Date dDi  = rs.getDate("NgayDi");
                row[5] = (dDen != null) ? dDen.toLocalDate() : null;
                row[6] = (dDi  != null) ? dDi.toLocalDate()  : null;

                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    // Lấy toàn bộ phiếu đặt phòng
    public List<PhieuDatPhong> getAll() {
        List<PhieuDatPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM PHIEUDATPHONG";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Date dDen = rs.getDate("NgayDen");
                Date dDi = rs.getDate("NgayDi");
                LocalDate ngayDen = (dDen != null) ? dDen.toLocalDate() : null;
                LocalDate ngayDi  = (dDi  != null) ? dDi.toLocalDate()  : null;

                PhieuDatPhong p = new PhieuDatPhong(
                        rs.getInt("MaPhieu"),      // đổi sang int
                        rs.getInt("MaKhachHang"),  // đổi sang int
                        rs.getString("TrangThai"),
                        ngayDen,
                        ngayDi
                );
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm phiếu đặt phòng
    public boolean insert(PhieuDatPhong p) {
        String sql = "INSERT INTO PHIEUDATPHONG (MaKhachHang, TrangThai, NgayDen, NgayDi) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getMaKhachHang());
            ps.setString(2, p.getTrangThai());

            if (p.getNgayDen() != null) ps.setDate(3, Date.valueOf(p.getNgayDen()));
            else ps.setNull(3, Types.DATE);

            if (p.getNgayDi() != null) ps.setDate(4, Date.valueOf(p.getNgayDi()));
            else ps.setNull(4, Types.DATE);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm theo mã phiếu
    public PhieuDatPhong findById(int maPhieu) {
        String sql = "SELECT * FROM PHIEUDATPHONG WHERE MaPhieu=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhieu);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date dDen = rs.getDate("NgayDen");
                    Date dDi = rs.getDate("NgayDi");
                    LocalDate ngayDen = (dDen != null) ? dDen.toLocalDate() : null;
                    LocalDate ngayDi  = (dDi  != null) ? dDi.toLocalDate()  : null;

                    return new PhieuDatPhong(
                            rs.getInt("MaPhieu"),
                            rs.getInt("MaKhachHang"),
                            rs.getString("TrangThai"),
                            ngayDen,
                            ngayDi
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Lấy danh sách phiếu theo trạng thái
    public List<PhieuDatPhong> findByTrangThai(String trangThai) {
        List<PhieuDatPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM PHIEUDATPHONG WHERE TrangThai = ?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThai);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Date dDen = rs.getDate("NgayDen");
                Date dDi = rs.getDate("NgayDi");

                LocalDate ngayDen = (dDen != null) ? dDen.toLocalDate() : null;
                LocalDate ngayDi  = (dDi  != null) ? dDi.toLocalDate()  : null;

                PhieuDatPhong p = new PhieuDatPhong(
                        rs.getInt("MaPhieu"),
                        rs.getInt("MaKhachHang"),
                        rs.getString("TrangThai"),
                        ngayDen,
                        ngayDi
                );
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    // Tìm danh sách phiếu đặt phòng theo CCCD khách hàng
    public List<PhieuDatPhong> findByCCCD(String cccd) {
        List<PhieuDatPhong> list = new ArrayList<>();

        String sql = """
                SELECT p.* 
                FROM PHIEUDATPHONG p
                JOIN KHACHHANG kh ON p.MaKhachHang = kh.MaKhachHang
                WHERE kh.CCCD = ?
                """;

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cccd);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Date dDen = rs.getDate("NgayDen");
                Date dDi = rs.getDate("NgayDi");

                LocalDate ngayDen = (dDen != null) ? dDen.toLocalDate() : null;
                LocalDate ngayDi  = (dDi  != null) ? dDi.toLocalDate()  : null;

                PhieuDatPhong p = new PhieuDatPhong(
                        rs.getInt("MaPhieu"),
                        rs.getInt("MaKhachHang"),
                        rs.getString("TrangThai"),
                        ngayDen,
                        ngayDi
                );

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    
    // Cập nhật trạng thái phiếu (Đã đặt / Đang thuê / Đã trả / Đã hủy)
    public boolean updateTrangThai(int maPhieu, String trangThai) {
        String sql = "UPDATE PHIEUDATPHONG SET TrangThai=? WHERE MaPhieu=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThai);
            ps.setInt(2, maPhieu);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
