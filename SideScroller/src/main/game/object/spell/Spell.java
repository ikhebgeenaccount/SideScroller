package main.game.object.spell;

import main.game.coordinate.Coordinate;
import main.game.object.GameObject;

public class Spell extends GameObject{

	public static final int APPEAR = 0, TRAVEL = 1, HIT = 2;
	
	//Spell properties
	private int range;
	private int damage;
	private long cooldown;
	
	//Current location and start location
	private Coordinate coordinates;
	private Coordinate startCoordinates;
	
	//Active properties
	private boolean isFired;
	private long startTime;
	private long currentCooldown;
	
	public Spell(int range, int damage, int speed, long cooldown){
		setRange(range);
		setDamage(damage);
		setSpeed(speed);
		setCooldown(cooldown);
		currentCooldown = 0;
	}

	public void setDamage(int damage){
		this.damage = damage;
	}

	public void setRange(int range){
		this.range = range;
	}
	
	public void setCooldown(long cooldown){
		this.cooldown = cooldown;
	}

	public void fire(Coordinate startCoordinates, boolean movedLeft){
		if(currentCooldown == 0){
			this.moveLeft = movedLeft;
			this.startTime = System.currentTimeMillis();
			this.startCoordinates = startCoordinates;
			coordinates = this.startCoordinates;
			isFired = true;
		}else{
			isFired = false;
		}
	}
	
	public void move(){ //moveLeft = true, if moving left, false when moving right
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
	
	//Only call getCurrentSceneImage() if isFired()!
	public boolean isFired(){
		return isFired;
	}
	
	public long getRemainingCooldown(){
		if(System.currentTimeMillis - starTime > 0){
			currentCooldown = System.currentTimeMillis() - startTime;
		}else{
			currentCooldown = 0;
		}
		return currentCooldown;
	}
}
