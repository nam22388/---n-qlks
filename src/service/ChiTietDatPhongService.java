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

    public boolean addChiTiet(int maPhieuDatPhong, int maPhong) {
        ChiTietDatPhong ct = new ChiTietDatPhong(
            0,      // MaCTDP (không dùng)
            0,      // SoNgay (trigger)
            maPhong,
            maPhieuDatPhong,
            0       // GiaDat (trigger)
        );
        return chiTietDAO.insert(ct);
    }

    // Xóa chi tiết đặt phòng
    public boolean deleteChiTiet(int maCTDP) {
        return chiTietDAO.delete(maCTDP);
    }
}
