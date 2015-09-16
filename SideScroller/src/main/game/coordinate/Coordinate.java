package main.game.coordinate;

public class Coordinate{
    
    public int x, y;
    
    public Coordinate(){
    	x = 0;
    	y = 0;    
    }
    
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setCoordinates(Coordinate coordinates){
    	this.x = coordinates.x;
    	this.y = coordinates.y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
}
