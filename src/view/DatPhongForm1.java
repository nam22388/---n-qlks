package view;

import service.KhachHangService;
import service.PhieuDatPhongService;
import model.KhachHang;
import model.PhieuDatPhong;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class DatPhongForm1 extends JFrame {

    private JTextField txtHoTen, txtCCCD, txtSDT, txtDiaChi, txtEmail;
    private JSpinner spNgayDen, spNgayDi;
    private JButton btnNext, btnTrangChu;

    private KhachHangService khService = new KhachHangService();
    private PhieuDatPhongService pdpService = new PhieuDatPhongService();

    public DatPhongForm1() {
        setTitle("Đặt phòng - Thông tin khách hàng");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 5, 5)); // thêm 1 hàng cho nút mới

        txtHoTen = new JTextField();
        txtCCCD = new JTextField();
        txtSDT = new JTextField();
        txtDiaChi = new JTextField();
        txtEmail = new JTextField();

        spNgayDen = new JSpinner(new SpinnerDateModel());
        spNgayDi = new JSpinner(new SpinnerDateModel());

        btnNext = new JButton("Tiếp theo");
        btnTrangChu = new JButton("Trang Chủ");

        add(new JLabel("Họ tên:")); add(txtHoTen);
        add(new JLabel("CCCD:")); add(txtCCCD);
        add(new JLabel("SĐT:")); add(txtSDT);
        add(new JLabel("Địa chỉ:")); add(txtDiaChi);
        add(new JLabel("Email:")); add(txtEmail);
        add(new JLabel("Ngày đến:")); add(spNgayDen);
        add(new JLabel("Ngày đi:")); add(spNgayDi);
        add(btnNext); add(btnTrangChu); // 2 nút cuối cùng

        btnNext.addActionListener(e -> nextPage());

        // Nút quay về trang chủ
        btnTrangChu.addActionListener(e -> {
            new MainView().setVisible(true); // mở trang chủ
            this.dispose(); // đóng form hiện tại
        });
    }

    private void nextPage() {
        // Validate dữ liệu
        String hoTen = txtHoTen.getText().trim();
        String cccd = txtCCCD.getText().trim();
        if (hoTen.isEmpty() || cccd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên và CCCD không được trống");
            return;
        }

        // Lấy LocalDate từ JSpinner
        LocalDate ngayDen = ((java.util.Date) spNgayDen.getValue())
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate ngayDi = ((java.util.Date) spNgayDi.getValue())
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if (ngayDi.isBefore(ngayDen)) {
            JOptionPane.showMessageDialog(this, "Ngày đi không được trước ngày đến");
            return;
        }

        // Lưu khách hàng vào database (nếu chưa có)
        KhachHang kh = khService.findByCCCD(cccd);
        if (kh == null) {
            kh = new KhachHang(0, hoTen, cccd, txtSDT.getText(), txtDiaChi.getText(), txtEmail.getText());
            khService.addKhachHang(kh);
            kh = khService.findByCCCD(cccd);
        }

        // Tạo phiếu đặt phòng tạm thời
        PhieuDatPhong pdp = new PhieuDatPhong(0, kh.getMaKhachHang(), "Đã đặt", ngayDen, ngayDi);
        pdpService.addPhieu(pdp);

        // Lấy phiếu vừa tạo
        pdp = pdpService.getLatestByCCCD(cccd);
        if (pdp == null) {
            JOptionPane.showMessageDialog(this, "Lỗi: không tạo được phiếu!");
            return;
        }

        // Chuyển sang trang chọn phòng
        new DatPhongForm2(kh, pdp).setVisible(true);
        this.dispose();
    }

    
}
