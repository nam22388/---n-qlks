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
    private int maCTDP;
    private int soNgay;
    private int maPhong;
    private int maPhieuDatPhong;
    private double giaDat;

    public ChiTietDatPhong() {}

    public ChiTietDatPhong(int maCTDP, int soNgay, int maPhong, int maPhieuDatPhong, double giaDat) {
        this.maCTDP = maCTDP;
        this.soNgay = soNgay;
        this.maPhong = maPhong;
        this.maPhieuDatPhong = maPhieuDatPhong;
        this.giaDat = giaDat;
    }

    public int getMaCTDP() { return maCTDP; }
    public void setMaCTDP(int maCTDP) { this.maCTDP = maCTDP; }

    public int getSoNgay() { return soNgay; }
    public void setSoNgay(int soNgay) { this.soNgay = soNgay; }

    public int getMaPhong() { return maPhong; }
    public void setMaPhong(int maPhong) { this.maPhong = maPhong; }

    public int getMaPhieuDatPhong() { return maPhieuDatPhong; }
    public void setMaPhieuDatPhong(int maPhieuDatPhong) { this.maPhieuDatPhong = maPhieuDatPhong; }

    public double getGiaDat() { return giaDat; }
    public void setGiaDat(double giaDat) { this.giaDat = giaDat; }
}
