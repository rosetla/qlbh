package model;

public class CartItem {
    private String mahang;
    private String tenhang;
    private long gia;
    private int soluongmua;

    public CartItem(String mahang, String tenhang, long gia, int soluongmua) {
        this.mahang = mahang;
        this.tenhang = tenhang;
        this.gia = gia;
        this.soluongmua  = soluongmua ;
    }

    public String getMahang() {
        return mahang;
    }

    public void setMahang(String mahang) {
        this.mahang = mahang;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

 // Getter for soluongmua
    public int getSoluongmua() {
        return soluongmua;
    }

 // Setter for soluongmua
    public void setSoluongmua(int soluongmua) {
        this.soluongmua = soluongmua;
    }
}
