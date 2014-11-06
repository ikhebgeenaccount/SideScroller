package main.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class OptionPanel extends JPanel{
	
	private GridBagConstraints c;
	
	public OptionPanel(){
		setLayout(new GridBagLayout());
		
		//Set layout properties
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		
		
	}

}
