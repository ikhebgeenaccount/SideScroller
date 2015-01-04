package main.game.object.minion;

import main.game.object.GameObject;

public class Minion extends GameObject {
	
	private static final int WALK_RIGHT = 0, WALK_LEFT = 1, ATTACK = 2, DIE = 3, BE_DEAD = 4; 
	
	private int range;
	
	public Minion(int speed, int range){
		setSpeed(speed);
		setRange(range);
	}
	
	public void setRange(int range){
		this.range = range;
	}
	
	public void move(){
		
	}

}
