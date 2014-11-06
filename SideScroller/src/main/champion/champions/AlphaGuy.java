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
			/* All the animations are in one big image, that is why we use the getSubimage() function to get al the
			 * picture for the animations.
			 * I have decided to put the animations in one image because the resource folder will be less messy this way. 
			 */
			
			//Add the idle animation to animations[]
			animations[Champion.IDLE]= new Animation(); 
			BufferedImage idleSprite = ImageIO.read(cldr.getResourceAsStream("char/img/alphaguy/idle.png"));
			animations[Champion.IDLE].addScene(idleSprite.getSubimage(0, 0, 50, 100), 1);
			
			//Add the walk right animation to animations[]
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
	public void setAnimationType(int type, long currentTime){
		//We only have to change the animationtype when it is now running another type, otherwise the animation will continuously
		//reset itself
		if(currentAnimation == animations[type]){
			
		}else{
			currentAnimation = animations[type];
			currentAnimation.start(currentTime);			
		}
	}
	
	@Override
	public Image getCurrentAnimationImage(){
		return currentAnimation.getCurrentSceneImage();
	}
	
	@Override
	public void checkNextScene(long currentTime){
		currentAnimation.nextScene(currentTime);
	}

}
