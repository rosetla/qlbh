package model;

public class Admin {
	private String tendangnhap;
    private String matkhau;
    private boolean quyen;
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Admin(String tendangnhap, String matkhau, boolean quyen) {
		super();
		this.tendangnhap = tendangnhap;
		this.matkhau = matkhau;
		this.quyen = quyen;
	}
	public String getTendangnhap() {
		return tendangnhap;
	}
	public void setTendangnhap(String tendangnhap) {
		this.tendangnhap = tendangnhap;
	}
	public String getMatkhau() {
		return matkhau;
	}
	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}
	public boolean getQuyen() {
		return quyen;
	}
	public void setQuyen(boolean quyen) {
		this.quyen = quyen;
	}
    
}
