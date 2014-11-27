package main.game.object.champion;

import main.game.coordinate.Coordinate;
import main.game.object.GameObject;
import main.game.object.spell.Spell;

public class Champion extends GameObject{
	
	public static final int IDLE = 0, WALK_LEFT = 1, WALK_RIGHT = 2, JUMP = 3, FALL = 4, CAST_Q = 5, CAST_W = 6, CAST_E = 7, CAST_R = 8;
	public static final int Q = 0, W = 1, E = 2, R = 3;
	
	public Spell q, w, e, r;
	
	public Champion(){
		
		//Movement speed in px
		setSpeed(5);
		
	}
	
	public boolean castQ(Coordinate startCoordinate, boolean movedLeft){
		return q.fire(startCoordinate, movedLeft);
	}
	
	public boolean castW(Coordinate startCoordinate, boolean movedLeft){
		return w.fire(startCoordinate, movedLeft);
	}
	
	public boolean castE(Coordinate startCoordinate, boolean movedLeft){
		return e.fire(startCoordinate, movedLeft);
	}
	
	public boolean castR(Coordinate startCoordinate, boolean movedLeft){
		return r.fire(startCoordinate, movedLeft);
	}
	
	public long getRemainingCooldown(int spell){
		if(spell == Q){
			return q.getRemainingCooldown();
		}else if(spell == W){
			return w.getRemainingCooldown();
		}else if(spell == E){
			return e.getRemainingCooldown();
		}else if(spell == R){
			return r.getRemainingCooldown();
		}else{
			return 0;
		}
	}

}
