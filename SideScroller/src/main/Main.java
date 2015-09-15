package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;

import main.game.object.champion.Champion;
import main.game.object.champion.champions.alphaguy.AlphaGuy;
import main.gui.Panel;
import main.gui.panel.GamePanel;
import main.gui.panel.MenuPanel;
import main.gui.panel.OptionPanel;
import main.gui.panel.SelectPanel;

public class Main extends JFrame{
	
	//Version
	private static final String VERSION = "v0.5.1";
	
	//Game properties
	private static int maxFPS;
	private static long currentFPS;
	private static boolean fpsCap;
	private static int ticksPS;
	private static long currentTPS;
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
	
	//Threads
	private static GameLoop gameLoop;
	private static PaintLoop paintLoop;
	
	//Font
	private static Font font;
	
	//Main method
	public static void main(String[] args){
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, Main.class.getClassLoader().getResourceAsStream("font/neuropolitical rg.ttf"));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadProperties();
		createFrame();		
		//Thread for frames
		frame.setVisible(true);
	}
	
	//Method to start game, creates character and game loop thread
	public static void startGame(){		
		ticksPS = 45;
		
		//Create character
		character = new AlphaGuy();
		
		//Create gamepanel
		gamePanel = new GamePanel(character, "alphaguy");
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
		frame.add(menuPanel);
		optionPanel = new OptionPanel();
		frame.pack();
		frame.setLocationRelativeTo(null);

	}
	
	//Method to change panel
	public static void setPanel(Panel panel){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel);
		panel.requestFocusInWindow();
		frame.revalidate();
		frame.repaint();
	}
	
	//Read config/config.properties
	public static void loadProperties(){
		try {
			Properties properties = new Properties();
			InputStream propertiesFile = Main.class.getClassLoader().getResourceAsStream("config/config.properties");
			properties.load(propertiesFile);
			
			//Get properties from config.properties file
			maxFPS = Integer.parseInt(properties.getProperty("FPS"));
			fpsCap = Boolean.parseBoolean(properties.getProperty("fpsCap"));
			
			propertiesFile.close();
		}catch(IOException e){
			e.printStackTrace();
			
			//Manual
			maxFPS = 45;
			fpsCap = true;
		}catch(NullPointerException e){
			e.printStackTrace();
			
			//Manual
			maxFPS = 45;
			fpsCap = true;
		}
	}
	
	//Thread for repaint(), graphics
	private static class PaintLoop extends Thread{
		
		private PaintLoop(){
			
		}
		
		public void run(){
			long startTime;
			long frameTime = 1000/maxFPS;
			long startTimeFPS = System.currentTimeMillis();
			long endTime;
			while(running){
				//The startTime of this loop
				startTime = System.currentTimeMillis();
				gamePanel.repaint();
				endTime = System.currentTimeMillis();
				try{
					//If the time it took to paint this frame is bigger than the time set for one frame, it needs to instantly
					//repaint(), since it is behind on schedule
					if(endTime - startTime > frameTime){
						
					 //If the time it took to paint this frame is equal to the time set to paint one frame, it needs to 
					 //instantly repaint(), since it is perfect on schedule
					}else if(endTime - startTime == frameTime){
						
					 //If it took less time, we need to sleep the remaining millis of the loop time	
					}else if(fpsCap){
						Thread.sleep(frameTime - (endTime - startTime));
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
					e.printStackTrace();
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
			long endTime;
			long startTimeTPS = System.currentTimeMillis();
			while(running){
				startTime = System.currentTimeMillis();
				gamePanel.update();
				endTime = System.currentTimeMillis();
				try {
					if(endTime - startTime > tickTime){
						
					}else if(endTime - startTime == tickTime){
						
					}else{
						Thread.sleep(tickTime - (endTime - startTime));
					}
					
					if(System.currentTimeMillis() - startTimeTPS >= 500){
						if(System.currentTimeMillis() - startTime == 0){
							currentTPS = ticksPS;;
							startTimeTPS = System.currentTimeMillis();
						}else{
							currentTPS = 1000 / (System.currentTimeMillis() - startTime);
							startTimeTPS = System.currentTimeMillis();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static MenuPanel getMenu(){
		return menuPanel;
	}

	public static OptionPanel getOptionPanel() {
		return optionPanel;
	}
	
	public static GamePanel getGamePanel(){
		return gamePanel;
	}
	
	//Method to return font
	public static Font getFont(int size){
		return font.deriveFont(Font.PLAIN, size);
	}
	
	//Method to return Version string
	public static String getVersion(){
		return VERSION;
	}
	
	//Methods to get game properties
	public static boolean getFPSCap(){
		return fpsCap;
	}
	
	public static int getMaxFPS(){
		return maxFPS;
	}
	
	//Methods to set game properties
	public static void setFPSCap(boolean cap){
		fpsCap = cap;
	}
	
	public static void setMaxFPS(int fps){
		maxFPS = fps;
	}
	
	//Methods to get current speed
	public static String getCurrentFPS(){
		return String.valueOf(currentFPS);
	}
	
	public static String getCurrentTPS(){
		return String.valueOf(currentTPS);
	}
}
