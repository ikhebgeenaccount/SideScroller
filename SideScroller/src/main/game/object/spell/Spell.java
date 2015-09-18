package main.game.object.spell;

import main.game.coordinate.Coordinate;
import main.game.object.GameObject;

/**This class contains all methods and variables regardins spells.
 * @author ikhebgeenaccount
 * @version 18 sep. 2015
 */
public class Spell extends GameObject{

	/**Public constants for animation types.
	 * 
	 */
	public static final int APPEAR = 0, TRAVEL = 1, DISAPPEAR = 2, HIT = 3;
	private int[] animationsLength;
	
	//Spell properties
	private int range;
	private int damage;
	private long cooldown;
	
	//Start location
	private Coordinate startCoordinates;
	
	//Active properties
	private boolean isFired;
	private long startTime;
	private long currentCooldown;
	private boolean moveLeft;
	
	/**Creates a new Spell with specified properties.
	 * @param range The range in pixels.
	 * @param damage The damage.
	 * @param speed The speed in pixels.
	 * @param cooldowninms The cooldown in milliseconds.
	 */
	public Spell(int range, int damage, int speed, long cooldowninms){
		animationsLength = new int[4];
		setRange(range);
		setDamage(damage);
		setSpeed(speed);
		setCooldown(cooldowninms);
		currentCooldown = 0;
	}
	
	/**Default constructor. Not recommended for use.
	 * 
	 */
	public Spell(){
		animationsLength = new int[4];
	}

	/**Sets the damage.
	 * @param damage The damage.
	 */
	public void setDamage(int damage){
		this.damage = damage;
	}

	/**Sets the range.
	 * @param range The range in pixels.
	 */
	public void setRange(int range){
		this.range = range;
	}
	
	/**Sets the cooldown.
	 * @param cooldowninms The cooldown in milliseconds.
	 */
	public void setCooldown(long cooldowninms){
		this.cooldown = cooldowninms;
	}

	/**Fires the spell if cooldown is zero. Returns true when fired, otherwise false.
	 * @param startCoordinates The Coordinate from where this Spell is fired.
	 * @param movedLeft The direction in which the Spell is fired. False for right, true for left.
	 * @return boolean fired
	 */
	public boolean fire(Coordinate startCoordinates, boolean movedLeft){
		if(currentCooldown == 0){
			this.moveLeft = movedLeft;
			this.startTime = System.currentTimeMillis();
			this.startCoordinates = new Coordinate();
			this.startCoordinates.setCoordinates(startCoordinates);
			getCoordinates().setCoordinates(this.startCoordinates);
			isFired = true;
			setAnimationType(Spell.TRAVEL);
			currentCooldown = cooldown;
		}else{
			isFired = false;
		}		
		return isFired;
	}
	
	/**Moves the Spell. Returns true if it has moved, returns false if the Spell has moved out of its range and has disappeared.
	 * @return boolean moved
	 */
	public boolean move(){ //moveLeft = true, if moving left, false when moving right
		boolean move;
		if(moveLeft){
			//Spell is moving to the left
			if(startCoordinates.x - getCoordinates().x < range){
				//Move
				move = true;
				getCoordinates().setCoordinates(getCoordinates().x - getSpeed(), getCoordinates().y);
			}else{
				//Out of range
				move = false;
				isFired = false;
				setAnimationType(Spell.DISAPPEAR); //Won't play since isFired is already false, needs fixing.
			}
		}else{
			//Spell is moving to the right
			if(getCoordinates().x - startCoordinates.x < range){
				//Move
				move = true;
				getCoordinates().setCoordinates(getCoordinates().x + getSpeed(), getCoordinates().y);
			}else{
				//Out of range
				move = false;
				isFired = false;
				setAnimationType(Spell.DISAPPEAR);
			}
		}
		return move;
	}
	
	/**Returns true if this Spell is fired and flying, otherwise false.
	 * @return boolean isFired
	 */
	public boolean isFired(){
		return isFired;
	}
	
	/**Starts the cooldown of this Spell.
	 * 
	 */
	public void startCooldown(){
		startTime = System.currentTimeMillis();
		currentCooldown = cooldown;
	}
	
	/**Returns the remaining cooldown.
	 * @return long remainingCooldown
	 */
	public long getRemainingCooldown(){
		if(System.currentTimeMillis() - startTime < cooldown){
			currentCooldown = System.currentTimeMillis() - startTime;
		}else{
			currentCooldown = 0;
		}
		return currentCooldown;
	}

	/**Returns the cooldown of this Spell.
	 * @return long cooldown
	 */
	public long getCooldown() {
		return cooldown;
	}
	
	/** Returns the damage that this Spell deals.
	 * @return int damage
	 */
	public int getDamage(){
		return damage;
	}
}
