package view;

import service.PhieuDatPhongService;
import model.PhieuDatPhong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PhieuDatPhongView extends JFrame {

    private PhieuDatPhongService service = new PhieuDatPhongService();
    private JTable table;
    private DefaultTableModel model;

    private JTextField txtSearchCCCD;
    private JComboBox<String> cboTrangThai;

    public PhieuDatPhongView() {
        setTitle("Quản lý Phiếu Đặt Phòng");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadTable();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // ==================== TOP PANEL ====================
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtSearchCCCD = new JTextField(20);
        JButton btnSearch = new JButton("Tìm theo CCCD");

        cboTrangThai = new JComboBox<>(new String[]{
                "Tất cả", "Đã đặt", "Đang thuê", "Đã trả", "Đã hủy"
        });

        JButton btnFilter = new JButton("Lọc");

        topPanel.add(new JLabel("CCCD:"));
        topPanel.add(txtSearchCCCD);
        topPanel.add(btnSearch);
        topPanel.add(new JLabel("Trạng thái:"));
        topPanel.add(cboTrangThai);
        topPanel.add(btnFilter);

        add(topPanel, BorderLayout.NORTH);

        // ==================== TABLE ====================
        String[] cols = {
                "Mã phiếu", "Mã KH", "Tên KH", "CCCD",
                "Trạng thái", "Ngày đến", "Ngày đi"
        };

        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        // ==================== BUTTON PANEL ====================
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnNhan = new JButton("Nhận phòng");
        JButton btnTra = new JButton("Trả phòng");
        JButton btnHuy = new JButton("Hủy phiếu");
        JButton btnChiTiet = new JButton("Chi tiết đặt phòng");
        JButton btnHoaDon = new JButton("Xem hóa đơn");
        JButton btnTrangChu = new JButton("Trang Chủ"); // nút mới

        bottom.add(btnNhan);
        bottom.add(btnTra);
        bottom.add(btnHuy);
        bottom.add(btnChiTiet);
        bottom.add(btnHoaDon);
        bottom.add(btnTrangChu); // thêm vào panel

        add(bottom, BorderLayout.SOUTH);

        // ==================== EVENT ====================
        btnSearch.addActionListener(e -> searchByCCCD());
        btnFilter.addActionListener(e -> search());

        btnNhan.addActionListener(e -> updateTrangThai("Đang thuê"));
        btnTra.addActionListener(e -> updateTrangThai("Đã trả"));
        btnHuy.addActionListener(e -> updateTrangThai("Đã hủy"));

        btnChiTiet.addActionListener(e -> openChiTietDatPhong());
        btnHoaDon.addActionListener(e -> openHoaDon());

        // Nút quay về trang chủ
        btnTrangChu.addActionListener(e -> {
            new MainView().setVisible(true);
            this.dispose();
        });
    }

    // ==================== LOAD DỮ LIỆU ====================
    private void loadTable() {
        model.setRowCount(0);
        List<Object[]> list = service.getAllWithKhachHang();

        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    // ==================== TÌM KIẾM ====================
    private void searchByCCCD() {
        String cccd = txtSearchCCCD.getText().trim();
        if (cccd.isEmpty()) {
            loadTable();
            return;
        }

        List<Object[]> list = service.getByCCCD(cccd);
        model.setRowCount(0);

        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    private void search() {
        String cccd = txtSearchCCCD.getText().trim();
        String trangThai = cboTrangThai.getSelectedItem().toString();

        List<Object[]> list = service.searchCCCDAndTrangThai(cccd, trangThai);

        model.setRowCount(0);
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    // ==================== CẬP NHẬT TRẠNG THÁI ====================
    private void updateTrangThai(String trangThai) {
        int row = table.getSelectedRow();
        if (row == -1) {
            showMessage("Chọn 1 dòng trước!");
            return;
        }

        int maPhieu = (int) table.getValueAt(row, 0);
        boolean ok;

        if ("Đã trả".equals(trangThai)) {
            ok = service.traPhong(maPhieu);
        } else if ("Đang thuê".equals(trangThai)) {
            ok = service.nhanPhong(maPhieu);
        } else if ("Đã hủy".equals(trangThai)) {
            ok = service.huyPhieu(maPhieu);
        } else {
            ok = service.updateTrangThai(maPhieu, trangThai);
        }

        if (ok) {
            showMessage("Cập nhật thành công!");
            loadTable();
        } else {
            showMessage("Cập nhật thất bại!");
        }
    }

    private void openChiTietDatPhong() {
        int row = table.getSelectedRow();
        if (row == -1) {
            showMessage("Chọn phiếu trước!");
            return;
        }

        int maPhieu = (int) table.getValueAt(row, 0);
        new ChiTietDatPhongView(maPhieu).setVisible(true);
    }

    private void openHoaDon() {
        int row = table.getSelectedRow();
        if (row == -1) {
            showMessage("Chọn phiếu trước!");
            return;
        }

        int maPhieu = (int) table.getValueAt(row, 0);
        new HoaDonView(maPhieu).setVisible(true);
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    
}
