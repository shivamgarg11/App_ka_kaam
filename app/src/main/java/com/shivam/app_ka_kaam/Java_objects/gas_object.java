package com.shivam.app_ka_kaam.Java_objects;

public class gas_object {
    int input;
    int time;
    int difference;
    int scm;
    int mmbto;
    int ride;
    int bill;

    public gas_object(){

    }

    public gas_object(int input, int time, int difference, int scm, int mmbto, int ride, int bill) {
        this.input = input;
        this.time = time;
        this.difference = difference;
        this.scm = scm;
        this.mmbto = mmbto;
        this.ride = ride;
        this.bill = bill;
    }


    public int getInput() {
        return input;
    }

    public int getTime() {
        return time;
    }

    public int getDifference() {
        return difference;
    }

    public int getScm() {
        return scm;
    }

    public int getMmbto() {
        return mmbto;
    }

    public int getRide() {
        return ride;
    }

    public int getBill() {
        return bill;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    public void setScm(int scm) {
        this.scm = scm;
    }

    public void setMmbto(int mmbto) {
        this.mmbto = mmbto;
    }

    public void setRide(int ride) {
        this.ride = ride;
    }

    public void setBill(int bill) {
        this.bill = bill;
    }
}
