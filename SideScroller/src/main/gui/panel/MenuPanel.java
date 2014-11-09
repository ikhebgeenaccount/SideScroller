package main.gui.panel;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Main;
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
		
		xy = addFillerLabelsY(3, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		
		c.gridy++;
		
		//Create button for options
		options = new JButton("Options");
		add(options, c);
		
		c.gridy++;
		
		xy = addFillerLabelsY(3, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		
		c.gridy++;
		
		//Create button to quit
		quit = new JButton("Quit");
		add(quit, c);
		
		//Puts int labels FillerLabels in x-axis
		c.gridy = 0;
		xy = addFillerLabelsX(27, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw background image
	}
	
}
