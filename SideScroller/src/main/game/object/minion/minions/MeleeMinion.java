package main.game.object.minion.minions;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.game.object.minion.Minion;

public class MeleeMinion extends Minion {
	
	public MeleeMinion(){
		super(50, 50, 3, 5, 100);
		
		try {
			this.addAnimation(Minion.WALK_LEFT, ImageIO.read(new File("resources/img/minion/meleeminion/walkLeft.png")), 100, 50, 50);
			
			this.addAnimation(Minion.WALK_RIGHT, ImageIO.read(new File("resources/img/minion/meleeminion/walkRight.png")), 100, 50, 50);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setAnimationType(Minion.WALK_LEFT);	
		
	}

}
