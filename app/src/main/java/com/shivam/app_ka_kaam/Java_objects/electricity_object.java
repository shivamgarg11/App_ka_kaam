package com.shivam.app_ka_kaam.Java_objects;

public class electricity_object {
    double kwh;
    double kvah;
    double diffkwh;
    double diffkvah;
    double mpf;
    double ppf;
    double cal_pf;
    double amount1;
    double amount2;

    public electricity_object(){

    }

    public electricity_object(double kwh, double kvah, double diffkwh, double diffkvah, double mpf, double ppf, double cal_pf, double amount1, double amount2) {
        this.kwh = kwh;
        this.kvah = kvah;
        this.diffkwh = diffkwh;
        this.diffkvah = diffkvah;
        this.mpf = mpf;
        this.ppf = ppf;
        this.cal_pf = cal_pf;
        this.amount1 = amount1;
        this.amount2 = amount2;
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

    public double getDiffkwh() {
        return diffkwh;
    }

    public void setDiffkwh(double diffkwh) {
        this.diffkwh = diffkwh;
    }

    public double getDiffkvah() {
        return diffkvah;
    }

    public void setDiffkvah(double diffkvah) {
        this.diffkvah = diffkvah;
    }

    public double getMpf() {
        return mpf;
    }

    public void setMpf(double mpf) {
        this.mpf = mpf;
    }

    public double getPpf() {
        return ppf;
    }

    public void setPpf(double ppf) {
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
