package model;

public class OrderDetail {
    private String mahang;
    private int soluongmua;
    private long mahoadon;

    // Default constructor
    public OrderDetail() {
    }

    // Constructor with parameters (optional)
    public OrderDetail(String mahang, int soluongmua, long mahoadon) {
        this.mahang = mahang;
        this.soluongmua = soluongmua;
        this.mahoadon = mahoadon;
    }

    // Getters and Setters
    public String getMahang() {
        return mahang;
    }

    public void setMahang(String mahang) {
        this.mahang = mahang;
    }

    public int getSoluongmua() {
        return soluongmua;
    }

    public void setSoluongmua(int soluongmua) {
        this.soluongmua = soluongmua;
    }

    public long getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(long mahoadon) {
        this.mahoadon = mahoadon;
    }
}
