package main.gui.panes;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import main.GUIEngine;

public class GamePane extends Pane {
	
	private GUIEngine gui;
	
	private Canvas canvas;
	
	public GamePane(GUIEngine gui, Canvas canvas){
		this.gui = gui;
		this.canvas = canvas;
		this.canvas.setWidth(1000);
		this.canvas.setHeight(500);
		this.setStyle("-fx-background-color:red");
		this.widthProperty().addListener(e -> {
			this.canvas.setWidth(this.getWidth());			
		});
		this.heightProperty().addListener(e -> {
			this.canvas.setHeight(this.getHeight());			
		});
		this.getChildren().add(canvas);
	}

}
