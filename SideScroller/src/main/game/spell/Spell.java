package main.game.spell;

import java.awt.Image;
import java.awt.image.BufferedImage;

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
	  }
  }
  
  public Image getCurrentAnimationImage() {
	  return currentAnimation.getCurrentSceneImage();
  }
	
  public void checkNextScene(){
	  currentAnimation.nextScene();
  }
	
  public void addAnimation(int type, BufferedImage animationSprite, int sceneLength){
	  animations[type] = new Animation();
	  int scenes = animationSprite.getWidth() / 50;
	  for(int i = 0; i < scenes; i++){
		  animations[type].addScene(animationSprite.getSubimage(50 * i, 0, 50, 100), sceneLength);
	  }
  }

}
