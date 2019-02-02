package com.shivam.app_ka_kaam.response;

public class LASTVALUE{
	private int date;
	private int kvah;
	private int month;
	private int year;
	private int time;
	private int kwh;
	private int value;

	public void setDate(int date){
		this.date = date;
	}

	public int getDate(){
		return date;
	}

	public void setKvah(int kvah){
		this.kvah = kvah;
	}

	public int getKvah(){
		return kvah;
	}

	public void setMonth(int month){
		this.month = month;
	}

	public int getMonth(){
		return month;
	}

	public void setYear(int year){
		this.year = year;
	}

	public int getYear(){
		return year;
	}

	public void setTime(int time){
		this.time = time;
	}

	public int getTime(){
		return time;
	}

	public void setKwh(int kwh){
		this.kwh = kwh;
	}

	public int getKwh(){
		return kwh;
	}

	public void setValue(int value){
		this.value = value;
	}

	public int getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"LASTVALUE{" + 
			"date = '" + date + '\'' + 
			",kvah = '" + kvah + '\'' + 
			",month = '" + month + '\'' + 
			",year = '" + year + '\'' + 
			",time = '" + time + '\'' + 
			",kwh = '" + kwh + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}
