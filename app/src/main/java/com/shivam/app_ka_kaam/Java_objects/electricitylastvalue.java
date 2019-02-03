package com.shivam.app_ka_kaam.Java_objects;

public class electricitylastvalue {
    int date;
    int time;
    int month;
    int year;
    double kwh;
    double kvah;

    public electricitylastvalue(){

    }

    public electricitylastvalue(int date, int time, int month, int year, double kwh, double kvah) {
        this.date = date;
        this.time = time;
        this.month = month;
        this.year = year;
        this.kwh = kwh;
        this.kvah = kvah;
    }


    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getKwh() {
        return kwh;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    public double getKvah() {
        return kvah;
    }

    public void setKvah(double kvah) {
        this.kvah = kvah;
    }
}
