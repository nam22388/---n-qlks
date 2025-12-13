package service;

import dao.ThongKeDAO;
import java.sql.Date;

public class ThongKeService {

    private ThongKeDAO dao = new ThongKeDAO();

    public double tinhDoanhThu(Date tuNgay, Date denNgay) {
        if (tuNgay.after(denNgay)) {
            throw new IllegalArgumentException("Từ ngày không được lớn hơn đến ngày");
        }
        return dao.getDoanhThu(tuNgay, denNgay);
    }
}
