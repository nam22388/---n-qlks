package view;

import model.ChiTietDichVu;
import model.DichVu;
import service.ChiTietDichVuService;
import service.DichVuService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChiTietDichVuView extends JFrame {

    private int maCTDP;

    private JTable tblDichVu;
    private JTable tblCTDV;

    private JTextField txtSoLuong;

    private ChiTietDichVuService ctService;
    private DichVuService dvService;

    public ChiTietDichVuView(int maCTDP) {
        this.maCTDP = maCTDP;

        ctService = new ChiTietDichVuService();
        dvService = new DichVuService();

        initUI();
        loadDichVuTable();
        loadCTDV();
    }

    private void initUI() {
        setTitle("Chi tiết dịch vụ - Mã CTDP: " + maCTDP);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ======== TOP INPUT PANEL ========
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTop.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField(10);
        pnlTop.add(txtSoLuong);

        JButton btnAdd = new JButton("Thêm dịch vụ");
        pnlTop.add(btnAdd);

        add(pnlTop, BorderLayout.NORTH);

        // ======== CENTER: 2 TABLES ========
        JPanel pnlCenter = new JPanel(new GridLayout(1, 2));

        // Table dịch vụ
        tblDichVu = new JTable();
        tblDichVu.setModel(new DefaultTableModel(
                new Object[]{"Mã DV", "Tên dịch vụ", "Giá"}, 0
        ));
        pnlCenter.add(new JScrollPane(tblDichVu));

        // Table chi tiết dịch vụ
        tblCTDV = new JTable();
        tblCTDV.setModel(new DefaultTableModel(
                new Object[]{"Mã DV", "Tên DV", "Số lượng", "Thành tiền"}, 0
        ));
        pnlCenter.add(new JScrollPane(tblCTDV));

        add(pnlCenter, BorderLayout.CENTER);

        // ======== BOTTOM ========
        JPanel pnlBottom = new JPanel();
        JButton btnDelete = new JButton("Xóa dịch vụ");
        pnlBottom.add(btnDelete);
        add(pnlBottom, BorderLayout.SOUTH);

        // EVENTS
        btnAdd.addActionListener(e -> addDichVu());
        btnDelete.addActionListener(e -> deleteCTDV());
    }

    // Load bảng dịch vụ
    private void loadDichVuTable() {
        List<DichVu> list = dvService.getAll();
        DefaultTableModel model = (DefaultTableModel) tblDichVu.getModel();
        model.setRowCount(0);

        for (DichVu dv : list) {
            model.addRow(new Object[]{
                    dv.getMaDichVu(),
                    dv.getTenDichVu(),
                    dv.getGiaDichVu()
            });
        }
    }

    // Load bảng chi tiết dịch vụ
    private void loadCTDV() {
        List<Object[]> list = ctService.getByMaCTDP(maCTDP);

        DefaultTableModel model = (DefaultTableModel) tblCTDV.getModel();
        model.setRowCount(0);

        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    // Thêm dịch vụ
    private void addDichVu() {
        try {
            int row = tblDichVu.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Chọn dịch vụ trước!");
                return;
            }

            int maDV = (int) tblDichVu.getValueAt(row, 0);
            double giaDV = (double) tblDichVu.getValueAt(row, 2);

            int sl = Integer.parseInt(txtSoLuong.getText().trim());
            if (sl <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải > 0!");
                return;
            }

            double thanhTien = giaDV * sl;

            ChiTietDichVu ct = new ChiTietDichVu(
                    maDV,
                    maCTDP,
                    sl,
                    thanhTien
            );

            if (ctService.addCTDV(ct)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadCTDV();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu!");
        }
    }

    // Xóa dịch vụ khỏi chi tiết
    private void deleteCTDV() {
        int row = tblCTDV.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn dòng cần xóa!");
            return;
        }

        int maDV = (int) tblCTDV.getValueAt(row, 0);

        if (ctService.deleteCTDV(maDV, maCTDP)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loadCTDV();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!");
        }
    }
}
