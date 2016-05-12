package com.welliver.sidescroller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.welliver.sidescroller.AdHandler.AdType;
import com.welliver.sidescroller.CaseHandler.CaseType;
import com.welliver.sidescroller.CaseHandler.Outcome;
import com.welliver.sidescroller.Levels.Building;
import com.welliver.sidescroller.Levels.GamePart;
import com.welliver.sidescroller.Rainmaker.GameState;

public class Menu extends MouseAdapter{
	private Rainmaker ss;
	private Handler handler;
	private GameState state;
	private Image titleImage = null;
	private Random r;
	private int counter = 0;
	private int adButtonCounter = 0;
	private boolean countAdButtonCounter = true;
	Font fnt1 = new Font("arial", 1, 30);
	Font fnt2 = new Font("arial", 1, 50);
	private Image dollar;
	private boolean playPoppedOut = false;
	private boolean optionsPoppedOut = false;
	private boolean helpPoppedOut = false;
	private boolean quitPoppedOut = false;
	private boolean tooMuchMoney = false;
	private boolean yesPoppedOut = false;
	private boolean noPoppedOut = false;
	private boolean justChangedStates = false;
	private int stateChangeCounter = 0;
	private int moneyCounter = 0;
	private int backToMenuCounter = 0;
	private boolean justBackToMenu = false;
	private int numberOfAds = 0;
	private boolean optionsBackPopped = false;  
	private boolean helpBackPopped = false;
	private boolean musicYes = true;
	private boolean musicNo = false;
	
	public Menu(Rainmaker ss, Handler handler, GameState state){
		this.ss = ss;
		this.handler = handler;
		this.state = state;
		r = new Random();
		try{
			titleImage = ImageIO.read(new File("res/title.png"));
			dollar = ImageIO.read(new File("res/dollarbill.png"));
		} catch(IOException e){
			
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		float mx = e.getX();
		float my = e.getY();
		if(ss.getState() == GameState.Menu){
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
				if(my <= 300 && my >= 250){
					playPoppedOut = true;
				}
				else{
					playPoppedOut = false;
				}
			}
			
			if(playPoppedOut){
				if(mx < ss.WIDTH - 300){
					playPoppedOut = false;
				}
				if (my > 300 || my < 250){
					playPoppedOut = false;
				}
			}
			
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
				if (my <= 400 && my >= 350){
					optionsPoppedOut = true;
				}else {
					optionsPoppedOut = false;
				}
			}
			
			if(optionsPoppedOut){
				if(mx < ss.WIDTH - 300){
					optionsPoppedOut = false;
				}
				if (my > 400 || my < 350){
					optionsPoppedOut = false;
				}
			}
			
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
				if (my <= 500 && my >= 450){
					helpPoppedOut = true;
				}else {
					helpPoppedOut = false;
				}
			}
			
