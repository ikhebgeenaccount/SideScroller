package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.champion.Champion;
import main.champion.champions.AlphaGuy;
import main.gamepanel.GamePanel;

public class Main extends JFrame{
	//Some text to check if pulling works.
	private static final int FPS = 45;
	private static boolean running;
	private static JFrame frame;
	private static GamePanel gamePanel;
	private static Champion character;
	
	public static void main(String[] args){
		System.out.println("Initializing game...");
		System.out.println("Reading config...");
		System.out.println("Updating settings...");
		System.out.println("Creating character...");
		character = new AlphaGuy();
		System.out.println("Setting up frame...");
		createFrame();		
		//Thread for frames
		System.out.println("Starting game...");
		running = true;
		frame.setVisible(true);
		
	}
	
	public static void startGame(){
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
	}
	
	public static void quitGame(){
		running = false;
	}
	
	//Frame is created and set in the middle of the screen
	//<---! NOT YET VISIBLE !--->
	private static void createFrame(){
		frame = new JFrame();
		gamePanel = new GamePanel(character);
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
	
	private class MenuPanel extends JPanel{
		
		private GridBagConstraints c;
		
		private MenuPanel(){
			setLayout(new GridBagLayout());
			c = new GridBagConstraints();
			
			//Set c properties
			c.anchor = GridBagConstraints.WEST;
		}
	}
}
