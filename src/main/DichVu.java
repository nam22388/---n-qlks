/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

public class DichVu {
    private String maDV;
    private String tenDV;
    private double giaDV;

    public DichVu(String maDV, String tenDV, double giaDV) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.giaDV = giaDV;
    }

    public String getMaDV() { return maDV; }
    public String getTenDV() { return tenDV; }
    public double getGiaDV() { return giaDV; }

    public void hienThi() {
        System.out.println(maDV + " | " + tenDV + " | " + giaDV);
    }
}
