package main.game.object;

import java.awt.Image;
import java.awt.image.BufferedImage;

import main.game.animation.Animation;
import main.game.coordinate.Coordinate;

public class GameObject {
	
	//Animations
	private Animation[] animations;
	private Animation currentAnimation;
	private int currentAnimationType;
	private int[] animationsLength;
	
	//Coordinates
	private Coordinate coordinates;
	
	//Speed in px
	private int speed;
	
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
	
	//Add an animation with default frame size: 50 x 100
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
		animations[type] = new Animation(frameWidth);
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
	
	//Return the current coordinates for this GameObject
	public Coordinate getCoordinates(){
		return coordinates;
	}
	
	//Set the coordinates for this GameObject
	public void setCoordinates(int x, int y){
		coordinates.x = x;
		coordinates.y = y;
	}
	
	public void setCoordinates(Coordinate coordinates){
		this.coordinates = coordinates;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public int getCurrentAnimationLength(){
		return animationsLength[currentAnimationType];
	}
}
