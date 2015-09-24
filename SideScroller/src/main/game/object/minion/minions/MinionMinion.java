package main.game.object.minion.minions;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.game.object.minion.Minion;

public class MinionMinion extends Minion {
	
	public MinionMinion(){
		super(50, 50, 3, 5, 42, 800);
		
		ClassLoader cldr = this.getClass().getClassLoader();
		
		try {
			this.addAnimation(Minion.WALK_LEFT, ImageIO.read(cldr.getResourceAsStream("img/minion/minionminion/walkLeft.png")), 100, 50, 50);
			
			this.addAnimation(Minion.WALK_RIGHT, ImageIO.read(cldr.getResourceAsStream("img/minion/minionminion/walkRight.png")), 100, 50, 50);
			
			this.addAnimation(Minion.ATTACK_LEFT, ImageIO.read(cldr.getResourceAsStream("img/minion/minionminion/attackLeft.png")), 100, 50, 50);
			
			this.addAnimation(Minion.ATTACK_RIGHT, ImageIO.read(cldr.getResourceAsStream("img/minion/minionminion/attackRight.png")), 100, 50, 50);
			
			this.addAnimation(Minion.DIE, ImageIO.read(cldr.getResourceAsStream("img/minion/minionminion/die.png")), 100, 50, 50);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setAnimationType(Minion.WALK_LEFT);	
		 
	}
}
