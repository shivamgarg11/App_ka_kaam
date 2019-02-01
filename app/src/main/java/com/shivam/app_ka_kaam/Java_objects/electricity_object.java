package com.shivam.app_ka_kaam.Java_objects;

public class electricity_object {
int kwh;
int kvah;
int mpf;
int ppf;
double cal_pf;
double amount1;
double amount2;

    public electricity_object(){

    }

    public electricity_object(int kwh, int kvah, int mpf, int ppf, double cal_pf, double amount1, double amount2) {
        this.kwh = kwh;
        this.kvah = kvah;
        this.mpf = mpf;
        this.ppf = ppf;
        this.cal_pf = cal_pf;
        this.amount1 = amount1;
        this.amount2 = amount2;
    }

    public int getKwh() {
        return kwh;
    }

    public void setKwh(int kwh) {
        this.kwh = kwh;
    }

    public int getKvah() {
        return kvah;
    }

    public void setKvah(int kvah) {
        this.kvah = kvah;
    }

    public int getMpf() {
        return mpf;
    }

    public void setMpf(int mpf) {
        this.mpf = mpf;
    }

    public int getPpf() {
        return ppf;
    }

    public void setPpf(int ppf) {
        this.ppf = ppf;
    }

    public double getCal_pf() {
        return cal_pf;
    }

    public void setCal_pf(double cal_pf) {
        this.cal_pf = cal_pf;
    }

    public double getAmount1() {
        return amount1;
    }

    public void setAmount1(double amount1) {
        this.amount1 = amount1;
    }

    public double getAmount2() {
        return amount2;
    }

    public void setAmount2(double amount2) {
        this.amount2 = amount2;
    }
}
