package com.welliver.sidescroller;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.imageio.ImageIO;

//Main class, all the main game logic is handled here
public class Rainmaker extends Canvas implements Runnable{
	//All variables and objects initially needed
	public final float WIDTH = 900, HEIGHT = WIDTH / 12 * 9;
	private static final long serialVersionUID = -7200341409995216607L;
	private Thread thread;
	private boolean running = true;
	private float fps;
	private Graphics g;
	private Handler handler;
	public GameState state = GameState.Menu;
	private Menu menu;
	public Color backgroundColor = new Color(210, 105, 30);
	public Image background;
	public Image one;
	public Image two;
	public Image three;
	public Image four;
	public Image five;
	public Image six;
	private int counter = 0;
	public Levels level;
	public int volume;
	public boolean startedMusic = false;
	public boolean musicFirst = false;
	String pharaoh = "Pharaoh.wav";
	String schizo = "Schizo.wav";
	String beware = "Beware.wav";
	String got_paper = "Got_Paper.wav";
	String hollow_dreams = "Hollow_Dreams.wav";
	String kingdom_come = "Kingdom_Come.wav";
	String stackin = "Stackin.wav";
	String no_price_tag = "No_Price_Tag.wav";
	String[] songs = {no_price_tag, schizo, pharaoh, got_paper, beware, hollow_dreams, kingdom_come, stackin};
	File[] songList = {new File("res/" + no_price_tag).getAbsoluteFile(), new File("res/" + schizo).getAbsoluteFile(), 
			new File("res/" + pharaoh).getAbsoluteFile(),new File("res/" + got_paper).getAbsoluteFile(), 
			new File("res/" + beware).getAbsoluteFile(), new File("res/" + hollow_dreams).getAbsoluteFile(), 
			new File("res/" + kingdom_come).getAbsoluteFile(),new File("res/" + stackin).getAbsoluteFile(),};
	public Random r = new Random();
	public Sound sound;
	int number;
	private int musicCounter = 0;
	public boolean musicStopped = false;
	public boolean songReset = false;
	public int helpPage = 1;
	
	public enum GameState{
		Menu,
		LevelScreen,
		Options,
		Help,
		Pause,
		Game,
		QuitScreen,
		Employees,
		Facilities,
		Advertising,
		BackToMenu;
	}
	
	public GameState getState(){
		return this.state;
	}
	
	public void setState(GameState state){
		this.state = state;
	}
	public Rainmaker(){
		handler = new Handler(this);
		menu = new Menu(this, handler, state);
		level = new Levels(this, handler);
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		new Window("Rainmaker", this, HEIGHT, WIDTH);
		try{
			background = ImageIO.read(new File("res/scales2.jpg"));
			one = ImageIO.read(new File("res/Pg.1.PNG"));
			two = ImageIO.read(new File("res/Pg.2.PNG"));
			three = ImageIO.read(new File("res/Pg.3.PNG"));
			four = ImageIO.read(new File("res/Pg.4.PNG"));
			five = ImageIO.read(new File("res/Pg.5.PNG"));
			six = ImageIO.read(new File("res/Pg.6.PNG"));
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new Rainmaker();
	}

	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1){
				tick();
				delta--;
			}
			if (running){
				render();
			}
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("FPS: " + frames);
				fps = frames;
				frames = 0;
			}
		}
		stop();
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try{
			thread.join();
		}catch(Exception e){
			e.printStackTrace();
		}
		running = false;
	}
	
	public void tick(){
		if(getState() == GameState.Options || getState() == GameState.Help){
			handler.removeAll();
		}
		if(getState() == GameState.Menu || getState() == GameState.Options || getState() == GameState.Help){
			menu.tick();
		}
		
		if(!startedMusic){
			if(number == 7){
				number = 0;
				startedMusic = true;
			}
			else{
				number += 1;
				startedMusic = true;
			}
			
			if(musicFirst){
				sound.resetSong(songList[number]);
			}
			
			if(songReset){
				int num = number;
				number = r.nextInt(8);
				if(number == num){
					number++;
				}
				songReset = false;
				sound.stop();
				sound.resetSong(songList[number]);
				sound.play();
			}
			else if(!musicFirst){
				number = 1;
				musicFirst = true;
				sound = new Sound("res/" + songs[number], this);
			}
			
			sound.play();
			startedMusic = true;
			musicCounter = 0;
		}
		if(!sound.clip.isActive() && !musicStopped){
			musicCounter++;
			if(!sound.clip.isActive() && musicCounter > 400){
				startedMusic = false;
			}
		}
		
		if(musicStopped){
			startedMusic = true;
			sound.stop();
		}
		
		if(getState() == GameState.Game || state == GameState.Advertising || state == GameState.BackToMenu
				|| state == GameState.Employees || state == GameState.Facilities){
			level.tick();
		}
		handler.tick();
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null){
			this.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.setColor(backgroundColor);
		if(state == GameState.Game || state == GameState.Advertising || state == GameState.BackToMenu
				|| state == GameState.Employees || state == GameState.Facilities){
			g.fillRect(0, 0, (int)WIDTH, (int)HEIGHT);
			level.render(g);
		}
		else if(getState() == GameState.Menu || getState() == GameState.QuitScreen
				|| getState() == GameState.Options || getState() == GameState.Help){
			g.drawImage(background, 0, 0, (int)WIDTH, (int)HEIGHT, null);
			menu.render(g);
		} else{g.setColor(Color.black);g.fillRect(0, 0, (int)WIDTH, (int)HEIGHT);}
		handler.render(g);
		g.dispose();
		bs.show();
	}
	
	public float clamp(float min, float max, float var){
		if (var >= max){
			return var = max;
		}
		else if (var <= min){
			return var = min;
		}
		else{
			return var;
		}
	}
	
	public Graphics getG(){
		return g;
	}
}
