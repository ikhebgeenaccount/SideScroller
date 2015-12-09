package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.game.engine.GameEngine;
import main.game.engine.handlers.FrameUpdater;
import main.game.engine.handlers.GameUpdater;
import main.game.object.champion.champions.alphaguy.AlphaGuy;
import main.game.properties.GameProperties;
import main.gui.panes.GamePane;
import main.gui.panes.MenuPane;

public class GUIEngine extends Application {
	
	private Stage window;
	
	private Scene scene;
	
	private MenuPane menuPane;
	//private OptionsPane optionsPane;
	
	private GamePane gamePane;
	private Canvas canvas;
	
	private static GameEngine engine;
	private static FrameUpdater frameUpdater;
	private static GameUpdater gameUpdater;

	@Override
	public void start(Stage stage) throws Exception {		
		this.canvas = new Canvas();
		this.invokeEngine();
		this.invokeScene();
		
		this.window = stage;
		this.window.setMinHeight(500);
		this.window.setMinWidth(1000);
		
		this.window.setOnCloseRequest(e ->{
			System.exit(0);
		});
		this.window.setTitle("League of Legends SideScroller");
		this.window.setScene(scene);
		this.window.show();
		
	}
	
	private void invokeEngine(){
		GameProperties.loadProperties();
		engine = new GameEngine(new AlphaGuy(), "alphaguy");
		frameUpdater = new FrameUpdater(engine, canvas);
		gameUpdater = new GameUpdater(engine);
	}
	
	private void invokeScene(){		
		//Set MenuPane as starting Pane in center
		this.menuPane = new MenuPane(this);
		this.menuPane.setPrefSize(1000, 500);
		this.gamePane = new GamePane(this, canvas);
		this.gamePane.setPrefSize(1000, 500);
		
		this.scene = new Scene(menuPane);
	}
	
	public void showGame(){
		this.scene.setRoot(gamePane);
		this.window.sizeToScene();
		frameUpdater.start();
		gameUpdater.start();
	}
	
	public void startGame(){
		frameUpdater.start();
		gameUpdater.start();
	}
	
	public void pauseGame(){
		frameUpdater.stop();
		gameUpdater.stop();
	}
	
	public void startNewGame(){
		engine = new GameEngine(new AlphaGuy(), "alphaguy");
		frameUpdater.start();
		gameUpdater.start();
	}
	
	public void showMenu(){
		this.scene.setRoot(menuPane);
		this.window.sizeToScene();
	}
	
	public static void main(String[] args){
		launch(args);
	}
	
	public static GameEngine getGameEngine(){
		return engine;
	}

}
