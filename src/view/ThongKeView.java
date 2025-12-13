package view;

import service.ThongKeService;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;

public class ThongKeView extends JFrame {

    private JComboBox<Integer> cbTuNgay, cbTuThang, cbTuNam;
    private JComboBox<Integer> cbDenNgay, cbDenThang, cbDenNam;
    private JLabel lblKetQua;

    private ThongKeService service = new ThongKeService();

    public ThongKeView() {
        setTitle("Thống kê doanh thu");
        setSize(500, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlTop = new JPanel(new GridLayout(2, 1));

        pnlTop.add(createTuNgayPanel());
        pnlTop.add(createDenNgayPanel());

        JButton btnThongKe = new JButton("Thống kê");
        btnThongKe.addActionListener(e -> thongKe());

        lblKetQua = new JLabel("Doanh thu: 0 VND", SwingConstants.CENTER);
        lblKetQua.setFont(new Font("Arial", Font.BOLD, 16));

        add(pnlTop, BorderLayout.NORTH);
        add(btnThongKe, BorderLayout.CENTER);
        add(lblKetQua, BorderLayout.SOUTH);
    }

    private JPanel createTuNgayPanel() {
        JPanel p = new JPanel();
        p.add(new JLabel("Từ ngày:"));

        cbTuNgay = new JComboBox<>(days());
        cbTuThang = new JComboBox<>(months());
        cbTuNam = new JComboBox<>(years());

        p.add(cbTuNgay);
        p.add(cbTuThang);
        p.add(cbTuNam);

        return p;
    }

    private JPanel createDenNgayPanel() {
        JPanel p = new JPanel();
        p.add(new JLabel("Đến ngày:"));

        cbDenNgay = new JComboBox<>(days());
        cbDenThang = new JComboBox<>(months());
        cbDenNam = new JComboBox<>(years());

        p.add(cbDenNgay);
        p.add(cbDenThang);
        p.add(cbDenNam);

        return p;
    }

    private void thongKe() {
        try {
            LocalDate tu = LocalDate.of(
                    (int) cbTuNam.getSelectedItem(),
                    (int) cbTuThang.getSelectedItem(),
                    (int) cbTuNgay.getSelectedItem()
            );

            LocalDate den = LocalDate.of(
                    (int) cbDenNam.getSelectedItem(),
                    (int) cbDenThang.getSelectedItem(),
                    (int) cbDenNgay.getSelectedItem()
            );

            double doanhThu = service.tinhDoanhThu(
                    Date.valueOf(tu),
                    Date.valueOf(den)
            );

            lblKetQua.setText("Doanh thu: " +
                    String.format("%,.0f", doanhThu) + " VND");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private Integer[] days() {
        Integer[] d = new Integer[31];
        for (int i = 0; i < 31; i++) d[i] = i + 1;
        return d;
    }

    private Integer[] months() {
        Integer[] m = new Integer[12];
        for (int i = 0; i < 12; i++) m[i] = i + 1;
        return m;
    }

    private Integer[] years() {
        Integer[] y = new Integer[10];
        int start = LocalDate.now().getYear() - 5;
        for (int i = 0; i < 10; i++) y[i] = start + i;
        return y;
    }

    
}
