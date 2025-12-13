package service;

import dao.HoaDonDAO;
import model.HoaDon;
import java.util.List;

public class HoaDonService {

    private HoaDonDAO hoaDonDAO;

    public HoaDonService() {
        hoaDonDAO = new HoaDonDAO();
    }


    // Lấy hóa đơn theo mã phiếu đặt phòng
    public HoaDon getByMaPhieuDatPhong(int maPhieuDatPhong) {
        return hoaDonDAO.getByMaPhieuDatPhong(maPhieuDatPhong);
    }

    

}
