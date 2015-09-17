package main.gui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.ImageIcon;

import main.Main;
import main.game.coordinate.Coordinate;
import main.game.navmesh.NavMesh;
import main.game.object.GameObject;
import main.game.object.champion.Champion;
import main.game.object.minion.Minion;
import main.game.object.minion.minions.Baron;
import main.game.object.minion.minions.BlueGolem;
import main.game.object.minion.minions.CasterMinion;
import main.game.object.minion.minions.Dragon;
import main.game.object.minion.minions.LargeWolf;
import main.game.object.minion.minions.MeleeMinion;
import main.game.object.minion.minions.MiniLizard;
import main.game.object.minion.minions.RedLizard;
import main.game.object.minion.minions.SiegeMinion;
import main.game.object.minion.minions.SuperMinion;
import main.game.object.spell.Spell;
import main.gui.Panel;

public class GamePanel extends Panel implements KeyListener{
	
	//Levels
	private int levelIDx;
	private int levelIDy;
	private int[][] currentLevel;
	private int[][] levelOne;
	private NavMesh navMesh;
	
	//Array for keys
	private boolean[] keys;
	
	//Images
	private Image ground, air, grass_ground;	
	
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
	
	//The GameObject[]s that are in this level and those that are onscreen. For checks we will only check onscreen.
	private GameObject[] inLevel, onScreen;
	private final int objectCap = 50; //Maximum number of objects in one level
	
	//Level properties
	private Coordinate startSquare;
	private Coordinate endSquare;
	private String theme;
	
