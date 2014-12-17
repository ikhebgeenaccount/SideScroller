package main.gui.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

import main.Main;
import main.game.object.champion.Champion;
import main.gui.Panel;

public class GamePanel extends Panel implements KeyListener{
	
	//Levels
	private int levelIDx;
	private int levelIDy;
	private int[][] currentLevel;
	private int[][] levelOne;
	private int levelLengthX;
	private int levelLengthY;
	
	//Array for keys
	private boolean[] keys;
	
	//Images
	private Image ground, air, grass_ground, grass_air;
	
	//Coordinates of the character
	private int charx, chary;
	private int newcharx, newchary;
	private boolean movedLeft;
	
	//Tick
	private int tick;
	
	//Gravity and jump instances
	private Gravity gravity;
	private Jump jump;

	//When the character is on an edge of a square, these are true
	private boolean onEdgeY, onEdgeX;
	
	//The character instance of Champion
	private Champion character;
	private String characterName;
	
	//isFired is for animations, only gets true for the duration of the animation (not yet implemented)
	//isFlying is for the spells itself, to check if a spell is currently on screen and needs to be drawed and moved
	private boolean isFiredQ, isFiredW, isFiredE, isFiredR;
	private boolean isFlyingQ, isFlyingW, isFlyingE, isFlyingR;
	
	public GamePanel(Champion character, String characterName){
		tick = 0;
		
		//Get character
		this.character = character;
		this.characterName = characterName;
		
		//Load environment images
		loadPics();
		
		//Create array for keys
		keys = new boolean[1000];
		
		//Set character coordinates to 0
		character.setCoordinates(0, 0);
		
		//Set first level and create levels
		createLevels();
		currentLevel = levelOne;
		levelIDx = 0;
		levelIDy = 0;
		levelLengthX = currentLevel[0].length - 1;
		levelLengthY = currentLevel.length -1;
		
		//Gravity and Jump instances
		gravity = new Gravity();
		jump = new Jump();
		
		//First spell will be fired to the right
		movedLeft = false;
		
		//No spell is currently flying
		isFlyingQ = false;
		isFlyingW = false;
		isFlyingE = false;
		isFlyingR = false;
		
		//Set this as keylistener and make it focusable so the keylistener works
		addKeyListener(this);
		setFocusable(true);
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
				switch(currentLevel[y + (levelIDy * 10)][x + (levelIDx * 20)]){
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
		
		//Draw scene of character
		g.drawImage(character.getCurrentAnimationImage(), character.getCoordinates().x, character.getCoordinates().y, null);
		
		//Check if next scene should play
		character.checkNextScene();
		
		//Draw spells if fired
		if(isFlyingQ){
			g.drawImage(character.q.getCurrentAnimationImage(), character.q.getCoordinates().x, character.q.getCoordinates().y, null);
			character.q.checkNextScene();
		}
		if(isFlyingW){
			g.drawImage(character.w.getCurrentAnimationImage(), character.w.getCoordinates().x, character.w.getCoordinates().y, null);
			character.w.checkNextScene();
		}
	//	if(isFlyingE){
	//		g.drawImage(character.e.getCurrentAnimationImage(), character.e.getCoordinates().x, character.e.getCoordinates().y, null);
	//		character.e.checkNextScene();
	//	}
		if(isFlyingR){
			g.drawImage(character.r.getCurrentAnimationImage(), character.r.getCoordinates().x, character.r.getCoordinates().y, null);
			character.r.checkNextScene();
		}
		
		//Draw current FPS
		g.drawString(Main.getCurrentFPS(), 985, 12);
		g.drawString(Main.getCurrentTPS(), 985, 24);
		g.dispose();
	}
	
	//Images are loaded
	public void loadPics(){
		ClassLoader cldr = this.getClass().getClassLoader();
		air = new ImageIcon(cldr.getResource("img/landscape/default/air.png")).getImage();
		ground = new ImageIcon(cldr.getResource("img/landscape/default/ground.png")).getImage();
		grass_ground = new ImageIcon(cldr.getResource("img/landscape/default/grass-ground.png")).getImage();
		grass_air = new ImageIcon(cldr.getResource("img/landscape/default/grass-air.png")).getImage();
	}
	
	//Called when a key is pressed
	public void keyPressed(KeyEvent e){
		keys[e.getKeyCode()] = true;	    
		
		//If the up-key is pressed, check if the square beneath the character is solid, so he can really jump
		if(keys[KeyEvent.VK_UP]){
			onEdge();
	    	
			charx = character.getCoordinates().x;
			chary = character.getCoordinates().y;
	    
			int matrix_x_left = roundDownToClosestMultipleOfFifty(charx)/50 ;
			int matrix_y =  roundDownToClosestMultipleOfFifty(chary + 99)/50 ;
	    
			boolean solid = false;
	    
			try{
				if(onEdgeY){
					if(currentLevel[matrix_y + 1 + (levelIDy * 10)][matrix_x_left + (levelIDx * 20)] == 0){

					}
				}else{
					if(currentLevel[matrix_y + (levelIDy * 10)][matrix_x_left + (levelIDx * 20)] == 0){
	
					}
				}
			}catch(ArrayIndexOutOfBoundsException e1){
				solid = true;
			}
		    
	    	if(onEdgeY){
				if(onEdgeX){
					if(solid || currentLevel[matrix_y + 1 + (levelIDy * 10)][matrix_x_left + (levelIDx * 20)] != 0){
						jump.jumping = true;
				    	jump.start_tick = tick;
					}else{
						
					}
				}else{
					if(solid || currentLevel[matrix_y + 1 + (levelIDy * 10)][matrix_x_left + (levelIDx * 20)] != 0 || currentLevel[matrix_y + 1 + (levelIDy * 10)][matrix_x_left + (levelIDx * 20) + 1] != 0){
						jump.jumping = true;
				    	jump.start_tick = tick;
					}else{
						
					}
				}
			}else{
				if(onEdgeX){
					if(solid || currentLevel[matrix_y + (levelIDy * 10)][matrix_x_left + (levelIDx * 20)] != 0){
						jump.jumping = true;
				    	jump.start_tick = tick;
					}else{
						
					}
				}else{
					if(solid || currentLevel[matrix_y + (levelIDy * 10)][matrix_x_left + (levelIDx * 20)] != 0 || currentLevel[matrix_y + (levelIDy * 10)][matrix_x_left + (levelIDx * 20)] != 0){
						jump.jumping = true;
				    	jump.start_tick = tick;
					}else{
						
					}
				}
			}
	    	
		}
	
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			Main.pauseGame();
		}
		
		if(keys[KeyEvent.VK_Q] && character.q.getRemainingCooldown() == 0){
			isFiredQ = character.castQ(character.getCoordinates(), movedLeft);
			isFlyingQ = isFiredQ;
		}else{
			keys[KeyEvent.VK_Q] = false;
		}
		
		if(keys[KeyEvent.VK_W] && character.w.getRemainingCooldown() == 0){
			isFiredW = character.castW(character.getCoordinates(), movedLeft);
			isFlyingW = isFiredW
		}else{
			keys[KeyEvent.VK_W] = false;
		}
		
		if(keys[KeyEvent.VK_E] /*&& character.e.getRemainingCooldown() == 0*/){
			//isFiredE = character.castE(character.getCoordinates(), movedLeft);
			//isFlyingE = isFiredE
		}else{
			keys[KeyEvent.VK_E] = false;
		}
		
		if(keys[KeyEvent.VK_R] && character.r.getRemainingCooldown() == 0){
			isFiredR = character.castR(character.getCoordinates(), movedLeft);
			isFlyingR = isFiredR
		}else{
			keys[KeyEvent.VK_R] = false;
		}
	}
	
