package main.game.object.spell;

import main.Main;
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
	public static final int APPEAR = 0, TRAVEL_LEFT = 1, TRAVEL_RIGHT = 2, DISAPPEAR = 3, HIT = 4;
	
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
	
	private boolean disappearOnHit;
	
	/**Creates a new Spell with specified properties.
	 * @param range The range in pixels.
	 * @param damage The damage.
	 * @param speed The speed in pixels.
	 * @param cooldowninms The cooldown in milliseconds.
	 */
	public Spell(int range, int damage, int speed, long cooldowninms){
		setRange(range);
		setDamage(damage);
		setSpeed(speed);
		setDefaultSpeed(speed);
		setCooldown(cooldowninms);
		currentCooldown = 0;
		disappearOnHit = true;
	}
	
	/**Default constructor. Not recommended for use.
	 * 
	 */
	public Spell(){
		
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
	
	public void setDisappearOnHit(boolean disappear){
		this.disappearOnHit = disappear;
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
			if(movedLeft){
				//Move left
				this.startCoordinates.setCoordinates(new Coordinate(startCoordinates.x - this.getWidth(), startCoordinates.y));
			}else{
				this.startCoordinates.setCoordinates(startCoordinates);
			}
			getCoordinates().setCoordinates(this.startCoordinates);
			isFired = true;
			if(moveLeft){
				setAnimationType(Spell.TRAVEL_LEFT);
			}else{
				setAnimationType(Spell.TRAVEL_RIGHT);				
			}
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
		boolean move = false;
		boolean hit = false;
		
		int coordy;
		GameObject[] onScreen = Main.getGameEngine().getOnScreenObjects();
		boolean[] onSameHeight = new boolean[Main.getGameEngine().getObjectCap()];
		
		for(int i = 0; i < onSameHeight.length; i++){
			onSameHeight[i] = false;
		}
		
		for(int j = 1; j < onScreen.length; j++){
			if(onScreen[j] != null){
				/*
				 * Case 1: spell is smaller than this Minion
				 * 
				 * 	Y     Spell		Minion
				 *  0				*
				 *  1     ***       *
				 *  2	  ***		*
				 *  3				*
				 * 
				 * Hits when:
				 * 		1. Spell.getCoordinates().y >= Minion.getCoordinates.y && Spell.getCoordinates().y <= Minion.getCoordinates().y + Minion.getHeight()
				 * 		2. Spell.getCoordinates().y + Spell.getHeight() >= Minion.getCoordinates().y 
				 * 							&& Spell.getCoordinates().y + Spell.getHeight() <= Minion.getCoordinates().y + Minion.getHeight()
				 * 
				 * Test:
				 * 		1. 1 >= 0 && 1 <= 0 + 4
				 * 		2. 1 + 2 >= 0 && 1 + 2 <= 0 + 4
				 * 
				 * Case 2: spell is bigger than this Minion
				 * 
				 * Y Spell		Minion
				 * 0 **
				 * 1 **			**
				 * 2 **			**
				 * 3 **
				 * 
				 * Hits when:
				 * 		1. Minion.getCoordinates().y >= Spell.getCoordinates().y && Minion.getCoordinates().y <= Spell.getCoordinates().y + Spell.getHeight()
				 * 		2. Minion.getCoordinates().y + Minion.getHeight() >= Spell.getCoordinates().y 
				 * 							&& Minion.getCoordinates().y + Minion.getHeight() <= Spell.getCoordinates().y + Spell.getHeight()
				 * 
				 * Test:
				 * 		1. 1 >= 0 && 1 <= 0 + 4
				 * 		2. 1 + 2 >= 0 && 1 + 2 <= 0 + 4
				 * 
				 */	
				
				if(		(this.getCoordinates().y >= onScreen[j].getCoordinates().y && this.getCoordinates().y <= onScreen[j].getCoordinates().y + onScreen[j].getHeight())
					||	(this.getCoordinates().y + this.getHeight() >= onScreen[j].getCoordinates().y && this.getCoordinates().y + this.getHeight() <= onScreen[j].getCoordinates().y + onScreen[j].getHeight())
					||	(onScreen[j].getCoordinates().y >= this.getCoordinates().y && onScreen[j].getCoordinates().y <= this.getCoordinates().y + this.getHeight())
					||	(onScreen[j].getCoordinates().y + onScreen[j].getHeight() >= this.getCoordinates().y && onScreen[j].getCoordinates().y + onScreen[j].getHeight() <= this.getCoordinates().y + this.getHeight())
					){
					onSameHeight[j] = true;
				}
			}else{
				j = onScreen.length;
			}
		}
		
		//Get Minion with closest x coordinate
		
		/*
		 * Scenario:
		 * 
		 * 
		 * * Minion
		 * c Character
		 * s Spell
		 * < Direction of spell
		 * 
		 * 
		 *     * *   s<c     *
		 * x 0 1 2 3 4 5 6 7 8 9
		 * 
		 * 
		 */
		
		int j = 1;
		int min_difference = Integer.MAX_VALUE;
		int id = -1;
		while(j < onSameHeight.length && onScreen[j] != null){
			if(onSameHeight[j]){
				if(moveLeft){
					if(this.getCoordinates().x > onScreen[j].getCoordinates().x){
						// this - that
						if(this.getCoordinates().x - (onScreen[j].getCoordinates().x + onScreen[j].getWidth()) < min_difference){
							id = j;
							min_difference = this.getCoordinates().x - onScreen[j].getCoordinates().x + onScreen[j].getWidth();
						}
					}else{
						// that - this
						if((onScreen[j].getCoordinates().x + onScreen[j].getWidth()) - this.getCoordinates().x < min_difference){
							id = j;
							min_difference = this.getCoordinates().x - onScreen[j].getCoordinates().x + onScreen[j].getWidth();
						}
					}
				}else{
					if(this.getCoordinates().x > onScreen[j].getCoordinates().x){
						// this - that
						if(this.getCoordinates().x - onScreen[j].getCoordinates().x < min_difference){
							id = j;
							min_difference = this.getCoordinates().x - onScreen[j].getCoordinates().x + onScreen[j].getWidth();
						}
					}else{
						// that - this
						if(onScreen[j].getCoordinates().x - this.getCoordinates().x < min_difference){
							id = j;
							min_difference = this.getCoordinates().x - onScreen[j].getCoordinates().x + onScreen[j].getWidth();
						}
					}
				}				
			}
			j++;
		}
		
		//Check if x coordinates are the same
		if(moveLeft && id != -1){
			if(this.getCoordinates().x <= onScreen[id].getCoordinates().x + onScreen[id].getWidth() && this.getCoordinates().x + this.getWidth() >= onScreen[id].getCoordinates().x + onScreen[id].getWidth()){
				//Hit!!
				onScreen[id].damage(this.damage);
				isFired = !disappearOnHit;
				move = !disappearOnHit;
				hit = disappearOnHit;
			}
		}else if(id != -1){
			if(this.getCoordinates().x + this.getWidth() >= onScreen[id].getCoordinates().x && this.getCoordinates().x <= onScreen[id].getCoordinates().x){
				//Hit!!
				onScreen[id].damage(this.damage);
				isFired = !disappearOnHit;
				move = !disappearOnHit;
				hit = disappearOnHit;
			}			
		}
		
		//Move the spell
		if(!hit && moveLeft){
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
		}else if(!hit){
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
