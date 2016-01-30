package main.game.object.minion.minions;

import javafx.scene.image.Image;
import main.game.object.minion.Minion;

public class MinionMinion extends Minion {
	
	public MinionMinion(){
		super(50, 50, 3, 5, 21, 400);
		
		ClassLoader cldr = this.getClass().getClassLoader();
		
		this.addAnimation(Minion.WALK_LEFT, new Image(cldr.getResourceAsStream("img/minion/minionminion/walkLeft.png")), 100, 50, 50);
		
		this.addAnimation(Minion.WALK_RIGHT, new Image(cldr.getResourceAsStream("img/minion/minionminion/walkRight.png")), 100, 50, 50);
		
		this.addAnimation(Minion.ATTACK_LEFT, new Image(cldr.getResourceAsStream("img/minion/minionminion/attackLeft.png")), 100, 50, 50);
		
		this.addAnimation(Minion.ATTACK_RIGHT, new Image(cldr.getResourceAsStream("img/minion/minionminion/attackRight.png")), 100, 50, 50);
		
		this.addAnimation(Minion.DIE, new Image(cldr.getResourceAsStream("img/minion/minionminion/die.png")), 100, 50, 50);
		
		this.setAnimationType(Minion.WALK_LEFT);	
		 
	}
}
