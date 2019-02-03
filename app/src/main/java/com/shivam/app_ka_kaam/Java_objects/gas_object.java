package com.shivam.app_ka_kaam.Java_objects;

public class gas_object {
    double input;
    int time;
    double difference;
    double scm;
    double mmbto;
    double ride;
    double bill;

    public gas_object(){

    }

    public gas_object(double input, int time, int difference, double scm, double mmbto, double ride, double bill) {
        this.input = input;
        this.time = time;
        this.difference = difference;
        this.scm = scm;
        this.mmbto = mmbto;
        this.ride = ride;
        this.bill = bill;
    }


    public double getInput() {
        return input;
    }

    public int getTime() {
        return time;
    }

    public double getDifference() {
        return difference;
    }

    public double getScm() {
        return scm;
    }

    public double getMmbto() {
        return mmbto;
    }

    public double getRide() {
        return ride;
    }

    public double getBill() {
        return bill;
    }

    public void setInput(double input) {
        this.input = input;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }

    public void setScm(double scm) {
        this.scm = scm;
    }

    public void setMmbto(double mmbto) {
        this.mmbto = mmbto;
    }

    public void setRide(double ride) {
        this.ride = ride;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }
}
