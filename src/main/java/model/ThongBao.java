package model;

public class ThongBao {
	private String makh;
	private String mahang;
	private String tenhang;
	private String mahoadon;
	private int soluongmua;
	public ThongBao() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ThongBao(String makh, String mahang, String tenhang, int soluongmua) {
		super();
		this.makh = makh;
		this.mahang = mahang;
		this.tenhang = tenhang;
		this.soluongmua = soluongmua;
	}
	public String getMakh() {
		return makh;
	}
	public void setMakh(String makh) {
		this.makh = makh;
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
	public int getSoluongmua() {
		return soluongmua;
	}
	public void setSoluongmua(int soluongmua) {
		this.soluongmua = soluongmua;
	}
	
}
