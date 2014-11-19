package main.game.champion;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import main.game.animation.Animation;

public class Champion {
	
	public static final int IDLE = 0, WALK_LEFT = 1, WALK_RIGHT = 2, JUMP = 3, FALL = 4, CAST_Q = 5, CAST_W = 6, CAST_E = 7, CAST_R = 8;
	public Animation[] animations;
	public Animation currentAnimation;
	private int currentAnimationType;
	private Point coordinations;
		
	public Champion(){
		currentAnimationType = 0;
		
		coordinations = new Point();
	}

	public void setAnimationType(int type) {
		//We only have to change the animationtype when it is now running another type, otherwise the animation will continuously
		//reset itself
		if(currentAnimationType == type){
			
		}else{
			currentAnimationType = type;
			currentAnimation = animations[type];
			currentAnimation.start();			
		}
	}

	public Image getCurrentAnimationImage() {
		return currentAnimation.getCurrentSceneImage();
	}
	
	public void checkNextScene(){
		currentAnimation.nextScene();
	}
	
	public void addAnimation(int type, BufferedImage animationSprite, int sceneLength){
		animations[type] = new Animation();
		int scenes = animationSprite.getWidth() / 50;
		for(int i = 0; i < scenes; i++){
			animations[type].addScene(animationSprite.getSubimage(50 * i, 0, 50, 100), sceneLength);
		}
	}
	
	public Point getCoordinations(){
		return coordinations;
	}
	
	public void setCoordinations(int x, int y){
		coordinations.x = x;
		coordinations.y = y;
	}

}
