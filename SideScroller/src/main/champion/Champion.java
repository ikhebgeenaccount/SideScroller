package main.champion;

import java.awt.Image;

public class Champion {
	
	private Image currentAnimationFrame;
	private static final int WALK = 0, JUMP_START = 1, JUMP_TOP = 2, IDLE = 3, CAST_Q = 4, CAST_W = 5, CAST_E = 6, CAST_R = 7;
	
	public Champion(){
			
	}
	
	public void fireQ(){
		
	}
	
	public void fireW(){
		
	}
	
	public void fireE(){
		
	}
	
	public void fireR(){
		
	}
	
	public Image getAnimationFrame(){
		return currentAnimationFrame;
	}
	
	public void setAnimationFrame(Image animationFrame){
		currentAnimationFrame = animationFrame;
	}

}
