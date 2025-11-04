/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

public class ChiTietDV {
    private String maHD;
    private String maDV;
    private double thanhTien;

    public ChiTietDV(String maHD, String maDV, double thanhTien) {
        this.maHD = maHD;
        this.maDV = maDV;
        this.thanhTien = thanhTien;
    }

    public String getMaHD() { return maHD; }
    public String getMaDV() { return maDV; }
    public double getThanhTien() { return thanhTien; }

    public void hienThi() {
        System.out.println(maHD + " | " + maDV + " | " + thanhTien);
    }
}
