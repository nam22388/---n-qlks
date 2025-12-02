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
    private int maDichVu;
    private int maCTDP;
    private int soLuong;
    private double thanhTien;

    public ChiTietDichVu() {}

    public ChiTietDichVu(int maDichVu, int maCTDP, int soLuong, double thanhTien) {
        this.maDichVu = maDichVu;
        this.maCTDP = maCTDP;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public int getMaDichVu() { return maDichVu; }
    public void setMaDichVu(int maDichVu) { this.maDichVu = maDichVu; }

    public int getMaCTDP() { return maCTDP; }
    public void setMaCTDP(int maCTDP) { this.maCTDP = maCTDP; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
}
