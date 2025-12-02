package dao;

import model.PhieuDatPhong;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PhieuDatPhongDAO {

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

    // Cập nhật phiếu đặt phòng
    public boolean update(PhieuDatPhong p) {
        String sql = "UPDATE PHIEUDATPHONG SET MaKhachHang=?, TrangThai=?, NgayDen=?, NgayDi=? "
                   + "WHERE MaPhieu=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getMaKhachHang());
            ps.setString(2, p.getTrangThai());

            if (p.getNgayDen() != null) ps.setDate(3, Date.valueOf(p.getNgayDen()));
            else ps.setNull(3, Types.DATE);

            if (p.getNgayDi() != null) ps.setDate(4, Date.valueOf(p.getNgayDi()));
            else ps.setNull(4, Types.DATE);

            ps.setInt(5, p.getMaPhieuDatPhong());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa phiếu đặt phòng
    public boolean delete(int maPhieu) {
        String sql = "DELETE FROM PHIEUDATPHONG WHERE MaPhieu=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhieu);
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
