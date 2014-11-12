package main.gui.panel;

import javax.swing.JButton;

import main.Main;

public class Button extends JButton {
	
	public Button(String text){
		setText(text);
		setFont(Main.getFont(20));
	}

}
