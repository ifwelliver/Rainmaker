package com.welliver.sidescroller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.welliver.sidescroller.CaseHandler.CaseType;
import com.welliver.sidescroller.CaseHandler.Outcome;
import com.welliver.sidescroller.Rainmaker.GameState;

public class Levels {
	public boolean starting = true;
	public boolean updated = false;
	public boolean adBackPopped = false;
	public boolean employeesBackPopped = false;
	public boolean facilitiesBackPopped = false;
	private Color brown = new Color(210, 105, 30);
	public float day = 1;
	public float monthDay = 1;
	public float money = 2000;
	public float moneyToday = 0;
	public float reputation = 1;
	public float reputationDay = 1;
	public float casesToday = 0;
	public float totalCases = 0;
	public float totalMoney = 0;
	public float weeklyFees = 0;
	public float monthlyFees = 1250;
	public float employees = 1;
	public float maxEmployees = 2;
	public int radioNum = 0;
	public int magNum = 0;
	public int onlineNum = 0;
	public int newsNum = 0;
	public int billboardNum = 0;
	public int tvNum = 0;
	public int mailNum = 0;
	public float counter = 0;
	private Rainmaker ss;
	private Random r;
	private Font fntLarge = new Font("arial", 1, 50);
	private Font fntMed = new Font("arial", 1, 30);
	private Font fntSmall = new Font("arial", 1, 15);
	public boolean beginPoppedOut = false;
	public boolean backToMenuPoppedOut = false;
	public boolean buyingRadio = false;
	public boolean buyingMagazine = false;
	public boolean buyingOnline = false;
	public boolean buyingNewpaper = false;
	public boolean buyingBillboard = false;
	public boolean buyingTV = false;
	public boolean buyingMail = false;
	public boolean backYesPopped = false;
	public boolean backNoPopped = false;
	public boolean hiringAssociate = false;
	public boolean promotingPartner = false;
	public boolean hiringClerk = false;
	public boolean hiringParalegal = false;
	public boolean hiringAssistant = false;
	public boolean hiringReceptionist = false;
	public boolean hiringInvestigator = false;
	public int associates = 0;
	public int partners = 0;
	public int clerks = 0;
	public int paralegals = 0;
	public int assistants = 0;
	public int receptionists = 0;
	public int investigators = 0;
	public int minuteX = (190 + 500) - 250;
	public int minuteY = 130;
	public int minuteXIncrement = -1;
	public int minuteYIncrement;
	public int hourX = (190 + 500) - 250;
	public int hourY = 250;
	public boolean clockRunning = false;
	public boolean clockUp = false;
	private Handler handler;
	public boolean adPayment = false;
	private Math math;
	public int numberAds = 0;
	LinkedList <AdHandler> ads = new LinkedList<AdHandler>();
	LinkedList <AdHandler> finishedAds = new LinkedList <AdHandler>();
	LinkedList <Integer> adNumbers = new LinkedList<Integer>();
	LinkedList <CaseHandler> pendingCases = new LinkedList <CaseHandler>();
	LinkedList <Integer> pendingDays = new LinkedList<Integer>();
	LinkedList <CaseHandler> dailyCases = new LinkedList <CaseHandler>();
	LinkedList <CaseHandler> finishedCases = new LinkedList <CaseHandler>();
	public GamePart part = GamePart.BeginDay;
	public boolean notContinue = false;
	public int continueCounter = 0;
	public int maxCases = 3;
	public boolean overMax = false;
	public int maxCounter = 0;
	public boolean casesRunning = false;
	public boolean outcomeReady = false;
	public Building building = Building.Tiny;
	public boolean notEnoughMoney = false;
	public int moneyCounter = 0;
	public boolean payingFees = false;
	public boolean bankrupt = false;
	private Image trump;
	public boolean rentingTiny = false;
	public boolean rentingSmall = false;
	public boolean rentingMed = false;
	public boolean rentingLarge = false;
	public boolean rentingHuge = false;
	public int cost;
	
	public enum GamePart{
		BeginDay,
		EndDay,
		MiddleDay,
		GameWon;
	}
	
	public enum Building{
		Tiny,
		Small,
		Medium,
		Large,
		Huge;
	}
	
