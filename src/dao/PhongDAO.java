package dao;

import model.Phong;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongDAO {

    // Lấy toàn bộ danh sách phòng
    public List<Phong> getAll() {
        List<Phong> list = new ArrayList<>();
        String sql = "SELECT * FROM PHONG";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Phong p = new Phong(
                        rs.getInt("MaPhong"),
                        rs.getString("LoaiPhong"),
                        rs.getDouble("GiaPhong"),
                        rs.getString("TinhTrang")
                );
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm phòng
    public boolean insert(Phong p) {
        String sql = "INSERT INTO PHONG (MaPhong, LoaiPhong, GiaPhong, TinhTrang) VALUES (?, ?, ?, ?)";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getMaPhong());
            ps.setString(2, p.getLoaiPhong());
            ps.setDouble(3, p.getGiaPhong());
            ps.setString(4, p.getTinhTrang());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật phòng
    public boolean update(Phong p) {
        String sql = "UPDATE PHONG SET LoaiPhong=?, GiaPhong=?, TinhTrang=? WHERE MaPhong=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getLoaiPhong());
            ps.setDouble(2, p.getGiaPhong());
            ps.setString(3, p.getTinhTrang());
            ps.setInt(4, p.getMaPhong());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa phòng
    public boolean delete(int maPhong) {
        String sql = "DELETE FROM PHONG WHERE MaPhong=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhong);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm phòng theo mã
public Phong findById(int maPhong) {
    String sql = "SELECT * FROM PHONG WHERE MaPhong=?";

    try (Connection conn = TestConnection.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, maPhong);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Phong(
                    rs.getInt("MaPhong"),
                    rs.getString("LoaiPhong"),
                    rs.getDouble("GiaPhong"),
                    rs.getString("TinhTrang")
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

    
    // Tìm phòng trống
    public List<Phong> findPhongTrong() {
    List<Phong> list = new ArrayList<>();
    String sql = "SELECT * FROM PHONG WHERE TinhTrang = 'Trống'";

    try (Connection conn = TestConnection.getJDBCConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Phong p = new Phong(
                    rs.getInt("MaPhong"),
                    rs.getString("LoaiPhong"),
                    rs.getDouble("GiaPhong"),
                    rs.getString("TinhTrang")
            );
            list.add(p);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


    // Cập nhật tình trạng phòng
    public boolean updateTinhTrang(int maPhong, String tinhTrang) {
        String sql = "UPDATE PHONG SET TinhTrang=? WHERE MaPhong=?";

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tinhTrang);
            ps.setInt(2, maPhong);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
