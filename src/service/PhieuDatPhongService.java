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
    public PhieuDatPhong getById(int maPhieuDatPhong) {
        return dao.findById(maPhieuDatPhong);
    }

    public List<Object[]> getByCCCD(String cccd) {
        return dao.findByCCCD(cccd);
    }

    

    public List<Object[]> searchCCCDAndTrangThai(String cccd, String trangThai) {
        return dao.findByCCCDAndTrangThai(cccd, trangThai);
    }
    
    // Cập nhật trạng thái phiếu
    public boolean updateTrangThai(int maPhieuDatPhong, String trangThai) {
        return dao.updateTrangThai(maPhieuDatPhong, trangThai);
    }

    // Hàm phục vụ nút nhận phòng
    public boolean nhanPhong(int maPhieu) {
        return dao.nhanPhong(maPhieu);
    }

    // Trả phòng + tạo hóa đơn (giữ tên traPhong để view không thay đổi)
    public boolean traPhong(int maPhieuDatPhong) {
        return dao.traPhong(maPhieuDatPhong);
    }

    // Hàm hủy phiếu
    public boolean huyPhieu(int maPhieu) {
        return dao.huyPhieu(maPhieu);
    }
    
    public PhieuDatPhong getLatestByCCCD(String cccd) {
        List<PhieuDatPhong> list = dao.findByCCCDAsObject(cccd);
        if(list.isEmpty()) return null;
        return list.get(list.size() - 1); // lấy phiếu mới tạo nhất
    }
}
