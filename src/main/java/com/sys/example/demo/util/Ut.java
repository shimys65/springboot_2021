package com.sys.example.demo.util;

public class Ut {

	public static boolean empty(Object obj) {
// null이면 부른곳으로 true 리턴
		if(obj == null) {
			return true;
		}
		
// null이 아니면 string 확인 후 string 아니면 부른곳으로 true 리턴	
		if(obj instanceof String == false) {
			return true;
		}
		
// 둘다 통과 시, 내용 받아서(예:user1) 공백과 길이 검사		
		String str = (String)obj;		
		return str.trim().length() == 0;
	}

	public static Object f(String format, Object... args) {
		return String.format(format, args);
	}

}
