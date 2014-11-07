package main.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.Main;

public class MenuPanel extends JPanel{
	
	private GridBagConstraints c;
	private JButton startGame, options, quit;
	
	public MenuPanel(){
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		//Set c properties
		c.anchor = GridBagConstraints.WEST;
		
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
		
		//Set preferred size
		Dimension dim = new Dimension(1000, 500);
		setPreferredSize(dim);
	}

}
