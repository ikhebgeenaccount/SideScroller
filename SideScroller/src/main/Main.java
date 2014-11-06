package main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.champion.Champion;
import main.champion.champions.AlphaGuy;
import main.panel.GamePanel;
import main.panel.MenuPanel;
import main.panel.OptionPanel;
import main.panel.SelectPanel;

public class Main extends JFrame{
	
	//Game properties
	private static int maxFPS;
	private static boolean fpsCap;
	private static int ticksPS;
	private static boolean running;
	
	//Frame
	private static JFrame frame;
	
	//Character
	private static Champion character;
	
	//Panels	
	private static MenuPanel menuPanel;
	private static GamePanel gamePanel;
	private static OptionPanel optionPanel;
	private static SelectPanel selectPanel;
	
	//Main method
	public static void main(String[] args){
		System.out.println("Initializing game...");
		
		//System.out.println("Reading config...");
		//System.out.println("Updating settings...");
		
		System.out.println("Setting up frame...");
		createFrame();		
		//Thread for frames
		frame.setVisible(true);
		
	}
	
	//Method to start game, creates character and game loop thread
	public static void startGame(){
		System.out.println("Initializing settings...");
		maxFPS = 45;
		fpsCap = false;
		ticksPS = 90;
		
		System.out.println("Creating character...");
		character = new AlphaGuy();
		
		System.out.println("Setting up panel...");
		gamePanel = new GamePanel(character);
		setPanel(gamePanel);
		
		System.out.println("Starting game...");
		running = true;
		
		new Thread("Game loop"){
			public void run(){
				long startTime;
				long tickTime = 1000/ticksPS;
				while(running){
					startTime = System.currentTimeMillis();
					gamePanel.update();
					try {
						if(System.currentTimeMillis() - startTime > tickTime){
							
						}else if(System.currentTimeMillis() - startTime == tickTime){
							
						}else{
							Thread.sleep(tickTime - (System.currentTimeMillis() - startTime));
						}
					} catch (InterruptedException e) {
						
					}
				}
			}
		}.start();
		
		new Thread("Graphics loop"){
			public void run(){
				long startTime;
				long frameTime = 1000/maxFPS;
				while(running){
					//The startTime of this loop
					startTime = System.currentTimeMillis();
					gamePanel.repaint();
					try{
						//If the time it took to paint this frame is bigger than the time set for one frame, it needs to instantly
						//repaint(), since it is behind on schedule
						if(System.currentTimeMillis() - startTime > frameTime){
							
						 //If the time it took to paint this frame is equal to the time set to paint one frame, it needs to 
						 //instantly repaint(), since it is perfect on schedule
						}else if(System.currentTimeMillis() - startTime == frameTime){
							
						 //If it took less time, we need to sleep the remaining millis of the loop time	
						}else if(fpsCap){
							Thread.sleep(frameTime - (System.currentTimeMillis() - startTime));
						}
					}catch(InterruptedException e){
						
					}
				}
			}
		}.start();
	}
	
	//Method to pause game
	public static void quitGame(){
		running = false;
	}
	
	//Frame is created and set in the middle of the screen
	//<---! NOT YET VISIBLE !--->
	private static void createFrame(){
		
		//Create frame with menuPanel
		frame = new JFrame("SideScroller");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		menuPanel = new MenuPanel();
		frame.getContentPane().add(menuPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);

	}
	
	//Label to fill up space
	private static class FillerLabel extends JLabel{
		
		private FillerLabel(){
			setText("         ");
		}
	}
	
	public static void setPanel(JPanel panel){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel);
		panel.requestFocusInWindow();
		frame.revalidate();
		frame.repaint();
	}
}
