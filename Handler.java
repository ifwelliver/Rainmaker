package com.welliver.sidescroller;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	private Rainmaker ss;
	LinkedList <GameObject> objects = new LinkedList<GameObject>();
	
	public Handler(Rainmaker ss){
		this.ss = ss;
	}
	
	public void tick(){
		for (int i = 0; i < objects.size(); i++){
			GameObject temp = objects.get(i);
			
			temp.tick();
		}
	}
	
	public void render(Graphics g){
		for (int i = 0; i < objects.size(); i++){
			GameObject temp = objects.get(i);
			
			temp.render(g);
		}
	}
	
	public void add(GameObject object){
		objects.add(object);
	}
	
	public void remove(GameObject object){
		objects.remove(object);
	}
	
	public void removeAll(){
		for (int i = 0; i < objects.size(); i++){
			GameObject temp = objects.get(i);
			remove(temp);
			i--;
		}
	}
}
