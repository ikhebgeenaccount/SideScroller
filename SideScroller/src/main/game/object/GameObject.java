package main.game.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import main.game.animation.Animation;
import main.game.coordinate.Coordinate;
import main.game.navmesh.NavMesh;

/**GameObject is the basis for every entity in the game that has to move, or has animations, or has health. 
 * @author ikhebgeenaccount
 * @version 17 sep. 2015
 */
public class GameObject {
	
	//Animations
	private Animation[] animations;
	private Animation currentAnimation;
	private int currentAnimationType;
	
	private BufferedImage healthBar;
	
	//Coordinates
	private Coordinate coordinates;
	
	private int width, height;
	
	//Speed in px
	private int startSpeed;
	private int speed, yspeed, defaultSpeed;
	
	private int health;
	private int currentHealth;
	
	/**Creates a GameObject with default values and Coordinate (0,0)
	 * 
	 */
	public GameObject(){
		coordinates = new Coordinate(0, 0);
		
		animations = new Animation[10];
		currentAnimation = new Animation();
		currentAnimationType = -1;
		
	}
	
	/**Creates a GameObject with specified width and height, and Coordinate (0,0)
	 * @param width Width of the to be created GameObject
	 * @param height Height of the to be created GameObject
	 */
	public GameObject(int width, int height){
		coordinates = new Coordinate(0, 0);
		
		animations = new Animation[10];
		currentAnimation = new Animation();
		currentAnimationType = -1;
		
		healthBar = new BufferedImage(width, 8, BufferedImage.TYPE_INT_RGB);
	}
	
	//Animation methods
		//Set animation type
		/**Sets the animation type, which decided what animation is played. Depending on what type of GameObject this is, (Minion, Champion, etc...) the animation that will be played depends on the constants in the class of the extension of GameObject (Minion, etc...).
		 * @param type The type of animation that needs to be played.
		 */
		public void setAnimationType(int type){
			//We only have to change the animationtype when it is now running another type, otherwise the animation will continuously
			//reset itself
			if(currentAnimationType == type){
					
			}else{
				currentAnimationType = type;
				currentAnimation = animations[type];
			}
		}
		
		//Add an animation with default frame size: 50 x 100 (for characters)
		/**Adds an animation with default width of 50 pixels and default height of 100 pixels
		 * @param type The type of animation this is, corresponds to a constant in a subclass of GameObject, such as Minion.
		 * @param animationSprite The image containing all frames of the animation.
		 * @param sceneLength The length of one scene in milliseconds.
		 */
		public void addAnimation(int type, BufferedImage animationSprite, int sceneLength){
			addAnimation(type, animationSprite, sceneLength, 50, 100);
		}
		
		//Add an animation with different frame size: frameWidth x frameHeight
		/**Adds an animation
		 * @param type The type of animation this is, corresponds to a constant in a subclass of GameObject, such as Minion.
		 * @param animationSprite The image containing all frames of the animation.
		 * @param sceneLength The length of one scene in milliseconds.
		 * @param frameWidth The width of one frame.
		 * @param frameHeight The height of one frame.
		 */
		public void addAnimation(int type, BufferedImage animationSprite, int sceneLength, int frameWidth, int frameHeight){
			animations[type] = new Animation(frameWidth, frameHeight);
			width = frameWidth;
			height = frameHeight;
			int scenes = animationSprite.getWidth() / frameWidth;
			for(int i = 0; i < scenes; i++){
				animations[type].addScene(animationSprite.getSubimage(frameWidth * i, 0, frameWidth, frameHeight), sceneLength);
			}
		}
		
		//Check if the next scene should start playing
		/**Checks if the next scene should be played.
		 * 
		 */
		public void checkNextScene(){
			currentAnimation.nextScene();
		}
		
		//Return the image of the current scene
		/** Returns the image of the animation that currently plays.
		 * @return The image of the current scene of the current animation.
		 */
		public Image getCurrentAnimationImage(){
			return currentAnimation.getCurrentSceneImage();
		}
		
		/**Returns the size of this GameObject.
		 * @return int[]{width, height}.
		 */
		public int[] getSize(){
			return new int[]{width, height};
		}
		
		/**Returns the width of this GameObject.
		 * @return int width
		 */
		public int getWidth(){
			return width;
		}
		
		/**Returns the height of this GameObject.
		 * @return int height
		 */
		public int getHeight(){
			return height;
		}
	
		
		
