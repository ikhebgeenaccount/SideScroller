package main.gui;

import javax.swing.JLabel;

import main.Main;

public class Label extends JLabel {
	
	public Label(String text, int size){
		setText(text);
		setFont(Main.getFont(size));
	}

}
