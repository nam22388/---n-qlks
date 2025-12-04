package service;

import dao.ChiTietDatPhongDAO;
import model.ChiTietDatPhong;
import java.util.List;

public class ChiTietDatPhongService {

    private ChiTietDatPhongDAO chiTietDAO;

    public ChiTietDatPhongService() {
        chiTietDAO = new ChiTietDatPhongDAO();
    }

    // Lấy chi tiết theo mã phiếu đặt phòng
    public List<ChiTietDatPhong> getByMaPhieu(int maPhieu) {
        return chiTietDAO.getByMaPhieu(maPhieu);
    }

    // Thêm chi tiết đặt phòng
    public boolean addChiTiet(ChiTietDatPhong ct) {
        // logic kiểm tra trước khi thêm
        if (ct.getSoNgay() <= 0) {
            System.out.println("Số ngày không hợp lệ!");
            return false;
        }
        if (ct.getGiaDat() < 0) {
            System.out.println("Giá đặt không hợp lệ!");
            return false;
        }

        return chiTietDAO.insert(ct);
    }

    // Xóa chi tiết đặt phòng
    public boolean deleteChiTiet(int maCTDP) {
        return chiTietDAO.delete(maCTDP);
    }
}
