package main.gui.panel;

import java.awt.Graphics;

import main.game.engine.GameEngine;
import main.gui.Panel;

/**The class GamePanel contains the gameloop and paintloop.
 * @author ikhebgeenaccount
 * @version 18 sep. 2015
 */
public class GamePanel extends Panel{
	
	private GameEngine gameEngine;
	
	/**Creates a new GamePanel with specified Champion. Initiziales the level by reading the config file.
	 * @param character The Champion which will be played.
	 * @param characterName The name of the Champion.
	 */
	public GamePanel(GameEngine game){
		this.gameEngine = game;
		
		setLayout(null);		
		
		//Set this as keylistener and make it focusable so the keylistener works
		setFocusable(true);
		addKeyListener(game);
	}
	
	/* Fill the panel with landscape
	 * One part : 50x50 px
	 * Panel : 1000x500 px
	 * Width : 20 prts
	 * Height : 10 prts
	 * 
	 * This method is called every frame
	 */
	/**Paints the level and all GameObject that are within the boundaries of the screen. Also paints cooldowns.
	 * 
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.drawImage(gameEngine.getCurrentFrame(), 0, 0, null);
		
	}
}
