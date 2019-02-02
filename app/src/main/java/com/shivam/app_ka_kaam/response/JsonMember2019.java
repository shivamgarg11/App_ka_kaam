package com.shivam.app_ka_kaam.response;

import java.util.List;

public class JsonMember2019{
	private List<Object> jsonMember2;

	public void setJsonMember2(List<Object> jsonMember2){
		this.jsonMember2 = jsonMember2;
	}

	public List<Object> getJsonMember2(){
		return jsonMember2;
	}

	@Override
 	public String toString(){
		return 
			"JsonMember2019{" + 
			"2 = '" + jsonMember2 + '\'' + 
			"}";
		}
}