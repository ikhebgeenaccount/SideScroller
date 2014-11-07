package main.gui.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Main;
import main.gui.FillerLabel;
import main.gui.Panel;

public class MenuPanel extends Panel{
	
	private GridBagConstraints c;
	private JButton startGame, options, quit;
	
	public MenuPanel(){
		c = new GridBagConstraints();
		
		//Set c properties
		c.anchor = GridBagConstraints.NORTHWEST;
		
		//Start grid
		c.gridx = 0;
		c.gridy = 0;
		
		//Create button to start game
		startGame = new JButton("Start game");
		startGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Main.startGame();
			}
		});
		add(startGame, c);
		
		c.gridy++;
		
		add(new FillerLabel(), c);
		
		c.gridy++;
		
		//Create button for options
		options = new JButton("Options");
		add(options, c);
		
		c.gridy++;
		
		add(new FillerLabel(), c);
		
		c.gridy++;
		
		//Create button to quit
		quit = new JButton("Quit");
		add(quit, c);
		
		//Puts int labels FillerLabels in x-axis
		int labels = 5;
		c.gridy = 0;
		for(c.gridx = 1; c.gridx <= labels; c.gridx++){
			add(new FillerLabel(), c);
		}
	}

}
