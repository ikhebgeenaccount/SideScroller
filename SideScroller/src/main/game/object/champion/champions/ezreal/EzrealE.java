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
	//Needs checking if movement is valid
	public boolean fire(Coordinate startCoordinates, boolean movedLeft){
		int levelIDx = Main.getGamePanel().getLevelIDs()[0];
		int levelIDy = Main.getGamePanel().getLevelIDs()[1];		
		int[][] level = Main.getGamePanel().getCurrentLevel();
		
		int newcoord;		
		if(movedLeft){
			newcoord = startCoordinates.x - 200;
		}else{
			newcoord = startCoordinates.x + 200;
		}
		
		//Upper left
		int matrixcoordx = Main.getGamePanel().roundDownToClosestMultipleOfFifty(newcoord)/50;	
		int matrixcoordy = Main.getGamePanel().roundDownToClosestMultipleOfFifty(startCoordinates.y)/50;
		int checked = 0;
		
		for(int i = 0; i < checks.length; i++){
			try{
				if(level[matrixcoordy][matrixcoordx] != 0 || level[matrixcoordy + 1][matrixcoordx] != 0){
					checked++;
					double even = (double) checked/2;
					if(even == Math.round(even)){
						matrixcoordx -= checks[checked];
					}else{
						matrixcoordx += checks[checked];
					}
				}else{
					Main.getGamePanel().getCharacter().getCoordinates().setX(matrixcoordx * 50);
					startCooldown();
				}
			}catch(ArrayIndexOutOfBoundsException e){
				checked++;
				double even = (double) checked/2;
				if(even == Math.round(even)){
					matrixcoordx -= checks[checked];
				}else{
					matrixcoordx += checks[checked];
				}
			}
		}
		
		return false;
	}

}
