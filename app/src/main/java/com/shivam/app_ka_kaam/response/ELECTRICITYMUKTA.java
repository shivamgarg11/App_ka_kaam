package com.shivam.app_ka_kaam.response;

public class ELECTRICITYMUKTA{
	private JsonMember2019 jsonMember2019;
	private CONSTANTS cONSTANTS;
	private LASTVALUE lASTVALUE;

	public void setJsonMember2019(JsonMember2019 jsonMember2019){
		this.jsonMember2019 = jsonMember2019;
	}

	public JsonMember2019 getJsonMember2019(){
		return jsonMember2019;
	}

	public void setCONSTANTS(CONSTANTS cONSTANTS){
		this.cONSTANTS = cONSTANTS;
	}

	public CONSTANTS getCONSTANTS(){
		return cONSTANTS;
	}

	public void setLASTVALUE(LASTVALUE lASTVALUE){
		this.lASTVALUE = lASTVALUE;
	}

	public LASTVALUE getLASTVALUE(){
		return lASTVALUE;
	}

	@Override
 	public String toString(){
		return 
			"ELECTRICITYMUKTA{" + 
			"2019 = '" + jsonMember2019 + '\'' + 
			",cONSTANTS = '" + cONSTANTS + '\'' + 
			",lASTVALUE = '" + lASTVALUE + '\'' + 
			"}";
		}
}
