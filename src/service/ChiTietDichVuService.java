package service;

import dao.ChiTietDichVuDAO;
import model.ChiTietDichVu;

import java.util.List;

public class ChiTietDichVuService {

    private ChiTietDichVuDAO dao;

    public ChiTietDichVuService() {
        dao = new ChiTietDichVuDAO();
    }


    // Lấy theo mã chi tiết đặt phòng
    public List<Object[]> getByMaCTDP(int maCTDP) {
        return dao.getByMaCTDP(maCTDP);
    }

    // Thêm dịch vụ vào chi tiết
    public boolean addCTDV(ChiTietDichVu ct) {
        if (ct.getSoLuong() <= 0) {
            System.out.println("Số lượng dịch vụ không hợp lệ!");
            return false;
        }
        return dao.insert(ct);
    }

    // Xóa chi tiết dịch vụ
    public boolean deleteCTDV(int maDichVu, int maCTDP) {
        return dao.delete(maDichVu, maCTDP);
    }
}
