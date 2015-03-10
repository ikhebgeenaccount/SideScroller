package main.game.object.minion;

import main.Main;
import main.game.object.GameObject;
import main.game.object.champion.Champion;

public class Minion extends GameObject {
	
	private static final int WALK_RIGHT = 0, WALK_LEFT = 1, ATTACK = 2, DIE = 3, BE_DEAD = 4; 
	
	private int range;
	private int damage;
	private long attackLength; //In seconds
	private long attackStart;
	
	private boolean moveLeft;
	
	public Minion(){
		
	}
	
	public Minion(int speed, int range, long attackLength){
		setSpeed(speed);
		setRange(range);
		setAttackLength(attackLength);
	}
	
	public void setRange(int range){
		this.range = range;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
	
	public void setAttackLength(int attacklength){
		this.attackLength = attackLength;
		attackStart = 0;
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
					//Target is in range: attack
					if(System.currentTimeMillis() - attackStart >= attackLength){
						//Attack
						onScreen[i].damage(getDamage());
					}
					activity = true;
				}
			}
		}
		
		int[][] level = Main.getGamePanel().getCurrentLevel();
		int levelIDx = Main.getGamePanel().getLevelIDs()[0];
		int levelIDy = Main.getGamePanel().getLevelIDs()[1];
		
		//Calculate coordinates and sizes of Minion
		int matrixcoordx = main.gui.panel.GamePanel.roundDownToClosestMultipleOfFifty(getCoordinates().x)/50;
		int matrixcoordy = main.gui.panel.GamePanel.roundDownToClosestMultipleOfFifty(getCoordinates().y)/50;
		
		int matrixheight = (getSize()[1] + 49)/50;//Round up to multiple of fifty
		int matrixwidth = (getSize()[0] + 49)/50;
		
		if(!activity){
			//Check if next step can be made
			boolean nextstep = true;
			
			//Move accordingly
			if(nextstep){
				//Move
			}else{
				//Turn around
				moveLeft = !moveLeft;
			}
		}
		
		if(activity){
			setAnimationType(Minion.ATTACK);
		}else{
			if(moveLeft){
				setAnimationType(Minion.WALK_LEFT);
			}else{
				setAnimationType(Minion.WALK_RIGHT);
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
