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
    public PhieuDatPhong findById(int maPhieuDatPhong) {
        String sql = "SELECT * FROM PHIEUDATPHONG WHERE MaPhieuDatPhong=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhieuDatPhong);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date dDen = rs.getDate("NgayDen");
                    Date dDi = rs.getDate("NgayDi");
                    LocalDate ngayDen = (dDen != null) ? dDen.toLocalDate() : null;
                    LocalDate ngayDi  = (dDi  != null) ? dDi.toLocalDate()  : null;

                    return new PhieuDatPhong(
                            rs.getInt("MaPhieuDatPhong"),
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

    // Tìm danh sách phiếu đặt phòng theo CCCD khách hàng
    public List<Object[]> findByCCCD(String cccd) {
        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT p.MaPhieuDatPhong, p.MaKhachHang, kh.HoTen, kh.CCCD,
                   p.TrangThai, p.NgayDen, p.NgayDi
            FROM PHIEUDATPHONG p
            JOIN KHACHHANG kh ON p.MaKhachHang = kh.MaKhachHang
            WHERE kh.CCCD = ?
        """;

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cccd);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("MaPhieuDatPhong"),
                        rs.getInt("MaKhachHang"),
                        rs.getString("HoTen"),
                        rs.getString("CCCD"),
                        rs.getString("TrangThai"),
                        rs.getDate("NgayDen") != null ? rs.getDate("NgayDen").toLocalDate() : null,
                        rs.getDate("NgayDi") != null ? rs.getDate("NgayDi").toLocalDate() : null
                };
                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<Object[]> findByCCCDAndTrangThai(String cccd, String trangThai) {
        List<Object[]> list = new ArrayList<>();

        String sql = """
            SELECT p.MaPhieuDatPhong, p.MaKhachHang, kh.HoTen, kh.CCCD,
                   p.TrangThai, p.NgayDen, p.NgayDi
            FROM PhieuDatPhong p
            JOIN KhachHang kh ON p.MaKhachHang = kh.MaKhachHang
            WHERE kh.CCCD LIKE ? 
              AND (p.TrangThai = ? OR ? = 'Tất cả')
        """;

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + cccd + "%");
            ps.setString(2, trangThai);
            ps.setString(3, trangThai);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getInt("MaPhieuDatPhong");
                row[1] = rs.getInt("MaKhachHang");
                row[2] = rs.getString("HoTen");
                row[3] = rs.getString("CCCD");
                row[4] = rs.getString("TrangThai");

                Date dDen = rs.getDate("NgayDen");
                Date dDi = rs.getDate("NgayDi");

                row[5] = (dDen != null) ? dDen.toLocalDate() : null;
                row[6] = (dDi  != null) ? dDi.toLocalDate()  : null;

                list.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    // Cập nhật trạng thái phiếu (Đã đặt / Đang thuê / Đã trả / Đã hủy)
    public boolean updateTrangThai(int maPhieuDatPhong, String trangThai) {
        String sql = "UPDATE PHIEUDATPHONG SET TrangThai=? WHERE MaPhieuDatPhong=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThai);
            ps.setInt(2, maPhieuDatPhong);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean nhanPhong(int maPhieu) {
        String sqlUpdatePhieu =
            "UPDATE PhieuDatPhong SET TrangThai='Đang thuê' WHERE MaPhieuDatPhong=?";

        String sqlUpdatePhong =
            "UPDATE Phong SET TinhTrang='Đang sử dụng' WHERE MaPhong=?";

        ChiTietDatPhongDAO ctDao = new ChiTietDatPhongDAO();

        try (Connection conn = TestConnection.getJDBCConnection()) {
            conn.setAutoCommit(false);

            // 1. Update phiếu
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdatePhieu)) {
                ps.setInt(1, maPhieu);
                ps.executeUpdate();
            }

            // 2. Update phòng
            for (int maPhong : ctDao.getMaPhongByMaPhieu(maPhieu)) {
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdatePhong)) {
                    ps.setInt(1, maPhong);
                    ps.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    // Trả phòng và tự động tạo hóa đơn
    public boolean traPhong(int maPhieuDatPhong) {

        String sqlCheck = "SELECT TrangThai FROM PhieuDatPhong WHERE MaPhieuDatPhong=?";
        String sqlUpdatePhieu = "UPDATE PhieuDatPhong SET TrangThai='Đã trả' WHERE MaPhieuDatPhong=?";

        String sqlUpdatePhong = """
            UPDATE Phong 
            SET TinhTrang='Trống'
            WHERE MaPhong IN (
                SELECT MaPhong FROM ChiTietDatPhong WHERE MaPhieuDatPhong=?
            )
        """;

        String sqlInsertHD = """
            INSERT INTO HoaDon (MaPhieuDatPhong, MaKhachHang)
            VALUES (?, (SELECT MaKhachHang FROM PhieuDatPhong WHERE MaPhieuDatPhong=?))
        """;

        try (Connection conn = TestConnection.getJDBCConnection()) {
            conn.setAutoCommit(false);

            /* 1. Kiểm tra trạng thái */
            try (PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
                ps.setInt(1, maPhieuDatPhong);
                ResultSet rs = ps.executeQuery();
                if (!rs.next() || !"Đang thuê".equals(rs.getString("TrangThai"))) {
                    conn.rollback();
                    return false;
                }
            }

            /* 2. Cập nhật trạng thái phiếu */
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdatePhieu)) {
                ps.setInt(1, maPhieuDatPhong);
                ps.executeUpdate();
            }

            /* 3. Chuyển phòng về Trống */
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdatePhong)) {
                ps.setInt(1, maPhieuDatPhong);
                ps.executeUpdate();
            }

            /* 4. Tạo hóa đơn */
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertHD)) {
                ps.setInt(1, maPhieuDatPhong);
                ps.setInt(2, maPhieuDatPhong);
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean huyPhieu(int maPhieu) {
        String sqlPhieu =
            "UPDATE PhieuDatPhong SET TrangThai='Đã hủy' " +
            "WHERE MaPhieuDatPhong=? AND TrangThai='Đã đặt'";

        String sqlPhong =
            "UPDATE Phong SET TinhTrang='Trống' WHERE MaPhong=?";

        ChiTietDatPhongDAO ctDao = new ChiTietDatPhongDAO();

        try (Connection conn = TestConnection.getJDBCConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlPhieu)) {
                ps.setInt(1, maPhieu);
                if (ps.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
            }

            for (int maPhong : ctDao.getMaPhongByMaPhieu(maPhieu)) {
                try (PreparedStatement ps = conn.prepareStatement(sqlPhong)) {
                    ps.setInt(1, maPhong);
                    ps.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<PhieuDatPhong> findByCCCDAsObject(String cccd) {
        List<PhieuDatPhong> list = new ArrayList<>();
        String sql = "SELECT p.* FROM PHIEUDATPHONG p " +
                     "JOIN KHACHHANG kh ON p.MaKhachHang = kh.MaKhachHang " +
                     "WHERE kh.CCCD=? ORDER BY p.MaPhieuDatPhong ASC";
        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cccd);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Date dDen = rs.getDate("NgayDen");
                Date dDi  = rs.getDate("NgayDi");
                list.add(new PhieuDatPhong(
                        rs.getInt("MaPhieuDatPhong"),
                        rs.getInt("MaKhachHang"),
                        rs.getString("TrangThai"),
                        (dDen != null) ? dDen.toLocalDate() : null,
                        (dDi != null) ? dDi.toLocalDate() : null
                ));
            }

        } catch(Exception e){ e.printStackTrace(); }
        return list;
    }

}
