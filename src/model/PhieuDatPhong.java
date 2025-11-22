/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LEGION
 */
import java.time.LocalDate;
public class PhieuDatPhong {
    private String maPhieuDatPhong;
    private String maKhachHang;
    private String trangThai; 
    // Đã đặt / Đang thuê / Đã trả / Đã hủy
    private LocalDate ngayDen;
    private LocalDate ngayDi;

    public PhieuDatPhong() {}

    public PhieuDatPhong(String maPhieuDatPhong, String maKhachHang, String trangThai, LocalDate ngayDen, LocalDate ngayDi) {
        this.maPhieuDatPhong = maPhieuDatPhong;
        this.maKhachHang = maKhachHang;
        this.trangThai = trangThai;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
    }

    public String getMaPhieuDatPhong() { return maPhieuDatPhong; }
    public void setMaPhieuDatPhong(String maPhieuDatPhong) { this.maPhieuDatPhong = maPhieuDatPhong; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public LocalDate getNgayDen() { return ngayDen; }
    public void setNgayDen(LocalDate ngayDen) { this.ngayDen = ngayDen; }

    public LocalDate getNgayDi() { return ngayDi; }
    public void setNgayDi(LocalDate ngayDi) { this.ngayDi = ngayDi; }
}