			if(helpPoppedOut){
				if(mx < ss.WIDTH - 300){
					helpPoppedOut = false;
				}
				if (my > 500 || my < 450){
					helpPoppedOut = false;
				}
			}
			
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
				if (my <= 600 && my >= 550){
					quitPoppedOut = true;
				}else {
					quitPoppedOut = false;
				}
			}
			
			if(quitPoppedOut){
				if(mx < ss.WIDTH - 300){
					quitPoppedOut = false;
				}
				if (my > 600 || my < 550){
					quitPoppedOut = false;
				}
			}
		}
		
		if(ss.getState() == GameState.Options){
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
				if (my <= 600 && my >= 550){
					optionsBackPopped = true;
				}else {
					optionsBackPopped = false;
				}
			}
			
			if(optionsBackPopped){
				if(mx < ss.WIDTH - 300){
					optionsBackPopped = false;
				}
				if (my > 600 || my < 550){
					optionsBackPopped = false;
				}
			}
		}
		
		if(ss.getState() == GameState.Help){
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
				if (my <= 600 && my >= 550){
					helpBackPopped = true;
				}else {
					helpBackPopped = false;
				}
			}
			
			if(helpBackPopped){
				if(mx < ss.WIDTH - 300){
					helpBackPopped = false;
				}
				if (my > 600 || my < 550){
					helpBackPopped = false;
				}
			}
		}
		
		if(ss.getState() == GameState.Game && ss.level.starting){
			if (mx >= ss.getWidth() - 250 && mx <= ss.getWidth() - 50){
				if(my >= 400 && my <= 450){
					ss.level.beginPoppedOut = true;
				}
				else{
					ss.level.beginPoppedOut = false;
				}
			}
			
			if(ss.level.beginPoppedOut){
				if(mx < ss.getWidth() - 350){
					ss.level.beginPoppedOut = false;
				}
				
				if(my < 400 || my > 450){
					ss.level.beginPoppedOut = false;
				}
			}
			
			if (mx >= ss.getWidth() - 250 && mx <= ss.getWidth() - 50){
				if(my >= 500 && my <= 550){
					ss.level.backToMenuPoppedOut = true;
				}
				else{
					ss.level.backToMenuPoppedOut = false;
				}
			}
			
			if(ss.level.backToMenuPoppedOut){
				if(mx < ss.getWidth() - 350){
					ss.level.backToMenuPoppedOut = false;
				}
				
				if(my < 500 || my > 550){
					ss.level.backToMenuPoppedOut = false;
				}
			}
		}
		
		if(ss.getState() == GameState.Advertising){
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 205){
				if(my <= 585 + 50 && my >= 585){
					ss.level.adBackPopped = true;
				}
				else{
					ss.level.adBackPopped = false;
				}
			}
			
			if(ss.level.adBackPopped){
				if(mx < ss.WIDTH - 305){
					ss.level.adBackPopped = false;
				}
				if (my > 585 + 50 || my < 585){
					ss.level.adBackPopped = false;
				}
			}
		}
		
		if(ss.getState() == GameState.Employees){
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 205){
				if(my <= 585 + 50 && my >= 585){
					ss.level.employeesBackPopped = true;
				}
				else{
					ss.level.employeesBackPopped = false;
				}
			}
			
			if(ss.level.employeesBackPopped){
				if(mx < ss.WIDTH - 305){
					ss.level.employeesBackPopped = false;
				}
				if (my > 585 + 50 || my < 585){
					ss.level.employeesBackPopped = false;
				}
			}
		}
		
		if(ss.getState() == GameState.Facilities){
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 205){
				if(my <= 585 + 50 && my >= 585){
					ss.level.facilitiesBackPopped = true;
				}
				else{
					ss.level.facilitiesBackPopped = false;
				}
			}
			
			if(ss.level.facilitiesBackPopped){
				if(mx < ss.WIDTH - 305){
					ss.level.facilitiesBackPopped = false;
				}
				if (my > 585 + 50 || my < 585){
					ss.level.facilitiesBackPopped = false;
				}
			}
		}
		
		if(ss.getState() == GameState.BackToMenu){
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 210){
				if(my <= 450 && my >= 400){
					ss.level.backYesPopped = true;
				}
				else{
					ss.level.backYesPopped = false;
				}
			}
			
			if(ss.level.backYesPopped){
				if(mx < ss.WIDTH - 310){
					ss.level.backYesPopped = false;
				}
				if (my > 450 || my < 400){
					ss.level.backYesPopped = false;
				}
			}
			
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
				if(my <= 550 && my >= 500){
					ss.level.backNoPopped = true;
				}
				else{
					ss.level.backNoPopped = false;
				}
			}
			
			if(ss.level.backNoPopped){
				if(mx < ss.WIDTH - 300){
					ss.level.backNoPopped = false;
				}
				if (my > 550 || my < 500){
					ss.level.backNoPopped = false;
				}
			}
		}
		
		if(ss.getState() == GameState.QuitScreen){
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
				if (my <= 500 && my >= 450){
					yesPoppedOut = true;
				}else {
					yesPoppedOut = false;
				}
			}
			
			if(yesPoppedOut){
				if(mx < ss.WIDTH - 300){
					yesPoppedOut = false;
				}
				if (my > 500 || my < 450){
					yesPoppedOut = false;
				}
			}
			
			if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
				if (my <= 600 && my >= 550){
					noPoppedOut = true;
				}else {
					noPoppedOut = false;
				}
			}
			
			if(noPoppedOut){
				if(mx < ss.WIDTH - 300){
					noPoppedOut = false;
				}
				if (my > 600 || my < 550){
					noPoppedOut = false;
				}
			}
		}
	}
	
	public void tick(){
		justBackToMenu = false;
	}
	
	public void render(Graphics g){
		if(ss.getState() == GameState.Menu){
			g.setColor(Color.WHITE);
			g.setFont(fnt1);
			g.drawImage(titleImage, (int)((ss.WIDTH * 0.5) - (0.5 * titleImage.getWidth(null))), 50, null);
			if(counter < 1){
				handler.add(new MenuDollar(0, r.nextInt(5) + 1, g, r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
				handler.add(new MenuDollar(0, r.nextInt(5) + 1, g, r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
				handler.add(new MenuDollar(0, r.nextInt(5) + 1, g, r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
				handler.add(new MenuDollar(0, r.nextInt(5) + 1, g, r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
				handler.add(new MenuDollar(0, r.nextInt(5) + 1, g, r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
				handler.add(new MenuDollar(0, r.nextInt(5) + 1, g, r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
				handler.add(new MenuDollar(0, r.nextInt(5) + 1, g, r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
				handler.add(new MenuDollar(0, r.nextInt(5) + 1, g, r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
				handler.add(new MenuDollar(0, r.nextInt(5) + 1, g, r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
				handler.add(new MenuDollar(0, r.nextInt(5) + 1, g, r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
				counter++;
			}
			if(playPoppedOut){
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 300, 250, 300, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Play",(int)ss.WIDTH - 270 , 285);
			} else{
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 200, 250, 200, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Play",(int)ss.WIDTH - 170 , 285);
			}
			
			if(optionsPoppedOut){
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 300, 350, 300, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Options", (int)ss.WIDTH - 270, 385);
			}else {
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 200, 350, 200, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Options", (int)ss.WIDTH - 170, 385);
			}
			
			if(helpPoppedOut){
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 300, 450, 300, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Help", (int)ss.WIDTH - 270, 485);
			}else {
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 200, 450, 200, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Help", (int)ss.WIDTH - 170, 485);
			}
			
			if(quitPoppedOut){
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 300, 550, 300, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Quit", (int)ss.WIDTH - 270, 585);
			}else {
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 200, 550, 200, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Quit", (int)ss.WIDTH - 170, 585);
			}
			
			if(tooMuchMoney){
				g.setColor(Color.red);
				g.drawString("You have too much money!", 260, 50);
				moneyCounter++;
				if(moneyCounter >= 500){
					moneyCounter = 0;
					tooMuchMoney = false;
				}
			}
		}
		
		if(ss.getState() == GameState.Options){
			Graphics2D g2 = (Graphics2D)g;
			g.setFont(fnt1);
			if(optionsBackPopped){
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 300, 550, 300, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Back", (int)ss.WIDTH - 280, 585);
			}else {
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 200, 550, 200, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Back", (int)ss.WIDTH - 180, 585);
			}
			
			g.setColor(Color.GREEN);
			g.drawString("Music: ", 75, 150);
			if(musicYes){
				g.setColor(Color.white);
				g.fillRect(175, 120, 60, 40);
				ss.musicStopped = false;
			}
			else if(musicNo){
				g.setColor(Color.white);
				g.fillRect(260, 120, 60, 40);
				ss.musicStopped = true;
			}
			g.setColor(Color.GREEN);
			g.drawString("Yes", 180, 150);
			g.drawString("No", 270, 150);
			g.setColor(ss.backgroundColor);
			g.fillRoundRect(75, 200, 250, 75, 10, 10);
			g.setColor(Color.GREEN);
			g.drawString("Random Song", 103, 250);
			g.setColor(Color.red);
			g.drawString("Song Credits", 350, ss.getHeight() / 2);
			g2.setStroke(new BasicStroke(5));
			g2.drawLine(15, 350, (int)ss.WIDTH - 15, 350);
			g.setFont(new Font("arial", 1, 20));
			g.setColor(Color.green);
			int loopY = 380;
			for(int i = 0; i < 6; i++){
				g.setColor(Color.YELLOW);
				g.fillRect(20, loopY - 20, 420, 30);
				loopY += 40;
			}
			loopY = 380;
			for(int i = 0; i < 2; i++){
				g.setColor(Color.YELLOW);
				g.fillRect(495, loopY - 20, (int)ss.WIDTH - 520, 30);
				loopY += 40;
			}
			g.setColor(Color.black);
			g.drawString("Swoope - Schizo / Hollow Dreams Interlude", 25, 380);
			g.drawString("Dre Murray - Pharaoh", 25, 420);
			g.drawString("Lecrae - Got Paper", 25, 460);
			g.drawString("Royal Ezenwa - Beware", 25, 500);
			g.drawString("Royal Ezenwa - Hollow Dreams", 25, 540);
			g.drawString("Derek Minor - Kingdom Come", 25, 580);
			g.drawString("Flame - Stackin'", 500, 380);
			g.drawString("Viktory - No Price Tag", 500, 420);
		}
		
		if(ss.getState() == GameState.Help){
			g.setFont(fnt1);
			if(helpBackPopped){
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 300, 550, 300, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Back", (int)ss.WIDTH - 280, 585);
			}else {
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 200, 550, 200, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Back", (int)ss.WIDTH - 180, 585);
			}
			
			if(ss.helpPage < 6){
				g.setColor(Color.green);
				g.fillRoundRect(700, 490, 150, 50, 10, 10);
				g.setColor(Color.black);
				g.drawString("Next", 740, 525);
			}
			
			if(ss.helpPage > 1){
				g.setColor(Color.green);
				g.fillRoundRect(50, 490, 150, 50, 10, 10);
				g.setColor(Color.black);
				g.drawString("Back", 90, 525);
			}
			
			if(ss.helpPage == 1){
				g.setColor(Color.white);
				g.fillRoundRect(120, 5, (int)(ss.one.getWidth(null) / 1.5) + 50, (int)(ss.one.getHeight(null) / 1.5) + 50, 10, 10);
				g.drawImage(ss.one, 145, 30, (int)(ss.one.getWidth(null) / 1.5), (int)(ss.one.getHeight(null) / 1.5), null);
			}
			if(ss.helpPage == 2){
				g.setColor(Color.white);
				g.fillRoundRect(120, 5, (int)(ss.one.getWidth(null) / 1.5) + 50, (int)(ss.one.getHeight(null) / 1.5) + 50, 10, 10);
				g.drawImage(ss.two, 145, 30, (int)(ss.one.getWidth(null) / 1.5), (int)(ss.one.getHeight(null) / 1.5), null);
			}
			if(ss.helpPage == 3){
				g.setColor(Color.white);
				g.fillRoundRect(120, 5, (int)(ss.one.getWidth(null) / 1.5) + 50, (int)(ss.one.getHeight(null) / 1.5) + 50, 10, 10);
				g.drawImage(ss.three, 145, 30, (int)(ss.one.getWidth(null) / 1.5), (int)(ss.one.getHeight(null) / 1.5), null);
			}
			if(ss.helpPage == 4){
				g.setColor(Color.white);
				g.fillRoundRect(120, 5, (int)(ss.one.getWidth(null) / 1.5) + 50, (int)(ss.one.getHeight(null) / 1.5) + 50, 10, 10);
				g.drawImage(ss.four, 145, 30, (int)(ss.one.getWidth(null) / 1.5), (int)(ss.one.getHeight(null) / 1.5), null);
			}
			if(ss.helpPage == 5){
				g.setColor(Color.white);
				g.fillRoundRect(120, 5, (int)(ss.one.getWidth(null) / 1.5) + 50, (int)(ss.one.getHeight(null) / 1.5) + 50, 10, 10);
				g.drawImage(ss.five, 145, 30, (int)(ss.one.getWidth(null) / 1.5), (int)(ss.one.getHeight(null) / 1.5), null);
			}
			if(ss.helpPage == 6){
				g.setColor(Color.white);
				g.fillRoundRect(120, 5, (int)(ss.one.getWidth(null) / 1.5) + 50, (int)(ss.one.getHeight(null) / 1.5) + 50, 10, 10);
				g.drawImage(ss.six, 145, 30, (int)(ss.one.getWidth(null) / 1.5), (int)(ss.one.getHeight(null) / 1.5), null);
			}
		}
		
		if(ss.getState() == GameState.QuitScreen){
			handler.removeAll();
			counter = 0;
			g.setFont(fnt2);
			g.setColor(Color.WHITE);
			g.drawString("Are you sure you want to quit?", 75, 100);
			g.setFont(fnt1);
			
			if(yesPoppedOut){
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 300, 450, 300, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Yes", (int)ss.WIDTH - 270, 485);
			}else {
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 200, 450, 200, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("Yes", (int)ss.WIDTH - 170, 485);
			}
			
			if(noPoppedOut){
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 300, 550, 300, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("No", (int)ss.WIDTH - 270, 585);
			}else {
				g.setColor(new Color(218, 165, 32));
				g.fillRoundRect((int)ss.WIDTH - 200, 550, 200, 50, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString("No", (int)ss.WIDTH - 170, 585);
			}
		}
	}
	
	public void mousePressed(MouseEvent e){
		float mx = e.getX();
		float my = e.getY();
		
		if(ss.getState() == GameState.Game){
			ss.level.counter = 0;
			
			if(ss.level.part == GamePart.GameWon){
				//g.fillRoundRect(320, 500, 250, 100, 10, 10);
				if(mx >= 320 && mx <= 570){
					if(my >= 500 && my <= 600){
						System.exit(0);
					}
				}
			}
			
			if(ss.level.part == GamePart.BeginDay && !ss.level.starting){
				if(ss.level.bankrupt){
					if(mx >= ss.WIDTH / 2 - 150 && mx <= ss.WIDTH / 2 + 150){
						if(my >= 500 && my <= 600){
							System.exit(0);
						}
					}
				}
				if(ss.level.payingFees && !ss.level.bankrupt){
					if(mx >= ss.WIDTH / 2 - 100 && mx <= ss.WIDTH / 2 + 100){
						if(my >= ss.HEIGHT - 250 && my <= ss.HEIGHT - 150){
							int due = (int)ss.level.monthlyFees;
							for(int i = 0; i < ss.level.ads.size(); i++){
								AdHandler ad = ss.level.ads.get(i);
								if(ad.monthly){
									due -= ad.price;
								}
							}
							ss.level.money -= due;
							ss.level.payingFees = false;
						}
					}
				}
				if(mx >= 100 && mx <= 400 && !ss.level.bankrupt){
					if(my >= 400 && my <= 475){
						ss.setState(GameState.Advertising);
						justChangedStates = true;
						stateChangeCounter = 0;
					}
					
					if(my >= 500 && my <= 575 && !ss.level.bankrupt){
						ss.setState(GameState.Facilities);
					}
				}
				
				if(mx >= 470 && mx <= 470 + 250 && !ss.level.bankrupt){
					if(my >= 175 && my <= 225){
						ss.level.part = GamePart.MiddleDay;
						ss.level.clockRunning = true;
						ss.level.updated = false;
					}
				}
				
				if(mx >= ss.getWidth() - 400 && mx <= ss.getWidth() - 100 && !ss.level.bankrupt){
					if(my >= 400 && my <= 475){
						ss.setState(GameState.Employees);
					}
					
					if(my >= 500 && my <= 575){
						ss.setState(GameState.BackToMenu);
						justBackToMenu = true;
						backToMenuCounter = 0;
					}
				}
			}
			
			if(ss.level.part == GamePart.EndDay){
				int y = 150;
				numberOfAds = 0;
				for (int i = 0; i < ss.level.finishedAds.size(); i++){
					numberOfAds++;
				}
				for(int i = 0; i < ss.level.finishedAds.size(); i++){
					//Renew
					if(mx >= 575 && mx <= 575 + 60){
						if(my >= y - 35 && my <= y + 5){
							if(ss.level.money >= ss.level.finishedAds.get(i).price){
								AdHandler finAd = ss.level.finishedAds.get(i);
								AdHandler ad = ss.level.ads.get(ss.level.ads.indexOf(finAd));
								ss.level.finishedAds.remove(i);
								ss.level.money -= ad.price;
								if(ad.type == AdType.Radio){
									int num = r.nextInt(10) + 1;
									if(num <= 15){
										ss.level.reputation++;
									}
								}
								else if(ad.type == AdType.Magazine){
									int num = r.nextInt(10) + 1;
									if(num <= 30){
										ss.level.reputation++;
									}
								}
								else if(ad.type == AdType.Newspaper){
									int num = r.nextInt(10) + 1;
									if(num <= 20){
										ss.level.reputation++;
									}
								}
								else if(ad.type == AdType.Online){
									int num = r.nextInt(10) + 1;
									if(num <= 60){
										ss.level.reputation++;
									}
								}
								else if(ad.type == AdType.Billboard){
									int num = r.nextInt(10) + 1;
									if(num <= 80){
										ss.level.reputation += 2;
									}
									else{
										ss.level.reputation++;
									}
								}
								else if(ad.type == AdType.TV){
									int num = r.nextInt(10) + 1;
									if(num <= 80){
										ss.level.reputation += 3;
									}
									else{
										ss.level.reputation += 2;
									}
								}
								else if(ad.type == AdType.Mail){
									int num = r.nextInt(10) + 1;
									if(num <= 15){
										ss.level.reputation++;
									}
								}
								ad.finished = false;
								if(ad.daily){
									ad.daysLeft++;
								}
								else if(ad.weekly){
									ad.daysLeft += 7;
								}
								else if(ad.monthly){
									ad.daysLeft += 31;
								}
							}
							else{
								ss.level.notEnoughMoney = true;
							}
						}
					}
					//Cancel
					if(mx >= 675 && mx <= 675 + 60){
						if(my >= y - 35 && my <= y + 5){
							AdHandler finAd = ss.level.finishedAds.get(i);
							AdHandler ad = ss.level.ads.get(ss.level.ads.indexOf(finAd));
							ss.level.finishedAds.remove(i);
							ss.level.ads.remove(ss.level.ads.indexOf(finAd));
							ad.finished = true;
							if(ad.weekly){
								ss.level.weeklyFees -= ad.price;
							}
							else if(ad.monthly){
								ss.level.monthlyFees -= ad.price;
							}
						}
					}
					y += 60;
				}
				
				if(mx >= ss.WIDTH - 160 && mx <= ss.WIDTH - 110){
					if(my >= 110 && my <= 160){
						if(ss.level.finishedAds.size() == 0 && ss.level.adPayment){
							ss.level.adPayment = false;
						}
					}
				}
				if(ss.level.outcomeReady){
					if(ss.level.finishedCases.size() == 0){
						if(mx >= (int)ss.WIDTH - 160 && mx <= (int)ss.WIDTH - 110){
							if(my >= 110 && my <= 160){
								ss.level.outcomeReady = false;
							}
						}
					}
					y = 200;
					for(int i = 0; i < ss.level.finishedCases.size(); i++){
						CaseHandler c = ss.level.finishedCases.get(i);
						if(mx >= 650 && mx <= 760){
							if(my >= y - 35 && my <= y + 15){
								if(c.outcome == Outcome.Settle){
									ss.level.money += c.settlement;
									ss.level.moneyToday += c.settlement;
									ss.level.totalMoney += c.settlement;
									ss.level.finishedCases.remove(c);
								}
								else if(c.outcome == Outcome.Lose){
									ss.level.finishedCases.remove(c);
								}
								else if(c.outcome == Outcome.Win){
									ss.level.money += c.maxWin;
									ss.level.moneyToday += c.maxWin;
									ss.level.totalMoney += c.maxWin;
									ss.level.finishedCases.remove(c);
								}
							}
						}
						y += 50;
					}
				}
				if(!ss.level.adPayment && !ss.level.outcomeReady){
					int caseY = 240;
					for(int i = 0; i < ss.level.dailyCases.size(); i++){
						CaseHandler cases = ss.level.dailyCases.get(i);
						if(cases.type == CaseType.ChildCustody || cases.type == CaseType.Divorce){
							if(mx >= 670 && mx <= 770){
								if(my >= caseY - 35 && my <= caseY + 15){
									if(!ss.level.casesRunning){
										ss.level.dailyCases.remove(cases);
										ss.level.money += cases.maxWin;
										ss.level.totalMoney += cases.maxWin;
										ss.level.totalCases += 1;
									}
									else{
										while(ss.level.casesRunning){
											
										}
										ss.level.dailyCases.remove(cases);
										ss.level.money += cases.maxWin;
										ss.level.totalMoney += cases.maxWin;
										ss.level.totalCases += 1;
									}
								}
							}
						}
						else{
							//Settle
							if(mx >= 600 && mx <= 675){
								if(my >= caseY - 35 && my <= caseY + 40){
									if(!ss.level.casesRunning){
										if(ss.level.pendingCases.size() < ss.level.maxCases){
											cases.outcome = Outcome.Settle;
											ss.level.pendingDays.addFirst(cases.settleTime);
											ss.level.dailyCases.remove(cases);
											ss.level.pendingCases.addFirst(cases);
										}
										else{
											ss.level.overMax = true;
										}
									}
									else{
										while(ss.level.casesRunning){
											
										}
										if(ss.level.pendingCases.size() < ss.level.maxCases){
											cases.outcome = Outcome.Settle;
											ss.level.pendingDays.addFirst(cases.settleTime);
											ss.level.dailyCases.remove(cases);
											ss.level.pendingCases.addFirst(cases);
										}
										else{
											ss.level.overMax = true;
										}
									}
								}
							}
							//Fight
							else if(mx >= 680 && mx <= 755){
								if(my >= caseY - 35 && my <= caseY + 40){
									if(!ss.level.casesRunning){
										if(ss.level.pendingCases.size() < ss.level.maxCases){
											ss.level.pendingCases.addFirst(cases);
											ss.level.pendingDays.addFirst(cases.disputeTime);
											ss.level.dailyCases.remove(cases);
											cases.findOutcome();
										}
										else{
											ss.level.overMax = true;
										}
									}
									else{
										while(ss.level.casesRunning){
											
										}
										if(ss.level.pendingCases.size() < ss.level.maxCases){
											ss.level.pendingCases.addFirst(cases);
											ss.level.pendingDays.addFirst(cases.disputeTime);
											ss.level.dailyCases.remove(cases);
											cases.findOutcome();
										}
										else{
											ss.level.overMax = true;
										}
									}
								}
							}
							//Reject
							else if(mx >= 760 && mx <= 840){
								if(my >= caseY - 35 && my <= caseY + 40){
									if(!ss.level.casesRunning){
										ss.level.dailyCases.remove(cases);
									}
									else{
										while(ss.level.casesRunning){
											
										}
										ss.level.dailyCases.remove(cases);
									}
								}
							}
						}
						caseY += 60;
					}
				}
				//((int)ss.WIDTH - 210, (int)ss.HEIGHT - 160, 150, 100, 20, 20);
				if(mx >= (int)ss.WIDTH - 210 && mx <= (int)ss.WIDTH - 60){
					if(my >= (int)ss.HEIGHT - 160 && my <= (int)ss.HEIGHT - 60){
						if(!ss.level.adPayment){
							if(ss.level.dailyCases.size() == 0){
								ss.level.part = GamePart.BeginDay;
								ss.level.updated = false;
								ss.level.day++;
								ss.level.monthDay++;
							}
							else{
								ss.level.notContinue = true;
							}
						}
					}
				}
			}
			if(ss.level.starting){
				if(ss.level.beginPoppedOut){
					if(mx >= ss.getWidth() - 350 && my >= 400 && my <= 450 && mx <= ss.getWidth() - 50){
						ss.level.starting = false;
					}
				}
				else if (mx >= ss.getWidth() - 250 && mx <= ss.getWidth() - 50){
					if(my >= 400 && my <= 450){
						ss.level.starting = false;
					}
				}
				if(ss.level.backToMenuPoppedOut){
					if(mx >= ss.getWidth() - 350 && my >= 500 && my <= 550 && mx <= ss.getWidth() - 50){
						ss.setState(GameState.Menu);
						ss.level.backToMenuPoppedOut = false;
					}
				}
				else if (mx >= ss.getWidth() - 250 && mx <= ss.getWidth() - 50){
					if(my >= 500 && my <= 550){
						ss.setState(GameState.Menu);
						ss.level.backToMenuPoppedOut = false;
					}
				}
			}
		}
		
		if(ss.getState() == GameState.Advertising){
			
			if(countAdButtonCounter){
				adButtonCounter++;
			}
			
			if(adButtonCounter > 0){
				countAdButtonCounter = false;
				adButtonCounter = 1;
			}
			
			if(justChangedStates){
				stateChangeCounter++;
			}
			
			if(stateChangeCounter > 1){
				justChangedStates = false;
				stateChangeCounter = 1;
			}
			
			if(ss.level.adBackPopped){
				if(mx >= ss.WIDTH - 305){
					if (my <= 585 + 50 && my >= 585){
						ss.setState(GameState.Game);
						ss.level.adBackPopped = false;
						ss.level.buyingBillboard = false;
						ss.level.buyingMagazine = false;
						ss.level.buyingMail = false;
						ss.level.buyingNewpaper = false;
						ss.level.buyingOnline = false;
						ss.level.buyingRadio = false;
						ss.level.buyingTV = false;
					}
				}
			}
			else if(mx <= ss.WIDTH && mx >= ss.WIDTH - 205){
				if(my <= 585 + 50 && my >= 585){
					ss.setState(GameState.Game);
					ss.level.adBackPopped = false;
					ss.level.buyingBillboard = false;
					ss.level.buyingMagazine = false;
					ss.level.buyingMail = false;
					ss.level.buyingNewpaper = false;
					ss.level.buyingOnline = false;
					ss.level.buyingRadio = false;
					ss.level.buyingTV = false;
				}
			}
			
			if(ss.level.buyingRadio){
				if(my >= 350 && my <= 430 && ss.level.money >= 150){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						int num = r.nextInt(10) + 1;
						if(num <= 15){
							ss.level.reputation++;
						}
						ss.level.buyingRadio = false;
						ss.level.money -= 150;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.ads.add(new AdHandler(AdType.Radio));
						ss.level.weeklyFees += new AdHandler(AdType.Radio).price;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.buyingRadio = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < 150){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.buyingRadio = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.buyingMagazine){
				if(my >= 350 && my <= 430 && ss.level.money >= 10000){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						int num = r.nextInt(10) + 1;
						if(num <= 30){
							ss.level.reputation++;
						}
						ss.level.buyingMagazine = false;
						ss.level.money -= 10000;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.ads.add(new AdHandler(AdType.Magazine));
						ss.level.monthlyFees += new AdHandler(AdType.Magazine).price;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.buyingMagazine = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < 10000){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.buyingMagazine = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.buyingOnline){
				if(my >= 350 && my <= 430 && ss.level.money >= 20000){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						int num = r.nextInt(10) + 1;
						if(num <= 50){
							ss.level.reputation++;
						}
						ss.level.buyingOnline = false;
						ss.level.money -= 20000;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.ads.add(new AdHandler(AdType.Online));
						ss.level.monthlyFees += new AdHandler(AdType.Online).price;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.buyingOnline = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < 20000){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.buyingOnline = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.buyingNewpaper){
				if(my >= 350 && my <= 430 && ss.level.money >= 6000){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						int num = r.nextInt(10) + 1;
						if(num <= 20){
							ss.level.reputation++;
						}
						ss.level.buyingNewpaper = false;
						ss.level.money -= 6000;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.ads.add(new AdHandler(AdType.Newspaper));
						ss.level.weeklyFees += new AdHandler(AdType.Newspaper).price;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.buyingNewpaper = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < 6000){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.buyingNewpaper = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.buyingBillboard){
				if(my >= 350 && my <= 430 && ss.level.money >= 25000){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						int num = r.nextInt(10) + 1;
						if(num <= 80){
							ss.level.reputation += 2;
						}
						else{
							ss.level.reputation++;
						}
						ss.level.buyingBillboard = false;
						ss.level.money -= 25000;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.ads.add(new AdHandler(AdType.Billboard));
						ss.level.weeklyFees += new AdHandler(AdType.Billboard).price;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.buyingBillboard = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < 25000){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.buyingBillboard = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.buyingTV){
				if(my >= 350 && my <= 430 && ss.level.money >= 100000){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						int num = r.nextInt(10) + 1;
						if(num <= 80){
							ss.level.reputation += 3;
						}
						else{
							ss.level.reputation += 2;
						}
						ss.level.buyingTV = false;
						ss.level.money -= 100000;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.ads.add(new AdHandler(AdType.TV));
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.buyingTV = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < 100000){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.buyingTV = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.buyingMail){
				if(my >= 350 && my <= 430 && ss.level.money >= 2000){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						int num = r.nextInt(100) + 1;
						if(num <= 15){
							ss.level.reputation++;
						}
						ss.level.buyingMail = false;
						ss.level.money -= 2000;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.ads.add(new AdHandler(AdType.Mail));
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.buyingMail = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < 2000){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.buyingMail = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(mx >= 365 && mx <= 365 + 75){
				//Radio
				if (my >= 85 && my <= 85 + 40 && !ss.level.buyingBillboard && !ss.level.buyingMagazine
						&& !ss.level.buyingMail && !ss.level.buyingNewpaper && !ss.level.buyingOnline
						&& !ss.level.buyingTV && adButtonCounter > 0 && !justChangedStates){
					ss.level.buyingRadio = true;
				}
				//Magazine
				if (my >= 160 && my <= 160 + 40 && !ss.level.buyingBillboard && !ss.level.buyingRadio
						&& !ss.level.buyingMail && !ss.level.buyingNewpaper && !ss.level.buyingOnline
						&& !ss.level.buyingTV && adButtonCounter > 0 && !justChangedStates){
					ss.level.buyingMagazine = true;
				}
				//Online
				if (my >= 230 && my <= 230 + 40 && !ss.level.buyingBillboard && !ss.level.buyingMagazine
						&& !ss.level.buyingMail && !ss.level.buyingNewpaper && !ss.level.buyingRadio
						&& !ss.level.buyingTV && adButtonCounter > 0 && !justChangedStates){
					ss.level.buyingOnline = true;
				}
				//Newspaper
				if (my >= 300 && my <= 300 + 40 && !ss.level.buyingBillboard && !ss.level.buyingMagazine
						&& !ss.level.buyingMail && !ss.level.buyingRadio && !ss.level.buyingOnline
						&& !ss.level.buyingTV && adButtonCounter > 0 && !justChangedStates){
					ss.level.buyingNewpaper = true;
				}
				//Billboard
				if (my >= 370 && my <= 370 + 40 && !ss.level.buyingRadio && !ss.level.buyingMagazine
						&& !ss.level.buyingMail && !ss.level.buyingNewpaper && !ss.level.buyingOnline
						&& !ss.level.buyingTV && adButtonCounter > 0 && !justChangedStates){
					ss.level.buyingBillboard = true;
				}
				//TV
				if (my >= 440 && my <= 440 + 40 && !ss.level.buyingBillboard && !ss.level.buyingMagazine
						&& !ss.level.buyingMail && !ss.level.buyingNewpaper && !ss.level.buyingOnline
						&& !ss.level.buyingRadio && adButtonCounter > 0 && !justChangedStates){
					ss.level.buyingTV = true;
				}
				//Mail
				if (my >= 510 && my <= 510 + 40 && !ss.level.buyingBillboard && !ss.level.buyingMagazine
						&& !ss.level.buyingRadio && !ss.level.buyingNewpaper && !ss.level.buyingOnline
						&& !ss.level.buyingTV && adButtonCounter > 0 && !justChangedStates){
					ss.level.buyingMail = true;
				}
			}
		}
		
		if(ss.getState() == GameState.Facilities){
			
			if(countAdButtonCounter){
				adButtonCounter++;
			}
			
			if(adButtonCounter > 0){
				countAdButtonCounter = false;
				adButtonCounter = 1;
			}
			
			if(justChangedStates){
				stateChangeCounter++;
			}
			
			if(stateChangeCounter > 1){
				justChangedStates = false;
				stateChangeCounter = 1;
			}
			
			if(ss.level.rentingTiny){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + 1250 - ss.level.cost){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.monthlyFees += 1250;
						ss.level.monthlyFees -= ss.level.cost;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.rentingTiny = false;
						ss.level.building = Building.Tiny;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.rentingTiny = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else{
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.rentingTiny = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.rentingSmall){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + 5000 - ss.level.cost){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.monthlyFees += 5000;
						ss.level.monthlyFees -= ss.level.cost;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.rentingSmall = false;
						ss.level.building = Building.Small;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.rentingSmall = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else{
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.rentingSmall = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.rentingMed){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + 8000 - ss.level.cost){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.monthlyFees += 8000;
						ss.level.monthlyFees -= ss.level.cost;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.rentingMed = false;
						ss.level.building = Building.Medium;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.rentingMed = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else{
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.rentingMed = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.rentingLarge){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + 14000 - ss.level.cost){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.monthlyFees += 14000;
						ss.level.monthlyFees -= ss.level.cost;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.rentingLarge = false;
						ss.level.building = Building.Large;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.rentingLarge = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else{
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.rentingLarge = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.rentingHuge){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + 25000 - ss.level.cost){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.monthlyFees += 25000;
						ss.level.monthlyFees -= ss.level.cost;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.rentingHuge = false;
						ss.level.building = Building.Huge;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.rentingHuge = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else{
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.rentingHuge = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.facilitiesBackPopped){
				if(mx >= ss.WIDTH - 305){
					if (my <= 585 + 50 && my >= 585){
						ss.setState(GameState.Game);
						ss.level.facilitiesBackPopped = false;
					}
				}
			}
			else if(mx <= ss.WIDTH && mx >= ss.WIDTH - 205){
				if(my <= 585 + 50 && my >= 585){
					ss.setState(GameState.Game);
					ss.level.facilitiesBackPopped = false;
				}
			}
			
			if(mx >= 750 && mx <= 850){
				if(my >= 160 && my <= 200 && !ss.level.rentingSmall && !ss.level.rentingMed
						&& !ss.level.rentingLarge && ! ss.level.rentingHuge){
					ss.level.rentingTiny = true;
				}
				if(my >= 225 && my <= 265 && !ss.level.rentingTiny && !ss.level.rentingMed
						&& !ss.level.rentingLarge && ! ss.level.rentingHuge){
					ss.level.rentingSmall = true;
				}
				if(my >= 295 && my <= 335 && !ss.level.rentingSmall && !ss.level.rentingTiny
						&& !ss.level.rentingLarge && ! ss.level.rentingHuge){
					ss.level.rentingMed = true;
				}
				if(my >= 365 && my <= 405 && !ss.level.rentingSmall && !ss.level.rentingMed
						&& !ss.level.rentingTiny && ! ss.level.rentingHuge){
					ss.level.rentingLarge = true;
				}
				if(my >= 435 && my <= 475 && !ss.level.rentingSmall && !ss.level.rentingMed
						&& !ss.level.rentingLarge && ! ss.level.rentingTiny){
					ss.level.rentingHuge = true;
				}
			}
		}
		
		if(ss.getState() == GameState.Employees){
			if(ss.level.employeesBackPopped){
				if(mx >= ss.WIDTH - 305){
					if (my <= 585 + 50 && my >= 585){
						ss.setState(GameState.Game);
						ss.level.employeesBackPopped = false;
					}
				}
			}
			else if(mx <= ss.WIDTH && mx >= ss.WIDTH - 205){
				if(my <= 585 + 50 && my >= 585){
					ss.setState(GameState.Game);
					ss.level.employeesBackPopped = false;
				}
			}
			
			if(countAdButtonCounter){
				adButtonCounter++;
			}
			
			if(adButtonCounter > 0){
				countAdButtonCounter = false;
				adButtonCounter = 1;
			}
			
			if(justChangedStates){
				stateChangeCounter++;
			}
			
			if(stateChangeCounter > 1){
				justChangedStates = false;
				stateChangeCounter = 1;
			}
			
			if(ss.level.employeesBackPopped){
				if(mx >= ss.WIDTH - 305){
					if (my <= 585 + 50 && my >= 585){
						ss.setState(GameState.Game);
						ss.level.hiringAssistant = false;
						ss.level.promotingPartner = false;
						ss.level.hiringAssociate= false;
						ss.level.hiringClerk = false;
						ss.level.hiringInvestigator = false;
						ss.level.hiringParalegal = false;
						ss.level.hiringReceptionist = false;
					}
				}
			}
			else if(mx <= ss.WIDTH && mx >= ss.WIDTH - 205){
				if(my <= 585 + 50 && my >= 585){
					ss.setState(GameState.Game);
					ss.level.hiringAssistant = false;
					ss.level.promotingPartner = false;
					ss.level.hiringAssociate= false;
					ss.level.hiringClerk = false;
					ss.level.hiringInvestigator = false;
					ss.level.hiringParalegal = false;
					ss.level.hiringReceptionist = false;
				}
			}
			
			//Fix stuff below
			
			if(ss.level.promotingPartner){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + ((176000 - 68000) / 12)){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.promotingPartner = false;
						ss.level.reputation += 5;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.monthlyFees += ((176000 - 68000) / 12);
						ss.level.partners++;
						ss.level.associates--;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.promotingPartner = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < ss.level.monthlyFees + ((176000 - 68000) / 12)){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.promotingPartner = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.hiringAssociate){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + (68000 / 12)){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.maxCases += 3;
						ss.level.hiringAssociate = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.monthlyFees += (68000 / 12);
						ss.level.associates++;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.hiringAssociate = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < ss.level.monthlyFees + (68000 / 12)){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.hiringAssociate = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.hiringClerk){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + (20000 / 12)){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.maxCases += 1;
						ss.level.hiringClerk = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.monthlyFees += (20000 / 12);
						ss.level.clerks++;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.hiringClerk = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < ss.level.monthlyFees + (20000 / 12)){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.hiringClerk = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.hiringParalegal){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + (45000 / 12)){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.hiringParalegal = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.monthlyFees += (45000 / 12);
						ss.level.paralegals++;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.hiringParalegal = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < ss.level.monthlyFees + (45000 / 12)){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.hiringParalegal = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.hiringAssistant){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + (41000 / 12)){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.hiringAssistant = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.monthlyFees += (41000 / 12);
						ss.level.assistants++;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.hiringAssistant = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < ss.level.monthlyFees + (41000 / 12)){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.hiringAssistant = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.hiringReceptionist){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + (30000 / 12)){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.hiringReceptionist = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.monthlyFees += (30000 / 12);
						ss.level.receptionists++;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.hiringReceptionist = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < ss.level.monthlyFees + (30000 / 12)){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.hiringReceptionist = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			if(ss.level.hiringInvestigator){
				if(my >= 350 && my <= 430 && ss.level.money >= ss.level.monthlyFees + (51000 / 12)){
					//Purchase
					if (mx >= 275 && mx <= 275 + 150){
						ss.level.hiringInvestigator = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
						ss.level.monthlyFees += (51000 / 12);
						ss.level.investigators++;
					}
					//Cancel
					if(mx >= 460 && mx <= 460 + 150){
						ss.level.hiringInvestigator = false;
						countAdButtonCounter = true;
						adButtonCounter = 0;
					}
				}
				else if(ss.level.money < ss.level.monthlyFees + (51000 / 12)){
					if(mx >= 345 && mx <= 545){
						if(my >= 350 && my <= 450){
							ss.level.hiringInvestigator = false;
							countAdButtonCounter = true;
							adButtonCounter = 0;
						}
					}
				}
			}
			
			//I need to add the button functionality in levels
			
			if(mx >= 360 && mx <= 440){
				if(my >= 140 && my <= 180 && !ss.level.hiringAssistant && !ss.level.hiringClerk
						&& !ss.level.hiringInvestigator && !ss.level.hiringParalegal 
						&& !ss.level.hiringReceptionist && !ss.level.promotingPartner
						&& adButtonCounter > 0 && !justChangedStates){
					ss.level.hiringAssociate = true;
				}
				
				if(my >= 200 && my <= 240 && !ss.level.hiringAssistant && !ss.level.hiringClerk
						&& !ss.level.hiringInvestigator && !ss.level.hiringParalegal 
						&& !ss.level.hiringReceptionist && !ss.level.hiringAssociate
						&& adButtonCounter > 0 && !justChangedStates){
					ss.level.promotingPartner = true;
				}
				
				if(my >= 260 && my <= 300 && !ss.level.hiringAssistant && !ss.level.promotingPartner
						&& !ss.level.hiringInvestigator && !ss.level.hiringParalegal 
						&& !ss.level.hiringReceptionist && !ss.level.hiringAssociate
						&& adButtonCounter > 0 && !justChangedStates){
					ss.level.hiringClerk = true;
				}
				
				if(my >= 325 && my <= 365 && !ss.level.hiringAssistant && !ss.level.promotingPartner
						&& !ss.level.hiringInvestigator && !ss.level.hiringClerk 
						&& !ss.level.hiringReceptionist && !ss.level.hiringAssociate
						&& adButtonCounter > 0 && !justChangedStates){
					ss.level.hiringParalegal = true;
				}
				
				if(my >= 390 && my <= 430 && !ss.level.hiringParalegal && !ss.level.promotingPartner
						&& !ss.level.hiringInvestigator && !ss.level.hiringClerk 
						&& !ss.level.hiringReceptionist && !ss.level.hiringAssociate
						&& adButtonCounter > 0 && !justChangedStates){
					ss.level.hiringAssistant = true;
				}
				
				if(my >= 450 && my <= 490 && !ss.level.hiringParalegal && !ss.level.promotingPartner
						&& !ss.level.hiringInvestigator && !ss.level.hiringClerk 
						&& !ss.level.hiringAssistant && !ss.level.hiringAssociate){
					ss.level.hiringReceptionist = true;
				}
				
				if(my >= 515 && my <= 555 && !ss.level.hiringParalegal && !ss.level.promotingPartner
						&& !ss.level.hiringReceptionist && !ss.level.hiringClerk 
						&& !ss.level.hiringAssistant && !ss.level.hiringAssociate
						&& adButtonCounter > 0 && !justChangedStates){
					ss.level.hiringInvestigator = true;
				}
			}
		}
		
		if(ss.getState() == GameState.Game && ss.level.part == GamePart.EndDay){
			
		}
		
		if(ss.getState() == GameState.BackToMenu){
			if(justBackToMenu){
				backToMenuCounter++;
			}
			
			if(backToMenuCounter > 1){
				backToMenuCounter = 0;
				justBackToMenu = false;
			}
			
			if(ss.level.backYesPopped){
				if(mx >= ss.WIDTH - 300){
					if (my <= 450 && my >= 400){
						ss.setState(GameState.Menu);
						ss.level.backYesPopped = false;
					}
				}
			}
			else if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
				if(my <= 450 && my >= 400){
					ss.setState(GameState.Game);
					ss.level.backYesPopped = false;
				}
			}
			
			if(ss.level.backNoPopped){
				if(mx >= ss.WIDTH - 310){
					if (my <= 550 && my >= 500 && !justBackToMenu){
						ss.setState(GameState.Game);
						ss.level.backNoPopped = false;
					}
				}
			}
			else if(mx <= ss.WIDTH && mx >= ss.WIDTH - 210){
				if(my <= 550 && my >= 500 && !justBackToMenu){
					ss.setState(GameState.Game);
					ss.level.backNoPopped = false;
				}
			}
		}
		
		if(ss.getState() == GameState.Options){
			if(optionsBackPopped){
				if(mx >= ss.WIDTH - 300 && mx <= ss.WIDTH){
					if(my >= 550 && my <= 600){
						ss.setState(GameState.Menu);
						justBackToMenu = true;
						optionsBackPopped = false;
						optionsPoppedOut = false;
					}
				}
			}
			else{
				if(mx >= ss.WIDTH - 200 && mx <= ss.WIDTH){
					if(my >= 550 && my <= 600){
						ss.setState(GameState.Menu);
						justBackToMenu = true;
						optionsBackPopped = false;
						optionsPoppedOut = false;
					}
				}
			}

			if(mx >= 175 && mx <= 235){
				if(my >= 120 && my <= 160){
					musicYes = true;
					musicNo = false;
					ss.songReset = true;
					ss.startedMusic = false;
				}
			}
			
			if(mx >= 260 && mx <= 320){
				if(my >= 120 && my <= 160){
					musicYes = false;
					musicNo = true;
				}
			}
			
			if(mx >= 75 && mx <= 325){
				if(my >= 200 && my <= 275){
					ss.sound.stop();
					ss.songReset = true;
					ss.startedMusic = false;
				}
			}
		}
		
		if(ss.getState() == GameState.Help){
			if(helpBackPopped){
				if(mx >= ss.WIDTH - 300 && mx <= ss.WIDTH){
					if(my >= 550 && my <= 600){
						ss.setState(GameState.Menu);
						justBackToMenu = true;
						helpBackPopped = false;
						helpPoppedOut = false;
					}
				}
			}
			else{
				if(mx >= ss.WIDTH - 200 && mx <= ss.WIDTH){
					if(my >= 550 && my <= 600){
						ss.setState(GameState.Menu);
						justBackToMenu = true;
						helpBackPopped = false;
						helpPoppedOut = false;
					}
				}
			}
			
			if(ss.helpPage < 6){
				if(mx >= 700 && mx <= 850){
					if(my >= 490 && my <= 540){
						ss.helpPage++;
					}
				}
			}

			if(ss.helpPage > 1){
				if(mx >= 50 && mx <= 200){
					if(my >= 490 && my <= 540){
						ss.helpPage--;
					}
				}
			}
		}
			
		
		if(ss.getState() == GameState.Menu){
			//Play button
			if(playPoppedOut && ss.getState() == GameState.Menu){
				if(mx >= ss.WIDTH - 300 && mx <= ss.WIDTH){
					if(my >= 250 && my <= 300){
						handler.removeAll();
						counter = 0;
						ss.setState(GameState.Game);
						//ss.startedMusic = false;
						ss.getG().fillRect(0, 0, ss.getWidth(), ss.getHeight());
					}
				}
			}
			else if (mx >= ss.WIDTH - 200 && mx <= ss.WIDTH && ss.getState() == GameState.Menu){
				if(my >= 250 && my <= 300){
					handler.removeAll();
					counter = 0;
					ss.setState(GameState.Game);
					ss.getG().fillRect(0, 0, ss.getWidth(), ss.getHeight());
				}
			}
			
			if(optionsPoppedOut && ss.getState() == GameState.Menu){
				if(mx >= ss.WIDTH - 300 && mx <= ss.WIDTH){
					if(my >= 350 && my <= 400){
						ss.setState(GameState.Options);
						optionsPoppedOut = false;
						handler.removeAll();
						counter = 0;
						handler.removeAll();
					}
				}
			}
			else if (mx >= ss.WIDTH - 200 && mx <= ss.WIDTH && ss.getState() == GameState.Menu){
				if(my >= 350 && my <= 400){
					ss.setState(GameState.Options);
					optionsPoppedOut = false;
					handler.removeAll();
					counter = 0;
					handler.removeAll();
				}
			}
			//Help Button
			if(helpPoppedOut && ss.getState() == GameState.Menu){
				if(mx >= ss.WIDTH - 300 && mx <= ss.WIDTH){
					if(my >= 450 && my <= 500){
						ss.setState(GameState.Help);
						helpPoppedOut = false;
						handler.removeAll();
						counter = 0;
						handler.removeAll();
					}
				}
			}
			else if (mx >= ss.WIDTH - 200 && mx <= ss.WIDTH && ss.getState() == GameState.Menu){
				if(my >= 450 && my <= 500){
					ss.setState(GameState.Help);
					helpPoppedOut = false;
					handler.removeAll();
					counter = 0;
					handler.removeAll();
				}
			}
			
			//Quit button
			if(quitPoppedOut && ss.getState() == GameState.Menu && !justBackToMenu){
				if(mx >= ss.WIDTH - 300 && mx <= ss.WIDTH){
					if(my >= 550 && my <= 600){
						System.exit(0);
						ss.setState(GameState.QuitScreen);
						handler.removeAll();
						counter = 0;
					}
				}
			}
			else if (mx >= ss.WIDTH - 200 && mx <= ss.WIDTH && ss.getState() == GameState.Menu && !justBackToMenu){
				if(my >= 550 && my <= 600){
					System.exit(0);
					ss.setState(GameState.QuitScreen);
					handler.removeAll();
					counter = 0;
				}
			}
			
			for(int i = 0; i < handler.objects.size(); i++){
				GameObject object = handler.objects.get(i);
				if(mx >= object.getX() && mx <= object.getWidth() + object.getX()){
					if(my >= object.getY() && my <= object.getHeight() + object.getY() && handler.objects.size() < 150 && ss.getState() == GameState.Menu){
						handler.remove(object);
						handler.add(new MenuDollar(0, r.nextInt(5) + 1, ss.getG(), r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
						handler.add(new MenuDollar(0, r.nextInt(5) + 1, ss.getG(), r.nextInt((int)ss.WIDTH) - dollar.getWidth(null), 0, ss));
					}
					else if(handler.objects.size() >= 150){
						tooMuchMoney = true;
					}
					
					if(ss.getState() == GameState.QuitScreen){
						handler.removeAll();
						counter = 0;
					}
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent e){
		float my = e.getY();
		float mx = e.getX();
		
		if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
			if(my <= 300 && my >= 250){
				playPoppedOut = true;
			}
			else{
				playPoppedOut = false;
			}
		}
		
		if(playPoppedOut){
			if(mx < ss.WIDTH - 300){
				playPoppedOut = false;
			}
			if (my > 300 || my < 250){
				playPoppedOut = false;
			}
		}
		
		if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
			if (my <= 400 && my >= 350){
				optionsPoppedOut = true;
			}else {
				optionsPoppedOut = false;
			}
		}
		
		if(optionsPoppedOut){
			if(mx < ss.WIDTH - 300){
				optionsPoppedOut = false;
			}
			if (my > 400 || my < 350){
				optionsPoppedOut = false;
			}
		}
		
		if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
			if (my <= 500 && my >= 450){
				helpPoppedOut = true;
			}else {
				helpPoppedOut = false;
			}
		}
		
		if(helpPoppedOut){
			if(mx < ss.WIDTH - 300){
				helpPoppedOut = false;
			}
			if (my > 500 || my < 450){
				helpPoppedOut = false;
			}
		}
		
		if(mx <= ss.WIDTH && mx >= ss.WIDTH - 200){
			if (my <= 600 && my >= 550){
				quitPoppedOut = true;
			}else {
				quitPoppedOut = false;
			}
		}
		
		if(quitPoppedOut){
			if(mx < ss.WIDTH - 300){
				quitPoppedOut = false;
			}
			if (my > 600 || my < 550){
				quitPoppedOut = false;
			}
		}
		
		if(ss.getState() == GameState.Game && ss.level.starting == true){
			if (mx >= ss.getWidth() - 250 && mx <= ss.getWidth() - 50){
				if(my >= 400 && my <= 450){
					ss.level.beginPoppedOut = true;
				}
				else{
					ss.level.beginPoppedOut = false;
				}
			}
			
			if(ss.level.beginPoppedOut){
				if(mx < ss.getWidth() - 350){
					ss.level.beginPoppedOut = false;
				}
				
				if(my < 400 || my > 450){
					ss.level.beginPoppedOut = false;
				}
			}
			
			if (mx >= ss.getWidth() - 250 && mx <= ss.getWidth() - 50){
				if(my >= 500 && my <= 550){
					ss.level.backToMenuPoppedOut = true;
				}
				else{
					ss.level.backToMenuPoppedOut = false;
				}
			}
			
			if(ss.level.backToMenuPoppedOut){
				if(mx < ss.getWidth() - 350){
					ss.level.backToMenuPoppedOut = false;
				}
				
				if(my < 500 || my > 550){
					ss.level.backToMenuPoppedOut = false;
				}
			}
		}
	}
}
