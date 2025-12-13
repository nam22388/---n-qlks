package view;

import model.KhachHang;
import model.PhieuDatPhong;
import model.Phong;
import service.ChiTietDatPhongService;
import service.PhongService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DatPhongForm2 extends JFrame {

    private KhachHang kh;
    private PhieuDatPhong pdp;

    private JComboBox<String> cboLoaiPhong;
    private JComboBox<Phong> cboPhongTrong;
    private JTextField txtGia;
    private JButton btnThem, btnXoa, btnXacNhan;

    private JTable table;
    private DefaultTableModel model;

    private List<Phong> phongDaChon = new ArrayList<>();

    private PhongService phongService = new PhongService();
    private ChiTietDatPhongService ctService = new ChiTietDatPhongService();

    public DatPhongForm2(KhachHang kh, PhieuDatPhong pdp) {
        this.kh = kh;
        this.pdp = pdp;

        setTitle("Đặt phòng - Chọn phòng");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        loadPhongTrong();
    }

    private void initUI() {
        // ===== TOP =====
        JPanel top = new JPanel();

        cboLoaiPhong = new JComboBox<>(new String[]{"Đơn", "Đôi", "VIP"});
        cboPhongTrong = new JComboBox<>();
        txtGia = new JTextField(8);
        txtGia.setEditable(false);

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnXacNhan = new JButton("Xác nhận đặt phòng");

        top.add(new JLabel("Loại phòng:"));
        top.add(cboLoaiPhong);
        top.add(new JLabel("Phòng trống:"));
        top.add(cboPhongTrong);
        top.add(new JLabel("Giá:"));
        top.add(txtGia);
        top.add(btnThem);
        top.add(btnXoa);
        top.add(btnXacNhan);

        add(top, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{"Mã phòng", "Loại", "Giá"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== EVENTS =====
        cboLoaiPhong.addActionListener(e -> loadPhongTrong());
        cboPhongTrong.addActionListener(e -> showGiaPhong());

        btnThem.addActionListener(e -> themPhong());
        btnXoa.addActionListener(e -> xoaPhong());
        btnXacNhan.addActionListener(e -> xacNhanDatPhong());
    }

    private void loadPhongTrong() {
        cboPhongTrong.removeAllItems();
        String loai = cboLoaiPhong.getSelectedItem().toString();

        for (Phong p : phongService.getPhongTrong(loai)) {
            cboPhongTrong.addItem(p);
        }
        showGiaPhong();
    }

    private void showGiaPhong() {
        Phong p = (Phong) cboPhongTrong.getSelectedItem();
        txtGia.setText(p != null ? String.valueOf(p.getGiaPhong()) : "");
    }

    private void themPhong() {
        Phong p = (Phong) cboPhongTrong.getSelectedItem();
        if (p == null) return;

        if (phongDaChon.contains(p)) {
            JOptionPane.showMessageDialog(this, "Phòng đã được chọn!");
            return;
        }

        phongDaChon.add(p);
        model.addRow(new Object[]{p.getMaPhong(), p.getLoaiPhong(), p.getGiaPhong()});
    }

    private void xoaPhong() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        phongDaChon.remove(row);
        model.removeRow(row);
    }

    // =================== CORE ===================
    private void xacNhanDatPhong() {
        if (phongDaChon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa chọn phòng!");
            return;
        }

        for (Phong p : phongDaChon) {
            // 1️⃣ Insert chi tiết đặt phòng (TRIGGER xử lý)
            boolean ok = ctService.addChiTiet(
                    pdp.getMaPhieuDatPhong(),
                    p.getMaPhong()
            );

            if (!ok) {
                JOptionPane.showMessageDialog(this,
                        "Lỗi tạo chi tiết phòng: " + p.getMaPhong());
                return;
            }

            // 2️⃣ Update tình trạng phòng
            phongService.updateTinhTrang(p.getMaPhong(), "Đã đặt");
        }

        JOptionPane.showMessageDialog(this, "Đặt phòng thành công!");
        dispose();
    }
}
