package main.game.navmesh;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**Collision is detected via a NavMesh. A NavMesh is an image containing the level, blue pixels for places where the player is allowed to move to, red where the player is not allowed to move to. In this class are all methods regarding a NavMesh. 
 * @author ikhebgeenaccount
 * @version 17 sep. 2015
 */
public class NavMesh extends BufferedImage{
	
	private Graphics2D graphics;
	private Shape shapes[];
	private int numberOfShapes;
	
	/**Creates a NavMesh corresponding to int[][] level
	 * @param level The int[][] where the NavMesh is based on.
	 */
	public NavMesh(int[][] level){
		super(level[0].length * 50, level.length * 50, BufferedImage.TYPE_INT_RGB);
		
		shapes = new Shape[1000];
		numberOfShapes = 0;
		graphics = createGraphics();
		
		drawRectangle(0, 0, level[0].length * 50, level.length * 50, Color.BLUE);
		
		for(int x = 0; x < level[0].length; x++){
			for(int y = 0; y < level.length; y++){
				if(level[y][x] != 0){
					drawRectangle(x * 50, y * 50, 50, 50, Color.RED);
				}
			}
		}
	}
	
	private void drawRectangle(int x, int y, int width, int height, Paint color){
		graphics.setPaint(color);
		graphics.fill(new Rectangle(x, y, width, height));
		shapes[numberOfShapes] = new Rectangle(x, y, width, height);
		numberOfShapes++;
	}
	
	private class Rectangle extends Rectangle2D.Double{
		
		Rectangle(int x, int y, int width, int height){
			super(x, y, width, height);
		}
	}

}
