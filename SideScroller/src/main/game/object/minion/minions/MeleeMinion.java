package main.game.object.minion.minions;

import javafx.scene.image.Image;
import main.game.object.minion.Minion;

public class MeleeMinion extends Minion {
	
	public MeleeMinion(){
		super(50, 50, 3, 5, 42, 800);
		
		ClassLoader cldr = this.getClass().getClassLoader();
	
		this.addAnimation(Minion.WALK_LEFT, new Image(cldr.getResourceAsStream("img/minion/meleeminion/walkLeft.png")), 100, 50, 50);
		
		this.addAnimation(Minion.WALK_RIGHT, new Image(cldr.getResourceAsStream("img/minion/meleeminion/walkRight.png")), 100, 50, 50);
		
		this.addAnimation(Minion.ATTACK_LEFT, new Image(cldr.getResourceAsStream("img/minion/meleeminion/attackLeft.png")), 100, 50, 50);
		
		this.addAnimation(Minion.ATTACK_RIGHT, new Image(cldr.getResourceAsStream("img/minion/meleeminion/attackRight.png")), 100, 50, 50);
		
		this.addAnimation(Minion.DIE, new Image(cldr.getResourceAsStream("img/minion/meleeminion/die.png")), 100, 50, 50);
	
		this.setAnimationType(Minion.WALK_LEFT);	
		
	}

}
