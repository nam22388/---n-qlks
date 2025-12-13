package view;

import model.DichVu;
import service.DichVuService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class DichVuView extends JFrame {

    private DichVuService service;
    private JTable table;
    private DefaultTableModel model;

    private JTextField txtTenDV, txtGiaDV, txtSearch;

    public DichVuView() {
        service = new DichVuService();

        setTitle("Quản lý dịch vụ");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ======== TABLE =========
        model = new DefaultTableModel(
                new String[]{"Mã DV", "Tên dịch vụ", "Giá dịch vụ"}, 0
        );
        table = new JTable(model);

        loadData();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ========= FORM INPUT =========
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin dịch vụ"));

        txtTenDV = new JTextField();
        txtGiaDV = new JTextField();

        formPanel.add(new JLabel("Tên dịch vụ:"));
        formPanel.add(txtTenDV);
        formPanel.add(new JLabel("Giá dịch vụ:"));
        formPanel.add(txtGiaDV);

        add(formPanel, BorderLayout.NORTH);

        // ========= SEARCH =========
        JPanel searchPanel = new JPanel();
        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Tìm");
        JButton btnReload = new JButton("Làm mới");

        searchPanel.add(new JLabel("Tìm theo tên: "));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnReload);

        add(searchPanel, BorderLayout.SOUTH);

        // ========= BUTTONS =========
        JPanel buttonPanel = new JPanel();

        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);

        add(buttonPanel, BorderLayout.WEST);

        // ========= EVENTS =========

        btnAdd.addActionListener(e -> addDichVu());
        btnDelete.addActionListener(e -> deleteDichVu());

        btnSearch.addActionListener(e -> searchByName());
        btnReload.addActionListener(e -> loadData());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fillFormFromTable();
            }
        });

        setVisible(true);
    }

    // ================= FUNCTIONS ===================

    private void loadData() {
        model.setRowCount(0);
        for (DichVu dv : service.getAll()) {
            model.addRow(new Object[]{
                    dv.getMaDichVu(),
                    dv.getTenDichVu(),
                    dv.getGiaDichVu()
            });
        }
    }

    private void addDichVu() {
        try {
            String ten = txtTenDV.getText().trim();
            double gia = Double.parseDouble(txtGiaDV.getText().trim());

            if (ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được để trống tên dịch vụ!");
                return;
            }

            DichVu dv = new DichVu();
            dv.setTenDichVu(ten);
            dv.setGiaDichVu(gia);

            if (service.addDichVu(dv)) {
                JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập dữ liệu!");
        }
    }

    private void deleteDichVu() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn 1 dòng để xóa!");
            return;
        }

        int maDV = (int) table.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Xóa dịch vụ này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.deleteDichVu(maDV)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }

    private void searchByName() {
        String keyword = txtSearch.getText().trim();
        model.setRowCount(0);

        for (DichVu dv : service.searchByName(keyword)) {
            model.addRow(new Object[]{
                    dv.getMaDichVu(),
                    dv.getTenDichVu(),
                    dv.getGiaDichVu()
            });
        }
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row != -1) {
            txtTenDV.setText(table.getValueAt(row, 1).toString());
            txtGiaDV.setText(table.getValueAt(row, 2).toString());
        }
    }
    
}
