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
		startCooldown();
		boolean fired = true;
		if(movedLeft){
			Main.getGamePanel().getCharacter().setSpeed(distance);
			fired = Main.getGamePanel().getCharacter().moveLeft(Main.getGamePanel().getNavMesh());
			Main.getGamePanel().getCharacter().setSpeed(Main.getGamePanel().getCharacter().getDefaultSpeed());
		}else{
			Main.getGamePanel().getCharacter().setSpeed(distance);
			fired = Main.getGamePanel().getCharacter().moveRight(Main.getGamePanel().getNavMesh());
			Main.getGamePanel().getCharacter().setSpeed(Main.getGamePanel().getCharacter().getDefaultSpeed());
		}
		return false;		
	}
}