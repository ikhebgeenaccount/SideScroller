package main.gui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Main;
import main.gui.Button;
import main.gui.Label;
import main.gui.MessageBox;
import main.gui.Panel;

public class MenuPanel extends Panel{
	
	private Button startGame, levelEditor, options, quit;
	private Label createdBy, ownedBy, title, version;
	
	public MenuPanel(){
		
		//Set c properties
		c.anchor = GridBagConstraints.NORTHWEST;
		
		//Start grid
		c.gridx = 0;
		c.gridy = 0;
		
		//Puts 27 FillerLabels in x-axis
		//xy = addFillerLabelsX(30, c.gridx, c.gridy);
		//c.gridx = 0;
		//c.gridy = xy[1];
		
		c.gridwidth = 15;
		title = new Label("League of Legends: SideScroller", 32);
		title.setForeground(Color.WHITE);
		contentPanel.add(title, c);
		
		c.gridy++;
		
		version = new Label(Main.getVersion(), 20);
		version.setForeground(Color.WHITE);
		contentPanel.add(version, c);
		
		c.gridy++;
		
		xy = addFillerLabelsY(4, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		
		//Create button to start game
		startGame = new Button("Start game");
		startGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				MessageBox startOfGame = new MessageBox("Go to the end of the level to win!", MessageBox.START_GAME);
				Main.setMessageBox(startOfGame);
			}
		});
		contentPanel.add(startGame, c);
		
		c.gridy++;
		
		xy = addFillerLabelsY(2, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		
		c.gridy++;
		
		levelEditor = new Button("Level editor");
		levelEditor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//Main.setPanel(Main.getLevelEditorPanel());			
			}			
		});
		contentPanel.add(levelEditor, c);
		
		c.gridy++;
		
		xy = addFillerLabelsY(2, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		
		c.gridy++;
		
		//Create button for options
		options = new Button("Options");
		options.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Main.setPanel(Main.getOptionPanel());
			}
		});
		contentPanel.add(options, c);
		
		c.gridy++;
		
		xy = addFillerLabelsY(2, c.gridx, c.gridy);
		
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
		contentPanel.add(quit, c);
		
		c.gridy++;
		
		xy = addFillerLabelsY(5, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		
		c.gridy++;
		c.gridwidth = 10;
		c.anchor = GridBagConstraints.SOUTHWEST;
		
		createdBy = new Label("Developed by ikhebgeenaccount", 20);
		createdBy.setForeground(Color.WHITE);
		contentPanel.add(createdBy, c);
		
		c.gridy++;
		
		ownedBy = new Label("League of Legends is owned by Riot Games", 20);
		ownedBy.setForeground(Color.WHITE);
		contentPanel.add(ownedBy, c);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw background image
	}
	
}
