package main.game.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.Main;
import main.game.coordinate.Coordinate;
import main.game.level.Level;
import main.game.navmesh.NavMesh;
import main.game.object.GameObject;
import main.game.object.champion.Champion;
import main.game.object.minion.Minion;
import main.game.object.spell.Spell;
import main.gui.MessageBox;

public class GameEngine{
	
	private int FPS, TPS;
	
	private Level[] levels;
	private int currentLevel;
	
	//Array for keys
	private boolean[] keys;
	
	private boolean movedLeft;
	
	//Tick
	private int tick;
	
	//The character instance of Champion
	private Champion character;
	private String characterName;
	
	//isFired is for animations, only gets true for the duration of the animation (not yet implemented)
	//isFlying is for the spells itself, to check if a spell is currently on screen and needs to be drawed and moved
	private boolean isFiredQ, isFiredW, isFiredE, isFiredR;
	private boolean isFlyingQ, isFlyingW, isFlyingE, isFlyingR;	

	private boolean jumping;
	private int jumpStartTick;
	private static final int JUMP_LENGTH_TICK = 20;
	
	private int xOffSetStart = 400, xOffSetEnd = 600, yOffSetStart = 250, yOffSetEnd = 250;
	
	public GameEngine(Champion character, String characterName){
		tick = 0;
		
		//Get character
		this.character = character;
		this.characterName = characterName;
		
		//Create array for keys
		keys = new boolean[1000];
		
		//First spell will be fired to the right
		movedLeft = false;
		
		//No spell is currently flying
		isFlyingQ = false;
		isFlyingW = false;
		isFlyingE = false;
		isFlyingR = false;		
		
		//Read level
		try{
			Properties levelOneCfg = new Properties();
			InputStream stream = getClass().getClassLoader().getResourceAsStream("levels/one.properties");
			levelOneCfg.load(stream);
			stream.close();
			
			Level levelOne = new Level(this, levelOneCfg);
			
			levels = new Level[10];
			levels[0] = levelOne;
			currentLevel = 0;
			
			levels[currentLevel].updateGameObjects();
		}catch(NullPointerException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void drawCurrentFrame(GraphicsContext g, int width, int height){
		g.clearRect(0, 0, (double)width, (double)height);
		//g.drawImage(levels[currentLevel].getLevelImage().getSubimage(levels[currentLevel].getOffSetX(), levels[currentLevel].getOffSetY(), width, height), (double)0, (double)0);
		
		int[][] level = levels[currentLevel].getLevelArray();
		for(int x = 0; x < level[0].length; x++){
			for(int y = 0; y < level.length; y++){
				g.drawImage(levels[currentLevel].getImageById(level[y][x]), (double)x * 50, (double)y * 50);
			}
		}
		
		
		//No spells in onScreen (or in inLevel)		
		for(GameObject object : levels[currentLevel].getOnScreenObjects()){
			if(object != null && object.getCurrentHealth() != 0){
				g.drawImage(object.getCurrentAnimationImage(), (double)(object.getCoordinates().x - levels[currentLevel].getOffSetX()), (double)(object.getCoordinates().y - levels[currentLevel].getOffSetY()));
				//g.drawImage(object.getHealthBarImage(), (double)(object.getCoordinates().x - levels[currentLevel].getOffSetX()), (double)(object.getCoordinates().y - levels[currentLevel].getOffSetY() - 16));
				object.checkNextScene();
			}
		}
		
		//Draw spells if fired
		if(isFlyingQ){
			g.drawImage(character.q.getCurrentAnimationImage(), (double)(character.q.getCoordinates().x - levels[currentLevel].getOffSetX()), (double)(character.q.getCoordinates().y - levels[currentLevel].getOffSetY()));
			character.q.checkNextScene();
		}
		if(isFlyingW){
			g.drawImage(character.w.getCurrentAnimationImage(), (double)(character.w.getCoordinates().x - levels[currentLevel].getOffSetX()), (double)(character.w.getCoordinates().y - levels[currentLevel].getOffSetY()));
			character.w.checkNextScene();
		}
		if(isFlyingE){
			g.drawImage(character.e.getCurrentAnimationImage(), (double)(character.e.getCoordinates().x - levels[currentLevel].getOffSetX()), (double)(character.e.getCoordinates().y - levels[currentLevel].getOffSetY()));
			character.e.checkNextScene();
		}
		if(isFlyingR){
			g.drawImage(character.r.getCurrentAnimationImage(), (double)(character.r.getCoordinates().x - levels[currentLevel].getOffSetX()), (double)(character.r.getCoordinates().y - levels[currentLevel].getOffSetY()));
			character.r.checkNextScene();
		}		
		
		//drawCooldowns(g, width, height);
		
		//g.setColor(Color.BLACK);
		//Draw current FPS
		//int length = String.valueOf(Main.getCurrentFPS()).length();
		//length *= 8;
		//g.drawString(Main.getCurrentFPS(), 1000 - length, 12);
		
		//length = String.valueOf(Main.getCurrentTPS()).length();
		//length *= 8;
		//g.drawString(Main.getCurrentTPS(), 1000 - length, 24);

		//g.dispose();
	}
	
	//Cooldown: Tekent de cooldowns op het scherm.
	//return: void; arg1: Graphic g;
	//argument g als workaround:
	//Zou g een private member mogen worden van GamePanel?
	private void drawCooldowns(Graphics g, int width, int height){
		final int SPELL_BOX_X = (width - 250)/2;
		final int SPELL_BOX_Y = height - 35;
		final int SPELL_BOX_W = 250;
		final int SPELL_BOX_H = 35;
		
		//Draw spellbox
		g.setColor(Color.GREEN);
		g.fillRect(SPELL_BOX_X, SPELL_BOX_Y, SPELL_BOX_W, SPELL_BOX_H);
				
		Spell[] spells = {character.q , character.w, character.e, character.r};
		//spell_index is voor q = 0; w=1;e=2;r=3;
		int spell_index =0;
		for( Spell spl : spells){	
			
			g.setColor(Color.BLACK);
			if(spl.getRemainingCooldown() != 0){
				//range 0 tot 1
				double cooldown_percentage =    1 - (double)( spl.getRemainingCooldown()) /spl.getCooldown();
					
				//Teken het cooldown balkje
				g.setColor(Color.BLUE);
				int cooldown_balk_w = (int)(SPELL_BOX_W /4 * cooldown_percentage);
				g.fillRect(SPELL_BOX_X + spell_index*(SPELL_BOX_W /4)  , SPELL_BOX_Y, cooldown_balk_w , SPELL_BOX_H);
						
				//Laat de waarde van de resterende cooldown zien
				g.setColor(Color.WHITE);
				g.drawString(String.valueOf((double)Math.round(((double)(spl.getCooldown() - spl.getRemainingCooldown()) / 1000) * 10)/10), SPELL_BOX_X + spell_index*(SPELL_BOX_W /4), 495);
			}
			spell_index++;
		}
	}
	
	/*	The update method contains:
	 * 		- Movement left and right
	 * 		- Gravity check
	 * 		- Checking if the location where the character is travelling is valid
	 * 	
	 */
	
	/**update() is the gameloop. In update() the game checks for collision, gravity and what GameObjects should be moved. The animation type of the character are also handled in update(). update() also checks if the win-condition is met.
	 * 
	 */
	public void update(){
		//Check collision with navmesh instead of reading array
		tick++;
		
		int oldX = character.getCoordinates().x;
		int oldY = character.getCoordinates().y;
		
		//Check if character is still alive
		if(character.getCurrentHealth() == 0){
			MessageBox diedBox = new MessageBox("kek, u ded", MessageBox.RETURN_TO_MAIN_MENU);
			Main.setMessageBox(diedBox);
			Main.freezeGame();
		}
		
		if(levels[currentLevel].getEndCoordinates().equals(new Coordinate(character.getCoordinates().x, character.getCoordinates().y + 50))){
			MessageBox victoryBox = new MessageBox("Victory!", MessageBox.RETURN_TO_MAIN_MENU);
			Main.setMessageBox(victoryBox);
		}

		//Variables to check for animations
		boolean moveLeft = false, moveRight = false, moveUp = false, moveDown = false;
		
		//Check left and right movement
		if(keys[KeyEvent.VK_LEFT]){
			moveLeft = character.moveLeft(levels[currentLevel].getNavMesh());
			movedLeft = true;
		}
		if(keys[KeyEvent.VK_RIGHT]){
			moveRight = character.moveRight(levels[currentLevel].getNavMesh());
			movedLeft = false;
		}

		if(keys[KeyEvent.VK_UP]){
			if(!jumping){
				//If character can move down, it is not possible to jump, and vice versa
				jumping = !character.moveDown(levels[currentLevel].getNavMesh());
				
				//If jumping = false, the character moved down, so we need to move him up again
				//Otherwise, we start the jump by setting jumpStartTick and setting initial yspeed
				if(!jumping){
					character.moveUp(levels[currentLevel].getNavMesh());
				}else{
					jumpStartTick = tick;
					character.resetSpeed();
					character.accelerate(18);
				}
			}
		}
		
		if(jumping){
			if(tick - jumpStartTick <= JUMP_LENGTH_TICK){
				//Still jumping
				moveUp = character.moveUp(levels[currentLevel].getNavMesh());
			}else{
				//Not jumping anymore
				jumping = false;
				character.resetSpeed();
			}
			if(!moveUp){
				//character was not able to move up, stop jumping
				jumping = false;
				character.resetSpeed();
			}else{
				//character was able to jump, decelerate
				character.accelerate(-1);
			}
		}else{
			//Not jumping, check if character should fall
			moveDown = character.moveDown(levels[currentLevel].getNavMesh());
			if(moveDown){
				character.accelerate(1);
			}else{
				character.resetSpeed();
			}
		}
		
		//Update the screen offset
		if(character.getCoordinates().x - levels[currentLevel].getOffSetX() >= xOffSetEnd && levels[currentLevel].getOffSetX() < levels[currentLevel].getMaxOffSetX()){
			if(levels[currentLevel].getOffSetX() + Math.abs(character.getCoordinates().x - oldX) > levels[currentLevel].getMaxOffSetX()){
				levels[currentLevel].setOffSetX(levels[currentLevel].getMaxOffSetX());
			}else{
				levels[currentLevel].setOffSetX(levels[currentLevel].getOffSetX() + Math.abs(character.getCoordinates().x - oldX));				
			}
		}else if(character.getCoordinates().x - levels[currentLevel].getOffSetX() <= xOffSetStart && levels[currentLevel].getOffSetX() > 0){
			if(levels[currentLevel].getOffSetX() - Math.abs(character.getCoordinates().x - oldX) < 0){
				levels[currentLevel].setOffSetX(0);
			}else{
				levels[currentLevel].setOffSetX(levels[currentLevel].getOffSetX() - Math.abs(character.getCoordinates().x - oldX));
			}
		}
		
		if(character.getCoordinates().y - levels[currentLevel].getOffSetY() >= yOffSetStart && levels[currentLevel].getOffSetY() < levels[currentLevel].getMaxOffSetY()){
			if(levels[currentLevel].getOffSetY() + Math.abs(character.getCoordinates().y - oldY) > levels[currentLevel].getMaxOffSetY()){
				levels[currentLevel].setOffSetY(levels[currentLevel].getMaxOffSetY());
			}else{
				levels[currentLevel].setOffSetY(levels[currentLevel].getOffSetY() + Math.abs(character.getCoordinates().y - oldY));
			}
		}else if(character.getCoordinates().y - levels[currentLevel].getOffSetY() <= yOffSetEnd && levels[currentLevel].getOffSetY() > 0){
			if(levels[currentLevel].getOffSetY() - Math.abs(character.getCoordinates().y - oldY) < 0){
				levels[currentLevel].setOffSetY(0);
			}else{
				levels[currentLevel].setOffSetY(levels[currentLevel].getOffSetY() - Math.abs(character.getCoordinates().y - oldY));
			}
		}
		
		//Pause game on escape
		if(keys[KeyEvent.VK_ESCAPE]){
			Main.pauseGame();
		}
		
		//Check spells
		if(keys[KeyEvent.VK_Q] && character.q.getRemainingCooldown() == 0){
			isFiredQ = character.castQ(character.getCoordinates(), movedLeft);
			isFlyingQ = isFiredQ;
		}else{
			keys[KeyEvent.VK_Q] = false;
		}
		
		if(keys[KeyEvent.VK_W] && character.w.getRemainingCooldown() == 0){
			isFiredW = character.castW(character.getCoordinates(), movedLeft);
			isFlyingW = isFiredW;
		}else{
			keys[KeyEvent.VK_W] = false;
		}
		
		if(keys[KeyEvent.VK_E] && character.e.getRemainingCooldown() == 0){
			isFiredE = character.castE(character.getCoordinates(), movedLeft);
			isFlyingE = isFiredE;
		}else{
			keys[KeyEvent.VK_E] = false;
		}
		
		if(keys[KeyEvent.VK_R] && character.r.getRemainingCooldown() == 0){
			isFiredR = character.castR(character.getCoordinates(), movedLeft);
			isFlyingR = isFiredR;
		}else{
			keys[KeyEvent.VK_R] = false;
		}
		
		if(isFlyingQ){
			isFlyingQ = character.q.move();
		}
		if(isFlyingW){
			isFlyingW = character.w.move();
		}
		if(isFlyingE){
			isFlyingE = character.e.move();
		}
		if(isFlyingR){
			isFlyingR = character.r.move();
		}
		
		levels[currentLevel].updateGameObjects();
		
		//Determine which animation should run
		if(moveUp){
			character.setAnimationType(Champion.JUMP);
		}else if(moveDown){
			character.setAnimationType(Champion.FALL);
		}else if(moveLeft){
			character.setAnimationType(Champion.WALK_LEFT);
		}else if(moveRight){
			character.setAnimationType(Champion.WALK_RIGHT);
		}else if(isFiredQ){
			character.setAnimationType(Champion.CAST_Q);
			isFiredQ = false;
		}else if(isFiredW){
			character.setAnimationType(Champion.CAST_W);
			isFiredW = false;
		}else if(isFiredE){
			character.setAnimationType(Champion.CAST_E);
			isFiredE = false;
		}else if(isFiredR){
			character.setAnimationType(Champion.CAST_R);
			isFiredR = false;
		}else{
			character.setAnimationType(Champion.IDLE);
		}
		
		for(GameObject object : levels[currentLevel].getOnScreenObjects()){
			if(object != null && object instanceof Minion){
				Minion minion = (Minion) object;
				minion.move();
			}
		}
	}
	
	public static int roundDownToClosestMultipleOfFifty(int num){
		int mod = num % 50;
		return num-mod;    
	}
	
	//Getter for String characterName
	/**Returns the name of the Champion that is being played.
	 * @return String characterName
	 */
	public String getCharacterName(){
		return characterName;
	}
	
	//Getter for character
	/**Returns the Champion instance of the Champion that is being played.
	 * @return Champion character
	 */
	public Champion getCharacter(){
		return character;
	}
	
	public String getTheme(){
		return levels[currentLevel].getTheme();
	}
	
	/**Returns an array with all GameObjects that are on the screen, which means they are visible in the window.
	 * @return GameObject[] onScreen
	 */
	public GameObject[] getOnScreenObjects(){
		return levels[currentLevel].getOnScreenObjects();
	}

	/**Returns the NavMesh of the current level.
	 * @return NavMesh navMesh
	 */
	public NavMesh getNavMesh() {
		return levels[currentLevel].getNavMesh();
	}

	public int getObjectCap() {
		return levels[currentLevel].getObjectCap();
	}

}
