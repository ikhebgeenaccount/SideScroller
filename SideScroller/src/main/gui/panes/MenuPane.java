package main.gui.panes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import main.GUIEngine;

public class MenuPane extends GridPane {
	
	private Text title, disclaimer;
	private Button startButton, optionsButton, creditsButton, quitButton;
	
	private GUIEngine gui;
	
	public MenuPane(GUIEngine gui){
		this.gui = gui;
		//this.setAlignment(Pos.CENTER);
		this.setGridLinesVisible(true);
		this.setStyle("-fx-background-color:red");
		
		startButton = new Button("Start " /*this motherfucking*/ + "game");
		startButton.setOnAction(e -> {
			gui.showGame();
		});
		startButton.setAlignment(Pos.TOP_CENTER);
		
		optionsButton = new Button("Options");
		optionsButton.setOnAction(e -> {
			
		});
		
		creditsButton = new Button("Credits");
		creditsButton.setOnAction(e -> {
			
		});
		
		quitButton = new Button("Exit");
		quitButton.setOnAction(e -> {
			
		});
		
		disclaimer = new Text("League of Legends is owned by Riot Games. This game is created by ikhebgeenaccount and is not endorsed by Riot Games.");
		
		
		this.add(startButton, 0, 0);
		this.add(optionsButton, 0, 1);
		this.add(creditsButton, 0, 2);
		this.add(quitButton, 0, 3);
		this.add(disclaimer, 0, 4);
	}

}
