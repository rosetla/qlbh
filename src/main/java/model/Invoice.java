package model;

import java.sql.Timestamp;

public class Invoice {
    private long mahoadon;
    private long makh;
    private Timestamp ngaymua;
    private boolean damua;

    public long getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(long mahoadon) {
        this.mahoadon = mahoadon;
    }

    public long getMakh() {
        return makh;
    }

    public void setMakh(long makh) {
        this.makh = makh;
    }

    public Timestamp getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(Timestamp ngaymua) {
        this.ngaymua = ngaymua;
    }

    public boolean isDamua() {
        return damua;
    }

    public void setDamua(boolean damua) {
        this.damua = damua;
    }
}