	//Called when a key is released 
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
	public void update(){
		tick++;
		
		charx = character.getCoordinates().x;
		chary = character.getCoordinates().y;
		
		boolean xmovedleft = false;
		boolean xmovedright = false;
		boolean ymovedup = false;
		boolean ymoveddown = false;
		
		keys[KeyEvent.VK_UP]= false; 
		
		newcharx = charx;
		newchary = chary;
		
		if(keys[KeyEvent.VK_LEFT]){ 	
			newcharx -= character.getSpeed();
		}
	    	
		if(keys[KeyEvent.VK_RIGHT]){  	
		    newcharx += character.getSpeed();
		}   
	    
		onEdge();
		
		int matrix_x_old = roundDownToClosestMultipleOfFifty(charx)/50;
		int matrix_y_old =  roundDownToClosestMultipleOfFifty(chary + 99)/50;
	    
		boolean solid = false;
	    
		try{
			if(currentLevel[matrix_y_old + 1 + (levelIDy * 10)][matrix_x_old + (levelIDx * 20)] == 0){
			
			}
		}catch(ArrayIndexOutOfBoundsException e){
			solid = true;
		}
		//If the character is not jumping, we check for gravity, otherwise we jump()
		if(!jump.jumping){
			//Here we check if the substance beneath the character is solid
			if(onEdgeX){
				if(onEdgeY){
					if(solid || currentLevel[matrix_y_old + 1 + (levelIDy * 10)][matrix_x_old + (levelIDx * 20)] == 0){
						if(gravity.falling){
							newchary = chary + gravity.falldown_px;
							//gravity.setNextFall();
						}else{
							gravity.startFall(tick);
							newchary = chary + gravity.falldown_px;
							//gravity.setNextFall();	    				
						}
					}else{
						gravity.endFall();
					}
				}else{
					if(solid || currentLevel[matrix_y_old + (levelIDy * 10)][matrix_x_old + (levelIDx * 20)] == 0){
						if(gravity.falling){
							newchary = chary + gravity.falldown_px;
							//gravity.setNextFall();
						}else{
							gravity.startFall(tick);
							newchary = chary + gravity.falldown_px;
							//gravity.setNextFall();	    				
						}
					}else{
						gravity.endFall();
					}
				}
			}else{
				if(onEdgeY){
					if(solid || currentLevel[matrix_y_old + 1 + (levelIDy * 10)][matrix_x_old + (levelIDx * 20)] == 0 && currentLevel[matrix_y_old + 1 + (levelIDy * 10)][matrix_x_old + (levelIDx * 20) + 1] == 0){
						if(gravity.falling){
							newchary = chary + gravity.falldown_px;
							//gravity.setNextFall();
						}else{
							gravity.startFall(tick);
							newchary = chary + gravity.falldown_px;
							//gravity.setNextFall();	    				
						}
					}else{
						gravity.endFall();
					}	    		
				}else{
					if(solid || currentLevel[matrix_y_old + (levelIDy * 10)][matrix_x_old + (levelIDx * 20)] == 0 && currentLevel[matrix_y_old + (levelIDy * 10)][matrix_x_old + (levelIDx * 20) + 1] == 0){
						if(gravity.falling){
							newchary = chary + gravity.falldown_px;
							//gravity.setNextFall();
						}else{
							gravity.startFall(tick);
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
			if(tick - jump.start_tick <= jump.jump_tick){
				newchary -= jump.jump_px;
				jump.start_float_tick = tick;
			}else if(tick - jump.start_float_tick <= jump.float_tick){
				
			}else{
				jump.jumping = false;
				newchary -= jump.jump_px;
			}
		}
	    
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
		int matrix_y_upper_right_old = matrix_y_upper_left_old;
		int matrix_x_middle_left_old = matrix_x_upper_left_old;
		int matrix_y_middle_left_old = roundDownToClosestMultipleOfFifty(chary + 49)/50;
		int matrix_x_middle_right_old = matrix_x_upper_right_old;
		int matrix_y_middle_right_old = matrix_y_middle_left_old;
		int matrix_x_bottom_left_old = matrix_x_upper_left_old;
		int matrix_y_bottom_left_old = roundDownToClosestMultipleOfFifty(chary + 99)/50;
		int matrix_x_bottom_right_old = matrix_x_upper_right_old;
		int matrix_y_bottom_right_old = matrix_y_bottom_left_old;
	    
		/*Here we check if the character is allowed to move to the newcharx, newchary. We check the x- and y-axis independently
		 *because we can move in two directions: if the character is falling down, but the right-arrow-key is also pressed, but 
		 *he can't move to the right because of a solid block, he still needs to fall down. If we check x and y at the same time
		 *the character will get stuck and won't fall down even though that should happen.
		 *This is also the reason why we use old coordinates, from before this update(). When we check the y-axis, we check the
		 *newchary but the charx (old), because the old coordinate is always valid, but we need to check the newchary. Vice versa
		 *for the x-axis.
		*/
		//Check x-axis
		//Check if array is not out of bounds
		if(matrix_x_upper_left + (levelIDx * 20) < 0 || matrix_x_upper_right + (levelIDx * 20) < 0 || matrix_x_upper_left + (levelIDx * 20) > levelLengthX || matrix_x_upper_right + (levelIDx * 20) > levelLengthX){
		
		}else{
			//Check if the coordinates where the character is moving are valid
			if(newcharx > charx){
				//Moving right
				movedLeft = false;
				if(currentLevel[matrix_y_upper_right_old + (levelIDy * 10)][matrix_x_upper_right + (levelIDx * 20)] != 0 || currentLevel[matrix_y_middle_right_old + (levelIDy * 10)][matrix_x_middle_right + (levelIDx * 20)] != 0 || currentLevel[matrix_y_bottom_right_old + (levelIDy * 10)][matrix_x_bottom_right + (levelIDx * 20)] != 0){
					//Can't move there!
				}else{
					//Can move there
					charx = newcharx;
					xmovedright = true;
				}
			}else if(newcharx < charx){
				//Moving left
				movedLeft = true;
				if(currentLevel[matrix_y_upper_left_old + (levelIDy * 10)][matrix_x_upper_left + (levelIDx * 20)] != 0 || currentLevel[matrix_y_middle_left_old + (levelIDy * 10)][matrix_x_middle_left + (levelIDx * 20)] != 0 || currentLevel[matrix_y_bottom_left_old + (levelIDy * 10)][matrix_x_bottom_left + (levelIDx * 20)] != 0){
					//Can't move there!
				}else{
					//Can move there
					charx = newcharx;
					xmovedleft = true;
				}
			}	    	
		}
	    
		//Check y-axis
		if(matrix_y_bottom_left + (levelIDy * 10) < 0  || matrix_y_upper_right + (levelIDy * 10) < 0 || matrix_y_bottom_left + (levelIDy * 10) > levelLengthY  || matrix_y_upper_right + (levelIDy * 10) > levelLengthY){

		}else{
			if(newchary > chary){
				//Is falling down

				if(currentLevel[matrix_y_bottom_left + (levelIDy * 10)][matrix_x_bottom_left_old + (levelIDx * 20)] != 0 || currentLevel[matrix_y_bottom_right + (levelIDy * 10)][matrix_x_bottom_right_old + (levelIDx * 20)] != 0){
					//Can't move there
				}else{
					//Can move there
					chary = newchary;
					ymoveddown = true;
				}	    			    		
			}else if(newchary < chary){
				//Is jumping
				if(currentLevel[matrix_y_upper_left + (levelIDy * 10)][matrix_x_upper_left_old + (levelIDx * 20)] != 0 || currentLevel[matrix_y_upper_right + (levelIDy * 10)][matrix_x_upper_right_old + (levelIDx * 20)] != 0){
					//Can't move there
				}else{
					//Can move there
					chary = newchary;
					ymovedup = true;
				}
			}
		}
	    
		//Check if character should go to next level in x-axis
		if((matrix_x_bottom_right  + (levelIDx * 20)) > ((levelIDx + 1) * 20) - 1 && (levelIDx + 1) * 20 <= levelLengthX){
			levelIDx++;
			charx = 0;
		}else if(matrix_x_bottom_left < 0 && levelIDx > 0){
			levelIDx--;
			charx = 950;
		}
	    
		//Check if character should go to next level in y-axis
		if((matrix_y_bottom_right + (levelIDy * 10)) > ((levelIDy + 1) * 10) - 1 && (matrix_y_middle_right + (levelIDy * 10)) > ((levelIDy + 1) * 10) - 1 && (matrix_y_upper_right + (levelIDy * 10)) > ((levelIDy + 1) * 10) - 1){
			levelIDy++;
			chary = -50;
		}else if(matrix_y_upper_right < 0 && levelIDy > 0 && matrix_y_middle_right < 0){
			levelIDy--;
			chary = 500;
		}
	    
		//Check next scenes for spells
		if(isFlyingQ){
			isFlyingQ = character.q.move();
		}
		if(isFlyingW){
			isFlyingW = character.w.move();
		}
		//if(isFlyingE){
		//	isFlyingE = character.e.move();
		//}
		if(isFlyingR){
			isFlyingR = character.r.move();
		}
	    
		//Determine which animation should run
		if(ymovedup){
			character.setAnimationType(Champion.JUMP);
		}else if(ymoveddown){
			character.setAnimationType(Champion.FALL);
		}else if(xmovedleft){
		character.setAnimationType(Champion.WALK_LEFT);
		}else if(xmovedright){
			character.setAnimationType(Champion.WALK_RIGHT);
		}else if(isFiredQ){
			character.setAnimationType(Champion.CAST_Q);
			isFiredQ = false;
		}else if(isFiredW){
			character.setAnimationType(Champion.CAST_W);
			isFiredW = false;
		}else if(isFiredE){
			character.setAnimationType(Champion.CAST_E);
			isFiredE = false;
		}else if(isFiredR){
			character.setAnimationType(Champion.CAST_R);
			isFiredR = false;
		}else{
			character.setAnimationType(Champion.IDLE);
		}

		character.setCoordinates(charx, chary);
	}
	
	//In this class the jumpvariables are stored
	private class Jump{
		
		public boolean jumping;
		public int start_tick;
		public int start_float_tick;
		public int jump_px;
		public int jump_tick;
		public int float_tick;
		
		Jump(){
			jumping = false;
			jump_px = character.getSpeed();
			jump_tick = 31;
			float_tick = 7;
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
			falldown_px = character.getSpeed();
			FALLDOWN_PX_START = character.getSpeed();
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
	//Create levels
	public void createLevels(){
		levelOne = new int[][]{	{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
		
	}
	
	//Getter for String characterName
	public String getCharacterName(){
		return characterName;
	}
	
	//Getter for character
	public Champion getCharacter(){
		return character;
	}
}
