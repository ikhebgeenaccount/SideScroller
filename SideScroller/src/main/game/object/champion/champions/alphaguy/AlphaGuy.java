package main.game.object.champion.champions.alphaguy;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Main;
import main.game.object.champion.Champion;
import main.game.object.spell.Spell;

public class AlphaGuy extends Champion {
	
	public AlphaGuy(){
		ClassLoader cldr = this.getClass().getClassLoader();
		
		
		try {
			/* All the animations are in one big image, that is why we use the getSubimage() function to get all the
			 * pictures for the animations.
			 * I have decided to put the animations in one image because the resource folder will be less messy this way. 
			 */			

			//Create spells
			q = new Spell(600, 200, 10, 5000);
			q.addAnimation(Spell.APPEAR, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/qAppear.png")), 250, 70, 15);
			q.addAnimation(Spell.TRAVEL, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/qAppear.png")), 250, 70, 15);
			q.addAnimation(Spell.DISAPPEAR, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/qAppear.png")), 250, 70, 15);
			q.addAnimation(Spell.HIT, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/qAppear.png")), 250, 70, 15);
			
			w = new Spell(500, 100, 10, 5000);
			w.addAnimation(Spell.APPEAR, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/wAppear.png")), 250, 50, 30);
			w.addAnimation(Spell.TRAVEL, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/wAppear.png")), 250, 50, 30);
			w.addAnimation(Spell.DISAPPEAR, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/wAppear.png")), 250, 50, 30);
			w.addAnimation(Spell.HIT, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/wAppear.png")), 250, 50, 30);
			
			r = new Spell(2000, 500, 10, 10000);
			r.addAnimation(Spell.APPEAR, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/rAppear.png")), 250, 100, 80);
			r.addAnimation(Spell.TRAVEL, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/rAppear.png")), 250, 100, 80);
			r.addAnimation(Spell.DISAPPEAR, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/rAppear.png")), 250, 100, 80);
			r.addAnimation(Spell.HIT, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/rAppear.png")), 250, 100, 80);
			
			//Add the idle animation to animations[]
			BufferedImage idleSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/idle.png"));
			addAnimation(Champion.IDLE, idleSprite, 100);
			
			//Add the walk left animation to animations[]		
			BufferedImage walkLeftSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/walkLeft.png"));
			addAnimation(Champion.WALK_LEFT, walkLeftSprite, 250);
			
			//Add the walk right animation to animations[]
			BufferedImage walkRightSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/walkRight.png"));
			addAnimation(Champion.WALK_RIGHT, walkRightSprite, 250);
			
			//Add the jump animation to animations[]
			BufferedImage jumpSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/jump.png"));
			addAnimation(Champion.JUMP, jumpSprite, 250);
			
			//Add the fall animation to animations[]
			BufferedImage fallSprite = ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/fall.png"));
			addAnimation(Champion.FALL, fallSprite, 250);
			
			//Add the cast q animation to animations[]
			addAnimation(Champion.CAST_Q, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/castQ.png")), 250);
			
			//Add the cast w animation to animations[]
			addAnimation(Champion.CAST_W, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/castW.png")), 250);
			
			//Add the cast e animation to animations[]
			addAnimation(Champion.CAST_E, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/castE.png")), 250);
			
			//Add the cast r animation to animations[]
			addAnimation(Champion.CAST_R, ImageIO.read(cldr.getResourceAsStream("img/char/alphaguy/castR.png")), 250);
			
			//Start idle animation
			setAnimationType(Champion.IDLE);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			Main.quitGame();
		}
	}

}