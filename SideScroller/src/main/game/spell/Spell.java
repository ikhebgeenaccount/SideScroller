package main.game.spell;

import main.game.animation.Animation;
import main.game.coordinate.Coordinate;

public class Spell {
  
  private Coordinate coordinates;
  private int range;
  private int damage;
  
  private Animation[] animations;
  private Animation currentAnimation;
  private int currentAnimationType;
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
  
  public Coordinate getCoordinates(){
    return coordinates;
  }
  
  public void setAnimationType(int type) {
		//We only have to change the animationtype when it is now running another type, otherwise the animation will continuously
		//reset itself
		if(currentAnimationType == type){
			
		}else{
			currentAnimationType = type;
			currentAnimation = animations[type];
			currentAnimation.start();			
		}
	}

}
