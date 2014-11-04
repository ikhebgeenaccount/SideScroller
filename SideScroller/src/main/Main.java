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
	private static final int FPS = 45;
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
		System.out.println("Creating character...");
		character = new AlphaGuy();
		
		System.out.println("Setting up panel...");
		gamePanel = new GamePanel(character);
		setPanel(gamePanel);
		
		System.out.println("Starting game...");
		running = true;
		
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
