package service;

import dao.HoaDonDAO;
import model.HoaDon;
import java.util.List;

public class HoaDonService {

    private HoaDonDAO hoaDonDAO;

    public HoaDonService() {
        hoaDonDAO = new HoaDonDAO();
    }

    // Lấy toàn bộ hóa đơn
    public List<HoaDon> getAll() {
        return hoaDonDAO.getAll();
    }

    // Lấy hóa đơn theo mã phiếu đặt phòng
    public HoaDon getByMaPhieuDatPhong(int maPhieu) {
        return hoaDonDAO.getByMaPhieuDatPhong(maPhieu);
    }

    // Thêm hóa đơn
    public boolean insert(HoaDon hd) {
        return hoaDonDAO.insert(hd);
    }

}
