package main.gui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Main;
import main.gui.Label;
import main.gui.Panel;

public class MenuPanel extends Panel{
	
	private Button startGame, options, quit;
	private Label createdBy, ownedBy;
	
	public MenuPanel(){
		
		//Set c properties
		c.anchor = GridBagConstraints.NORTHWEST;
		
		//Start grid
		c.gridx = 0;
		c.gridy = 0;
		
		//Puts 27 FillerLabels in x-axis
		xy = addFillerLabelsX(29, c.gridx, c.gridy);
		c.gridx = 0;
		c.gridy = xy[1];
		
		xy = addFillerLabelsY(5, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		
		//Create button to start game
		startGame = new Button("Start game");
		startGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Main.startGame();
			}
		});
		add(startGame, c);
		
		c.gridy++;
		
		xy = addFillerLabelsY(6, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		
		c.gridy++;
		
		//Create button for options
		options = new Button("Options");
		add(options, c);
		
		c.gridy++;
		
		xy = addFillerLabelsY(6, c.gridx, c.gridy);
		
		c.gridx = xy[0];
		c.gridy = xy[1];
		
		c.gridy++;
		
		//Create button to quit
		quit = new Button("Quit");
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Main.quitGame();
			}
		});
		add(quit, c);
		
		c.gridy++;
		
		xy = addFillerLabelsY(4, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		
		c.gridy++;
		c.gridwidth = 10;
		c.anchor = GridBagConstraints.SOUTHWEST;
		
		createdBy = new Label("Developed by ikhebgeenaccount", 20);
		createdBy.setForeground(Color.WHITE);
		add(createdBy, c);
		
		c.gridy++;
		
		ownedBy = new Label("League of Legends is owned by Riot Games", 20);
		ownedBy.setForeground(Color.WHITE);
		add(ownedBy, c);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw background image
	}
	
}
