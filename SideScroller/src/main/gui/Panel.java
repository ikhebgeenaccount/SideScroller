package main.gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class Panel extends JPanel{
	
	public Panel(){
		setLayout(new GridBagLayout());
		
		Dimension dim = new Dimension(1000, 500);
		setPreferredSize(dim);
	}

}
