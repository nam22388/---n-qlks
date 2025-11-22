/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LEGION
 */
public class ChiTietDatPhong {
    private String maCTDP;
    private int soNgay;
    private String maPhong;
    private String maPhieuDatPhong;
    private double giaDat;

    public ChiTietDatPhong() {}

    public ChiTietDatPhong(String maCTDP, int soNgay, String maPhong, String maPhieuDatPhong, double giaDat) {
        this.maCTDP = maCTDP;
        this.soNgay = soNgay;
        this.maPhong = maPhong;
        this.maPhieuDatPhong = maPhieuDatPhong;
        this.giaDat = giaDat;
    }

    public String getMaCTDP() { return maCTDP; }
    public void setMaCTDP(String maCTDP) { this.maCTDP = maCTDP; }

    public int getSoNgay() { return soNgay; }
    public void setSoNgay(int soNgay) { this.soNgay = soNgay; }

    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }

    public String getMaPhieuDatPhong() { return maPhieuDatPhong; }
    public void setMaPhieuDatPhong(String maPhieuDatPhong) { this.maPhieuDatPhong = maPhieuDatPhong; }

    public double getGiaDat() { return giaDat; }
    public void setGiaDat(double giaDat) { this.giaDat = giaDat; }
}
