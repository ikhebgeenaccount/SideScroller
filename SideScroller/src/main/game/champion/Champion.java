package main.game.champion;

import java.awt.Image;
import java.awt.image.BufferedImage;

import main.game.animation.Animation;

public class Champion {
	
	public static final int IDLE = 0, WALK_LEFT = 1, WALK_RIGHT = 2, JUMP = 3, FALL = 4, CAST_Q = 5, CAST_W = 6, CAST_E = 7, CAST_R = 8;
	private Animation[] animations;
	private Animation currentAnimation;
		
	public Champion(){
		
	}

	public void setAnimationType(int type) {
		//We only have to change the animationtype when it is now running another type, otherwise the animation will continuously
		//reset itself
		if(currentAnimation == animations[type]){
			
		}else{
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
	/*
	public void addAnimation(int type, BufferedImage animationSprite){
		animations[type] = new Animation();
		int scenes = animationSprite.getWidth() / 50;
		for(int i = 0; i < scenes; i++){
			animations[type].addScene(animationSprite.getSubimage())
		}
	}*/

}
