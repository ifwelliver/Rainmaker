package com.welliver.sidescroller;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{

	private static final long serialVersionUID = -2353784903345171749L;
	
	public Window(String title, Rainmaker ss, float height, float width){
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension((int)width, (int)height));
		frame.setMaximumSize(new Dimension((int)width, (int)height));
		frame.setMinimumSize(new Dimension((int)width, (int)height));
		
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(ss);
		frame.requestFocus();
		ss.start();
	}

}
