package main.game.object.minion;

import main.game.object.GameObject;

public class Minion extends GameObject {
	
	private static final int WALK_RIGHT = 0, WALK_LEFT = 1, ATTACK = 2, DIE = 3, BE_DEAD = 4; 
	
	private int range;
	
	private boolean moveLeft;
	
	public Minion(int speed, int range){
		setSpeed(speed);
		setRange(range);
	}
	
	public void setRange(int range){
		this.range = range;
	}
	
	public void move(){
		/* For move():
		 * 	1. Check if this should attack
		 * 	2. If yes, attack
		 * 	3. If not, check if this should proceed in current direction, thus if it is valid to move to the next position 
		 * 	4. If valid, move to next position
		 * 	5. If not valid, turn around and change moveLeft
		 */
	}

}
