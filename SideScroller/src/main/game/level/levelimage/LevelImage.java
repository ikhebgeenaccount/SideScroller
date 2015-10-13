/**
 * 
 */
package main.game.level.levelimage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

/**
 * @author ikhebgeenaccount
 * @version 24 sep. 2015
 */
public class LevelImage extends BufferedImage {
	
	private Graphics2D g;
	
	//Images
	private Image ground, air, grass_ground;
	
	private String theme;
	
	public LevelImage(int[][] level, String theme){
		super(level[0].length * 50, level.length * 50, BufferedImage.TYPE_INT_RGB);
		
		try{
			ClassLoader cldr = this.getClass().getClassLoader();
			air = new ImageIcon(cldr.getResource("img/landscape/" + theme + "/air.png")).getImage();
			ground = new ImageIcon(cldr.getResource("img/landscape/" + theme + "/ground.png")).getImage();
			grass_ground = new ImageIcon(cldr.getResource("img/landscape/" + theme + "/grass-ground.png")).getImage();
			
			this.theme = theme;
			
			g = createGraphics();
			
			for(int x = 0; x < level[0].length; x++){
				for(int y = 0; y < level.length; y++){
					switch(level[y][x]){
						case 0:g.drawImage(air, 50 * x, 50 * y, null);
							break;
						case 1:g.drawImage(ground, 50 * x, 50 * y, null);
							break;	
						case 2:g.drawImage(grass_ground, 50 * x, 50 * y, null);
							break;
						default:g.drawImage(air, 50 * x, 50 * y, null);
							break;
					}
				}
			}
			
		}catch(NullPointerException e){
			ClassLoader cldr = this.getClass().getClassLoader();
			air = new ImageIcon(cldr.getResource("img/landscape/default/air.png")).getImage();
			ground = new ImageIcon(cldr.getResource("img/landscape/default/ground.png")).getImage();
			grass_ground = new ImageIcon(cldr.getResource("img/landscape/default/grass-ground.png")).getImage();
			
			this.theme = theme;
			
			g = createGraphics();
			
			for(int x = 0; x < level[0].length; x++){
				for(int y = 0; y < level.length; y++){
					switch(level[y][x]){
					case 0:g.drawImage(air, 50 * x, 50 * y, null);
						break;
					case 1:g.drawImage(ground, 50 * x, 50 * y, null);
						break;	
					case 2:g.drawImage(grass_ground, 50 * x, 50 * y, null);
						break;
					default:g.drawImage(air, 50 * x, 50 * y, null);
						break;
					}
				}
			}
		}
	}

}
