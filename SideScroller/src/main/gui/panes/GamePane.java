package main.gui.panes;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class GamePane extends Pane {
	
	public GamePane(Canvas canvas){
		this.getChildren().add(canvas);
	}

}
