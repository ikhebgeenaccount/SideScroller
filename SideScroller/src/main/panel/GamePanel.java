package main.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;
import main.champion.Champion;

public class GamePanel extends JPanel implements KeyListener{
	
	//Levels are created at the bottom!
	
	//Array for keys
	private boolean[] keys;
	
	//Images
	private Image ground, air, grass_ground, grass_air;
	private Image charIdle;
	
	//Coordinates of the character
	private int charx, chary;
	private int newcharx, newchary;
	
	//The pixels that the character moves
	private final int MOVEPX;
	
	//Time
	private long time;
	
	//Gravity and jump instances
	private Gravity gravity;
	private Jump jump;

	//When the character is on an edge of a square, these are true
	private boolean onEdgeY, onEdgeX;
	
	//The character instance of Champion
	private Champion character;

	
	//Set this to true to test animations, false to play without.
	private boolean testAnimation = false;
	
	public GamePanel(Champion character){
		this.character = character;
		loadPics();
		keys = new boolean[1000];
		charx = 0;
		chary = 0;
		MOVEPX = 5;
		gravity = new Gravity();
		jump = new Jump();
		ClassLoader cldr = this.getClass().getClassLoader();
		charIdle = new ImageIcon(cldr.getResource("char/img/alphaguy/idle.png")).getImage();
		
		Dimension dim = new Dimension(1000, 500);
		setPreferredSize(dim);
		
		addKeyListener(this);
		setFocusable(true);
		/*
			1. Changing the MOVEPX value itself.
			2. Changing the FPS, the MOVEPX doesn't change, but the visualising of the movement itself goes faster. 
					For example: we have MOVEPX = 10 and FPS = 30. If we change FPS to 45, the MOVEPX 'changes' indirectly to 15.
		*/
	}
	
