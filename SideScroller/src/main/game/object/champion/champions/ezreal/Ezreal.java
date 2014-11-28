package main.game.object.champion.champions.ezreal;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Main;
import main.game.object.champion.Champion;
import main.game.object.spell.Spell;

public class Ezreal extends Champion{
	
	private Spell q, w, e, r;
	
	public Ezreal(){
		//Create spells
		q = new Spell(600, 200, 10, 5);
		w = new Spell(500, 100, 10, 5);
		
		r = new Spell(2000, 500, 10, 10);
		
		ClassLoader cldr = this.getClass().getClassLoader();	
		try {
			/* All the animations are in one big image, that is why we use the getSubimage() function to get al the
			 * picture for the animations.
			 * I have decided to put the animations in one image because the resource folder will be less messy this way. 
			 */
			
			//Add the idle animation to animations[]
			BufferedImage idleSprite = ImageIO.read(cldr.getResourceAsStream("img/char/ezreal/idle.png"));
			addAnimation(Champion.IDLE, idleSprite, 100);
			
			//Add the walk left animation to animations[]		
			BufferedImage walkLeftSprite = ImageIO.read(cldr.getResourceAsStream("img/char/ezreal/walkLeft.png"));
			addAnimation(Champion.WALK_LEFT, walkLeftSprite, 250);
			
			//Add the walk right animation to animations[]
			BufferedImage walkRightSprite = ImageIO.read(cldr.getResourceAsStream("img/char/ezreal/walkRight.png"));
			addAnimation(Champion.WALK_RIGHT, walkRightSprite, 250);
			
			//Add the jump animation to animations[]
			BufferedImage jumpSprite = ImageIO.read(cldr.getResourceAsStream("img/char/ezreal/jump.png"));
			addAnimation(Champion.JUMP, jumpSprite, 250);
			
			//Add the fall animation to animations[]
			BufferedImage fallSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/fall.png"));
			addAnimation(Champion.FALL, fallSprite, 250);
			
			//Add the cast q animation to animations[]
			
			
			//Add the cast w animation to animations[]
			
			
			//Add the cast e animation to animations[]
			
			
			//Add the cast r animation to animations[]
			
			
			//Start idle animation
			setAnimationType(Champion.IDLE);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			Main.quitGame();
		}
	}
}
