package com.baitan001.android;

public class UserInfo{


	private static String nickname;
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
}	

	