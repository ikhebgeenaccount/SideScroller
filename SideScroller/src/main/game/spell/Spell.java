package main.game.spell;

import main.game.animation.Animation;

public class Spell {
  
  public int[] coordinates;
  public int range;
  public int damage;
  
  public Animation[] animations;
  public static final int APPEAR = 0, TRAVEL = 1, HIT = 2;
  
  private boolean isFired;
  
  public Spell(){
    
  }
  
  public void fire(){
    
  }
  
  public boolean isFired(){
    return isFired;
  }
  
  public int[] getCoordinates(){
    return coordinates;
  }

}
