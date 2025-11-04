/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

public class Phong {
    private String maPhong;
    private String loaiPhong;
    private double giaPhong;
    private String tinhTrang;
    
    public Phong(String maPhong, String loaiPhong, double giaPhong, String tinhTrang) {
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.giaPhong = giaPhong;
        this.tinhTrang = tinhTrang;
    }
    
    public String getMaPhong() { return maPhong; }
    public String getLoaiPhong() { return loaiPhong; }
    public double getGiaPhong() { return giaPhong; }
    public String getTinhTrang() { return tinhTrang; }
    
    public void hienThi() {
        System.out.println(maPhong + " | " + loaiPhong + " | " + giaPhong + " | " + tinhTrang);
    }
}
