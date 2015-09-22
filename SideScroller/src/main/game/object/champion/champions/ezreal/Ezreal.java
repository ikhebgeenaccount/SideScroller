package main.game.object.champion.champions.ezreal;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Main;
import main.game.object.champion.Champion;
import main.game.object.spell.Spell;

/**This class contains all information about Ezreal.
 * @author ikhebgeenaccount
 * @version 17 sep. 2015
 */
public class Ezreal extends Champion{
	
	/**Creates Ezreal with animations and spells.
	 * 
	 */
	public Ezreal(){
		//Create spells
		q = new Spell(600, 200, 10, 5000);
		w = new Spell(500, 100, 10, 5000);
		e = new EzrealE();
		r = new Spell(2000, 500, 10, 10000);
		
		ClassLoader cldr = this.getClass().getClassLoader();	
		try {
			/* All the animations are in one big image, that is why we use the getSubimage() function to get al the
			 * picture for the animations.
			 * I have decided to put the animations in one image because the resource folder will be less messy this way. 
			 */
			 
			//Create spells
			q = new Spell(600, 200, 10, 5);
			int width = 70;
			int height = 15;
			q.addAnimation(Spell.APPEAR, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/qAppear.png")), 250, width, height);
			q.addAnimation(Spell.TRAVEL_LEFT, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/qTravel.png")), 250, width, height);
			q.addAnimation(Spell.TRAVEL_RIGHT, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/qTravel.png")), 250, width, height);
			q.addAnimation(Spell.DISAPPEAR, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/qDisappear.png")), 250, width, height);
			q.addAnimation(Spell.HIT, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/qHit.png")), 250, width, height);
			
			w = new Spell(500, 100, 10, 5);
			width = 50;
			height = 30;
			w.addAnimation(Spell.APPEAR, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/wAppear.png")), 250, width, height);
			w.addAnimation(Spell.TRAVEL_LEFT, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/wTravel.png")), 250, width, height);
			w.addAnimation(Spell.TRAVEL_RIGHT, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/wTravel.png")), 250, width, height);
			w.addAnimation(Spell.DISAPPEAR, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/wDisappear.png")), 250, width, height);
			w.addAnimation(Spell.HIT, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/wHit.png")), 250, width, height);
			
			e = new EzrealE();
			
			r = new Spell(2000, 500, 10, 10);
			width = 100;
			height = 80;
			r.addAnimation(Spell.APPEAR, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/rAppear.png")), 250, width, height);
			r.addAnimation(Spell.TRAVEL_LEFT, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/rTravel.png")), 250, width, height);
			r.addAnimation(Spell.TRAVEL_RIGHT, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/rTravel.png")), 250, width, height);
			r.addAnimation(Spell.DISAPPEAR, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/rDisappear.png")), 250, width, height);
			r.addAnimation(Spell.HIT, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/rHit.png")), 250, width, height);
			
			//Add the idle animation to animations[]
			addAnimation(Champion.IDLE, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/idle.png")), 100, 100, 50);
			
			//Add the walk left animation to animations[]
			addAnimation(Champion.WALK_LEFT, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/walkLeft.png")), 250, 100, 50);
			
			//Add the walk right animation to animations[]
			addAnimation(Champion.WALK_RIGHT, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/walkRight.png")), 250, 100, 50);
			
			//Add the jump animation to animations[]
			addAnimation(Champion.JUMP, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/jump.png")), 250, 100, 50);
			
			//Add the fall animation to animations[]
			addAnimation(Champion.FALL, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/fall.png")), 250, 100, 50);
			
			//Add the cast q animation to animations[]
			addAnimation(Champion.CAST_Q, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/castQ.png")), 250, 100, 50);
			
			//Add the cast w animation to animations[]
			addAnimation(Champion.CAST_W, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/castW.png")), 250, 100, 50);
			
			//Add the cast e animation to animations[]
			addAnimation(Champion.CAST_E, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/castE.png")), 250, 100, 50);
			
			//Add the cast r animation to animations[]
			addAnimation(Champion.CAST_R, ImageIO.read(cldr.getResourceAsStream("img/champs/ezreal/castR.png")), 250, 100, 50);
			
			//Start idle animation
			setAnimationType(Champion.IDLE);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			Main.quitGame();
		}
	}
}
