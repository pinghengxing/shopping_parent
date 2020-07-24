package com.phx.utils;

import com.phx.constants.Constants;

import java.util.UUID;

public class TokenUtils {

	 public static String getMemberToken(){
		 return Constants.TOKEN_MEMBER+"-"+UUID.randomUUID();
	 }

    public static String getPayToken() {
        return Constants.TOKEN_PAY + "-" + UUID.randomUUID();
    }
	
}
