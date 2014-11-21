package main.game.object.champion;

import main.game.coordinate.Coordinate;
import main.game.object.GameObject;
import main.game.object.spell.Spell;

public class Champion extends GameObject{
	
	public static final int IDLE = 0, WALK_LEFT = 1, WALK_RIGHT = 2, JUMP = 3, FALL = 4, CAST_Q = 5, CAST_W = 6, CAST_E = 7, CAST_R = 8;
	
	private Spell q, w, e, r;
	
	public Champion(){
		
		//Movement speed in px
		setSpeed(5);
		
	}
	
	public void castQ(Coordinate startCoordinate){
		q.fire(startCoordinate);
	}
	
	public void castW(Coordinate startCoordinate){
		w.fire(startCoordinate);
	}
	
	public void castE(Coordinate startCoordinate){
		e.fire(startCoordinate);
	}
	
	public void castR(Coordinate startCoordinate){
		r.fire(startCoordinate);
	}

}
