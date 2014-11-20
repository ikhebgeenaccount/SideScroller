package main.game.object;

import java.awt.Image;
import java.awt.image.BufferedImage;

import main.game.animation.Animation;
import main.game.coordinate.Coordinate;

public class GameObject {
	
	private Animation[] animations;
	private Animation currentAnimation;
	private int currentAnimationType;
	private Coordinate coordinates;
	
	public GameObject(){
		coordinates = new Coordinate();
		
		animations = new Animation[10];
		currentAnimation = new Animation();
		currentAnimationType = -1;
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
	
	//Add an animation
	public void addAnimation(int type, BufferedImage animationSprite, int sceneLength){
		animations[type] = new Animation();
		int scenes = animationSprite.getWidth() / 50;
		for(int i = 0; i < scenes; i++){
			animations[type].addScene(animationSprite.getSubimage(50 * i, 0, 50, 100), sceneLength);
		}
	}
	
	//Check if the next scene should start playing
	public void checkNextScene(){
		currentAnimation.nextScene();
	}
	
	//Return the image of the current scene
	public Image getCurrentAnimationImage() {
		return currentAnimation.getCurrentSceneImage();
	}
	
	//Return the current coordinates for this GameObject
	public Coordinate getCoordinates(){
		return coordinates;
	}
	
	//Set the coordinates for this GameObject
	public void setCoordinations(int x, int y){
		coordinates.x = x;
		coordinates.y = y;
	}
}
