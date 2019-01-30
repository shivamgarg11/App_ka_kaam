package com.shivam.app_ka_kaam.Java_objects;

public class lastvalue {
    int date;
    int time;
    int month;
    int year;
    int value;

    public lastvalue(){

    }

    public lastvalue(int date, int time, int month, int year, int value) {
        this.date = date;
        this.time = time;
        this.month = month;
        this.year = year;
        this.value = value;
    }

    public int getDate() {
        return date;
    }

    public int getTime() {
        return time;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
