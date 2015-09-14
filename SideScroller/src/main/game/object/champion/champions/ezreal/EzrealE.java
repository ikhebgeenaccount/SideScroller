package main.game.object.champion.champions.ezreal;

import main.Main;
import main.game.coordinate.Coordinate;
import main.game.object.spell.Spell;

public class EzrealE extends Spell{
	
	private int distance;
	
	public EzrealE(){
		setCooldown(7000);
		distance = 200;
	}
	
	@Override
	public boolean move(){
		return false;
	}
	
	@Override
	public boolean fire(Coordinate startCoordinates, boolean movedLeft){
		int travelDistance = movedLeft ? -distance : distance;
		if(Main.getGamePanel().getCharacter().blink(Main.getGamePanel().getNavMesh(), movedLeft, travelDistance)){
			startCooldown();
		}
		//We have to return false no matter what, since no projectile is fired.
		return false;		
	}
}