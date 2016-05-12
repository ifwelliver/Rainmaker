package com.welliver.sidescroller;

import java.util.Random;

public class CaseHandler {
	public CaseType type;
	private Random r = new Random();
	public String name;
	public int settlement;
	public int maxWin;
	private Rainmaker ss;
	private int chance;
	public int disputeTime;
	public int settleTime;
	public boolean pending = false;
	public Outcome outcome;
	public enum CaseType{
		Divorce,
		ChildCustody,
		MinorAccident,
		MajorAccident,
		MinorInjury,
		MajorInjury,
		ContractDispute;
	}
	public enum Outcome{
		Win,
		Lose,
		Settle;
	}
	
	public CaseHandler(Rainmaker ss){
		this.ss = ss;
		if(ss.level.day < 90){
			chance = r.nextInt(100) + 1;
			if(chance <= 40){
				type = CaseType.MinorAccident;
				name = "Minor Accident";
				disputeTime = 180; //180
				settleTime = 31; //31
				settlement = 300;
				maxWin = 1000;
			}
			else if(chance > 40 && chance <= 50){
				type = CaseType.ChildCustody;
				name = "Child Custody";
				settlement = 1500;
				maxWin = 1500;
			}
			else if(chance > 50 && chance <= 60){
				type = CaseType.MinorInjury;
				name = "Minor Slip and Fall";
				disputeTime = 180;
				settleTime = 31;
				settlement = 700;
				maxWin = 2000;
			}
			else if(chance > 60){
				type = CaseType.Divorce;
				name = "Divorce";
				settlement = 175;
				maxWin = 175;
			}
		}
		else{
			chance = r.nextInt(100) + 1;
			if(chance <= 20){
				type = CaseType.Divorce;
				name = "Divorce";
				settlement = 175;
				maxWin = 175;
			}
			else if(chance > 20 && chance <= 40){
				type = CaseType.ChildCustody;
				name = "Child Custody";
				settlement = 2000;
				maxWin = 2000;
			}
			else if(chance > 40 && chance <= 60){
				type = CaseType.MinorInjury;
				name = "Minor Slip and Fall";
				disputeTime = 180;
				settleTime = 31;
				settlement = 700;
				maxWin = 2000;
			}
			else if(chance > 60 && chance <= 80){
				type = CaseType.MinorAccident;
				name = "Minor Accident";
				disputeTime = 180;
				settleTime = 31;
				settlement = 300;
				maxWin = 1000;
			}
			else if(chance > 80 && chance <= 90){
				type = CaseType.ContractDispute;
				name = "Contract Dispute";
				disputeTime = 365;
				settleTime = 90;
				settlement = 5000;
				maxWin = 10000;
			}
			else if (chance > 90 && chance <= 95){
				type = CaseType.MajorAccident;
				name = "Major Accident";
				disputeTime = 365;
				settleTime = 60;
				settlement = 10000;
				maxWin = 25000;
			}
			else if(chance > 95){
				type = CaseType.MajorInjury;
				name = "Major Slip and Fall";
				disputeTime = 365;
				settleTime = 60;
				settlement = 5000;
				maxWin = 10000;
			}
		}
	}
	
	public void findOutcome(){
		if(type == CaseType.ContractDispute){
			int num = r.nextInt(100) + 1;
			if(num < 40){
				outcome = Outcome.Win;
			}
			else{
				outcome = Outcome.Lose;
			}
		}
		else{
			int num = r.nextInt(100) + 1;
			if(num <= 50){
				outcome = Outcome.Lose;
			}
			else{
				outcome = Outcome.Win;
			}
		}
	}
}
