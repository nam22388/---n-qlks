/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

public class KhachHang {
    private String maKH;
    private String hoTen;
    private String cccd;
    private String soDT;
    private String diaChi;

    public KhachHang(String maKH, String hoTen, String cccd, String soDT, String diaChi) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.cccd = cccd;
        this.soDT = soDT;
        this.diaChi = diaChi;
    }

    public String getMaKH() { return maKH; }
    public String getHoTen() { return hoTen; }
    public String getCCCD() { return cccd; }
    public String getSoDT() { return soDT; }
    public String getDiaChi() { return diaChi; }

    public void hienThi() {
        System.out.println(maKH + " | " + hoTen + " | " + cccd + " | " + soDT + " | " + diaChi);
    }
}
