package main.game.object;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import main.game.animation.Animation;
import main.game.coordinate.Coordinate;
import main.game.navmesh.NavMesh;

public class GameObject {
	
	//Animations
	private Animation[] animations;
	private Animation currentAnimation;
	private int currentAnimationType;
	private int[] animationsLength;
	
	//Coordinates
	private Coordinate coordinates;
	
	private int width, height;
	
	//Speed in px
	private int startSpeed;
	private int speed, yspeed, defaultSpeed;
	
	private int health;
	private int currentHealth;
	
	public GameObject(){
		coordinates = new Coordinate();
		
		animations = new Animation[10];
		currentAnimation = new Animation();
		currentAnimationType = -1;
		
		animationsLength = new int[30];
	}
	
	//Animation methods
		//Set animation type
		public void setAnimationType(int type) {
			//We only have to change the animationtype when it is now running another type, otherwise the animation will continuously
			//reset itself
			if(currentAnimationType == type){
					
			}else{
			  currentAnimationType = type;
			  currentAnimation = animations[type];
			}
		}
		
		//Add an animation with default frame size: 50 x 100 (for characters)
		@Deprecated
		public void addAnimation(int type, BufferedImage animationSprite, int sceneLength){
			animations[type] = new Animation();
			int scenes = animationSprite.getWidth() / 50;
			for(int i = 0; i < scenes; i++){
				animations[type].addScene(animationSprite.getSubimage(50 * i, 0, 50, 100), sceneLength);
			}
			animationsLength[type] = scenes * sceneLength;
		}
		
		//Add an animation with different frame size: frameWidth x frameHeight
		public void addAnimation(int type, BufferedImage animationSprite, int sceneLength, int frameWidth, int frameHeight){
			animations[type] = new Animation(frameWidth, frameHeight);
			width = frameWidth;
			height = frameHeight;
			int scenes = animationSprite.getWidth() / frameWidth;
			for(int i = 0; i < scenes; i++){
				animations[type].addScene(animationSprite.getSubimage(frameWidth * i, 0, frameWidth, frameHeight), sceneLength);
			}
			animationsLength[type] = scenes * sceneLength;
		}
		
		//Check if the next scene should start playing
		public void checkNextScene(){
			currentAnimation.nextScene();
		}
		
		//Return the image of the current scene
		public Image getCurrentAnimationImage(){
			return currentAnimation.getCurrentSceneImage();
		}
		
		public int getCurrentAnimationLength(){
			return animationsLength[currentAnimationType];
		}
		
		public int[] getSize(){
			return new int[]{width, height};
		}
		
		public int getWidth(){
			return width;
		}
		
		public int getHeight(){
			return height;
		}
	
		
		
	//Coordinate methods
		//Return the current coordinates for this GameObject
		public Coordinate getCoordinates(){
			return coordinates;
		}
		
		//Set the coordinates for this GameObject
		public void setCoordinates(int x, int y){
			this.coordinates.x = x;
			this.coordinates.y = y;
		}
		
		public void setCoordinates(Coordinate coordinates){
			this.coordinates.x = coordinates.x;
			this.coordinates.y = coordinates.y;
		}
		
		
	
	//Movement methods
	
		public int getSpeed(){
			return speed;
		}
		
		public int getDefaultSpeed(){
			return defaultSpeed;
		}
		
		public void setDefaultSpeed(int defaultSpeed){
			this.defaultSpeed = defaultSpeed;
		}
		
		public int getYSpeed(){
			return yspeed;
		}
		
		public void setSpeed(int speed){
			this.startSpeed = speed;
			this.speed = speed;
		}
		
		public void accelerate(int acceleration){
			if(yspeed + acceleration > 0){
				yspeed += acceleration;
			}
		}
		
		public void resetSpeed(){
			this.yspeed = 0;
		}
		
		public boolean moveUp(NavMesh navMesh){
			boolean move = true;
			for(int i = 0; i <= yspeed; i++){
				for(int j = 0; j < width; j++){
					if(getCoordinates().y - 1 >= 0){
						int rgb = navMesh.getRGB(getCoordinates().x + j, getCoordinates().y - 1);
						
						if(rgb != Color.BLUE.getRGB()){
							//Not allowed to move
							move = false;
							break;
						}					
					}else{
						move = false;
					}
				}
				if(move){
					//Move
					setCoordinates(getCoordinates().x, getCoordinates().y - 1);
				}
			}
			return move;		
		}
		
		public boolean moveDown(NavMesh navMesh){
			boolean move = true;
			for(int i = 0; i <= yspeed; i++){
				for(int j = 0; j < width; j++){
					if(getCoordinates().y + height + 1 <= navMesh.getHeight()){
						int rgb = navMesh.getRGB(getCoordinates().x + j, getCoordinates().y + height);
						
						if(rgb != Color.BLUE.getRGB()){
							//Not allowed to move
							move = false;
							break;
						}
					}else{
						move = false;
					}
				}
				if(move){
					//Move
					setCoordinates(getCoordinates().x, getCoordinates().y + 1);
				}
			}
			return move;
		}
		
		public boolean moveLeft(NavMesh navMesh){
			boolean move = true;
			for(int i = 0; i <= speed; i++){
				for(int j = 0; j < height; j++){
					if(getCoordinates().x - 1 >= 0){
						int rgb = navMesh.getRGB(getCoordinates().x - 1, getCoordinates().y + j);
						
						if(rgb != Color.BLUE.getRGB()){
							//Not allowed to move
							move = false;
							break;
						}					
					}else{
						move = false;
					}
				}
				
				if(move){
					//Move
					setCoordinates(getCoordinates().x - 1, getCoordinates().y);
				}
			}
			return move;
		}
		
		public boolean moveRight(NavMesh navMesh){
			boolean move = true;
			for(int i = 0; i <= speed; i++){
				for(int j = 0; j < height; j++){
					if(getCoordinates().x + width + 1 <= navMesh.getWidth()){
						int rgb = navMesh.getRGB(getCoordinates().x + width, getCoordinates().y + j);
						
						if(rgb != Color.BLUE.getRGB()){
							//Not allowed to move
							move = false;
							break;
						}					
					}else{
						move = false;
					}
				}
				
				if(move){
					//Move
					setCoordinates(getCoordinates().x + 1, getCoordinates().y);
				}				
			}
			return move;
		}		
		
		
		
	//Combat methods		
		public void setHealth(int health){
			this.health = health;
			currentHealth = health;
		}
		
		public int getHealth(){
			return health;
		}
		
		public int getCurrentHealth(){
			return currentHealth;
		}
		
		public void damage(int damage){
			currentHealth -= damage;
		}
}
