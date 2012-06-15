package com.baitan001.android;


public class ItemInfo{
	
	public String id = "";
	public String bookname = "";
	public String bookauthor = "";
	public String alias = "";
	public String isbn13 = "";
	public int bookcategoryid = 1;
	public String doubansimg = "";
	public String price = "";
	public String baitanprice = "";
	public String contactpeople;
	public String tel;
	public String publisher;
	
	public ItemInfo(){
		id = "1238";
		bookname = "Who moved my cheese";
		bookauthor = "Whoknows";
		alias = "Cheese";
		isbn13 = "1232314";
		bookcategoryid = 1;
		doubansimg = "http://img1.douban.com/mpic/s1106991.jpg";
		price = "10";
		baitanprice = "2";
		contactpeople = "XiaoMaGe";
	}

	public ItemInfo(String id, String bookname, String seller, String baitanprice, String doubansimg, String publisher, String bookauthor) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.bookname = bookname;
		this.contactpeople = seller;
		this.baitanprice = baitanprice;
		this.doubansimg = doubansimg;
		this.publisher = publisher;
		this.bookauthor = bookauthor;
	}
	
	
	
	
}
