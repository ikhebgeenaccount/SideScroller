package main.game.object.spell;

import main.game.coordinate.Coordinate;
import main.game.object.GameObject;

public class Spell extends GameObject{

	public static final int APPEAR = 0, TRAVEL = 1, DISAPPEAR = 2, HIT = 3;
	private int[] animationsLength;
	
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
	private boolean moveLeft;
	
	public Spell(int range, int damage, int speed, long cooldown){
		animationsLength = new int[4];
		setRange(range);
		setDamage(damage);
		setSpeed(speed);
		setCooldown(cooldown);
		currentCooldown = 0;
	}
	
	public Spell(){
		animationsLength = new int[4];
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

	public boolean fire(Coordinate startCoordinates, boolean movedLeft){
		if(currentCooldown == 0){
			this.moveLeft = movedLeft;
			this.startTime = System.currentTimeMillis();
			this.startCoordinates = startCoordinates;
			coordinates = this.startCoordinates;
			isFired = true;
			setAnimationType(Spell.TRAVEL);
		}else{
			isFired = false;
		}		
		return isFired;
	}
	
	public void move(){ //moveLeft = true, if moving left, false when moving right
		if(moveLeft){
			//Spell is moving to the left
			if(coordinates.x - startCoordinates.x > 0){
				//Move
				coordinates.x = coordinates.x - getSpeed();
			}else{
				//Out of range
				isFired = false;
				setAnimationType(Spell.DISAPPEAR); //Won't play since isFired is already false, needs fixing.
			}
		}else{
			//Spell is moving to the right
			if(startCoordinates.x - coordinates.x > 0){
				//Move
				coordinates.x = coordinates.x + getSpeed();
			}else{
				//Out of range
				isFired = false;
				setAnimationType(Spell.DISAPPEAR);
			}
		}
	}
	
	public boolean isFired(){
		return isFired;
	}
	
	public long getRemainingCooldown(){
		if(System.currentTimeMillis() - startTime > 0){
			currentCooldown = System.currentTimeMillis() - startTime;
		}else{
			currentCooldown = 0;
		}
		return currentCooldown;
	}
}
