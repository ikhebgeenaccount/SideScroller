package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends JFrame{
	//Some text to check if pulling works.
	private static final int FPS = 30;
	private static boolean running = true;
	private static Main frame = new Main();
	private static GamePanel gamePanel = new GamePanel();
	private static Champion character;
	
	public static void main(String[] args){
		character = new Champion();
		character.setAnimationFrame(new ImageIcon("resources\\char\\char-idle.png").getImage());
		createFrame();
		//Thread for frames
		new Thread("Game loop"){
			public void run(){
				while(running){
					gamePanel.update();
					gamePanel.repaint();
					try {
						Thread.sleep(1000/FPS);
					} catch (InterruptedException e) {
						
					}
				}
			}
		}.start();
		frame.setVisible(true);
		
		
	}
	
	//Frame is created and set in the middle of the screen
	//<---! NOT YET VISIBLE !--->
	private static void createFrame(){
		frame.setTitle("SideScroller");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension dim = new Dimension(1000, 500);
		frame.getContentPane().add(gamePanel);
		gamePanel.setPreferredSize(dim);
		gamePanel.setFocusable(true);
		gamePanel.addKeyListener(gamePanel);
		frame.pack();
		frame.setLocationRelativeTo(null);

	}	
	
	//Get to-display image from character
	public static Image getAnimationFrame(){
		return character.getAnimationFrame();
	}
}
