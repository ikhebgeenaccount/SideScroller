package main.game.level;

import java.util.Properties;

import main.Main;
import main.game.coordinate.Coordinate;
import main.game.level.levelimage.LevelImage;
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
import main.game.object.minion.minions.MinionMinion;
import main.game.object.minion.minions.RedLizard;
import main.game.object.minion.minions.SiegeMinion;
import main.game.object.minion.minions.SuperMinion;

public class Level {
	
	//The GameObject[]s that are in this level and those that are onscreen. For checks we will only check onscreen.
	private GameObject[] inLevel, onScreen;
	private final int objectCap = 50; //Maximum number of objects in one level
	
	//Level properties
	private Coordinate startSquare;
	private Coordinate endSquare;
	private String theme;
	
	private LevelImage levelImage;
	
	private int offSetX, offSetY;
	private int maxOffSetX, maxOffSetY;
	
	private NavMesh navMesh;
	private int[][] levelMatrix = new int[][]{	{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{2,2,2,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{1,1,1,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,1,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,1,1,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,2,2,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,2,2,2,2,2,2,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
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
	
	public Level(Properties properties){		
		if(Main.getMenuTheme().equals("mlg")){
			theme = "mlg";
		}else{
			theme = properties.getProperty("theme");			
		}
		
		offSetX = 0;
		offSetY = 0;
		maxOffSetX = (levelMatrix[0].length - 20) * 50;
		maxOffSetY = (levelMatrix.length - 10) * 50;
		
		//Create GameObject[]s
		inLevel = new GameObject[objectCap];
		onScreen = new GameObject[objectCap];//Since inLevel has a cap of 50, onScreen doesn't need more than that
		
		inLevel[0] = Main.getCharacter();
		
		readProperties(properties);		
		
		navMesh = new NavMesh(levelMatrix);
		levelImage = new LevelImage(levelMatrix, theme);
	}
	
	private void readProperties(Properties properties){
		//Read properties of startSquare and endSquare, start and end of level
		String startSquareCfg = properties.getProperty("startsquare");
		startSquare = new Coordinate();
		startSquare.x = Integer.parseInt(startSquareCfg.split(",")[0]) * 50;
		startSquare.y = Integer.parseInt(startSquareCfg.split(",")[1]) * 50;
		
		endSquare = new Coordinate();
		String endSquareCfg = properties.getProperty("endsquare");
		endSquare.x = Integer.parseInt(endSquareCfg.split(",")[0]) * 50;
		endSquare.y = Integer.parseInt(endSquareCfg.split(",")[1]) * 50;
		
		initializeMinions(properties);
	}
	
	private void initializeMinions(Properties properties){	
		//Read minions, fill inLevel with corresponding type
		String minionsCfg = properties.getProperty("minions"); //minionsCfg: "type:x.y/x.y,type:x.y/x.y/x.y/x.y"
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
				case 10:for(int j = 0; j < type.length; j++){
							type[j] = new MinionMinion();
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
	}
	
	public Coordinate getStartCoordinates(){
		return startSquare;
	}
	
	public Coordinate getEndCoordinates(){
		return endSquare;
	}
	
	public GameObject[] getInLevelObjects(){
		return inLevel;
	}
	
	public GameObject[] getOnScreenObjects(){
		return onScreen;
	}
	
	public LevelImage getLevelImage(){
		return levelImage;
	}
	
	//Update GameObject[] onScreen
	public void updateGameObjects(){
		int i = 1;
		onScreen = new GameObject[objectCap];
		onScreen[0] = Main.getCharacter();
		for(GameObject object : inLevel){
			if(object != null && !(object instanceof Champion)){
				if(object.getCurrentHealth() != 0/* && object.getCoordinates().x >= offSetX && object.getCoordinates().x < offSetX + 1000 && object.getCoordinates().y >= offSetY && object.getCoordinates().y < offSetY + 500*/){
					//Is in the level currently on screen
					onScreen[i] = object;
					i++;
				}
			}
		}
	}
	
	public int getOffSetX(){
		return offSetX;
	}
	
	public void setOffSetX(int x){
		offSetX = x;
	}
	
	public int getOffSetY(){
		return offSetY;
	}
	
	public void setOffSetY(int y){
		offSetY = y;
	}
	
	public int getMaxOffSetX(){
		return maxOffSetX;
	}
	
	public int getMaxOffSetY(){
		return maxOffSetY;
	}
	
	public String getTheme(){
		return theme;
	}
	
	public NavMesh getNavMesh(){
		return navMesh;
	}
	
	public int getObjectCap(){
		return objectCap;
	}

}
