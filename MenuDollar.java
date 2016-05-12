package com.welliver.sidescroller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class MenuDollar extends GameObject{
	
	private Graphics g;
	private Image dollar;
	private Rainmaker ss;
	private Random r;
	
	public MenuDollar(float xSpeed, float ySpeed, Graphics g, float x, float y, Rainmaker ss){
		setCanMove(true);
		setXSpeed(xSpeed);
		setYSpeed(ySpeed);
		setX(x);
		setY(y);
		r = new Random();
		this.g = g;
		this.ss = ss;
		try{
			dollar = ImageIO.read(new File("res/dollarbill.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		setHeight(dollar.getHeight(null));
		setWidth(dollar.getWidth(null));
	}

	public void tick() {
		setX(getX() + getXSpeed());
		setY(getY() + getYSpeed()); 
		if (y > ss.HEIGHT + dollar.getHeight(null)){
			setY(-dollar.getHeight(null));
			setX(r.nextInt((int)ss.WIDTH - dollar.getWidth(null)));
			setYSpeed(r.nextInt(5) + 1);
		}
	}

	public void render(Graphics g) {
		g.drawImage(dollar, (int)this.x, (int)this.y, null);
	}

}
