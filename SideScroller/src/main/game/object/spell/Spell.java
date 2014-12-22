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
	
	//Start location
	private Coordinate startCoordinates;
	
	//Active properties
	private boolean isFired;
	private long startTime;
	private long currentCooldown;
	private boolean moveLeft;
	
	public Spell(int range, int damage, int speed, long cooldowninms){
		animationsLength = new int[4];
		setRange(range);
		setDamage(damage);
		setSpeed(speed);
		setCooldown(cooldowninms);
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
	
	public void setCooldown(long cooldowninms){
		this.cooldown = cooldowninms;
	}

	public boolean fire(Coordinate startCoordinates, boolean movedLeft){
		if(currentCooldown == 0){
			this.moveLeft = movedLeft;
			this.startTime = System.currentTimeMillis();
			this.startCoordinates = new Coordinate();
			this.startCoordinates.setCoordinates(startCoordinates);
			setCoordinates(this.startCoordinates);
			isFired = true;
			setAnimationType(Spell.TRAVEL);
			currentCooldown = cooldown;
		}else{
			isFired = false;
		}		
		return isFired;
	}
	
	public boolean move(){ //moveLeft = true, if moving left, false when moving right
		boolean move;
		if(moveLeft){
			//Spell is moving to the left
			if(startCoordinates.x - getCoordinates().x < range){
				//Move
				move = true;
				setCoordinates(getCoordinates().x - getSpeed(), getCoordinates().y);
			}else{
				//Out of range
				move = false;
				isFired = false;
				setAnimationType(Spell.DISAPPEAR); //Won't play since isFired is already false, needs fixing.
			}
		}else{
			//Spell is moving to the right
			if(getCoordinates().x - startCoordinates.x < range){
				//Move
				move = true;
				setCoordinates(getCoordinates().x + getSpeed(), getCoordinates().y);
			}else{
				//Out of range
				move = false;
				isFired = false;
				setAnimationType(Spell.DISAPPEAR);
			}
		}
		return move;
	}
	
	public boolean isFired(){
		return isFired;
	}
	
	public void startCooldown(){
		startTime = System.currentTimeMillis();
		currentCooldown = cooldown;
	}
	
	public long getRemainingCooldown(){
		if(System.currentTimeMillis() - startTime < cooldown){
			currentCooldown = System.currentTimeMillis() - startTime;
		}else{
			currentCooldown = 0;
		}
		return currentCooldown;
	}
}
