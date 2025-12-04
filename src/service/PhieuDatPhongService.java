package service;

import dao.PhieuDatPhongDAO;
import model.PhieuDatPhong;

import java.util.List;

public class PhieuDatPhongService {

    private PhieuDatPhongDAO dao;

    public PhieuDatPhongService() {
        dao = new PhieuDatPhongDAO();
    }

    // Lấy danh sách phiếu kèm thông tin khách hàng
    public List<Object[]> getAllWithKhachHang() {
        return dao.getAllWithKhachHang();
    }
    
    // Lấy toàn bộ phiếu đặt phòng
    public List<PhieuDatPhong> getAll() {
        return dao.getAll();
    }

    // Thêm phiếu đặt phòng (có thể kiểm tra ngày)
    public boolean addPhieu(PhieuDatPhong p) {

        // Kiểm tra ngày đến phải <= ngày đi
        if (p.getNgayDen() != null && p.getNgayDi() != null) {
            if (p.getNgayDi().isBefore(p.getNgayDen())) {
                System.out.println("Ngày đi không được trước ngày đến!");
                return false;
            }
        }

        return dao.insert(p);
    }

    // Tìm phiếu theo mã
    public PhieuDatPhong getById(int maPhieu) {
        return dao.findById(maPhieu);
    }

    // Lấy phiếu theo trạng thái (Đã đặt / Đang thuê / Đã trả / Đã hủy)
    public List<PhieuDatPhong> getByTrangThai(String trangThai) {
        return dao.findByTrangThai(trangThai);
    }

    // Lấy danh sách phiếu theo CCCD khách hàng
    public List<PhieuDatPhong> getByCCCD(String cccd) {
        return dao.findByCCCD(cccd);
    }

    // Cập nhật trạng thái phiếu
    public boolean updateTrangThai(int maPhieu, String trangThai) {
        return dao.updateTrangThai(maPhieu, trangThai);
    }

    // Hàm phục vụ nút nhận phòng
    public boolean nhanPhong(int maPhieu) {
        return dao.updateTrangThai(maPhieu, "Đang thuê");
    }

    // Hàm phục vụ nút trả phòng
    public boolean traPhong(int maPhieu) {
        return dao.updateTrangThai(maPhieu, "Đã trả");
    }

    // Hàm hủy phiếu
    public boolean huyPhieu(int maPhieu) {
        return dao.updateTrangThai(maPhieu, "Đã hủy");
    }
}
