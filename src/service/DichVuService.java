package service;

import dao.DichVuDAO;
import model.DichVu;

import java.util.List;

public class DichVuService {

    private DichVuDAO dichVuDAO;

    public DichVuService() {
        dichVuDAO = new DichVuDAO();
    }

    // Lấy toàn bộ dịch vụ
    public List<DichVu> getAll() {
        return dichVuDAO.getAll();
    }

    // Thêm dịch vụ
    public boolean addDichVu(DichVu dv) {
        if (dv.getGiaDichVu() < 0) {
            System.out.println("Giá dịch vụ không hợp lệ!");
            return false;
        }
        return dichVuDAO.insert(dv);
    }

    // Xóa dịch vụ
    public boolean deleteDichVu(int maDV) {
        return dichVuDAO.delete(maDV);
    }

    // Tìm theo mã dịch vụ
    public DichVu findById(int maDV) {
        return dichVuDAO.findById(maDV);
    }
    
    public List<DichVu> searchByName(String name) {
        return dichVuDAO.findByName(name);
    }
}
