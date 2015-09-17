package main.game.coordinate;

/**This class contains all methods and variables to use Coordinates. Coordinates are Objects that hold the coordinates of a GameObject. Coordinates are stored in pixel units.
 * @author ikhebgeenaccount
 * 17 sep. 2015
 */
public class Coordinate{
    
    public int x, y;
    
    /**Default constructor. Constructs a Coordinate with values (0,0)
     * 
     */
    public Coordinate(){
    	x = 0;
    	y = 0;    
    }
    
    /**Constructs a Coordinate with values (x,y)
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**Changes the x- and y-coordinate
     * @param x The new value for the x-coordinate
     * @param y The new value for the y-coordinate
     */
    public void setCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**Changes the x- and y-coordinate of this Coordinate to the x- and y-coordinate of given Coordinate
     * @param coordinates Contains the new values for this Coordinate
     */
    public void setCoordinates(Coordinate coordinates){
    	this.x = coordinates.x;
    	this.y = coordinates.y;
    }
    
    /**Changes the x-coordinate to the new value
     * @param x New value for x-coordinate
     */
    public void setX(int x){
        this.x = x;
    }
    
    /**Changes the y-coordinate to the new value
     * @param y New value for y-coordinate
     */
    public void setY(int y){
        this.y = y;
    }
}
