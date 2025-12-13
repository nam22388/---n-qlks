package service;

import dao.PhongDAO;
import model.Phong;

import java.util.List;

public class PhongService {

    private PhongDAO phongDAO;

    public PhongService() {
        phongDAO = new PhongDAO();
    }

    // Lấy toàn bộ danh sách phòng
    public List<Phong> getAllPhong() {
        return phongDAO.getAll();
    }

    // Thêm phòng
    public boolean addPhong(Phong p) {
        // Kiểm tra phòng đã tồn tại
        Phong existing = phongDAO.findById(p.getMaPhong());
        if (existing != null) {
            System.out.println("Phòng đã tồn tại, không thể thêm!");
            return false;
        }

        return phongDAO.insert(p);
    }

    // Sửa phòng
    public boolean updatePhong(Phong p) {
        return phongDAO.update(p);
    }

    // Xóa phòng
    public boolean deletePhong(int maPhong) {
        return phongDAO.delete(maPhong);
    }

    public List<Phong> getPhongTrong(String loai) {
        return phongDAO.findPhongTrong(loai);
    }
    
    // Đổi tình trạng phòng
    public boolean updateTinhTrang(int maPhong, String tinhTrang) {
        return phongDAO.updateTinhTrang(maPhong, tinhTrang);
    }
}
