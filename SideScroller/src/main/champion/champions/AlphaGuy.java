package main.champion.champions;

import javax.swing.ImageIcon;

import main.Main;
import main.animation.Animation;
import main.champion.Champion;

public class AlphaGuy extends Champion{
	
	private Animation[] animations;
	
	public AlphaGuy(){
		System.out.println("Loading animations...");
		ClassLoader cldr = Main.class.getClassLoader();
		animations = new Animation[8];
		animations[Champion.WALK]= new Animation(); 
		animations[Champion.WALK].addScene(new ImageIcon(cldr.getResource("char/img/alphaman/idle.png")).getImage(), 5);
	}

}
