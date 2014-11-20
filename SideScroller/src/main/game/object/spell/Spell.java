package main.game.object.spell;

import java.awt.Image;
import java.awt.image.BufferedImage;

import main.game.animation.Animation;
import main.game.coordinate.Coordinate;
import main.game.object.GameObject;

public class Spell extends GameObject{

	private int range;
	private int damage;

	public static final int APPEAR = 0, TRAVEL = 1, HIT = 2;

	private boolean isFired;

	public Spell(){

	}

	public Spell(int range, int damage){
		this.range = range;
		this.damage = damage;
	}

	public void setDamage(int damage){
		this.damage = damage;
	}

	public void setRange(int range){
		this.range = range;
	}

	public void fire(){
		
	}

	public boolean isFired(){
		return isFired;
	}
}