	/* Fill the panel with landscape
	 * One part : 50x50 px
	 * Panel : 1000x500 px
	 * Width : 20 prts
	 * Height : 10 prts
	 * 
	 * This method is called every frame
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
		if(testAnimation){
			g.drawImage(character.getCurrentAnimationImage(), charx, chary, null);
			character.checkNextScene(time);
		}else{
			g.drawImage(charIdle, charx, chary, null);			
		}
		g.dispose();
	}
	
	//Images are loaded
	public void loadPics(){
		//air = new ImageIcon(GamePanel.class.getResource("/resources/landscape-img/air.png")).getImage();
		ClassLoader cldr = this.getClass().getClassLoader();
		air = new ImageIcon(cldr.getResource("landscape/img/default/air.png")).getImage();
		ground = new ImageIcon(cldr.getResource("landscape/img/default/ground.png")).getImage();
		grass_ground = new ImageIcon(cldr.getResource("landscape/img/default/grass-ground.png")).getImage();
		grass_air = new ImageIcon(cldr.getResource("landscape/img/default/grass-air.png")).getImage();
	}
	
	//Called when a key is pressed
	public void keyPressed(KeyEvent e){
	    keys[e.getKeyCode()] = true;
	    
	    
	    onEdge();
	    
	    int matrix_x_left = roundDownToClosestMultipleOfFifty(charx)/50;
	    int matrix_y =  roundDownToClosestMultipleOfFifty(chary + 99)/50;
	    
	    //If the up-key is pressed, check if the square beneath the character is solid, so he can really jump
	    if(keys[KeyEvent.VK_UP]){
	    	if(onEdgeY){
				if(onEdgeX){
					if(currentLevel[matrix_y + 1][matrix_x_left] != 0){
						jump.jumping = true;
				    	jump.start_time = time;
					}else{
						
					}
				}else{
					if(currentLevel[matrix_y + 1][matrix_x_left] != 0 || currentLevel[matrix_y + 1][matrix_x_left + 1] != 0){
						jump.jumping = true;
				    	jump.start_time = time;
					}else{
						
					}
				}
			}else{
				if(onEdgeX){
					if(currentLevel[matrix_y][matrix_x_left] != 0){
						jump.jumping = true;
				    	jump.start_time = time;
					}else{
						
					}
				}else{
					if(currentLevel[matrix_y][matrix_x_left] != 0 || currentLevel[matrix_y][matrix_x_left] != 0){
						jump.jumping = true;
				    	jump.start_time = time;
					}else{
						
					}
				}
			}
	    	
	    }
	    
	    if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
	    	chary = 0;
	    	charx = 0;
	    }
	    
	    if(e.getKeyCode() == KeyEvent.VK_SPACE){
	    	chary = 350;
	    	charx = 0;
	    }
	}
	
	//Called when a key is released (for jumping this is not the most optimal way to initiate the jump, it only starts when
	//the up-key is released, feels a bit sloppy)
	public void keyReleased(KeyEvent e){
	    keys[e.getKeyCode()] = false;
	    
	}
	
	//Is called when the ASCII-character for that particular button is send to the computer
	public void keyTyped(KeyEvent e){
		
	}
	
	/*	The update method contains:
	 * 		- Movement left and right
	 * 		- Gravity check
	 * 		- Checking if the location where the character is travelling is valid
	 * 	
	 */
	public void update(long startTime){	
		time = startTime;
		
		keys[KeyEvent.VK_UP]= false; 
		
		newcharx = charx;
		newchary = chary;
		
	    if(keys[KeyEvent.VK_LEFT]){
	    	if(testAnimation){
	    		character.setAnimationType(Champion.WALK_LEFT, startTime);
	    	}	    	
	        newcharx -= MOVEPX;
	    }else if(testAnimation){
	    	character.setAnimationType(Champion.IDLE, startTime);
	    }

	    if(keys[KeyEvent.VK_RIGHT]){
	    	if(testAnimation){
	    		character.setAnimationType(Champion.WALK_RIGHT, startTime);
	    	}	    	
	        newcharx += MOVEPX;
	    }else if(testAnimation){
	    	character.setAnimationType(Champion.IDLE, startTime);
	    }
	    
	    checkGravity();
	    
	    //Here we calculate the coordinates of the character in the matrix for the new coordinates that are set by movement/gravity.
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
	    
	    //Here we calculate the coordinates of the character in the matrix for the old coordinates, from before this update().
	    int matrix_x_upper_left_old = roundDownToClosestMultipleOfFifty(charx)/50;
	    int matrix_y_upper_left_old = roundDownToClosestMultipleOfFifty(chary)/50;
	    int matrix_x_upper_right_old = roundDownToClosestMultipleOfFifty(charx + 49)/50;
	    int matrix_y_upper_right_old = matrix_y_upper_left;
	    int matrix_x_middle_left_old = matrix_x_upper_left;
	    int matrix_y_middle_left_old = roundDownToClosestMultipleOfFifty(chary + 49)/50;
	    int matrix_x_middle_right_old = matrix_x_upper_right;
	    int matrix_y_middle_right_old = matrix_y_middle_left;
	    int matrix_x_bottom_left_old = matrix_x_upper_left;
	    int matrix_y_bottom_left_old = roundDownToClosestMultipleOfFifty(chary + 99)/50;
	    int matrix_x_bottom_right_old = matrix_x_upper_right;
	    int matrix_y_bottom_right_old = matrix_y_bottom_left;
	    
	    //Check if array is not out of bounds
	    if(matrix_x_upper_left < 0 || matrix_y_upper_left < 0 || matrix_x_upper_right < 0 || matrix_y_upper_right < 0 || matrix_x_bottom_left < 0 || matrix_y_bottom_left < 0 || matrix_x_bottom_right < 0 || matrix_y_bottom_right < 0){
	    	
	    }else{
	    	//Check if the coordinates where the character is moving are valid
	    	if(newcharx > charx){
	    		//Moving right
	    		if(currentLevel[matrix_y_upper_right_old][matrix_x_upper_right] != 0 || currentLevel[matrix_y_middle_right_old][matrix_x_middle_right] != 0 || currentLevel[matrix_y_bottom_right_old][matrix_x_bottom_right] != 0){
	    			//Can't move there!
	    		}else{
	    			//Can move there
	    			charx = newcharx;
	    		}
	    	}else if(newcharx < charx){
	    		//Moving left
	    		if(currentLevel[matrix_y_upper_left_old][matrix_x_upper_left] != 0 || currentLevel[matrix_y_middle_left_old][matrix_x_middle_left] != 0 || currentLevel[matrix_y_bottom_left_old][matrix_x_bottom_left] != 0){
	    			//Can't move there!
	    		}else{
	    			//Can move there
	    			charx = newcharx;
	    		}
	    	}
	    	//Feet get stuck when jumping or falling
	    	//Rest of the body does not get stuck, only feet detection points. No idea...
	    	//So: only bottom_left and bottom_right is a problem.
	    	/*Here we check if the character is allowed to move to the newcharx, newchary. We check the x- and y-axis independently
	    	 *because we can move in two directions: if the character is falling down, but the right-arrow-key is also pressed, but 
	    	 *he can't move to the right because of a solid block, he still needs to fall down. If we check x and y at the same time
	    	 *the character will get stuck and won't fall down even though that should happen.
	    	 *This is also the reason why we use old coordinates, from before this update(). When we check the y-axis, we check the
	    	 *newchary but the charx (old), because the old coordinate is always valid, but we need to check the newchary. Vice versa
	    	 *for the x-axis.
	    	*/
	    	if(newchary > chary){
	    		//Is falling down
	    		if(currentLevel[matrix_y_bottom_left][matrix_x_bottom_left_old] != 0 || currentLevel[matrix_y_bottom_right][matrix_x_bottom_right_old] != 0){
	    			//Can't move there
	    		}else{
	    			//Can move there
	    			chary = newchary;
	    		}	    			    		
	    	}else if(newchary < chary){
	    		//Is jumping
	    		if(currentLevel[matrix_y_upper_left][matrix_x_upper_left_old] != 0 || currentLevel[matrix_y_upper_right][matrix_x_upper_right_old] != 0){
	    			//Can't move there
	    		}else{
	    			//Can move there
	    			chary = newchary;
	    		}
	    	}
	    }
	    
	    //System.out.println("x: " + charx);
	    //System.out.println("y: " + chary);
	}
	
