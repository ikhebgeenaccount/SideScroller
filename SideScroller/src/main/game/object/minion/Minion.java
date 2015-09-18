package main.game.object.minion;

import main.Main;
import main.game.navmesh.NavMesh;
import main.game.object.GameObject;
import main.game.object.champion.Champion;

/**This class contains all methods regarding Minions.
 * @author ikhebgeenaccount
 * @version 17 sep. 2015
 */
public class Minion extends GameObject {
	
	/**The public constants for animations types.
	 * 
	 */
	public static final int WALK_RIGHT = 0, WALK_LEFT = 1, ATTACK = 2, DIE = 3, BE_DEAD = 4; 
	
	private int range;
	private int damage;
	private long attackLength; //In seconds
	private long attackStart;
	
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
	 * @param range The range of the Minion in pixels.
	 * @param attackLength The length of an attack in milliseconds.
	 */
	public Minion(int width, int height, int speed, int range, long attackLength){
		super(width, height);
		setSpeed(speed);
		setRange(range);
		setAttackLength(attackLength);
		setMaxHealth(200);
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
		
		GameObject[] onScreen = Main.getGamePanel().getOnScreenObjects();
		int coordx = getCoordinates().x;
		int coordy = getCoordinates().y;
		
		int newcoordx = coordx;
		int newcoordy = coordy;
		 
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
						//onScreen[i].damage(getDamage());
					}
					activity = true;
				}
			}
		}
		
		NavMesh navMesh = Main.getGamePanel().getNavMesh();
		if(!activity){
			//Check if next step can be made
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
			setAnimationType(Minion.ATTACK);
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
