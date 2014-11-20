package main.game.coordinate;

public class Coordinate{
    
    public int x, y;
    
    public Coordinate(){
    
    }
    
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setCoordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
}