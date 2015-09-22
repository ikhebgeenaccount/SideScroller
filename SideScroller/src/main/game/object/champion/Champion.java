package main.game.object.champion;

import main.game.coordinate.Coordinate;
import main.game.object.GameObject;
import main.game.object.spell.Spell;

/**Class that represents a Champion.
 * @author ikhebgeenaccount
 * @version 17 sep. 2015
 */
public class Champion extends GameObject{
	
	/**Constants for animations types.
	 * 
	 */
	public static final int IDLE = 0, WALK_LEFT = 1, WALK_RIGHT = 2, JUMP = 3, FALL = 4, CAST_Q = 5, CAST_W = 6, CAST_E = 7, CAST_R = 8;
	/**Constants for spell ids.
	 * 
	 */
	public static final int Q = 0, W = 1, E = 2, R = 3;
	
	public Spell q, w, e, r;
	
	private Coordinate spellCastOffSet;
	
	/**Default constructor, creates a GameObject with size (50, 100) and sets the following default value:
	 * - speed : 8
	 * - defaultSpeed : 8
	 * - maxHealth : 300
	 * 
	 */
	public Champion(){
		super(50, 100);
		
		//Movement speed in px
		setSpeed(8);
		setDefaultSpeed(8);
		setMaxHealth(300);
		
		spellCastOffSet = new Coordinate(0, 0);
	}
	
	/**This method is used to fire this Champion's Q spell. Returns true if firing is succesfull, otherwise false. False means the spell is still on cooldown.
	 * @param startCoordinate The Coordinate from where the spell should be fired.
	 * @param movedLeft Decides which way the spell is fired. False means to the right, true means to the left.
	 * @return boolean fired
	 */
	public boolean castQ(Coordinate startCoordinate, boolean movedLeft){
		return q.fire(new Coordinate(startCoordinate.x + spellCastOffSet.x, startCoordinate.y + spellCastOffSet.y - q.getHeight()/2), movedLeft);
	}
	
	/**This method is used to fire this Champion's W spell. Returns true if firing is succesfull, otherwise false. False means the spell is still on cooldown.
	 * @param startCoordinate The Coordinate from where the spell should be fired.
	 * @param movedLeft Decides which way the spell is fired. False means to the right, true means to the left.
	 * @return boolean fired
	 */
	public boolean castW(Coordinate startCoordinate, boolean movedLeft){
		return w.fire(new Coordinate(startCoordinate.x + spellCastOffSet.x, startCoordinate.y + spellCastOffSet.y - w.getHeight()/2), movedLeft);
	}
	
	/**This method is used to fire this Champion's E spell. Returns true if firing is succesfull, otherwise false. False means the spell is still on cooldown.
	 * @param startCoordinate The Coordinate from where the spell should be fired.
	 * @param movedLeft Decides which way the spell is fired. False means to the right, true means to the left.
	 * @return boolean fired
	 */
	public boolean castE(Coordinate startCoordinate, boolean movedLeft){
		return e.fire(new Coordinate(startCoordinate.x + spellCastOffSet.x, startCoordinate.y + spellCastOffSet.y - e.getHeight()/2), movedLeft);
	}
	
	/**This method is used to fire this Champion's R spell. Returns true if firing is succesfull, otherwise false. False means the spell is still on cooldown.
	 * @param startCoordinate The Coordinate from where the spell should be fired.
	 * @param movedLeft Decides which way the spell is fired. False means to the right, true means to the left.
	 * @return boolean fired
	 */
	public boolean castR(Coordinate startCoordinate, boolean movedLeft){
		return r.fire(new Coordinate(startCoordinate.x + spellCastOffSet.x, startCoordinate.y + spellCastOffSet.y - r.getHeight()/2), movedLeft);
	}
	
	/**This method returns the remaining cooldown of specified spell.
	 * @param spell Constant that represents one of the four available spell. Constants can be found in Class Spell.
	 * @return long remainingCooldown
	 */
	public long getRemainingCooldown(int spell){
		if(spell == Q){
			return q.getRemainingCooldown();
		}else if(spell == W){
			return w.getRemainingCooldown();
		}else if(spell == E){
			return e.getRemainingCooldown();
		}else if(spell == R){
			return r.getRemainingCooldown();
		}else{
			return 0;
		}
	}
	
	public void setCastOffSet(int offSet){
		spellCastOffSet.setCoordinates(new Coordinate(this.getWidth()/2, offSet));
	}

}
