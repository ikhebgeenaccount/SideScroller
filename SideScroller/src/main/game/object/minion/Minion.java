package main.game.object.minion;

import main.GUIEngine;
import main.Main;
import main.game.coordinate.Coordinate;
import main.game.navmesh.NavMesh;
import main.game.object.GameObject;

/**This class contains all methods regarding Minions.
 * @author ikhebgeenaccount
 * @version 17 sep. 2015
 */
public class Minion extends GameObject {
	
	/**The public constants for animations types.
	 * 
	 */
	public static final int WALK_RIGHT = 0, WALK_LEFT = 1, ATTACK_RIGHT = 2, ATTACK_LEFT = 3, DIE = 4, BE_DEAD = 5; 
	
	private int range;
	private int damage;
	private long attackLength;
	private long attackStart;
	private boolean attackLeft;
	
	private boolean moveLeft;
	
	/**Default constructor. Not recommended to use.
	 * 
	 */
	public Minion(){
		
	}
	
	/**Create Minion with specified properties.
	 * @param width The width of the Minion in pixels.
	 * @param height The height of the Minion in pixels.
	 * @param speed The speed of the Minion in pixels.
	 * @param range The range of the Minion in pixels. Range is calculated from the sides of the minion.
	 * @param attackLength The length of an attack in milliseconds.
	 */
	public Minion(int width, int height, int speed, int range, int damage, long attackLength){
		super(width, height);
		setSpeed(speed);
		setRange(range);
		setAttackLength(attackLength);
		setMaxHealth(200);
		setDamage(damage);
	}
	
	/**Sets the range.
	 * @param range The new range in pixels.
	 */
	public void setRange(int range){
		this.range = range;
	}
	
	/**Sets the damage.
	 * @param damage The new damage per attack.
	 */
	public void setDamage(int damage){
		this.damage = damage;
	}
	
	/**Sets the attack length.
	 * @param attackLength The length of one attack in milliseconds.
	 */
	public void setAttackLength(long attackLength){
		this.attackLength = attackLength;
		attackStart = 0;
	}
	
	/**Decides what this Minion should do. It first searches for targets within it's range. If those are not found, it moves along the same way as it was moving, when it has to move on air, it turns around.
	 * 
	 */
	public void move(){
		boolean activity = false;
		/* For move():
		 * 	1. Check if this should attack
		 * 	2. If yes, attack
		 * 	3. If not, check if this should proceed in current direction, thus if it is valid to move to the next position 
		 * 	4. If valid, move to next position
		 * 	5. If not valid, turn around and change moveLeft
		 */
		
		GameObject[] onScreen = GUIEngine.getGameEngine().getOnScreenObjects();
		int coordy = getCoordinates().y;
		
		Coordinate champCoordinate = onScreen[0].getCoordinates();
		
		boolean sameHeight = false;
		
		//Check if the minion and the champion are on the same height 
		for(int i = coordy; i <= coordy + this.getHeight(); i++){
			if(i >= champCoordinate.y && i <= champCoordinate.y + onScreen[0].getHeight()){
				//When they overlap in height, they are on the same height and valid to attack if x is within range.
				sameHeight = true;
			}
		}
		
		//The range of the minion is the radius from the minion.
		if(sameHeight){
			//Same height
			if(champCoordinate.x > this.getCoordinates().x){
				//Champion is to the right of this Minion
				int xend = this.getCoordinates().x + this.getWidth() + this.range; //and extends all the way from the left side to the right side plus the range
				//Start of the range does not have to be checked, because that is the x coordinate of the Minion, and that coordinate is smaller than the x coordinate of the character. 
				if(champCoordinate.x < xend){
					//Within range
					if(System.currentTimeMillis() - attackStart >= attackLength){
						attackStart = System.currentTimeMillis();
						onScreen[0].damage(damage);
						attackLeft = false;
					}
					activity = true;
				}
			}else{
				//Champion is to the left of this Minion
				int xend = this.getCoordinates().x - this.range;
				
				if(champCoordinate.x + onScreen[0].getWidth() > xend){
					//Within range
					if(System.currentTimeMillis() - attackStart >= attackLength){
						attackStart = System.currentTimeMillis();
						onScreen[0].damage(damage);
						attackLeft = true;
					}
					activity = true;
				}
			}
		}
		
		//Other activity is only necessary when the Minion should not attack
		if(!activity){
			//Check if next step can be made
			NavMesh navMesh = GUIEngine.getGameEngine().getNavMesh();
			if(moveLeft){
				//Move left
				moveLeft = moveLeft(navMesh);
				//If this minion can fall down from this place, it needs to turn around
				if(moveDown(navMesh)){
					moveUp(navMesh);
					moveLeft = false;
					moveRight(navMesh);
				}
			}else{
				//Move right
				moveLeft = !moveRight(navMesh);
				if(moveDown(navMesh)){
					moveUp(navMesh);
					moveLeft = true;
					moveLeft(navMesh);
				}
			}
		}
		
		if(activity){
			if(attackLeft){
				this.setAnimationType(ATTACK_LEFT);
			}else{
				this.setAnimationType(ATTACK_RIGHT);
			}
		}else{
			if(moveLeft){
				setAnimationType(Minion.WALK_LEFT);
			}else{
				setAnimationType(Minion.WALK_RIGHT);
			}
		}
	}
	
	/**Returns damage from one attack.
	 * @return int damage
	 */
	public int getDamage(){
		return damage;
	}
	
	/**Return the range of this Minion.
	 * @return int range
	 */
	public int getRange(){
		return range;
	}

}