	public GamePanel(Champion character, String characterName){
		tick = 0;
		
		setLayout(null);
		
		//Get character
		this.character = character;
		this.characterName = characterName;
		
		//Create array for keys
		keys = new boolean[1000];
		
		//Set first level and create levels
		createLevels();
		navMesh = new NavMesh(levelOne);
		//NavMeshTest navFrame = new NavMeshTest(navMesh);
		currentLevel = levelOne;
		levelIDx = 0;
		levelIDy = 0;
		
		//First spell will be fired to the right
		movedLeft = false;
		
		//No spell is currently flying
		isFlyingQ = false;
		isFlyingW = false;
		isFlyingE = false;
		isFlyingR = false;
		
		//Create GameObject[]s
		inLevel = new GameObject[objectCap];
		onScreen = new GameObject[objectCap];//Since inLevel has a cap of 50, onScreen doesn't need more than that
		
		inLevel[0] = character;
		
		//Read level
		try{
			Properties levelOneCfg = new Properties();
			InputStream stream = getClass().getClassLoader().getResourceAsStream("levels/one.properties");
			levelOneCfg.load(stream);
			stream.close();
			
			theme = levelOneCfg.getProperty("theme");
		
			//Read properties of startSquare and endSquare, start and end of level
			String startSquareCfg = levelOneCfg.getProperty("startsquare");
			startSquare = new Coordinate();
			startSquare.x = Integer.parseInt(startSquareCfg.split(",")[0]) * 50;
			startSquare.y = Integer.parseInt(startSquareCfg.split(",")[1]) * 50;
			character.getCoordinates().setCoordinates(startSquare);
			
			endSquare = new Coordinate();
			String endSquareCfg = levelOneCfg.getProperty("endsquare");
			endSquare.x = Integer.parseInt(endSquareCfg.split(",")[0]) * 50;
			endSquare.y = Integer.parseInt(endSquareCfg.split(",")[1]) * 50;
		
			//Read minions, fill inLevel with corresponding type
			String minionsCfg = levelOneCfg.getProperty("minions"); //minionsCfg: "type:x.y/x.y,type:x.y/x.y/x.y/x.y"
			String[] minionTypeCfg = minionsCfg.split(","); //minionTypeCfg: {"type:x.y/x.y", "type:x.y/x.y/x.y/x.y"}
			
			int index = 1;
			
			for(int i = 0; i < minionTypeCfg.length; i++){					
				String[] typeCfg = minionTypeCfg[i].split(":"); //typeCfg: {"type", "x.y/x.y"} (next iteration: {"type", "x.y/x.y/x.y/x.y"})
				String[] minionCoordinatesCfg = typeCfg[1].split("/");// minionCoordinatesCfg: {"x.y", "x.y"}
				Minion[] type = new Minion[minionCoordinatesCfg.length];
				
				switch(Integer.parseInt(typeCfg[0])){
					case 0:	for(int j = 0; j < type.length; j++){
								type[j] = new MeleeMinion();
							}
						break;
					case 1:	for(int j = 0; j < type.length; j++){
								type[j] = new CasterMinion();
							}
						break;
					case 2:	for(int j = 0; j < type.length; j++){
								type[j] = new SiegeMinion();
							}
						break;
					case 3:	for(int j = 0; j < type.length; j++){
								type[j] = new SuperMinion();
							}
						break;
					case 4:	for(int j = 0; j < type.length; j++){
								type[j] = new MiniLizard();
							}
						break;
					case 5:	for(int j = 0; j < type.length; j++){
								type[j] = new LargeWolf();
							}
						break;
					case 6:	for(int j = 0; j < type.length; j++){
								type[j] = new RedLizard();
							}
						break;
					case 7:	for(int j = 0; j < type.length; j++){
								type[j] = new BlueGolem();
							}
						break;
					case 8:	for(int j = 0; j < type.length; j++){
								type[j] = new Dragon();
							}
						break;
					case 9:	for(int j = 0; j < type.length; j++){
								type[j] = new Baron();
							}
						break;
					default:for(int j = 0; j < type.length; j++){
								type[j] = new MeleeMinion();
							}
						break;
				}
				
				for(int j = 0; j < minionCoordinatesCfg.length; j++){
					String[] coordinates = minionCoordinatesCfg[j].split("\\.");
					type[j].getCoordinates().setCoordinates(Integer.parseInt(coordinates[0]) * 50, Integer.parseInt(coordinates[1]) * 50);
					inLevel[index] = type[j];
					index++;
				}
			}
			
			updateGameObjects();
		}catch(NullPointerException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		//Set this as keylistener and make it focusable so the keylistener works
		addKeyListener(this);
		setFocusable(true);
		
		//Load environment images
		loadPics();
		
		updateGameObjects();
	}
	
	/* Fill the panel with landscape
	 * One part : 50x50 px
	 * Panel : 1000x500 px
	 * Width : 20 prts
	 * Height : 10 prts
	 * 
	 * This method is called every frame
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//i is x
		//j is y
		for(int x = 0; x < 20; x++){
			for(int y = 0; y < 10; y++){
                //Decide which landscape-img should be used
				int xCoord = x + (levelIDx * 20);
				int yCoord = y + (levelIDy * 10);
				switch(currentLevel[y + (levelIDy * 10)][x + (levelIDx * 20)]){
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
		
		//No spells in onScreen (or in inLevel)
		for(GameObject object : onScreen){
			if(object != null){
				g.drawImage(object.getCurrentAnimationImage(), object.getCoordinates().x - levelIDx * 1000, object.getCoordinates().y - levelIDy * 500, null);
				g.drawImage(object.getHealthBarImage(), object.getCoordinates().x - levelIDx * 1000, object.getCoordinates().y - levelIDy * 500 - 16, null);
				object.checkNextScene();
			}
		}
		
		//Draw spells if fired
		if(isFlyingQ){
			g.drawImage(character.q.getCurrentAnimationImage(), character.q.getCoordinates().x - levelIDx * 1000, character.q.getCoordinates().y - levelIDy * 500, null);
			character.q.checkNextScene();
		}
		if(isFlyingW){
			g.drawImage(character.w.getCurrentAnimationImage(), character.w.getCoordinates().x - levelIDx * 1000, character.w.getCoordinates().y - levelIDy * 500, null);
			character.w.checkNextScene();
		}
		if(isFlyingE){
			g.drawImage(character.e.getCurrentAnimationImage(), character.e.getCoordinates().x - levelIDx * 1000, character.e.getCoordinates().y - levelIDy * 500, null);
			character.e.checkNextScene();
		}
		if(isFlyingR){
			g.drawImage(character.r.getCurrentAnimationImage(), character.r.getCoordinates().x - levelIDx * 1000, character.r.getCoordinates().y - levelIDy * 500, null);
			character.r.checkNextScene();
		}
		
		
		drawCooldowns(g);
		
		g.setColor(Color.BLACK);
		//Draw current FPS
		int length = String.valueOf(Main.getCurrentFPS()).length();
		length *= 8;
		g.drawString(Main.getCurrentFPS(), 1000 - length, 12);
		
		length = String.valueOf(Main.getCurrentTPS()).length();
		length *= 8;
		g.drawString(Main.getCurrentTPS(), 1000 - length, 24);
		g.dispose();
	}
	
	//Cooldown: Tekent de cooldowns op het scherm.
	//return: void; arg1: Graphic g;
	//argument g als workaround:
	//Zou g een private member mogen worden van GamePanel?
	public void drawCooldowns(Graphics g){
		final int SPELL_BOX_X = 375;
		final int SPELL_BOX_Y = 465;
		final int SPELL_BOX_W = 250;
		final int SPELL_BOX_H = 35;
		
		//Draw spellbox
		g.setColor(Color.GREEN);
		g.fillRect(SPELL_BOX_X, SPELL_BOX_Y, SPELL_BOX_W, SPELL_BOX_H);
				
		Spell spells[] = {character.q , character.w, character.e, character.r};
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
	//Images are loaded
	public void loadPics(){
		ClassLoader cldr = this.getClass().getClassLoader();
		air = new ImageIcon(cldr.getResource("img/landscape/" + theme + "/air.png")).getImage();
		ground = new ImageIcon(cldr.getResource("img/landscape/" + theme + "/ground.png")).getImage();
		grass_ground = new ImageIcon(cldr.getResource("img/landscape/" + theme + "/grass-ground.png")).getImage();
	}
	
	//Called when a key is pressed
	public void keyPressed(KeyEvent e){
		keys[e.getKeyCode()] = true;	
	}
	
	//Called when a key is released 
	public void keyReleased(KeyEvent e){
		keys[e.getKeyCode()] = false;
	}
	
	//Is called when the ASCII-character for that particular button is send to the computer
	public void keyTyped(KeyEvent e){
		
	}
	
	/*	The update method contains:
	 * 		- Movement left and right
	 * 		- Gravity check
	 * 		- Checking if the location where the character is travelling is valid
	 * 	
	 */
	private boolean jumping;
	private int jumpStartTick;
	private final int JUMP_LENGTH_TICK = 20;
	
	public void update(){
		//Check collision with navmesh instead of reading array
		tick++;

		//Variables to check for animations
		boolean moveLeft = false, moveRight = false, moveUp = false, moveDown = false;
		
		//Check left and right movement
		if(keys[KeyEvent.VK_LEFT]){
			moveLeft = character.moveLeft(navMesh);
			movedLeft = true;
		}
		if(keys[KeyEvent.VK_RIGHT]){
			moveRight = character.moveRight(navMesh);
			movedLeft = false;
		}

		if(keys[KeyEvent.VK_UP]){
			if(!jumping){
				//If character can move down, it is not possible to jump, and vice versa
				jumping = !character.moveDown(navMesh);
				
				//If jumping = false, the character moved down, so we need to move him up again
				//Otherwise, we start the jump by setting jumpStartTick and setting initial yspeed
				if(!jumping){
					character.moveUp(navMesh);
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
				moveUp = character.moveUp(navMesh);
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
			moveDown = character.moveDown(navMesh);
			if(moveDown){
				character.accelerate(1);
			}else{
				character.resetSpeed();
			}
		}
		
		//Check if levelID should change
		Coordinate characterCoordinate = character.getCoordinates();
		if(characterCoordinate.y + character.getHeight()/2 < levelIDy * 500){
			levelIDy--;
		}else if(characterCoordinate.y - character.getHeight()/2 > (levelIDy + 1) * 500){
			levelIDy++;
		}
		
		if(characterCoordinate.x + character.getWidth()/2 < levelIDx * 1000){
			levelIDx--;
		}else if(characterCoordinate.x + character.getWidth()/2 > (levelIDx + 1) * 1000){
			levelIDx++;
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
		
		updateGameObjects();
		
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
		
		for(GameObject object : onScreen){
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
	
	//Update GameObject[] onScreen;
	public void updateGameObjects(){
		int i = 1;
		onScreen = new GameObject[objectCap];
		onScreen[0] = character;
		for(GameObject object : inLevel){
			if(object != null){
				if(object.getCoordinates().x >= levelIDx * 1000 && object.getCoordinates().x < (levelIDx + 1) * 1000 && object.getCoordinates().y >= levelIDy * 500 && object.getCoordinates().y < (levelIDy + 1) * 500){
					//Is in the level currenty on screen
					onScreen[i] = object;
					i++;
				}
			}
		}
	}
	
	/*Levels:
	 * 0. Air
	 * 1. Ground
	 * 2. Grass
	 */
	//Create levels
	public void createLevels(){
		levelOne = new int[][]{	{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{2,2,2,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,1,1,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,1,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,1,1,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,2,2,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,2,2,2,2,2,2,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,1,2,2,2,2,2,2,2,2,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,1,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,1,1,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,2,2,2,2,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{1,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
								{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}};		
	}
	
	//Getter for String characterName
	public String getCharacterName(){
		return characterName;
	}
	
	//Getter for character
	public Champion getCharacter(){
		return character;
	}
	
	public int[][] getCurrentLevel(){
		return currentLevel;
	}
	
	public int[] getLevelIDs(){
		return new int[]{levelIDx, levelIDy};
	}
	
	public GameObject[] getOnScreenObjects(){
		return onScreen;
	}

	public NavMesh getNavMesh() {
		return navMesh;
	}
}
