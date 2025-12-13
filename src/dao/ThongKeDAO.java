package dao;

import java.sql.*;

public class ThongKeDAO {

    public double getDoanhThu(Date tuNgay, Date denNgay) {
        String sql = """
            SELECT SUM(TongCong) AS DoanhThu
            FROM HoaDon
            WHERE NgayTao BETWEEN ? AND ?
        """;

        try (Connection conn = TestConnection.getJDBCConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, tuNgay);
            ps.setDate(2, denNgay);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("DoanhThu");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
