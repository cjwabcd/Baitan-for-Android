package com.baitan001;

public class UserInfo{


	private String username;
	private String userid;
	private String auth_key;
	private String session = "false";
	private static boolean login_status = false;
	
	public UserInfo(){}
	
	
	public static boolean isLogin(){
		return login_status;
	}
	
	public static void setLogin_status(boolean status){
		login_status = status;
	}
}	

	