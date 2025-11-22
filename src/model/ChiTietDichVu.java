/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LEGION
 */
public class ChiTietDichVu {
    private String maDichVu;
    private String maCTDP;
    private int soLuong;
    private double thanhTien;

    public ChiTietDichVu() {}

    public ChiTietDichVu(String maDichVu, String maCTDP, int soLuong, double thanhTien) {
        this.maDichVu = maDichVu;
        this.maCTDP = maCTDP;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public String getMaDichVu() { return maDichVu; }
    public void setMaDichVu(String maDichVu) { this.maDichVu = maDichVu; }

    public String getMaCTDP() { return maCTDP; }
    public void setMaCTDP(String maCTDP) { this.maCTDP = maCTDP; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
}
