package com.banisaleh.stmik.inkubatormobile.Models;

public class GetNotif {
    private String id_pir;
    private String tgl_mess;
    private String pesan;
    private Integer rownum;

    public String getId_pir() {
        return id_pir;
    }

    public void setId_pir(String id_pir) {
        this.id_pir = id_pir;
    }

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public String getTgl_mess() {
        return tgl_mess;
    }

    public void setTgl_mess(String tgl_mess) {
        this.tgl_mess = tgl_mess;
    }


    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}