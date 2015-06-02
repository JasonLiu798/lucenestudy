package com.jason.tools;

import java.io.UnsupportedEncodingException;

public class StringTools {
	public static String Iso2Utf8(String str) {  
        try {  
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }
	
}
