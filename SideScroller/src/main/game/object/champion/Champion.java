package main.game.object.champion;

import main.game.coordinate.Coordinate;
import main.game.object.GameObject;
import main.game.object.spell.Spell;

public class Champion extends GameObject{
	
	public static final int IDLE = 0, WALK_LEFT = 1, WALK_RIGHT = 2, JUMP = 3, FALL = 4, CAST_Q = 5, CAST_W = 6, CAST_E = 7, CAST_R = 8;
	public static final int Q = 0, W = 1, E = 2, R = 3;
	
	private Spell q, w, e, r;
	
	public Champion(){
		
		//Movement speed in px
		setSpeed(5);
		
	}
	
	public void castQ(Coordinate startCoordinate, boolean movedLeft){
		q.fire(startCoordinate, movedLeft);
	}
	
	public void castW(Coordinate startCoordinate, boolean movedLeft){
		w.fire(startCoordinate, movedLeft);
	}
	
	public void castE(Coordinate startCoordinate, boolean movedLeft){
		e.fire(startCoordinate, movedLeft);
	}
	
	public void castR(Coordinate startCoordinate, boolean movedLeft){
		r.fire(startCoordinate, movedLeft);
	}
	
	public String getRemainingCooldown(int spell){
		if(spell == Q){
			return q.getRemainingCooldown();
		}else if(spell == W){
			return w.getRemainingCooldown();
		}else if(spell == E){
			return e.getRemainingCooldown();
		}else if(spell == r){
			return r.getRemainingCooldown();
		}else{
			return "Spell not found";
		}
	}

}
