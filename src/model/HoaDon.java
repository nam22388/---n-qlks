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
    private int maHoaDon;
    private int maPhieuDatPhong;
    private LocalDate ngayTao;
    private double tongTienPhong;
    private double tongTienDichVu;
    private double tongCong;
    private int maKhachHang;

    public HoaDon() {}

    public HoaDon(int maHoaDon, int maPhieuDatPhong, LocalDate ngayTao,
                  double tongTienPhong, double tongTienDichVu, double tongCong, int maKhachHang) {
        this.maHoaDon = maHoaDon;
        this.maPhieuDatPhong = maPhieuDatPhong;
        this.ngayTao = ngayTao;
        this.tongTienPhong = tongTienPhong;
        this.tongTienDichVu = tongTienDichVu;
        this.tongCong = tongCong;
        this.maKhachHang = maKhachHang;
    }

    public int getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(int maHoaDon) { this.maHoaDon = maHoaDon; }

    public int getMaPhieuDatPhong() { return maPhieuDatPhong; }
    public void setMaPhieuDatPhong(int maPhieuDatPhong) { this.maPhieuDatPhong = maPhieuDatPhong; }

    public LocalDate getNgayTao() { return ngayTao; }
    public void setNgayTao(LocalDate ngayTao) { this.ngayTao = ngayTao; }

    public double getTongTienPhong() { return tongTienPhong; }
    public void setTongTienPhong(double tongTienPhong) { this.tongTienPhong = tongTienPhong; }

    public double getTongTienDichVu() { return tongTienDichVu; }
    public void setTongTienDichVu(double tongTienDichVu) { this.tongTienDichVu = tongTienDichVu; }

    public double getTongCong() { return tongCong; }
    public void setTongCong(double tongCong) { this.tongCong = tongCong; }

    public int getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(int maKhachHang) { this.maKhachHang = maKhachHang; }
}
