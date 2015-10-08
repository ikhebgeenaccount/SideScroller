/**
 * @author ikhebgeenaccount
 * @version 17 september 2015
 */package main;

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
import main.gui.MessageBox;
import main.gui.Panel;
import main.gui.panel.GamePanel;
import main.gui.panel.MenuPanel;
import main.gui.panel.OptionPanel;
import main.gui.panel.SelectPanel;

/**Class containing the game- and paintloop. This class also handles the frame and its displaying panel. Game properties are also read in this class.
 * @author ikhebgeenaccount
 * @version 17 sep. 2015
 */
public class Main{
	
	//Version
	private static final String VERSION = "v0.5.3";
	private static String menuTheme;
	
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
	
	/**Initializes the character, creates the GamePanel and starts the game- and paintloop in seperate Threads.
	 */
	public static void startGame(){
		
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
	
	/**Starts the game after a pause.
	 */
	public static void unpauseGame(){
		//Set running to true
		running = true;
		
		//Set panel gamepanel in frame
		setPanel(gamePanel);
	}
	
	/**Pauses the game without exiting it, for testing purposes only.
	 */
	public static void freezeGame(){
		running = false;
	}
	
	/**Pauses the game and returns to the main menu.
	 * TODO: add pause menu, with resume option
	 */
	public static void pauseGame(){
		running = false;
		setPanel(menuPanel);
	}
	
	/**Stops the game and returns to main menu.
	 * 
	 */
	public static void returnToMainMenu(){
		running = false;
		setPanel(menuPanel);
	}
	
	/**Stops the game and exits the virtual machine.
	 */
	public static void quitGame(){
		running = false;
		
		System.out.println("Exiting");
		
		System.exit(0);
	}
	
	/**Displays the specified MessageBox.
	 * @param box The MessageBox to be displayed.
	 */
	public static void setMessageBox(MessageBox box){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(box);
		frame.revalidate();
		frame.repaint();
	}
	
	/**In this method the JFrame that contains everything is created.
	 */
	private static void createFrame(){
		
		//Create frame with menuPanel
		frame = new JFrame("SideScroller");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	/**This method changes the displayed Panel in the JFrame.
	 * @param panel Panel panel is set as the visible Panel in the JFrame.
	 */
	public static void setPanel(Panel panel){
		frame.getContentPane().removeAll();
		frame.getContentPane().add(panel);
		panel.requestFocusInWindow();
		frame.revalidate();
		frame.repaint();
	}
	
	/**Reads game properties from /resources/config/config.properties.
	 * 
	 */
	public static void loadProperties(){
		try {
			Properties properties = new Properties();
			InputStream propertiesFile = Main.class.getClassLoader().getResourceAsStream("config/config.properties");
			properties.load(propertiesFile);
			
			//Get properties from config.properties file
			maxFPS = Integer.parseInt(properties.getProperty("FPS"));
			ticksPS = Integer.parseInt(properties.getProperty("TPS"));
			fpsCap = Boolean.parseBoolean(properties.getProperty("fpsCap"));
			menuTheme = properties.getProperty("menuTheme");
			
			propertiesFile.close();
		}catch(IOException e){
			e.printStackTrace();
			
			//Manual
			maxFPS = 45;
			fpsCap = true;
			menuTheme = "default";
			ticksPS = 45;
		}catch(NullPointerException e){
			e.printStackTrace();
			
			//Manual
			maxFPS = 45;
			fpsCap = true;
			menuTheme = "default";
			ticksPS = 45;
		}
	}
	
	/**This private class contains the PaintLoop that calles repaint() on GamePanel.
	 * 
	 * @author ikhebgeenaccount
	 *
	 */
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
	
	/**This private class contains the GameLoop that calls update() on the GamePanel.
	 * 
	 * @author ikhebgeenaccount
	 *
	 */
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
	
	/**Returns MenuPanel.
	 * 
	 * @return MenuPanel menuPanel
	 */
	public static MenuPanel getMenu(){
		return menuPanel;
	}
	
	/**Returns OptionPanel.
	 * 
	 * @return OptionPanel optionPanel
	 */
	public static OptionPanel getOptionPanel() {
		return optionPanel;
	}
	
	
	/**Returns GamePanel.
	 * @return GamePanel gamePanel
	 */
	public static GamePanel getGamePanel(){
		return gamePanel;
	}
	
	/**Returns the used Font with size size.
	 * @param size Font is returned with this size.
	 * @return Font font
	 */
	public static Font getFont(int size){
		return font.deriveFont(Font.PLAIN, size);
	}
	
	/**Returns "v" + version number in String.
	 * @return String VERSION
	 */
	public static String getVersion(){
		return VERSION;
	}
	
	/**Returns true if fps cap is set, otherwise false.
	 * @return boolean fpsCap
	 */
	public static boolean getFPSCap(){
		return fpsCap;
	}
	
	/**Returns the fps cap.
	 * @return int maxFPS
	 */
	public static int getMaxFPS(){
		return maxFPS;
	}
	
	/**Enables or disables the fps cap.
	 * @param cap True to enable fps cap, false to disable.
	 */
	public static void setFPSCap(boolean cap){
		fpsCap = cap;
	}
	
	/**Sets the fps cap.
	 * @param fps Maximum fps.
	 */
	public static void setMaxFPS(int fps){
		maxFPS = fps;
	}
	
	/**Returns the current fps.
	 * @return String currentFPS
	 */
	public static String getCurrentFPS(){
		return String.valueOf(currentFPS);
	}
	
	/**Returns the current ticks per second.
	 * @return String currentTPS
	 */
	public static String getCurrentTPS(){
		return String.valueOf(currentTPS);
	}
	
	/**Returns the current menu theme.
	 * @return String
	 */
	public static String getMenuTheme(){
		return menuTheme;
	}
	
	public static void setMenuTheme(String theme){
		menuTheme = theme;
	}
	
	public static Champion getCharacter(){
		return character;
	}
}
