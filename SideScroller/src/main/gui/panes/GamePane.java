package main.gui.panes;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import main.GUIEngine;

public class GamePane extends Pane {
	
	private GUIEngine gui;
	
	public GamePane(GUIEngine gui, Canvas canvas){
		this.gui = gui;
		this.getChildren().add(canvas);
	}

}
