/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import dao.DBConnection;
/**
 * Một file Java duy nhất chứa một ứng dụng Swing đơn giản cho quản lý khách sạn.
 * Save as HotelManagementApp.java, compile (javac) và chạy (java) để mở giao diện.
 * Thiết kế để dễ đọc, có các panel: Phòng, Khách hàng, Đặt phòng, Dịch vụ, Hóa đơn.
 */
public class HotelManagementApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

// ---------- Main Frame (UI) ----------
class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Hotel Management - Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Phòng", new PhongPanel());
        tabs.addTab("Khách hàng", new KhachHangPanel());
        tabs.addTab("Đặt phòng", new DatPhongPanel());
        tabs.addTab("Dịch vụ", new DichVuPanel());
        tabs.addTab("Hóa đơn", new HoaDonPanel());

        add(tabs, BorderLayout.CENTER);
    }
}

// ---------- Simple in-memory 'DB' storage ----------
class InMemoryDB {
    static List<Phong> phongList = new ArrayList<>();
    static List<KhachHang> khList = new ArrayList<>();
    static int phongId = 1;
    static int khId = 1;

    static {
        // some sample data
        phongList.add(new Phong(nextPhongId(), "101", "Đơn", 200_000, "Trống"));
        phongList.add(new Phong(nextPhongId(), "102", "Đôi", 350_000, "Có khách"));
        phongList.add(new Phong(nextPhongId(), "201", "Suite", 800_000, "Trống"));

        khList.add(new KhachHang(nextKhId(), "Nguyễn Văn A", "0123456789", "123456789", "a@example.com"));
        khList.add(new KhachHang(nextKhId(), "Trần Thị B", "0987654321", "987654321", "b@example.com"));
    }

    static int nextPhongId() { return phongId++; }
    static int nextKhId() { return khId++; }
}

// ---------- Models ----------
class Phong {
    int id;
    String soPhong;
    String loai;
    double gia;
    String trangThai;

    Phong(int id, String soPhong, String loai, double gia, String trangThai) {
        this.id = id; this.soPhong = soPhong; this.loai = loai; this.gia = gia; this.trangThai = trangThai;
    }
}

class KhachHang {
    int id;
    String ten;
    String phone;
    String cmnd;
    String email;

    KhachHang(int id, String ten, String phone, String cmnd, String email) {
        this.id = id; this.ten = ten; this.phone = phone; this.cmnd = cmnd; this.email = email;
    }
}

// ---------- Panels ----------
abstract class BasePanel extends JPanel {
    BasePanel() {
        setLayout(new BorderLayout(10,10));
        setBorder(new EmptyBorder(10,10,10,10));
    }
}

class PhongPanel extends BasePanel {
    DefaultTableModel model;
    JTable table;

    PhongPanel() {
        model = new DefaultTableModel(new Object[]{"ID","Số phòng","Loại","Giá","Trạng thái"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Thêm phòng");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");

        btnAdd.addActionListener(e -> onAdd());
        btnEdit.addActionListener(e -> onEdit());
        btnDelete.addActionListener(e -> onDelete());

        buttons.add(btnAdd); buttons.add(btnEdit); buttons.add(btnDelete);
        add(buttons, BorderLayout.SOUTH);
    }

    void refreshTable() {
        model.setRowCount(0);
        for (Phong p : InMemoryDB.phongList) {
            model.addRow(new Object[]{p.id, p.soPhong, p.loai, String.format("%,.0f", p.gia), p.trangThai});
        }
    }

    void onAdd() {
        PhongForm f = new PhongForm(null);
        int r = JOptionPane.showConfirmDialog(this, f, "Thêm phòng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r == JOptionPane.OK_OPTION) {
            Phong p = new Phong(InMemoryDB.nextPhongId(), f.tfSoPhong.getText(), f.cbLoai.getSelectedItem().toString(),
                    Double.parseDouble(f.tfGia.getText()), f.cbTrangThai.getSelectedItem().toString());
            InMemoryDB.phongList.add(p);
            refreshTable();
        }
    }

    void onEdit() {
        int sel = table.getSelectedRow();
        if (sel == -1) { JOptionPane.showMessageDialog(this, "Chọn 1 phòng để sửa."); return; }
        int id = (int) model.getValueAt(sel,0);
        Phong p = InMemoryDB.phongList.stream().filter(x->x.id==id).findFirst().orElse(null);
        if (p==null) return;
        PhongForm f = new PhongForm(p);
        int r = JOptionPane.showConfirmDialog(this, f, "Sửa phòng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r == JOptionPane.OK_OPTION) {
            p.soPhong = f.tfSoPhong.getText();
            p.loai = f.cbLoai.getSelectedItem().toString();
            p.gia = Double.parseDouble(f.tfGia.getText());
            p.trangThai = f.cbTrangThai.getSelectedItem().toString();
            refreshTable();
        }
    }

    void onDelete() {
        int sel = table.getSelectedRow();
        if (sel == -1) { JOptionPane.showMessageDialog(this, "Chọn 1 phòng để xóa."); return; }
        int id = (int) model.getValueAt(sel,0);
        int r = JOptionPane.showConfirmDialog(this, "Xóa phòng ID="+id+"?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (r==JOptionPane.YES_OPTION) {
            InMemoryDB.phongList.removeIf(x->x.id==id);
            refreshTable();
        }
    }

    // small form panel used in dialogs
    static class PhongForm extends JPanel {
        JTextField tfSoPhong = new JTextField(10);
        JComboBox<String> cbLoai = new JComboBox<>(new String[]{"Đơn","Đôi","Suite"});
        JTextField tfGia = new JTextField(10);
        JComboBox<String> cbTrangThai = new JComboBox<>(new String[]{"Trống","Có khách","Đang dọn"});

        PhongForm(Phong p) {
            setLayout(new GridLayout(4,2,6,6));
            add(new JLabel("Số phòng:")); add(tfSoPhong);
            add(new JLabel("Loại:")); add(cbLoai);
            add(new JLabel("Giá (VNĐ):")); add(tfGia);
            add(new JLabel("Trạng thái:")); add(cbTrangThai);
            if (p!=null) {
                tfSoPhong.setText(p.soPhong);
                cbLoai.setSelectedItem(p.loai);
                tfGia.setText(String.valueOf((long)p.gia));
                cbTrangThai.setSelectedItem(p.trangThai);
            } else {
                tfGia.setText("0");
            }
        }
    }
}

class KhachHangPanel extends BasePanel {
    DefaultTableModel model;
    JTable table;

