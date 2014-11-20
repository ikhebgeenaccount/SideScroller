package main.game.object.champion.champions;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.Main;
import main.game.animation.Animation;
import main.game.object.champion.Champion;

public class AlphaGuy extends Champion{
	
	public AlphaGuy(){
		String load = "Loading animations";
		Main.getLoadPanel().setNextLoadPart(load);
		ClassLoader cldr = this.getClass().getClassLoader();	
		try {
			/* All the animations are in one big image, that is why we use the getSubimage() function to get al the
			 * picture for the animations.
			 * I have decided to put the animations in one image because the resource folder will be less messy this way. 
			 */
			
			//Add the idle animation to animations[]
			BufferedImage idleSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/idle.png"));
			addAnimation(Champion.IDLE, idleSprite, 100);
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the walk left animation to animations[]		
			BufferedImage walkLeftSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/walkLeft.png"));
			addAnimation(Champion.WALK_LEFT, walkLeftSprite, 250);
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the walk right animation to animations[]
			BufferedImage walkRightSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/walkRight.png"));
			addAnimation(Champion.WALK_RIGHT, walkRightSprite, 250);
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the jump animation to animations[]
			BufferedImage jumpSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/jump.png"));
			addAnimation(Champion.JUMP, jumpSprite, 250);
			Main.getLoadPanel().setNextLoadPart(load);
			
			//Add the fall animation to animations[]
			BufferedImage fallSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/fall.png"));
			addAnimation(Champion.FALL, fallSprite, 250);
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
			setAnimationType(Champion.IDLE);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			Main.quitGame();
		}
	}
}