	public Levels(Rainmaker ss, Handler handler){
		this.ss = ss;
		this.handler = handler;
		r = new Random();
		try{
			trump = ImageIO.read(new File("res/trump.jpg"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void tick(){
		if(building == Building.Tiny){
			maxEmployees = 2;
			cost = 1250;
		}
		else if(building == Building.Small){
			maxEmployees = 4;
			cost = 5000;
		}
		else if(building == Building.Medium){
			maxEmployees = 8;
			cost = 8000;
		}
		else if(building == Building.Large){
			maxEmployees = 16;
			cost = 14000;
		}
		else if(building == Building.Huge){
			maxEmployees = 32;
			cost = 25000;
		}
		employees = associates + partners + clerks + paralegals + assistants + receptionists
				+ investigators + 1;
		handler.removeAll();
		if(pendingDays.size() == 0){
			pendingCases.clear();
		}
		for(int i = 0; i < finishedAds.size(); i++){
			AdHandler ad = finishedAds.get(i);
			ad.finishedPosition = i;
		}
		if(starting){
			if(counter < 1){
				backToMenuPoppedOut = false;
				beginPoppedOut = false;
				counter++;
			}
			return;
		}
		else{
			if(part == GamePart.BeginDay){
				if(money < 0){
					bankrupt = true;
				}
				moneyToday = 0;
				if(!updated){
					updated = true;
					dailyCases.clear();
					if(totalMoney >= 16700000){
						part = GamePart.GameWon;
					}
					for(int i = 0; i < pendingCases.size(); i++){
						CaseHandler c = pendingCases.get(i);
						//System.out.println("Pending: " + c.name);
						int days = pendingDays.get(i);
						//System.out.println(days);
						pendingDays.set(i, days - 1);
					}
					if(monthDay == 31){
						monthDay = 0;
						payingFees = true;
					}
				}
			}
			else if (part == GamePart.MiddleDay){
				if(reputation < 10 && !updated){
					casesToday = r.nextInt(2);
					totalCases += casesToday;
					updated = true;
					reputationDay++;
					if(reputationDay >= 31){
						reputation++;
						reputationDay = 0;
					}
					for(int i = 0; i < casesToday; i++){
						dailyCases.add(new CaseHandler(ss));
					}
				}
				else if(reputation < 25 && !updated && reputation >= 10){
					casesToday = r.nextInt(2) + 1;
					totalCases += casesToday;
					updated = true;
					reputationDay++;
					if(reputationDay >= 31){
						reputation++;
						reputationDay = 0;
					}
					for(int i = 0; i < casesToday; i++){
						dailyCases.add(new CaseHandler(ss));
					}
				}
				else if(reputation < 50 && !updated && reputation >= 25){
					casesToday = r.nextInt(2) + 2;
					totalCases += casesToday;
					updated = true;
					reputationDay++;
					if(reputationDay >= 31){
						reputation++;
						reputationDay = 0;
					}
					for(int i = 0; i < casesToday; i++){
						dailyCases.add(new CaseHandler(ss));
					}
				}
				else if(reputation < 75 && !updated && reputation >= 50){
					casesToday = r.nextInt(2) + 3;
					totalCases += casesToday;
					updated = true;
					reputationDay++;
					if(reputationDay >= 31){
						reputation++;
						reputationDay = 0;
					}
					for(int i = 0; i < casesToday; i++){
						dailyCases.add(new CaseHandler(ss));
					}
				}
				else if(!updated && reputation >= 75){
					casesToday = r.nextInt(2) + 4;
					totalCases += casesToday;
					updated = true;
					reputationDay++;
					if(reputationDay >= 31){
						reputation++;
						reputationDay = 0;
					}
					for(int i = 0; i < casesToday; i++){
						dailyCases.add(new CaseHandler(ss));
					}
				}
				
				for(int i = 0; i < ads.size(); i++){
					AdHandler ad = ads.get(i);
					ad.update();
				}
				
				for(int i = 0; i < ads.size(); i++){
					AdHandler ad = ads.get(i);
					if(ad.finished){
						if(!finishedAds.contains(ad)){
							adNotify(ad);
						}
					}
				}
				adPayment = true;
				part = GamePart.EndDay;
				updated = false;
			}
			else if (part == GamePart.EndDay){
				if(!updated){
					money += moneyToday;
					totalMoney += moneyToday;
					updated = true;
					for(int i = 0; i < pendingCases.size(); i++){
						CaseHandler cases = pendingCases.get(i);
						if(pendingDays.get(i) <= 0){
							outcomeReady = true;
							pendingCases.remove(cases);
							pendingDays.remove(i);
							finishedCases.add(cases);
						}
					}
				}
			}
		}
	}
	
	public void adNotify(AdHandler ad){
		finishedAds.add(ad);
		adPayment = true;
	}
	//Currently trying to get the clock hand to go all the way around, not just halfway and then reverse
	public void render(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		
		if(part == GamePart.GameWon){
			g.setColor(Color.white);
			g.fillRoundRect(50, 50, (int)ss.WIDTH - 100, (int)ss.HEIGHT - 100, 20, 20);
			g.setColor(Color.black);
			g.setFont(fntLarge);
			g.drawString("Congratulations!", 250, 125);
			g.setFont(fntMed);
			g.drawString("Well, despite what I had hoped,", 220, 400);
			g.drawString("you actually did alright after all.", 220, 440);
			g.drawString("You might actually make a decent lawyer!", 155, 480);
			g.setColor(Color.green);
			g.fillRoundRect(320, 500, 250, 100, 10, 10);
			g.setFont(fntLarge);
			g.setColor(Color.black);
			g.drawString("Yaayyy!!!", 340, 560);
		}
		else{
			if(clockRunning){
				if(minuteY == 330){
					minuteXIncrement *= -1;
					if(clockUp){
						clockUp = false;
					}
					else if(!clockUp){
						clockUp = true;
					}
				}
				minuteX += minuteXIncrement;
				if(clockUp){
					minuteY = (int)Math.sqrt(40000 + Math.pow(((minuteX) - 440), 2)) - 60;
				}
				else if(!clockUp){
					minuteY = (int)Math.sqrt(40000 - Math.pow((minuteX - 440), 2)) + 330;
				}
			}
			//Adding clock functionality, the hands going around, etc.
			if(ss.level.part == GamePart.MiddleDay){
				/*g.setColor(Color.white);
				g.fillOval(190, 80, 500, 500);
				g.setColor(Color.black);
				g2.setStroke(new BasicStroke(6));
				g2.drawOval(188, 78, 500, 500);
				g.setFont(fntLarge);
				g.drawString("12", (190 + 500) - 282, 130);
				g.drawString("6", (190 + 500) - 265, 560);
				g.drawString("3", 650, (190 + 500) - 340);
				g.drawString("9", 200, (190 + 500) - 340);
				g2.drawLine((190 + 500) - 250, (80 + 500) - 250, minuteX, minuteY);
				g2.drawLine((190 + 500) - 250, (80 + 500) - 250, hourX, hourY);*/
				adPayment = true;
			}
			
			if(ss.level.part == GamePart.EndDay){
				//ss.getGraphics().setColor(Color.white);
				//ss.getGraphics().fillRect(100, 100, (int)ss.WIDTH - 200, (int)ss.HEIGHT - 200);
			}
			
			if(starting){
				g.setColor(Color.WHITE);
				g.fillRoundRect(50, 50, ss.getWidth() - 100, ss.getHeight() - 100, 20, 20);
				g.setFont(fntLarge);
				g.setColor(Color.black);
				g.drawString("Hello!", 360, 100);
				g.setFont(fntMed);
				g.drawString("Welcome to Rainmaker, where the goal is to,", 100, 150);
				g.drawString("well, make it rain. The game is simple: you", 100, 185);
				g.drawString("are a lawyer who has just decided to create", 100, 220);
				g.drawString("his own law firm. You will start with a small", 100, 255);
				g.drawString("sum of money, and must avoid going bankrupt.", 100, 290);
				g.setFont(fntLarge);
				g.drawString("Good Luck!", 300, 350);
				
				if(beginPoppedOut){
					g.setColor(brown);
					g.fillRoundRect(ss.getWidth() -350, 400, 300, 50, 10, 10);
					g.setColor(Color.black);
					g.setFont(fntMed);
					g.drawString("Begin!", ss.getWidth() - 320, 435);
				}
				else{
					g.setColor(brown);
					g.fillRoundRect(ss.getWidth() -250, 400, 200, 50, 10, 10);
					g.setColor(Color.black);
					g.setFont(fntMed);
					g.drawString("Begin!", ss.getWidth() - 220, 435);
				}
				
				if(backToMenuPoppedOut){
					g.setColor(brown);
					g.fillRoundRect(ss.getWidth() - 350, 500, 300, 50, 10, 10);
					g.setColor(Color.black);
					g.setFont(fntMed);
					g.drawString("Back to Menu", ss.getWidth() - 345, 535);
				}
				else{
					g.setColor(brown);
					g.fillRoundRect(ss.getWidth() - 250, 500, 200, 50, 10, 10);
					g.setColor(Color.black);
					g.setFont(fntMed);
					g.drawString("Back to Menu", ss.getWidth() - 245, 535);
				}
			}
			else{
				if(part == GamePart.BeginDay && ss.getState() == GameState.Game){
					if(bankrupt){
						g.setColor(Color.WHITE);
						g.fillRoundRect(50, 50, (int)ss.WIDTH - 100, (int)ss.HEIGHT - 100, 20, 20);
						g.setFont(fntLarge);
						g.setColor(Color.black);
						g.drawString("Bankrupt!", 325, 150);
						g.setFont(fntMed);
						g.drawString("Dadgummit!", 350, 400);
						g.drawString("I seem to have failed.", 300, 450);
						g.setColor(Color.green);
						g.fillRoundRect((int)ss.WIDTH / 2 - 150, 500, 300, 100, 10, 10);
						g.setColor(Color.black);
						g.setFont(fntLarge);
						g.drawString("Quit Life", 343, 565);
						g.drawImage(trump, 300, 165, trump.getWidth(null) / 2, trump.getHeight(null) / 2, null);
					}
					else{
						if(payingFees){
							g.setColor(Color.white);
							g.setFont(fntMed);
							g.fillRoundRect(100, 100, (int)ss.WIDTH - 200, (int)ss.HEIGHT - 200, 20, 20);
							g.setColor(Color.black);
							int due = (int)monthlyFees;
							for(int i = 0; i < ads.size(); i++){
								AdHandler ad = ads.get(i);
								if(ad.monthly){
									due -= ad.price;
								}
							}
							g.drawString("Monthly Fees Due: $" + due, 260, 225);
							g.setColor(Color.green);
							g.fillRoundRect((int)ss.WIDTH / 2 - 100, (int)ss.HEIGHT - 250, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 410, 490);
						}
						else{
							int green = 0;
							int red = 200;
							int startX = 400;
							g.setFont(fntLarge);
							g.setColor(Color.white);
							g.fillRoundRect(50, 50, ss.getWidth() - 100, ss.getHeight() - 100, 20, 20);
							g.setColor(Color.black);
							g.drawString("Day " + (int)day, 370, 100);
							g.setFont(fntMed);
							g.drawString("Money: $" + (int)money, 75, 200);
							g.drawString("Total Money Made: $" + (int)totalMoney, 75, 250);
							g.drawString("Weekly Fees: $" + (int)weeklyFees, 75, 300);
							g.drawString("Monthly Fees: $" + (int)monthlyFees, 75, 350);
							for(int i = 0; i < 200; i++){
								g.setColor(new Color(red, green, 0));
								g.fillRect(startX, 310, 1, 50);
								startX++;
								g.fillRect(startX, 310, 1, 50);
								green++;
								red--;
								startX++;
							}
							g.setColor(Color.black);
							g.fillRect(397 + ((int)reputation * 4), 290, 3, 70);
							g.drawString("Reputation:", 510, 285);
							g.setColor(brown);
							g.fillRoundRect(100, 400, 300, 75, 10, 10);
							g.fillRoundRect(100, 500, 300, 75, 10, 10);
							g.fillRoundRect(ss.getWidth() - 400, 400, 300, 75, 10, 10);
							g.fillRoundRect(ss.getWidth() - 400, 500, 300, 75, 10, 10);
							g.setColor(Color.black);
							g.drawString("Buy Advertising", 140, 445);
							g.drawString("Buy Better Facilities", 109, 545);
							g.drawString("Hire Employees", 535, 445);
							g.drawString("Quit to Menu", 557, 545);
							g.setColor(Color.GREEN);
							g.fillRoundRect(470, 175, 250, 50, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntMed);
							g.drawString("Continue --->", 505, 210);
						}
					}
				}
				else if (part == GamePart.MiddleDay && ss.getState() == GameState.Game){
					
				}
				else if(part == GamePart.EndDay && ss.getState() == GameState.Game && outcomeReady){
					g.setColor(Color.WHITE);
					g.fillRoundRect(100, 100, (int)ss.WIDTH - 200, (int)ss.HEIGHT - 200, 20, 20);
					g.setColor(Color.BLACK);
					g.setFont(fntLarge);
					g.setColor(Color.black);
					g.drawString("Case Results", 275, 150);
					int y = 200;
					g.setFont(fntMed);
					for(int i = 0; i < finishedCases.size(); i++){
						CaseHandler c = finishedCases.get(i);
						g.drawString(c.name + ",", 120, y);
						if(c.outcome == Outcome.Settle){
							g.drawString("Settlement: $" + c.settlement, 400, y);
							g.setColor(Color.green);
							g.fillRoundRect(650, y - 35, 110, 50, 10, 10);
							g.setColor(Color.black);
							g.drawString("Collect", 655, y);
						}
						else if(c.outcome == Outcome.Lose){
							g.drawString("Loss: $0", 400, y);
							g.setColor(Color.green);
							g.fillRoundRect(650, y - 35, 110, 50, 10, 10);
							g.setColor(Color.black);
							g.drawString("OK", 675, y);
						}
						else if(c.outcome == Outcome.Win){
							g.drawString("Win: $" + c.maxWin, 400, y);
							g.setColor(Color.green);
							g.fillRoundRect(650, y - 35, 110, 50, 10, 10);
							g.setColor(Color.black);
							g.drawString("Collect", 655, y);
						}
						y += 50;
					}
					
					if(finishedCases.size() == 0){
						g.setFont(fntLarge);
						g.setColor(Color.black);
						g.drawString("None", 375, 350);
						g.setColor(Color.red);
						g.fillRoundRect((int)ss.WIDTH - 160, 110, 50, 50, 10, 10);
						g.setColor(Color.black);
						g.setFont(fntLarge);
						g.drawString("X", (int)ss.WIDTH - 152, 152);
					}
				}
				else if (part == GamePart.EndDay && ss.getState() != GameState.Advertising && ss.getState() != GameState.Employees
						&& ss.getState() != GameState.BackToMenu && ss.getState() != GameState.Facilities
						&& !outcomeReady){
					g.setColor(Color.WHITE);
					g.fillRoundRect(100, 100, (int)ss.WIDTH - 200, (int)ss.HEIGHT - 200, 20, 20);
					g.setColor(Color.BLACK);
					g.setFont(fntMed);
					g.drawString("Ad Type:", 100, 90);
					g.drawString("Price:", 400, 90);
					g.drawString("Renew?", 600, 90);
					if(adPayment){
						int y = 150;
						numberAds = 0;
						adNumbers.clear(); //Check if this will work
						
						if(finishedAds.size() == 0){
							g.setFont(fntLarge);
							g.setColor(Color.BLACK);
							g.drawString("None", 375, 300);
							g.setColor(Color.RED);
							g.fillRoundRect((int)ss.WIDTH - 160, 110, 50, 50, 5, 5);
							g.setColor(Color.BLACK);
							g.drawString("X", (int)ss.WIDTH - 152, 152);
						}else{
							for(int i = 0; i < finishedAds.size(); i++){
								if(notEnoughMoney){
									moneyCounter++;
									g.setColor(Color.red);
									g.setFont(fntMed);
									g.drawString("Not enough money to renew!", 275, 500);
								}
								if(moneyCounter >= 500){
									moneyCounter = 0;
									notEnoughMoney = false;
								}
								g.setFont(fntMed);
								g.setColor(Color.BLACK);
								g.drawString(finishedAds.get(i).name, 100, y);
								g.drawString("$" + Integer.toString(finishedAds.get(i).price), 410, y);
								g.setColor(Color.GREEN);
								g.fillRoundRect(575, y - 35, 60, 40, 5, 5);
								g.fillRoundRect(675, y - 35, 60, 40, 5, 5);
								g.setColor(Color.BLACK);
								g.drawString("Yes", 580, y - 5);
								g.drawString("No", 685, y - 5);
								y += 60;
								adNumbers.add(numberAds);
								numberAds++;
								if(finishedAds.size() == 0){
									
								}
							}
						}
					}
					else{
						g.setColor(Color.WHITE);
						g.fillRoundRect(50, 50, (int)ss.WIDTH - 100, (int)ss.HEIGHT - 100, 20, 20);
						g.setFont(fntLarge);
						g.setColor(Color.black);
						g.drawString("End of Day " + (int)day, 300, 125);
						g.setColor(Color.GREEN);
						g.fillRoundRect((int)ss.WIDTH - 210, (int)ss.HEIGHT - 160, 150, 100, 20, 20);
						g.setColor(Color.BLACK);
						g.setFont(fntMed);
						g.drawString("Continue", 700, 575);
						g.drawString("Cases", 75, 180);
						g.drawString("Settlement Profit", 200, 180);
						g.drawString("Maximum", 475, 180);
						g.drawString("Cases: " + pendingCases.size() + "/" + maxCases, 650, 180);
						g2.setStroke(new BasicStroke(5));
						g2.drawLine(55, 190, (int)ss.WIDTH - 55, 190);
						if(dailyCases.size() == 0){
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("None", 350, 350);
						}
						int caseY = 240;
						
						for(int i = 0; i < dailyCases.size(); i++){
							int size = dailyCases.size();
							CaseHandler cases = dailyCases.get(i);
							g.setFont(fntMed);
							if(i < size){
								casesRunning = true;
								g.drawString(cases.name, 75, caseY);
								g.drawString("$" + Integer.toString(cases.settlement), 350, caseY);
								g.drawString("$" + Integer.toString(cases.maxWin), 500, caseY);
								if(cases.type == CaseType.Divorce || cases.type == CaseType.ChildCustody){
									g.setColor(Color.GREEN);
									g.fillRoundRect(670, caseY - 35, 100, 50, 10, 10);
									g.setFont(fntMed);
									g.setColor(Color.BLACK);
									g.drawString("File", 695, caseY);
								}
								else{
									g.setColor(Color.GREEN);
									g.fillRoundRect(600, caseY - 35, 75, 50, 10, 10);
									g.fillRoundRect(680, caseY - 35, 75, 50, 10, 10);
									g.fillRoundRect(760, caseY - 35, 80, 50, 10, 10);
									g.setFont(fntMed);
									g.setColor(Color.BLACK);
									g.setFont(new Font("arial", 1, 25));
									g.drawString("Settle", 605, caseY);
									g.drawString("Fight", 687, caseY);
									g.drawString("Reject", 762, caseY);
								}
							}
							casesRunning = false;
							caseY += 60;
						}
						
						if(notContinue){
							g.setColor(Color.RED);
							g.setFont(fntMed);
							g.drawString("Must resolve all cases before continuing", 100, 600);
							continueCounter++;
						}
						
						if(continueCounter >= 800){
							notContinue = false;
							continueCounter = 0;
						}
						
						if(overMax){
							g.setColor(Color.RED);
							g.setFont(fntMed);
							g.drawString("You are too busy to take this case!", 100, 600);
							maxCounter++;
						}
						
						if(maxCounter >= 800){
							overMax = false;
							maxCounter = 0;
						}
					}
				}
				else if (ss.getState() == GameState.Advertising){
					g2.setColor(brown);
					g2.setStroke(new BasicStroke(5));
					g2.fillRect(0, 0, ss.getWidth(), ss.getHeight());
					g2.setColor(Color.black);
					g2.drawRect(0, 0, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(ss.getWidth() / 2, 0, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(0, ss.getHeight() / 9, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(ss.getWidth() / 2, ss.getHeight() / 9, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(0, ss.getHeight() / 9 * 2, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(ss.getWidth() / 2, ss.getHeight() / 9 * 2, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(0, ss.getHeight() / 9 * 3, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(ss.getWidth() / 2, ss.getHeight() / 9 * 3, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(0, ss.getHeight() / 9 * 4, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(ss.getWidth() / 2, ss.getHeight() / 9 * 4, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(0, ss.getHeight() / 9 * 5, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(ss.getWidth() / 2, ss.getHeight() / 9 * 5, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(0, ss.getHeight() / 9 * 6, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(ss.getWidth() / 2, ss.getHeight() / 9 * 6, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(0, ss.getHeight() / 9 * 7, ss.getWidth() / 2, ss.getHeight() / 9);
					g2.drawRect(ss.getWidth() / 2, ss.getHeight() / 9 * 7, ss.getWidth() / 2, ss.getHeight() / 9);
					g.setFont(fntLarge);
					g.drawString("Type", 150, 50);
					g.drawString("Price", 600, 50);
					g.setFont(fntMed);
					g.drawString("Radio", 165, 115);
					g.drawString("$150 per week", 555, 115);
					g.drawString("Magazine", 145, 187);
					g.drawString("$10,000 per month (single ad)", 460, 187);
					g.drawString("Online", 165, 260);
					g.drawString("$20,000 per month", 535, 260);
					g.drawString("Newspaper", 135, 330);
					g.drawString("$6,000 per week (one ad a day)", 452, 330);
					g.drawString("Billboard", 152, 400);
					g.drawString("$25,000 per week", 545, 400);
					g.drawString("TV Advertisement", 90, 470);
					g.drawString("$100,000 per day", 545, 470);
					g.drawString("Direct Mail", 144, 540);
					g.drawString("$2 per letter (min. of 1,000)", 480, 540);
					if(adBackPopped){
						g.fillRoundRect(ss.getWidth() - 300, 585, 300, 50, 10, 10);
						g.setColor(Color.white);
						g.drawString("Back", ss.getWidth() - 285, 620);
					}
					else{
						g.fillRoundRect(ss.getWidth() - 200, 585, 200, 50, 10, 10);
						g.setColor(Color.white);
						g.drawString("Back", ss.getWidth() - 185, 620);
					}
					
					g.fillRoundRect(365, 85, 75, 40, 10, 10);
					g.fillRoundRect(365, 160, 75, 40, 10, 10);
					g.fillRoundRect(365, 230, 75, 40, 10, 10);
					g.fillRoundRect(365, 300, 75, 40, 10, 10);
					g.fillRoundRect(365, 370, 75, 40, 10, 10);
					g.fillRoundRect(365, 440, 75, 40, 10, 10);
					g.fillRoundRect(365, 510, 75, 40, 10, 10);
					g.setColor(Color.black);
					g.drawString("Buy", 375, 115);
					g.drawString("Buy", 375, 190);
					g.drawString("Buy", 375, 260);
					g.drawString("Buy", 375, 330);
					g.drawString("Buy", 375, 400);
					g.drawString("Buy", 375, 470);
					g.drawString("Buy", 375, 540);
					
					if(buyingRadio){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 150){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Purchase", 285, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
					
					if(buyingMagazine){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 10000){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Purchase", 285, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
					
					if(buyingOnline){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 20000){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Purchase", 285, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
					
					if(buyingNewpaper){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 6000){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Purchase", 285, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
					
					if(buyingTV){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 100000){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Purchase", 285, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
					
					
					if(buyingBillboard){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 25000){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Purchase", 285, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
					
					if(buyingMail){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 2000){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Purchase", 285, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
				}
				else if (ss.getState() == GameState.BackToMenu){
					g.setColor(Color.black);
					g.setFont(fntLarge);
					g.drawString("Are you sure?", 260, 125);
					if(backYesPopped){
						g.setColor(Color.black);
						g.fillRoundRect(ss.getWidth() - 300, 400, 300, 50, 10, 10);
						g.setFont(fntMed);
						g.setColor(Color.white);
						g.drawString("Yes", ss.getWidth() - 250, 435);
					}
					else{
						g.setColor(Color.black);
						g.fillRoundRect(ss.getWidth() - 200, 400, 200, 50, 10, 10);
						g.setFont(fntMed);
						g.setColor(Color.white);
						g.drawString("Yes", ss.getWidth() - 150, 435);
					}

					if(backNoPopped){
						g.setColor(Color.black);
						g.fillRoundRect(ss.getWidth() - 300, 500, 300, 50, 10, 10);
						g.setFont(fntMed);
						g.setColor(Color.white);
						g.drawString("No", ss.getWidth() - 250, 535);
					}
					else{
						g.setColor(Color.black);
						g.fillRoundRect(ss.getWidth() - 200, 500, 200, 50, 10, 10);
						g.setFont(fntMed);
						g.setColor(Color.white);
						g.drawString("No", ss.getWidth() - 150, 535);
					}
				}
				else if (ss.getState() == GameState.Employees){
					g2.setStroke(new BasicStroke(5));
					g.setFont(fntMed);
					g.setColor(Color.black);
					g.drawString("Total Employees (including yourself): " + (int)employees + "/" + (int)maxEmployees, 165, 40);
					g2.drawRect(0, (ss.getHeight() - 75) / 9, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(ss.getWidth() / 2, (ss.getHeight() - 75) / 9, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(0, (ss.getHeight() - 75) / 9 * 2, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(ss.getWidth() / 2, (ss.getHeight() - 75) / 9 * 2, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(0, (ss.getHeight() - 75) / 9 * 3, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(ss.getWidth() / 2, (ss.getHeight() - 75) / 9 * 3, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(0, (ss.getHeight() - 75) / 9 * 4, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(ss.getWidth() / 2, (ss.getHeight() - 75) / 9 * 4, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(0, (ss.getHeight() - 75) / 9 * 5, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(ss.getWidth() / 2, (ss.getHeight() - 75) / 9 * 5, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(0, (ss.getHeight() - 75) / 9 * 6, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(ss.getWidth() / 2, (ss.getHeight() - 75) / 9 * 6, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(0, (ss.getHeight() - 75) / 9 * 7, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(ss.getWidth() / 2, (ss.getHeight() - 75) / 9 * 7, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(0, (ss.getHeight() - 75) / 9 * 8, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					g2.drawRect(ss.getWidth() / 2, (ss.getHeight() - 75) / 9 * 8, ss.getWidth() / 2, (ss.getHeight() - 75) / 9);
					if(employeesBackPopped){
						g.fillRoundRect(ss.getWidth() - 300, 585, 300, 50, 10, 10);
						g.setColor(Color.white);
						g.drawString("Back", ss.getWidth() - 285, 620);
					}
					else{
						g.fillRoundRect(ss.getWidth() - 200, 585, 200, 50, 10, 10);
						g.setColor(Color.white);
						g.drawString("Back", ss.getWidth() - 185, 620);
					}
					g.setFont(fntLarge);
					g.setColor(Color.black);
					g.drawString("Type", 150, 110);
					g.drawString("Annual Salary", 500, 110);
					g.setFont(fntMed);
					g.drawString("Associate Attorney", 85, 170);
					g.drawString("$68,000", 600, 170);
					g.drawString("Partner", 10, 230);
					g.setFont(fntSmall);
					g.drawString("(Cannot hire, must be promoted ", 125, 215);
					g.drawString("from existing Associate Attorney)", 125, 235);
					g.setFont(fntMed);
					g.drawString("$176,000", 590, 230);
					g.drawString("Law Clerk", 140, 290);
					g.drawString("$20,000", 600, 290);
					g.drawString("Paralegal", 140, 355);
					g.drawString("$45,000", 600, 355);
					g.drawString("Legal Assistant", 105, 420);
					g.drawString("$41,000", 600, 420);
					g.drawString("Receptionist", 130, 480);
					g.drawString("$30,000", 600, 480);
					g.drawString("Investigator", 135, 540);
					g.drawString("$51,000", 600, 540);
					g.setColor(Color.white);
					g.fillRoundRect(360, 140, 80, 40, 10, 10);
					g.fillRoundRect(360, 200, 80, 40, 10, 10);
					g.fillRoundRect(360, 260, 80, 40, 10, 10);
					g.fillRoundRect(360, 325, 80, 40, 10, 10);
					g.fillRoundRect(360, 390, 80, 40, 10, 10);
					g.fillRoundRect(360, 450, 80, 40, 10, 10);
					g.fillRoundRect(360, 515, 80, 40, 10, 10);
					g.setColor(Color.black);
					g.drawString("Hire", 370, 170);
					g.setFont(new Font("arial", 1, 20));
					g.drawString("Promote", 360, 225);
					g.setFont(fntMed);
					g.drawString("Hire", 370, 290);
					g.drawString("Hire", 370, 355);
					g.drawString("Hire", 370, 420);
					g.drawString("Hire", 370, 480);
					g.drawString("Hire", 370, 545);
					
					if(hiringAssociate){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= monthlyFees + (68000 / 12) && employees + 1 <= maxEmployees){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Hire", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							if(employees + 1 <= maxEmployees){
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
							else{
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Not enough room for", ss.getWidth() / 2 - 150, ss.getHeight() / 2 - 50);
								g.drawString("another employee", ss.getWidth() / 2 - 130, ss.getHeight() / 2 - 10);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
						}
					}
					
					if(promotingPartner){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= monthlyFees + (176000/ 12) - (68000 / 12) && associates > 0){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Hire", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							if(associates > 0){
								g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							}
							else{
								
								g.drawString("Not any associates to", ss.getWidth() / 2 - 160, ss.getHeight() / 2 - 50);
								g.drawString("promote!", ss.getWidth() / 2 - 70, ss.getHeight() / 2);
								
							}
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
					
					if(hiringClerk){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= monthlyFees + (20000 / 12) && employees + 1 <= maxEmployees){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Hire", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							if(employees + 1 <= maxEmployees){
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
							else{
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Not enough room for", ss.getWidth() / 2 - 150, ss.getHeight() / 2 - 50);
								g.drawString("another employee", ss.getWidth() / 2 - 130, ss.getHeight() / 2 - 10);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
						}
					}
					
					if(hiringParalegal){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= monthlyFees + (45000 / 12) && employees + 1 <= maxEmployees){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Hire", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							if(employees + 1 <= maxEmployees){
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
							else{
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Not enough room for", ss.getWidth() / 2 - 150, ss.getHeight() / 2 - 50);
								g.drawString("another employee", ss.getWidth() / 2 - 130, ss.getHeight() / 2 - 10);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
						}
					}
					
					if(hiringAssistant){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= monthlyFees + (41000 / 12) && employees + 1 <= maxEmployees){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Hire", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							if(employees + 1 <= maxEmployees){
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
							else{
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Not enough room for", ss.getWidth() / 2 - 150, ss.getHeight() / 2 - 50);
								g.drawString("another employee", ss.getWidth() / 2 - 130, ss.getHeight() / 2 - 10);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
						}
					}
					
					if(hiringReceptionist){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= monthlyFees + (30000 / 12) && employees + 1 <= maxEmployees){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Hire", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							if(employees + 1 <= maxEmployees){
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
							else{
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Not enough room for", ss.getWidth() / 2 - 150, ss.getHeight() / 2 - 50);
								g.drawString("another employee", ss.getWidth() / 2 - 130, ss.getHeight() / 2 - 10);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
						}
					}
					
					if(hiringInvestigator){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= monthlyFees + (51000 / 12) && employees + 1 <= maxEmployees){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Hire", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else{
							if(employees + 1 <= maxEmployees){
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
							else{
								g.setFont(fntMed);
								g.setColor(Color.black);
								g.drawString("Not enough room for", ss.getWidth() / 2 - 150, ss.getHeight() / 2 - 50);
								g.drawString("another employee", ss.getWidth() / 2 - 130, ss.getHeight() / 2 - 10);
								g.setColor(brown);
								g.fillRoundRect(345, 350, 200, 100, 10, 10);
								g.setColor(Color.black);
								g.setFont(fntLarge);
								g.drawString("OK", 400, 415);
							}
						}
					}
				}
				else if (ss.getState() == GameState.Facilities){
					g.setFont(fntMed);
					g.setColor(Color.black);
					if(facilitiesBackPopped){
						g.fillRoundRect(ss.getWidth() - 300, 585, 300, 50, 10, 10);
						g.setColor(Color.white);
						g.drawString("Back", ss.getWidth() - 285, 620);
					}
					else{
						g.fillRoundRect(ss.getWidth() - 200, 585, 200, 50, 10, 10);
						g.setColor(Color.white);
						g.drawString("Back", ss.getWidth() - 185, 620);
					}
					g.setFont(fntLarge);
					g.setColor(Color.black);
					g.drawString("Rent Buildings", 260, 65);
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(0, 150, (int)ss.WIDTH, 150);
					g.setFont(fntMed);
					g.drawString("Size", 25, 140);
					g.drawString("Max Employees", 110, 140);
					g.drawString("Monthly Fees", 360, 140);
					g.drawString("Current?", 570, 140);
					g.drawString("Rent", 755, 140);
					g2.drawLine(100, 105, 100, 490);
					g2.drawLine(350, 105, 350, 490);
					g2.drawLine(560, 105, 560, 490);
					g2.drawLine(700, 105, 700, 490);
					g2.drawLine(0, 105, (int)ss.WIDTH, 105);
					g.drawString("Tiny", 20, 190);
					g.drawString("2", 210, 190);
					g.drawString("$1,250", 410, 190);
					g.drawString("Small", 10, 260);
					g.drawString("4", 210, 260);
					g.drawString("$5,000", 410, 260);
					g.drawString("Med.", 20, 330);
					g.drawString("8", 210, 330);
					g.drawString("$8,000", 410, 330);
					g.drawString("Large", 10, 400);
					g.drawString("16", 200, 400);
					g.drawString("$14,000", 400, 400);
					g.drawString("Huge", 15, 470);
					g.drawString("32", 200, 470);
					g.drawString("$25,000", 400, 470);
					g2.drawLine(0, 210, (int)ss.WIDTH, 210);
					g2.drawLine(0, 280, (int)ss.WIDTH, 280);
					g2.drawLine(0, 350, (int)ss.WIDTH, 350);
					g2.drawLine(0, 420, (int)ss.WIDTH, 420);
					g2.drawLine(0, 490, (int)ss.WIDTH, 490);
					g.setColor(Color.green);
					g.fillRoundRect(750, 160, 100, 40, 10, 10);
					g.fillRoundRect(750, 225, 100, 40, 10, 10);
					g.fillRoundRect(750, 295, 100, 40, 10, 10);
					g.fillRoundRect(750, 365, 100, 40, 10, 10);
					g.fillRoundRect(750, 435, 100, 40, 10, 10);
					g.setColor(Color.black);
					g.drawString("Rent", 765, 190);
					g.drawString("Rent", 765, 255);
					g.drawString("Rent", 765, 325);
					g.drawString("Rent", 765, 395);
					g.drawString("Rent", 765, 465);
					if(building == Building.Tiny){
						g.drawString("Yes", 600, 190);
						cost = 1250;
					}
					else if(building == Building.Small){
						g.drawString("Yes", 600, 255);
						cost = 5000;
					}
					else if(building == Building.Medium){
						g.drawString("Yes", 600, 325);
						cost = 8000;
					}
					else if(building == Building.Large){
						g.drawString("Yes", 600, 395);
						cost = 14000;
					}
					else if(building == Building.Huge){
						g.drawString("Yes", 600, 465);
						cost = 25000;
					}
					
					if(rentingTiny){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 1250 + monthlyFees - cost && building != Building.Tiny){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Rent", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else if(money < 1250 + monthlyFees - cost){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("You are already renting this!", ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
					
					if(rentingSmall){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 5000 + monthlyFees - cost && building != Building.Small){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Rent", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else if(money < 5000 + monthlyFees - cost){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("You are already renting this!", ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
					
					if(rentingMed){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 8000 + monthlyFees - cost && building != Building.Medium){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Rent", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else if(money < 8000 + monthlyFees - cost){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("You are already renting this!", ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
					
					if(rentingLarge){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 14000 + monthlyFees - cost && building != Building.Large){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Rent", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else if(money < 14000 + monthlyFees - cost){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("You are already renting this!", ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
					
					if(rentingHuge){
						g.setColor(Color.white);
						g.fillRoundRect(ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 150, 400, 300, 10, 10);
						if(money >= 25000 + monthlyFees - cost && building != Building.Huge){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Are you sure?", ss.getWidth() / 2 - 100, ss.getHeight() / 2 - 100);
							g.setColor(brown);
							g.fillRoundRect(275, 350, 150, 80, 10, 10);
							g.fillRoundRect(460, 350, 150, 80, 10, 10);
							g.setColor(Color.black);
							g.drawString("Rent", 295, 400);
							g.drawString("Cancel", 485, 400);
						}
						else if(money < 25000 + monthlyFees - cost){
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("Sorry, not enough money", ss.getWidth() / 2 - 175, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
						else{
							g.setFont(fntMed);
							g.setColor(Color.black);
							g.drawString("You are already renting this!", ss.getWidth() / 2 - 200, ss.getHeight() / 2 - 50);
							g.setColor(brown);
							g.fillRoundRect(345, 350, 200, 100, 10, 10);
							g.setColor(Color.black);
							g.setFont(fntLarge);
							g.drawString("OK", 400, 415);
						}
					}
				}
			}
		}
	}
}