    KhachHangPanel() {
        model = new DefaultTableModel(new Object[]{"ID","Họ tên","SĐT","CMND","Email"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Thêm KH");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");

        btnAdd.addActionListener(e -> onAdd());
        btnEdit.addActionListener(e -> onEdit());
        btnDelete.addActionListener(e -> onDelete());

        buttons.add(btnAdd); buttons.add(btnEdit); buttons.add(btnDelete);
        add(buttons, BorderLayout.SOUTH);
    }

    void refreshTable() {
        model.setRowCount(0);
        for (KhachHang k : InMemoryDB.khList) {
            model.addRow(new Object[]{k.id, k.ten, k.phone, k.cmnd, k.email});
        }
    }

    void onAdd() {
        KhForm f = new KhForm(null);
        int r = JOptionPane.showConfirmDialog(this, f, "Thêm khách hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r==JOptionPane.OK_OPTION) {
            KhachHang k = new KhachHang(InMemoryDB.nextKhId(), f.tfTen.getText(), f.tfPhone.getText(), f.tfCmnd.getText(), f.tfEmail.getText());
            InMemoryDB.khList.add(k);
            refreshTable();
        }
    }

    void onEdit() {
        int sel = table.getSelectedRow();
        if (sel==-1) { JOptionPane.showMessageDialog(this, "Chọn 1 khách hàng để sửa."); return; }
        int id = (int) model.getValueAt(sel,0);
        KhachHang k = InMemoryDB.khList.stream().filter(x->x.id==id).findFirst().orElse(null);
        if (k==null) return;
        KhForm f = new KhForm(k);
        int r = JOptionPane.showConfirmDialog(this, f, "Sửa khách hàng", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r==JOptionPane.OK_OPTION) {
            k.ten = f.tfTen.getText(); k.phone = f.tfPhone.getText(); k.cmnd = f.tfCmnd.getText(); k.email = f.tfEmail.getText();
            refreshTable();
        }
    }

    void onDelete() {
        int sel = table.getSelectedRow();
        if (sel==-1) { JOptionPane.showMessageDialog(this, "Chọn 1 khách hàng để xóa."); return; }
        int id = (int) model.getValueAt(sel,0);
        int r = JOptionPane.showConfirmDialog(this, "Xóa khách hàng ID="+id+"?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (r==JOptionPane.YES_OPTION) {
            InMemoryDB.khList.removeIf(x->x.id==id);
            refreshTable();
        }
    }

    static class KhForm extends JPanel {
        JTextField tfTen = new JTextField(20);
        JTextField tfPhone = new JTextField(12);
        JTextField tfCmnd = new JTextField(20);
        JTextField tfEmail = new JTextField(20);

        KhForm(KhachHang k) {
            setLayout(new GridLayout(4,2,6,6));
            add(new JLabel("Họ tên:")); add(tfTen);
            add(new JLabel("SĐT:")); add(tfPhone);
            add(new JLabel("CMND/CCCD:")); add(tfCmnd);
            add(new JLabel("Email:")); add(tfEmail);
            if (k!=null) {
                tfTen.setText(k.ten); tfPhone.setText(k.phone); tfCmnd.setText(k.cmnd); tfEmail.setText(k.email);
            }
        }
    }
}

class DatPhongPanel extends BasePanel {
    public DatPhongPanel() {
        JPanel p = new JPanel(new BorderLayout(8,8));
        p.add(new JLabel("Danh sách đặt phòng (demo)"), BorderLayout.NORTH);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText("Chức năng đặt phòng sẽ kết hợp Phòng và Khách hàng.\n(Đây là demo để bạn phát triển tiếp.)");
        p.add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNewBooking = new JButton("Tạo đặt phòng mới");
        btnNewBooking.addActionListener(e -> JOptionPane.showMessageDialog(this, "Xây dựng logic đặt phòng ở bước phát triển tiếp theo."));
        bottom.add(btnNewBooking);
        p.add(bottom, BorderLayout.SOUTH);

        add(p, BorderLayout.CENTER);
    }
}

class DichVuPanel extends BasePanel {
    public DichVuPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("Quản lý dịch vụ (demo)"), BorderLayout.NORTH);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText("Ví dụ: Dọn phòng, Ăn sáng, Giặt ủi.\nBạn có thể thêm bảng tương tự Phòng/Khách hàng để quản lý dịch vụ.");
        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}

class HoaDonPanel extends BasePanel {
    public HoaDonPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("Hóa đơn (demo)"), BorderLayout.NORTH);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText("Hóa đơn sẽ tính từ thông tin đặt phòng + dịch vụ.\nỞ demo này chỉ hiển thị ví dụ.");
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnGen = new JButton("Tạo hóa đơn mẫu");
        btnGen.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tạo hóa đơn mẫu...\n(Phát triển thêm theo yêu cầu)"));
        btns.add(btnGen);
        add(btns, BorderLayout.SOUTH);
    }
}



