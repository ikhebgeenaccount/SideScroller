package main.game.champion.champions;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.Main;
import main.game.animation.Animation;
import main.game.champion.Champion;

public class AlphaGuy extends Champion{
	
	public AlphaGuy(){
		String load = "Loading animations";
		Main.getLoadPanel().setNextLoadPart(load);
		ClassLoader cldr = this.getClass().getClassLoader();
		animations = new Animation[8];		
		try {
			/* All the animations are in one big image, that is why we use the getSubimage() function to get al the
			 * picture for the animations.
			 * I have decided to put the animations in one image because the resource folder will be less messy this way. 
			 */
			
			//Add the idle animation to animations[]
			animations[Champion.IDLE]= new Animation(); 
			BufferedImage idleSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/idle.png"));
			int width = idleSprite.getWidth();
			int parts = width/50;
			for(int i =  0; i < parts; i++){
				animations[Champion.IDLE].addScene(idleSprite.getSubimage(50 * i, 0, 50, 100), 1);
			}
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the walk left animation to animations[]
			animations[Champion.WALK_LEFT]= new Animation();			
			BufferedImage walkLeftSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/walkLeft.png"));
			width = walkLeftSprite.getWidth();
			parts = width/50;
			for(int i = 0 ;i < parts; i++){
				animations[Champion.WALK_LEFT].addScene(walkLeftSprite.getSubimage(50 * i, 0, 50, 100), 250);
			}
			for(int i = 0; i < 1000000000; i++){
				
			}
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the walk right animation to animations[]
			animations[Champion.WALK_RIGHT]= new Animation();
			BufferedImage walkRightSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/walkRight.png"));
			width = walkRightSprite.getWidth();
			parts = width/50;
			for(int i = 0; i < parts; i++){
				animations[Champion.WALK_RIGHT].addScene(walkRightSprite.getSubimage(50 * i, 0, 50, 100), 250);
			}
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the jump animation to animations[]
			
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the fall animation to animations[]
			
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the cast q animation to animations[]
			
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the cast w animation to animations[]
			
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the cast e animation to animations[]
			
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the cast r animation to animations[]
			
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Start idle animation
			currentAnimation = animations[Champion.IDLE];
			currentAnimation.start();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			Main.quitGame();
		}
	}
}
