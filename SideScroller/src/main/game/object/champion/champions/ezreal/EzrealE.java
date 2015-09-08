package main.game.object.champion.champions.ezreal;

import main.Main;
import main.game.coordinate.Coordinate;
import main.game.object.spell.Spell;

public class EzrealE extends Spell{
	
	private int distance; //Has to be a mulitple of 50!!!
	
	private int[] checks;
	
	public EzrealE(){
		setCooldown(7000);
		distance = 200;
		
		checks = new int[distance/50 * 2];
		for(int i = 0; i < distance/50 * 2; i++){
			checks[i] = i;
		}
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
