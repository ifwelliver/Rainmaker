package com.welliver.sidescroller;

import java.awt.Graphics;

public abstract class GameObject {
	
	protected float x, y;
	protected boolean canMove;
	protected float xSpeed, ySpeed;
	protected float width, height;
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public void setX(float x){
		this.x = x;
	}
	
	public float getX(){
		return this.x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public float getY(){
		return this.y;
	}
	
	public void setCanMove(boolean canMove){
		this.canMove = canMove;
	}
	
	public boolean getCanMove(){
		return this.canMove;
	}
	
	public void setXSpeed(float xSpeed){
		this.xSpeed = xSpeed;
	}
	
	public float getXSpeed(){
		return this.xSpeed;
	}
	
	public void setYSpeed(float ySpeed){
		this.ySpeed = ySpeed;
	}
	
	public float getYSpeed(){
		return this.ySpeed;
	}
	
	public void setWidth(float width){
		this.width = width;
	}
	
	public float getWidth(){
		return this.width;
	}
	
	public void setHeight(float height){
		this.height = height;
	}
	
	public float getHeight(){
		return height;
	}
}
