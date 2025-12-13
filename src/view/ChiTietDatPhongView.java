package view;

import model.ChiTietDatPhong;
import service.ChiTietDatPhongService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChiTietDatPhongView extends JFrame {

    private int maPhieuDatPhong;
    private ChiTietDatPhongService service;

    private JTable table;
    private DefaultTableModel model;

    private JButton btnDatDichVu, btnRefresh;

    public ChiTietDatPhongView(int maPhieuDatPhong) {
        this.maPhieuDatPhong = maPhieuDatPhong;
        this.service = new ChiTietDatPhongService();

        initUI();
        loadData();
    }

    private void initUI() {
        setTitle("Chi tiết đặt phòng - Phiếu #" + maPhieuDatPhong);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // ======= BẢNG =======
        model = new DefaultTableModel(
                new String[]{"Mã CTDP", "Mã Phòng", "Số ngày", "Giá đặt"}, 0
        );
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ======= NÚT =======
        JPanel bottomPanel = new JPanel(new FlowLayout());

        btnDatDichVu = new JButton("Đặt dịch vụ");
        btnRefresh = new JButton("Làm mới");

        bottomPanel.add(btnDatDichVu);
        bottomPanel.add(btnRefresh);

        add(bottomPanel, BorderLayout.SOUTH);

        // ======= SỰ KIỆN =======
        btnRefresh.addActionListener(e -> loadData());

        btnDatDichVu.addActionListener(e -> openChiTietDichVu());

    }

    // Load dữ liệu chi tiết đặt phòng
    private void loadData() {
        model.setRowCount(0);

        List<ChiTietDatPhong> list = service.getByMaPhieu(maPhieuDatPhong);
        for (ChiTietDatPhong ct : list) {
            model.addRow(new Object[]{
                    ct.getMaCTDP(),
                    ct.getMaPhong(),
                    ct.getSoNgay(),
                    ct.getGiaDat()
            });
        }
    }

    private void openChiTietDichVu() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this,
                    "Hãy chọn một chi tiết đặt phòng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maCTDP = (int) table.getValueAt(table.getSelectedRow(), 0);

        // Mở trang đặt dịch vụ
        new ChiTietDichVuView(maCTDP).setVisible(true);
    }
    
}
