package view;

import model.HoaDon;
import service.HoaDonService;

import javax.swing.*;
import java.awt.*;

public class HoaDonView extends JFrame {

    private int maPhieuDatPhong;
    private HoaDonService service;

    private JLabel lblMaHD, lblMaPhieu, lblNgayTao, lblTienPhong, lblTienDV, lblTongCong, lblTenKH;
    private JButton btnClose;

    public HoaDonView(int maPhieuDatPhong) {
        this.maPhieuDatPhong = maPhieuDatPhong;
        this.service = new HoaDonService();

        initUI();
        loadData();
    }

    private void initUI() {
        setTitle("Hóa Đơn - Phiếu #" + maPhieuDatPhong);
        setSize(450, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(9, 1, 5, 5));

        lblMaHD     = new JLabel();
        lblMaPhieu  = new JLabel();
        lblNgayTao  = new JLabel();
        lblTienPhong = new JLabel();
        lblTienDV   = new JLabel();
        lblTongCong = new JLabel();
        lblTenKH    = new JLabel();

        btnClose = new JButton("Đóng");

        add(new JLabel("Mã hóa đơn:"));
        add(lblMaHD);
        add(new JLabel("Mã phiếu đặt phòng:"));
        add(lblMaPhieu);
        add(new JLabel("Tên khách hàng:"));
        add(lblTenKH);
        add(new JLabel("Ngày tạo:"));
        add(lblNgayTao);
        add(new JLabel("Tiền phòng:"));
        add(lblTienPhong);
        add(new JLabel("Tiền dịch vụ:"));
        add(lblTienDV);
        add(new JLabel("Tổng cộng:"));
        add(lblTongCong);
        add(btnClose);

        btnClose.addActionListener(e -> dispose());
    }

    private void loadData() {
        HoaDon hd = service.getByMaPhieuDatPhong(maPhieuDatPhong);

        if (hd == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lblMaHD.setText(String.valueOf(hd.getMaHoaDon()));
        lblMaPhieu.setText(String.valueOf(hd.getMaPhieuDatPhong()));
        lblNgayTao.setText(hd.getNgayTao() != null ? hd.getNgayTao().toString() : "");
        lblTienPhong.setText(hd.getTongTienPhong() + " VND");
        lblTienDV.setText(hd.getTongTienDichVu() + " VND");
        lblTongCong.setText(hd.getTongCong() + " VND");

        // ⭐ LẤY TÊN KHÁCH HÀNG TỪ DAO, KHÔNG ĐỔ VÀO MODEL
        lblTenKH.setText(getTenKhachHangFromDAO(maPhieuDatPhong));
    }

    // Lấy tên khách hàng trực tiếp từ DAO (KHÔNG đụng model)
    private String getTenKhachHangFromDAO(int maPhieu) {
        try (var conn = dao.TestConnection.getJDBCConnection()) {
            String sql = """
                SELECT kh.HoTen 
                FROM HOADON hd
                JOIN KhachHang kh ON hd.MaKhachHang = kh.MaKhachHang
                WHERE hd.MaPhieuDatPhong = ?
            """;

            var ps = conn.prepareStatement(sql);
            ps.setInt(1, maPhieu);

            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("HoTen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Không xác định";
    }

    
}