	//Coordinate methods
		//Return the current coordinates for this GameObject
		/**Returns the current Coordinate of this GameObject
		 * @return Coordinate coordinate
		 */
		public Coordinate getCoordinates(){
			return coordinates;
		}
		
		
	
	//Movement methods
	
		/**Returns the current speed in x-axis.
		 * @return int speed
		 */
		public int getSpeed(){
			return speed;
		}
		
		/**Returns the default speed in x-axis.
		 * @return int defaultSpeed
		 */
		public int getDefaultSpeed(){
			return defaultSpeed;
		}
		
		/**Sets the default speed.
		 * @param defaultSpeed The new value for default speed
		 */
		public void setDefaultSpeed(int defaultSpeed){
			this.defaultSpeed = defaultSpeed;
		}
		
		/**Returns the speed in y-axis.
		 * @return int yspeed
		 */
		public int getYSpeed(){
			return yspeed;
		}
		
		/**Sets the speed in x-axis.
		 * @param speed The new speed in x-axis
		 */
		public void setSpeed(int speed){
			this.startSpeed = speed;
			this.speed = speed;
		}
		
		/**Accelerate speed with acceleration. This accelerates the speed in y-axis.
		 * @param acceleration Acceleration
		 */
		public void accelerate(int acceleration){
			if(yspeed + acceleration > 0){
				yspeed += acceleration;
			}
		}
		
		/**Resets the speed in y-axis to zero.
		 * 
		 */
		public void resetSpeed(){
			this.yspeed = 0;
		}
		
		/**Checks if this GameObject can move up in NavMesh navMesh. Returns true if it has moved, otherwise false
		 * @param navMesh The NavMesh in which the movement happens
		 * @return boolean moved
		 */
		public boolean moveUp(NavMesh navMesh){
			boolean move = true;
			for(int i = 0; i <= yspeed; i++){
				for(int j = 0; j < width; j++){
					if(coordinates.y - 1 >= 0){
						int rgb = navMesh.getRGB(coordinates.x + j, coordinates.y - 1);
						
						if(rgb != Color.BLUE.getRGB()){
							//Not allowed to move
							return false;
						}					
					}else{
						return false;
					}
				}
				if(move){
					//Move
					getCoordinates().setCoordinates(coordinates.x, coordinates.y - 1);
				}
			}
			return true;		
		}
		
		/**Checks if this GameObject can move down in NavMesh navMesh. Returns true if it has moved, otherwise false
		 * @param navMesh The NavMesh in which the movement happens
		 * @return boolean moved
		 */
		public boolean moveDown(NavMesh navMesh){
			boolean move = true;
			for(int i = 0; i <= yspeed; i++){
				for(int j = 0; j < width; j++){
					if(coordinates.y + height + 1 <= navMesh.getHeight()){
						int rgb = navMesh.getRGB(coordinates.x + j, coordinates.y + height);
						
						if(rgb != Color.BLUE.getRGB()){
							//Not allowed to move
							return false;
						}
					}else{
						return false;
					}
				}
				if(move){
					//Move
					getCoordinates().setCoordinates(coordinates.x, coordinates.y + 1);
				}
			}
			return true;
		}

		
		/**Checks if this GameObject can move left in NavMesh navMesh. Returns true if it has moved, otherwise false
		 * @param navMesh The NavMesh in which the movement happens
		 * @return boolean moved
		 */
		public boolean moveLeft(NavMesh navMesh){
			boolean move = true;
			for(int i = 0; i <= speed; i++){
				for(int j = 0; j < height; j++){
					if(coordinates.x - 1 >= 0){
						int rgb = navMesh.getRGB(coordinates.x - 1, coordinates.y + j);
						
						if(rgb != Color.BLUE.getRGB()){
							//Not allowed to move
							return false;
						}					
					}else{
						return false;
					}
				}
				
				if(move){
					//Move
					getCoordinates().setCoordinates(coordinates.x - 1, coordinates.y);
				}
			}
			return true;
		}

		
		/**Checks if this GameObject can move right in NavMesh navMesh. Returns true if it has moved, otherwise false
		 * @param navMesh The NavMesh in which the movement happens
		 * @return boolean moved
		 */
		public boolean moveRight(NavMesh navMesh){
			boolean move = true;
			for(int i = 0; i <= speed; i++){
				for(int j = 0; j < height; j++){
					if(coordinates.x + width + 1 <= navMesh.getWidth()){
						int rgb = navMesh.getRGB(coordinates.x + width, coordinates.y + j);
						
						if(rgb != Color.BLUE.getRGB()){
							//Not allowed to move
							return false;
						}					
					}else{
						return false;
					}
				}
				
				if(move){
					//Move
					getCoordinates().setCoordinates(coordinates.x + 1, coordinates.y);
				}				
			}
			return true;
		}
		
