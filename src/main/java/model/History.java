package model;

import java.util.Date;

public class History {
    private String makh;
    private String hoten;
    private String mahoadon;
    private Date ngaymua;
    private String tenhang;
    private int soluongmua;
    private double tongTien;

    // Getters and Setters
    public String getMaKhachHang() {
        return makh;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.makh = maKhachHang;
    }

    public String getTenKhachHang() {
        return hoten;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.hoten = tenKhachHang;
    }

    public String getMaHoaDon() {
        return mahoadon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.mahoadon = maHoaDon;
    }

    public Date getNgayMua() {
        return ngaymua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngaymua = ngayMua;
    }

    public String getTenHangHoa() {
        return tenhang;
    }

    public void setTenHangHoa(String tenHangHoa) {
        this.tenhang = tenHangHoa;
    }

    public int getTongSoLuongMua() {
        return soluongmua;
    }

    public void setTongSoLuongMua(int tongSoLuongMua) {
        this.soluongmua = tongSoLuongMua;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}
