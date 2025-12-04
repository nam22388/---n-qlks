package service;

import dao.KhachHangDAO;
import model.KhachHang;

import java.util.List;

public class KhachHangService {

    private KhachHangDAO khachHangDAO;

    public KhachHangService() {
        this.khachHangDAO = new KhachHangDAO();
    }

    // Lấy toàn bộ khách hàng
    public List<KhachHang> getAllKhachHang() {
        return khachHangDAO.getAll();
    }

    // Thêm khách hàng
    public boolean addKhachHang(KhachHang kh) {

        // Check trùng CCCD
        if (khachHangDAO.findByCCCD(kh.getCccd()) != null) {
            System.out.println("Khách hàng với CCCD này đã tồn tại!");
            return false;
        }

        // Check cơ bản
        if (kh.getHoTen().isEmpty() || kh.getCccd().isEmpty()) {
            System.out.println("Thông tin không hợp lệ!");
            return false;
        }

        return khachHangDAO.insert(kh);
    }

    // Cập nhật khách hàng
    public boolean updateKhachHang(KhachHang kh) {
        return khachHangDAO.update(kh);
    }

    // Tìm theo mã
    public KhachHang findById(int id) {
        return khachHangDAO.findById(id);
    }

    // Tìm theo CCCD
    public KhachHang findByCCCD(String cccd) {
        return khachHangDAO.findByCCCD(cccd);
    }
}
