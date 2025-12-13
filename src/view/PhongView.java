package view;

import model.Phong;
import service.PhongService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class PhongView extends JFrame {
    private JTable tablePhong;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnTaiLai, btnTrangChu;

    private JTextField txtMaPhong, txtLoaiPhong, txtGiaPhong;

    private PhongService service;

    public PhongView() {
        service = new PhongService();

        setTitle("Quản lý phòng");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        initUI();
        loadData();
        initEvents();
    }

    private void initUI() {
        // Bảng hiển thị phòng
        model = new DefaultTableModel(new Object[]{"Mã Phòng", "Loại Phòng", "Giá", "Tình Trạng"}, 0);
        tablePhong = new JTable(model);
        JScrollPane sp = new JScrollPane(tablePhong);
        sp.setBounds(20, 20, 450, 400);
        add(sp);

        // Form nhập (không có txtTinhTrang)
        txtMaPhong = new JTextField(); txtMaPhong.setBounds(600, 20, 150, 25); add(txtMaPhong);
        txtLoaiPhong = new JTextField(); txtLoaiPhong.setBounds(600, 60, 150, 25); add(txtLoaiPhong);
        txtGiaPhong = new JTextField(); txtGiaPhong.setBounds(600, 100, 150, 25); add(txtGiaPhong);

        add(new JLabel("Mã phòng:")).setBounds(500, 20, 100, 25);
        add(new JLabel("Loại phòng:")).setBounds(500, 60, 100, 25);
        add(new JLabel("Giá:")).setBounds(500, 100, 100, 25);

        // Các nút thao tác
        btnThem = new JButton("Thêm"); btnThem.setBounds(500, 160, 80, 30); add(btnThem);
        btnSua = new JButton("Sửa"); btnSua.setBounds(590, 160, 80, 30); add(btnSua);
        btnXoa = new JButton("Xóa"); btnXoa.setBounds(680, 160, 80, 30); add(btnXoa);
        btnTaiLai = new JButton("Tải lại"); btnTaiLai.setBounds(590, 200, 80, 30); add(btnTaiLai);
        btnTrangChu = new JButton("Trang Chủ"); btnTrangChu.setBounds(500, 240, 120, 30); add(btnTrangChu);
    }

    private void initEvents() {
        // Click vào bảng -> điền dữ liệu
        tablePhong.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tablePhong.getSelectedRow();
                if (row != -1) {
                    txtMaPhong.setText(model.getValueAt(row, 0).toString());
                    txtLoaiPhong.setText(model.getValueAt(row, 1).toString());
                    txtGiaPhong.setText(model.getValueAt(row, 2).toString());
                }
            }
        });

        // Thêm phòng
        btnThem.addActionListener(e -> {
            try {
                Phong p = new Phong(
                        Integer.parseInt(txtMaPhong.getText()),
                        txtLoaiPhong.getText(),
                        Double.parseDouble(txtGiaPhong.getText()),
                        "Trống" // Tự gán mặc định
                );
                if (service.addPhong(p)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi nhập dữ liệu!");
            }
        });

        // Sửa phòng
        btnSua.addActionListener(e -> {
            try {
                int maPhong = Integer.parseInt(txtMaPhong.getText());
                Phong existing = service.getAllPhong().stream()
                        .filter(p -> p.getMaPhong() == maPhong)
                        .findFirst().orElse(null);
                if (existing != null) {
                    Phong p = new Phong(
                            maPhong,
                            txtLoaiPhong.getText(),
                            Double.parseDouble(txtGiaPhong.getText()),
                            existing.getTinhTrang() // Giữ nguyên tình trạng
                    );
                    if (service.updatePhong(p)) {
                        JOptionPane.showMessageDialog(null, "Sửa thành công!");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Sửa thất bại!");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi nhập dữ liệu!");
            }
        });

        // Xóa phòng
        btnXoa.addActionListener(e -> {
            try {
                int ma = Integer.parseInt(txtMaPhong.getText());
                if (service.deletePhong(ma)) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi nhập dữ liệu!");
            }
        });

        // Tải lại dữ liệu
        btnTaiLai.addActionListener(e -> loadData());

        // Quay về trang chủ
        btnTrangChu.addActionListener(e -> {
            new MainView().setVisible(true);
            this.dispose();
        });
    }

    private void loadData() {
        model.setRowCount(0);
        List<Phong> list = service.getAllPhong();
        for (Phong p : list) {
            model.addRow(new Object[]{
                    p.getMaPhong(),
                    p.getLoaiPhong(),
                    p.getGiaPhong(),
                    p.getTinhTrang()
            });
        }
    }

    
}
