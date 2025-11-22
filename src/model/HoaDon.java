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
public class HoaDon {
    private String maHoaDon;
    private String maPhieuDatPhong;
    private LocalDate ngayTao;
    private double tongTienPhong;
    private double tongTienDichVu;
    private double tongCong;
    private String maKhachHang;

    public HoaDon() {}

    public HoaDon(String maHoaDon, String maPhieuDatPhong, LocalDate ngayTao,
                  double tongTienPhong, double tongTienDichVu, double tongCong, String maKhachHang) {
        this.maHoaDon = maHoaDon;
        this.maPhieuDatPhong = maPhieuDatPhong;
        this.ngayTao = ngayTao;
        this.tongTienPhong = tongTienPhong;
        this.tongTienDichVu = tongTienDichVu;
        this.tongCong = tongCong;
        this.maKhachHang = maKhachHang;
    }

    public String getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(String maHoaDon) { this.maHoaDon = maHoaDon; }

    public String getMaPhieuDatPhong() { return maPhieuDatPhong; }
    public void setMaPhieuDatPhong(String maPhieuDatPhong) { this.maPhieuDatPhong = maPhieuDatPhong; }

    public LocalDate getNgayTao() { return ngayTao; }
    public void setNgayTao(LocalDate ngayTao) { this.ngayTao = ngayTao; }

    public double getTongTienPhong() { return tongTienPhong; }
    public void setTongTienPhong(double tongTienPhong) { this.tongTienPhong = tongTienPhong; }

    public double getTongTienDichVu() { return tongTienDichVu; }
    public void setTongTienDichVu(double tongTienDichVu) { this.tongTienDichVu = tongTienDichVu; }

    public double getTongCong() { return tongCong; }
    public void setTongCong(double tongCong) { this.tongCong = tongCong; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }
}
