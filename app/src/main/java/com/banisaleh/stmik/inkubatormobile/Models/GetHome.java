package com.banisaleh.stmik.inkubatormobile.Models;

public class GetHome {

    private String device_name;
    private String lampu_1;
    private String lampu_2;
    private String temp;
    private String humidity;
    private String date;
    private Integer rownum;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public String getLampu_1() {
        return lampu_1;
    }

    public void setLampu_1(String lampu_1) {
        this.lampu_1 = lampu_1;
    }

    public String getLampu_2() {
        return lampu_2;
    }

    public void setLampu_2(String lampu_2) {
        this.lampu_2 = lampu_2;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }
}
