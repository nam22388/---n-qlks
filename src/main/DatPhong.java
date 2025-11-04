/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.Date;

public class DatPhong {
    private String maDatPhong;
    private String maPhong;
    private String maKH;
    private Date ngayDen;
    private Date ngayDi;

    public DatPhong(String maDatPhong, String maPhong, String maKH, Date ngayDen, Date ngayDi) {
        this.maDatPhong = maDatPhong;
        this.maPhong = maPhong;
        this.maKH = maKH;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
    }

    public String getMaDatPhong() { return maDatPhong; }
    public String getMaPhong() { return maPhong; }
    public String getMaKH() { return maKH; }
    public Date getNgayDen() { return ngayDen; }
    public Date getNgayDi() { return ngayDi; }

    public void hienThi() {
        System.out.println(maDatPhong + " | " + maPhong + " | " + maKH + " | " + ngayDen + " | " + ngayDi);
    }
}
