package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        setTitle("Trang chủ - Quản lý khách sạn");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 3 hàng x 2 cột
        setLayout(new GridLayout(3, 2, 20, 20));

        JButton btnPhong = new JButton("Quản lý Phòng");
        JButton btnKhachHang = new JButton("Quản lý Khách hàng");
        JButton btnDatPhong = new JButton("Đặt phòng");
        JButton btnPhieuDat = new JButton("Phiếu đặt phòng");
        JButton btnDichVu = new JButton("Quản lý Dịch vụ");
        JButton btnThongKe = new JButton("Thống kê - Báo cáo");

        // ===== SỰ KIỆN =====
        btnPhong.addActionListener(e -> new PhongView().setVisible(true));
        btnKhachHang.addActionListener(e -> new KhachHangView().setVisible(true));
        btnDatPhong.addActionListener(e -> new DatPhongForm1().setVisible(true));
        btnPhieuDat.addActionListener(e -> new PhieuDatPhongView().setVisible(true));
        btnDichVu.addActionListener(e -> new DichVuView().setVisible(true));
        btnThongKe.addActionListener(e -> new ThongKeView().setVisible(true));

        // ===== ADD =====
        add(btnPhong);
        add(btnKhachHang);
        add(btnDatPhong);
        add(btnPhieuDat);
        add(btnDichVu);
        add(btnThongKe);
    }

    
}
