package org.example.booking.model;

public class StudentCrud {
    private Long id;
    private String mssv;
    private String ten;

    public StudentCrud() {}

    public StudentCrud(Long id, String mssv, String ten) {
        this.id = id;
        this.mssv = mssv;
        this.ten = ten;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
