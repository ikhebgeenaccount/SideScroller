package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;

import main.game.champion.Champion;
import main.game.champion.champions.AlphaGuy;
import main.gui.Panel;
import main.gui.panel.GamePanel;
import main.gui.panel.LoadPanel;
import main.gui.panel.MenuPanel;
import main.gui.panel.OptionPanel;
import main.gui.panel.SelectPanel;

public class Main extends JFrame{
	
	//Game properties
	private static int maxFPS;
	private static long currentFPS;
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
	private static LoadPanel loadPanel;
	private static OptionPanel optionPanel;
	private static SelectPanel selectPanel;
	
	//Threads
	private static GameLoop gameLoop;
	private static PaintLoop paintLoop;
	
	//Main method
	public static void main(String[] args){
		createFrame();		
		//Thread for frames
		frame.setVisible(true);
	}
	
	//Method to start game, creates character and game loop thread
	public static void startGame(){
		loadPanel = new LoadPanel(12, "Initializing settings");
		setPanel(loadPanel);
		//Set game properties
		
		try {			
			//Read config.properties
			//See: http://stackoverflow.com/questions/26871358/getresourceasstream-returning-null-for-properties-file
			Properties properties = new Properties();
			InputStream propertiesFile = Main.class.getClassLoader().getResourceAsStream("config/config.properties");
			properties.load(propertiesFile);
			
			//Get properties from config.properties file
			int maxFPS = Integer.parseInt(properties.getProperty("FPS"));
			System.out.println(properties.getProperty("FPS"));
			System.out.println(maxFPS);
			boolean fpsCap = Boolean.getBoolean(properties.getProperty("fpsCap"));
		}catch(IOException e){
			System.err.println(e.getStackTrace());
			System.err.println(e.getMessage());
			
			//Manual
			maxFPS = 45;
			fpsCap = true;
		}catch(NullPointerException e){
			e.printStackTrace();
			System.err.println("Can't find config.properties! Using default properties.");
			
			//Manual
			maxFPS = 45;
			fpsCap = true;
		}
		
		ticksPS = 90;
		
		//Create character
		loadPanel.setNextLoadPart("Creating character");
		character = new AlphaGuy();
		
		//Create gamepanel
		loadPanel.setNextLoadPart("Setting up panel");
		gamePanel = new GamePanel(character);
		setPanel(gamePanel);
		
		//Start game
		running = true;
		
		//Paintloop
		paintLoop = new PaintLoop();
		paintLoop.start();
		
		//Gameloop
		gameLoop = new GameLoop();
		gameLoop.start();
	}
	
	//Method to unpause game
	public static void unpauseGame(){
		//Set running to true
		running = true;
		
		//Set panel gamepanel in frame
		setPanel(gamePanel);
		
		//Start both threads for update() and repaint()
		paintLoop.start();
		gameLoop.start();
	}
	
	//Method to pause game
	public static void pauseGame(){
		running = false;
		setPanel(menuPanel);
	}
	
	//Method to quit game
	public static void quitGame(){
		running = false;
		
		System.out.println("Exiting");
		
		System.exit(0);
	}
	
	//Frame is created and set in the middle of the screen
	//<---! NOT YET VISIBLE !--->
	private static void createFrame(){
		
		//Create frame with menuPanel
		frame = new JFrame("SideScroller");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				quitGame();
			}
		});
		
		//Create menuPanel and set that in frame
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
	
	//Method to change panel
	public static void setPanel(Panel panel){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel);
		panel.requestFocusInWindow();
		frame.revalidate();
		frame.repaint();
	}
	
	//Method to return currentFPS
	public static String getCurrentFPS(){
		return String.valueOf(currentFPS);
	}
	
	//Thread for repaint(), graphics
	private static class PaintLoop extends Thread{
		
		private PaintLoop(){
			
		}
		
		public void run(){
			long startTime;
			long frameTime = 1000/maxFPS;
			long startTimeFPS = System.currentTimeMillis();
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
					if(System.currentTimeMillis() - startTimeFPS >= 500){
						if(System.currentTimeMillis() - startTime == 0){
							currentFPS = maxFPS;
							startTimeFPS = System.currentTimeMillis();
						}else{
							currentFPS = 1000 / (System.currentTimeMillis() - startTime);
							startTimeFPS = System.currentTimeMillis();
						}
					}
										
				}catch(InterruptedException e){
					System.err.println(e.getMessage());
				}
			}
		}
	}
	
	//Thread for update(), gameloop
	private static class GameLoop extends Thread{
		
		public GameLoop(){
			
		}
		
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
					System.err.println(e.getMessage());
				}
			}
		}
	}
	
	public static LoadPanel getLoadPanel(){
		return loadPanel;
	}
}
