package com.baitan001.android;

public class UserInfo{


	private static String nickname;
	private static String province,city = "北京",school = "北京邮电大学";
	private String email;
	private String auth_key;
	private static String sessionid = "false";
	private static boolean login_status = false;
	
	public UserInfo(){}
	
	
	public static boolean isLogin(){
		return login_status;
	}
	
	public static void setLogin_status(boolean status){
		login_status = status;
	}


	public static void setNickname(String nickname) {
		// TODO Auto-generated method stub
		UserInfo.nickname = nickname;
		setLogin_status(true);
		
	}
	
	public static String getNickname(){
		return nickname;
	}


	public static void setSessionid(String sessionid) {
		// TODO Auto-generated method stub
		UserInfo.sessionid = sessionid;
	}
	
	public static String getSessionid() {
		// TODO Auto-generated method stub
		return sessionid;
	}


	public static String getProvince() {
		return province;
	}


	public static void setProvince(String province) {
		UserInfo.province = province;
	}


	public static String getCity() {
		return city;
	}


	public static void setCity(String city) {
		UserInfo.city = city;
	}


	public static String getSchool() {
		return school;
	}


	public static void setSchool(String school) {
		UserInfo.school = school;
	}
}	

	