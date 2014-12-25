package main.game.object.champion.champions.ezreal;

import main.Main;
import main.game.coordinate.Coordinate;
import main.game.object.spell.Spell;

public class EzrealE extends Spell{
	
	public EzrealE(){
		setCooldown(7000);
	}
	
	@Override
	public boolean move(){
		return false;
	}
	
	@Override
	//Needs checking if movement is valid
	public boolean fire(Coordinate startCoordinates, boolean movedLeft){
		if(getRemainingCooldown() == 0){
			if(movedLeft){
				Main.getGamePanel().getCharacter().setCoordinates(Main.getGamePanel().getCharacter().getCoordinates().x - 200, Main.getGamePanel().getCharacter().getCoordinates().y);
			}else{
				Main.getGamePanel().getCharacter().setCoordinates(Main.getGamePanel().getCharacter().getCoordinates().x + 200, Main.getGamePanel().getCharacter().getCoordinates().y);
			}
			startCooldown();
		}
		return false;		
	}

}
