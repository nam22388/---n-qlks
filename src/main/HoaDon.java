/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.Date;

public class HoaDon {
    private String maHD;
    private String maDatPhong;
    private Date ngayTao;
    private double tongTienPhong;
    private double tongTienDV;
    private double tongCong;

    public HoaDon(String maHD, String maDatPhong, Date ngayTao,
                  double tongTienPhong, double tongTienDV, double tongCong) {
        this.maHD = maHD;
        this.maDatPhong = maDatPhong;
        this.ngayTao = ngayTao;
        this.tongTienPhong = tongTienPhong;
        this.tongTienDV = tongTienDV;
        this.tongCong = tongCong;
    }

    public String getMaHD() { return maHD; }
    public String getMaDatPhong() { return maDatPhong; }
    public Date getNgayTao() { return ngayTao; }
    public double getTongTienPhong() { return tongTienPhong; }
    public double getTongTienDV() { return tongTienDV; }
    public double getTongCong() { return tongCong; }

    public void hienThi() {
        System.out.println(maHD + " | " + maDatPhong + " | " + ngayTao + " | " +
                           tongTienPhong + " | " + tongTienDV + " | " + tongCong);
    }
}
