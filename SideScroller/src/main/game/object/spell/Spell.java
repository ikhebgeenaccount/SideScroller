package main.game.object.spell;

import main.game.coordinate.Coordinate;
import main.game.object.GameObject;

public class Spell extends GameObject{

	public static final int APPEAR = 0, TRAVEL = 1, HIT = 2;
	
	private int range;
	private int damage;
	
	private Coordinate coordinates;
	private Coordinate startCoordinates;

	private boolean isFired;

	public Spell(){
		
	}

	public Spell(int range, int damage, int speed){
		setRange(range);
		setDamage(damage);
		setSpeed(speed);
	}

	public void setDamage(int damage){
		this.damage = damage;
	}

	public void setRange(int range){
		this.range = range;
	}

	public void fire(Coordinate startCoordinates){
		this.startCoordinates = startCoordinates;
		coordinates = this.startCoordinates;
	}
	
	public void move(boolean moveLeft){ //moveLeft = true, if moving left, false when moving right
		if(moveLeft){
			//Spell is moving to the left
			if(coordinates.x - startCoordinates.x > 0){
				//Move
			}else{
				//Out of range
				isFired = false;
			}
		}else{
			//Spell is moving to the right
			if(startCoordinates.x - coordinates.x > 0){
				//Move
			}else{
				//Out of range
				isFired = false;
			}
		}
	}

	public boolean isFired(){
		return isFired;
	}
}
