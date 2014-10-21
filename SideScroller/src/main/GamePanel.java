package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener{
	
	//Levels are created at the bottom!
	private boolean[] keys = new boolean[100];
	private Image ground, air, grass_ground, grass_air;
	private int charx = 0, chary = 350;
	private final int MOVEPX = 5;
	private boolean jumping = false;
	private int jump_start;
	private int frame = 0;
	private int jump_px[] = {50,30,15,5,0,-5,15,30,50};
	private int falldownpx = 20;
	private final int FALLDOWNPX_START = 5;
	private int falldownframe = 0;
	//private static Image char_idle;
	
	public GamePanel(){
		loadPics();
	}
	
	/* Fill the panel with landscape
	 * One part : 50x50 px
	 * Panel : 1000x500 px
	 * Width : 20 prts
	 * Height : 10 prts
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//i is x
		//j is y
		for(int x = 0; x < 20; x++){
			for(int y = 0; y < 10; y++){
                //Decide which landscape-img should be used
				switch(currentLevel[y][x]){
					case 0:g.drawImage(air, 50 * x, 50 * y, null);
						break;
					case 1:g.drawImage(ground, 50 * x, 50 * y, null);
						break;	
					case 2:g.drawImage(grass_ground, 50 * x, 50 * y, null);
						break;
					case 3:g.drawImage(grass_air, 50 * x, 50 * y, null);
						break;
				}
			}
		}
		g.drawImage(Main.getAnimationFrame(), charx, chary, null);
		g.dispose();
		frame++;
	}
	
	public void loadPics(){
		//air = new ImageIcon(GamePanel.class.getResource("/resources/landscape-img/air.png")).getImage();
		air = new ImageIcon("resources\\landscape-img\\air.png").getImage();
		ground = new ImageIcon("resources\\landscape-img\\ground.png").getImage();
		grass_ground = new ImageIcon("resources\\landscape-img\\grass-ground.png").getImage();
		grass_air = new ImageIcon("resources\\landscape-img\\grass-air.png").getImage();
	}

	public void keyPressed(KeyEvent e){
	    keys[e.getKeyCode()] = true;
	    
	    if(keys[KeyEvent.VK_UP]){
	        jumping = true;
	        jump_start = frame;
	    }
	}

	public void keyReleased(KeyEvent e){
	    keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e){
		
	}

	public void update(){
		int newcharx = charx;
		int newchary = chary;

	    if(keys[KeyEvent.VK_LEFT]){
	        newcharx -= MOVEPX;
	    }

	    if(keys[KeyEvent.VK_RIGHT]){
	        newcharx += MOVEPX;
	    }
	    
	    if(keys[KeyEvent.VK_UP]){
	    	newchary = chary - MOVEPX;
	    }
	    
	    int plus = (chary)/50 == roundDownToClosestMultipleOfFifty(chary)/50 ? 1 : 0;
	    int checkx = (charx)/50 == roundDownToClosestMultipleOfFifty(charx)/50 ? 1 : 2;
	    int matrix_x = roundDownToClosestMultipleOfFifty(charx)/50;
	    
	    if(checkx == 1){
	    	if(currentLevel[roundDownToClosestMultipleOfFifty(newchary + 99)/50 + plus][matrix_x] == 0){
		    	if(falldownframe == 0){
		    		falldownframe = frame;	    		
		    		newchary -= FALLDOWNPX_START;	
		    	}else{
		    		newchary -= FALLDOWNPX_START * (falldownframe - frame);
		    	}
		    	
		    }else{
		    	falldownframe = 0;
		    }
	    }else{
	    	if(currentLevel[roundDownToClosestMultipleOfFifty(newchary + 99)/50 + plus][matrix_x] == 0 && currentLevel[roundDownToClosestMultipleOfFifty(newchary + 99)/50 + 1 + plus][matrix_x + 1] == 0){
		    	if(falldownframe == 0){
		    		falldownframe = frame;	    		
		    		newchary -= FALLDOWNPX_START;	
		    	}else{
		    		newchary -= FALLDOWNPX_START * (falldownframe - frame);
		    	}
		    	
		    }else{
		    	falldownframe = 0;
		    }
	    }
	    
	    
	    
	    int matrix_x_upper_left = roundDownToClosestMultipleOfFifty(newcharx)/50;
	    int matrix_y_upper_left = roundDownToClosestMultipleOfFifty(newchary)/50;
	    int matrix_x_upper_right = roundDownToClosestMultipleOfFifty(newcharx + 49)/50;
	    int matrix_y_upper_right = matrix_y_upper_left;
	    int matrix_x_middle_left = matrix_x_upper_left;
	    int matrix_y_middle_left = roundDownToClosestMultipleOfFifty(newchary + 49)/50;
	    int matrix_x_middle_right = matrix_x_upper_right;
	    int matrix_y_middle_right = matrix_y_middle_left;
	    int matrix_x_bottom_left = matrix_x_upper_left;
	    int matrix_y_bottom_left = roundDownToClosestMultipleOfFifty(newchary + 99)/50;
	    int matrix_x_bottom_right = matrix_x_upper_right;
	    int matrix_y_bottom_right = matrix_y_bottom_left;
	    
	    //Check if array is not out of bounds
	    if(matrix_x_upper_left < 0 || matrix_y_upper_left < 0 || matrix_x_upper_right < 0 || matrix_y_upper_right < 0 || matrix_x_bottom_left < 0 || matrix_y_bottom_left < 0 || matrix_x_bottom_right < 0 || matrix_y_bottom_right < 0){
	    	
	    }else{
	    	//Check if the coordinates where the character is moving are valid
	    	if(currentLevel[matrix_y_upper_left][matrix_x_upper_left] != 0 || currentLevel[matrix_y_upper_right][matrix_x_upper_right] != 0 || currentLevel[matrix_y_bottom_right][matrix_x_bottom_right] != 0 || currentLevel[matrix_y_bottom_left][matrix_x_bottom_left] != 0 || currentLevel[matrix_y_middle_left][matrix_x_middle_left] != 0 || currentLevel[matrix_y_middle_right][matrix_x_middle_right] != 0){
	    		//Can't move there!
		    }else{
		    	//Can move there.
		    	chary = newchary;
		    	charx = newcharx;
		    }
	    }
	    System.out.println("x: " + charx);
	    System.out.println("y: " + chary);
	    
	}
	
	public int roundDownToClosestMultipleOfFifty(int num){
		int mod = num % 50;
		return num-mod;    
	}
	
	/*Levels:
	 * 0. Air
	 * 1. Ground
	 */
	private int[][] levelOne = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
								{1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0},
								{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
								{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
	private int[][] currentLevel = levelOne;
}
