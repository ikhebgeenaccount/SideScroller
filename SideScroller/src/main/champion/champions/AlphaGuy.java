package main.champion.champions;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.Main;
import main.animation.Animation;
import main.champion.Champion;

public class AlphaGuy extends Champion{
	
	private Animation[] animations;
	private Animation currentAnimation;
	
	public AlphaGuy(){
		System.out.println("Loading animations...");
		ClassLoader cldr = this.getClass().getClassLoader();
		animations = new Animation[8];		
		try {
			//Hashcode changes after adding scene. Hashcode in this class is different than the hashcodes in Animation.
			
			//Add the idle animation to animations[]
			animations[Champion.IDLE]= new Animation(); 
			BufferedImage idleSprite = ImageIO.read(cldr.getResourceAsStream("char/img/alphaguy/idle.png"));
			animations[Champion.IDLE].addScene(idleSprite.getSubimage(0, 0, 50, 100), 1);
			
			//Add the walking animation to animations[]
			animations[Champion.WALK_RIGHT]= new Animation();
			BufferedImage walkSprite = ImageIO.read(cldr.getResourceAsStream("char/img/alphaguy/walk.png"));
			animations[Champion.WALK_RIGHT].addScene(walkSprite.getSubimage(0, 0, 50, 100), 10);
			animations[Champion.WALK_RIGHT].addScene(walkSprite.getSubimage(50, 0, 50, 100), 10);
			
			//Start idle animation
			currentAnimation = animations[Champion.IDLE];
			currentAnimation.start(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			Main.quitGame();
		}
	}
	
	@Override
	public void setAnimationType(int type, int frame){
		//We only have to change the animationtype when it is now running another type, otherwise the animation will continuously
		//reset itself
		if(currentAnimation == animations[type]){
			
		}else{
			currentAnimation = animations[type];
			currentAnimation.start(frame);			
		}
	}
	
	@Override
	public Image getCurrentAnimationImage(int frame){
		return currentAnimation.getCurrentSceneImage();
	}
	
	@Override
	public void checkNextScene(int frame){
		currentAnimation.nextScene(frame);
	}

}