		/**Checks if this GameObject can teleport specified distance in specified direction. If moving to the destination is not allowed, it checks for valid locations between destination and departure point with an interval of 25 pixels. If it finds a valid place, the GameObject moves there. Returns true if it moved, otherwise false.
		 * @param navMesh NavMesh on which the blinking happens.
		 * @param movedLeft Decides the direction in which the blinking happens, false for right, true for left.
		 * @param distanceX The distance that has to be traveled.
		 * @return boolean moved
		 */
		public boolean blink(NavMesh navMesh, boolean movedLeft, int distanceX){
			int i = 0;
			int j = 0;
			boolean allowed = true;
			
			//Check if the place where the character is going is allowed
			while(allowed && i < getWidth()){
				while(allowed && j < getHeight()){
					try{
						if(navMesh.getRGB(coordinates.x + distanceX + i, coordinates.y + j)!= Color.BLUE.getRGB()){
							allowed = false;
						}						
					}catch(ArrayIndexOutOfBoundsException e){
						allowed = false;
					}
					j++;
				}
				i++;
			}
			
			
			if(allowed){
				getCoordinates().setCoordinates(coordinates.x + distanceX, coordinates.y);
				return true;
			}else{				
				/*
				 * First, we start looking for the first blue pixel (this is air). For every (dx, dy) we check the topleft corner of the character 
				 * for air. (This is character coordinate(0,0)). If this is air, we do the same check as above for the destination, but for the
				 * current coordinate. If this location is valid, we move the character here. If not, we move the character 1 closer to its 
				 * departure point.
				 * 
				 * So:
				 * 
				 */
				i = distanceX;
				
				while(!allowed && i != 0){
					try{
					 	if(navMesh.getRGB(coordinates.x + i, coordinates.y) == Color.BLUE.getRGB()){
					 		//Do full check for this location
					 		
					 		//We assume there are no red pixels in this location (innocent until proven guilty, eh), so boolean red = false. Then, when
					 		//we start checking the pixels, if we encounter a red one, the boolean red is set to true. This means that at the end of 
					 		//the check, if we end up with red == true, the location is invalid. If boolean red == false, however, the location has no 
					 		//red pixels and is therefore valid to move to.
					 		boolean red = false;
					 		int x = 0, y = 0;
					 		while(!red && x < width){
					 			y = 0;
								while(!red && y < height){
									//Check pixel
									try{
										if(navMesh.getRGB(coordinates.x + i + x, coordinates.y + y) == Color.RED.getRGB()){
											red = true;
										}									
									}catch(ArrayIndexOutOfBoundsException e){
										red = true;
									}
									y++;
								}
								x++;
							}
					 		if(!red){
					 			//No red pixels found in this location, so the character can move here
					 			getCoordinates().setCoordinates(coordinates.x + i, coordinates.y);
					 			return true;
					 		}
					   	}						
					}catch(ArrayIndexOutOfBoundsException e){
						
					}
				 	i = i < 0 ? i + 25 : i - 25;
				}
				//If you end up here, there are no valid places between int distance and the starting point. So, return false
				return false;
			}
		}
		
		
	//Combat methods		
		/**Sets the maximum health for this GameObject.
		 * @param health The maximum health.
		 */
		public void setMaxHealth(int health){
			this.health = health;
			currentHealth = health;
		}
		
		/**Returns the maximum health of this GameObject.
		 * @return int maxhealth
		 */
		public int getMaxHealth(){
			return health;
		}
		
		/**Returns the current health.
		 * @return int currentHealth
		 */
		public int getCurrentHealth(){
			return currentHealth;
		}
		
		/**Returns the healthbar image with the current health displayed.
		 * @return BufferedImage healthBar
		 */
		public BufferedImage getHealthBarImage(){
			Graphics2D g = healthBar.createGraphics();
			g.setColor(Color.RED);
			g.fillRect(0, 0, width, height);
			double widthHealth = (double)width * ((double)currentHealth / (double)health);
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, (int)widthHealth, height);
			return healthBar;
		}
		
		/**Decreases this GameObject's health with damage
		 * @param damage The damage that is dealed
		 */
		public void damage(int damage){
			currentHealth -= damage;
		}
}