	//In this class the jumpvariables are stored
	private class Jump{
		
		public boolean jumping;
		public long start_time;
		public int jump_px;
		public long jump_time;
		
		Jump(){
			jumping = false;
			jump_px = MOVEPX;
			jump_time = 1000/(45 * 16);
		}
	}
	
	//The Gravity class contains severable variables for gravity:
	//		- The amount of pixels to fall down: falldown_px
	// 		- A boolean falling which is true when falling, otherwise false
	//		- FALLDOWN_PX_START, now unused, maybe used later when adding acceleration
	//		- start_frame, now unused, maybe used later for acceleration
	private class Gravity{
		
		public long start_time;
		public final int FALLDOWN_PX_START;
		public int falldown_px;
		public boolean falling;
		
		Gravity(){
			falling = false;
			falldown_px = MOVEPX;
			FALLDOWN_PX_START = MOVEPX;
		}
		
		//We start the fall by telling falling = true
		public void startFall(long startTime){
			start_time = startTime;
			falling = true;
		}
		
		//The fall ends, falling = false
		public void endFall(){
			//falldown_px = FALLDOWN_PX_START;
			falling = false;
		}
	}
	
	//In the checkGravity() method the squares beneath the characters feet are checked if they are air, see resources/gravity.txt
	public void checkGravity(){	    
	    onEdge();
		
	    int matrix_x_left = roundDownToClosestMultipleOfFifty(charx)/50;
	    int matrix_y =  roundDownToClosestMultipleOfFifty(chary + 99)/50;
	    
	    //If the character is not jumping, we check for gravity, otherwise we jump()
		if(!jump.jumping){
			//Here we check if the substance beneath the character is solid
		    if(onEdgeX){
		    	if(onEdgeY){
		    		if(currentLevel[matrix_y + 1][matrix_x_left] == 0){
		    			if(gravity.falling){
		    				newchary = chary + gravity.falldown_px;
		    				//gravity.setNextFall();
		    			}else{
		    				gravity.startFall(time);
		    				newchary = chary + gravity.falldown_px;
		    				//gravity.setNextFall();	    				
		    			}
		    		}else{
		    			gravity.endFall();
		    		}
		    	}else{
		    		if(currentLevel[matrix_y][matrix_x_left] == 0){
		    			if(gravity.falling){
		    				newchary = chary + gravity.falldown_px;
		    				//gravity.setNextFall();
		    			}else{
		    				gravity.startFall(time);
		    				newchary = chary + gravity.falldown_px;
		    				//gravity.setNextFall();	    				
		    			}
		    		}else{
		    			gravity.endFall();
		    		}
		    	}
		    }else{
		    	if(onEdgeY){
		    		if(currentLevel[matrix_y + 1][matrix_x_left] == 0 && currentLevel[matrix_y + 1][matrix_x_left + 1] == 0){
		    			if(gravity.falling){
		    				newchary = chary + gravity.falldown_px;
		    				//gravity.setNextFall();
		    			}else{
		    				gravity.startFall(time);
		    				newchary = chary + gravity.falldown_px;
		    				//gravity.setNextFall();	    				
		    			}
		    		}else{
		    			gravity.endFall();
		    		}	    		
		    	}else{
		    		if(currentLevel[matrix_y][matrix_x_left] == 0 && currentLevel[matrix_y][matrix_x_left + 1] == 0){
		    			if(gravity.falling){
		    				newchary = chary + gravity.falldown_px;
		    				//gravity.setNextFall();
		    			}else{
		    				gravity.startFall(time);
		    				newchary = chary + gravity.falldown_px;
		    				//gravity.setNextFall();	    				
		    			}
		    		}else{
		    			gravity.endFall();
		    		}    		
		    	}
		    }
		}else{
			//Coordinates are changed: character jumps
			//Also checks if the jump shouldn't end yet
			if(time - jump.start_time <= jump.jump_time){
				newchary -= jump.jump_px;
			}else{
				jump.jumping = false;
				newchary -= jump.jump_px;
			}			
		}		
	}
	
	public int roundDownToClosestMultipleOfFifty(int num){
		int mod = num % 50;
		return num-mod;    
	}
	
	//Checks if the character is on the edge of a square on the y- and x-axis, if so, onEdgeY = true, or onEdgeX = true.
	public void onEdge(){
		//On the x-axis
	    if(charx == roundDownToClosestMultipleOfFifty(charx)){
	    	onEdgeX = true;
	    }else{
	    	onEdgeX = false;
	    }
	    
	    //On the y-axis
	    if(chary == roundDownToClosestMultipleOfFifty(chary)){
	    	onEdgeY = true;
	    }else{
	    	onEdgeY = false;
	    }
		
	}
	
	/*Levels:
	 * 0. Air
	 * 1. Ground
	 */
	private int[][] levelOne = {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
								{1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0},
								{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
	private int[][] currentLevel = levelOne;
}
