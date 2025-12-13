package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import service.KhachHangService;
import model.KhachHang;

public class KhachHangView extends JFrame {
    private JTable table;
    private JTextField txtSearch;
    private JButton btnSua, btnTrangChu;
    private KhachHangService khService;

    public KhachHangView() {
        khService = new KhachHangService();

        setTitle("Danh sách khách hàng");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // TOP search bar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Tìm theo CCCD:"));
        txtSearch = new JTextField(20);
        topPanel.add(txtSearch);

        JButton btnTim = new JButton("Tìm kiếm");
        topPanel.add(btnTim);

        add(topPanel, BorderLayout.NORTH);

        // TABLE
        String[] columns = {
                "Mã KH", "Họ tên", "CCCD", "Số điện thoại", "Địa chỉ", "Email"
        };

        table = new JTable(new DefaultTableModel(columns, 0));
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // BOTTOM panel với 2 nút
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSua = new JButton("Sửa thông tin khách hàng");
        btnTrangChu = new JButton("Trang Chủ");
        bottomPanel.add(btnSua);
        bottomPanel.add(btnTrangChu);
        add(bottomPanel, BorderLayout.SOUTH);

        // LOAD dữ liệu mặc định
        loadData();

        // EVENT tìm kiếm
        btnTim.addActionListener(e -> search());

        // EVENT quay về trang chủ
        btnTrangChu.addActionListener(e -> {
            new MainView().setVisible(true); // mở trang chủ
            this.dispose(); // đóng KhachHangView
        });
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (KhachHang kh : khService.getAllKhachHang()) {
            model.addRow(new Object[]{
                    kh.getMaKhachHang(),
                    kh.getHoTen(),
                    kh.getCccd(),
                    kh.getSoDienThoai(),
                    kh.getDiaChi(),
                    kh.getEmail()
            });
        }
    }

    private void search() {
        String cccd = txtSearch.getText().trim();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (cccd.isEmpty()) {
            loadData();
            return;
        }

        KhachHang kh = khService.findByCCCD(cccd);
        if (kh != null) {
            model.addRow(new Object[]{
                    kh.getMaKhachHang(),
                    kh.getHoTen(),
                    kh.getCccd(),
                    kh.getSoDienThoai(),
                    kh.getDiaChi(),
                    kh.getEmail()
            });
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!");
        }
    }

    
}
