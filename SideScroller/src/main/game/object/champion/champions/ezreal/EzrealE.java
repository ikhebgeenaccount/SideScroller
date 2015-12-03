package main.game.object.champion.champions.ezreal;

import main.Main;
import main.game.coordinate.Coordinate;
import main.game.object.spell.Spell;

/**This class contains the methods for Ezreal's Spell E, since this is a special case. It is not just a Spell that should be fired; nothing is fired, but the character is teleported.
 * @author ikhebgeenaccount
 * @version 17 sep. 2015
 */
public class EzrealE extends Spell{
	
	private int distance;
	
	/**Creates the spell with cooldown of 7000 milliseconds and a traveldistance of 200 pixels.
	 * 
	 */
	public EzrealE(){
		setCooldown(7000);
		distance = 200;
	}
	
	/** Because no projectile is fired, this Spell never moves and therefore move() should return false.
	 * @return false
	 */
	@Override
	public boolean move(){
		return false;
	}
	
	/** Because no projectile is fired, this Spell does not have an animation for such a projectile, so fire() should always return false.
	 * @return false
	 */
	@Override
	public boolean fire(Coordinate startCoordinates, boolean movedLeft){
		int travelDistance = movedLeft ? -distance : distance;
		if(Main.getGame().getCharacter().blink(Main.getGame().getNavMesh(), movedLeft, travelDistance)){
			startCooldown();
		}
		//We have to return false no matter what, since no projectile is fired.
		return false;		
	}
}