package com.welliver.sidescroller;

public class AdHandler {
	
	public AdType type;
	public int price;
	public boolean monthly;
	public boolean weekly;
	public boolean daily = false;
	public int daysLeft;
	public boolean finished = false;
	public String name;
	public int finishedPosition;
	
	public enum AdType{
		Radio,
		Magazine,
		Online,
		Newspaper,
		Billboard,
		TV,
		Mail;
	}
	
	public AdHandler(AdType type){
		this.type = type;
		
		if(type == AdType.Radio){
			price = 150;
			weekly = true;
			monthly = false;
			daysLeft = 7;
			name = "Radio";
		}
		
		if(type == AdType.Magazine){
			price = 10000;
			weekly = false;
			monthly = true;
			daysLeft = 31;
			name = "Magazine";
		}
		
		if(type == AdType.Online){
			price = 20000;
			weekly = false;
			monthly = true;
			daysLeft = 31;
			name = "Online";
		}
		
		if(type == AdType.Newspaper){
			price = 6000;
			weekly = true;
			monthly = false;
			daysLeft = 7;
			name = "Newspaper";
		}
		
		if(type == AdType.Billboard){
			price = 25000;
			weekly = true;
			monthly = false;
			daysLeft = 7;
			name = "Billboard";
		}
		
		if(type == AdType.TV){
			price = 100000;
			weekly = false;
			monthly = false;
			daily = true;
			daysLeft = 1;
			name = "TV Ad";
		}
	}
	
	public void update(){
		daysLeft--;
		if(daysLeft <= 0){
			finished = true;
		}
	}
}
