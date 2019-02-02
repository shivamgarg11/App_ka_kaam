package com.shivam.app_ka_kaam.response;

public class Response{
	private ELECTRICITYMEENA eLECTRICITYMEENA;
	private GASMUKTA gASMUKTA;
	private ELECTRICITYMUKTA eLECTRICITYMUKTA;

	public void setELECTRICITYMEENA(ELECTRICITYMEENA eLECTRICITYMEENA){
		this.eLECTRICITYMEENA = eLECTRICITYMEENA;
	}

	public ELECTRICITYMEENA getELECTRICITYMEENA(){
		return eLECTRICITYMEENA;
	}

	public void setGASMUKTA(GASMUKTA gASMUKTA){
		this.gASMUKTA = gASMUKTA;
	}

	public GASMUKTA getGASMUKTA(){
		return gASMUKTA;
	}

	public void setELECTRICITYMUKTA(ELECTRICITYMUKTA eLECTRICITYMUKTA){
		this.eLECTRICITYMUKTA = eLECTRICITYMUKTA;
	}

	public ELECTRICITYMUKTA getELECTRICITYMUKTA(){
		return eLECTRICITYMUKTA;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"eLECTRICITYMEENA = '" + eLECTRICITYMEENA + '\'' + 
			",gASMUKTA = '" + gASMUKTA + '\'' + 
			",eLECTRICITYMUKTA = '" + eLECTRICITYMUKTA + '\'' + 
			"}";
		}
}
