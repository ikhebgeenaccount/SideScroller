package main.game.object.minion;

import main.Main;
import main.game.object.GameObject;
import main.game.object.champion.Champion;

public class Minion extends GameObject {
	
	private static final int WALK_RIGHT = 0, WALK_LEFT = 1, ATTACK = 2, DIE = 3, BE_DEAD = 4; 
	
	private int range;
	private int damage;
	
	private boolean moveLeft;
	
	public Minion(){
		
	}
	
	public Minion(int speed, int range){
		setSpeed(speed);
		setRange(range);
	}
	
	public void setRange(int range){
		this.range = range;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
	
	public void move(){
		boolean activity = false;
		/* For move():
		 * 	1. Check if this should attack
		 * 	2. If yes, attack
		 * 	3. If not, check if this should proceed in current direction, thus if it is valid to move to the next position 
		 * 	4. If valid, move to next position
		 * 	5. If not valid, turn around and change moveLeft
		 */
		
		GameObject[] onScreen = Main.getGamePanel().getOnScreenObjects();
		int coordx = getCoordinates().x;
		int coordy = getCoordinates().y;
		 
		 //Check if this should attack 
		for(int i = 0; i < onScreen.length; i++){
			//We should only consider attacking if this GameObject is a Champion
			if(onScreen[i] instanceof Champion){

				int width = onScreen[i].getSize()[0];
				int height = onScreen[i].getSize()[1];
				
				int targetcoordx = onScreen[i].getCoordinates().x;
				int targetcoordy = onScreen[i].getCoordinates().y;
				
				if(coordy >= targetcoordy + height && coordy + getSize()[1] <= targetcoordy && coordx >= targetcoordx + width && coordx + getSize()[1] <= targetcoordx){
					//Target is in range.
					setAnimationType(Minion.ATTACK);
					onScreen[i].damage(damage);
					activity = true;
				}
			}
		}
		
		
	}
	
	public int getDamage(){
		return damage;
	}
	
	public int getRange(){
		return range;
	}

}
