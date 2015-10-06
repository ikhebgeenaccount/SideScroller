package main.gui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Main;
import main.gui.Button;
import main.gui.Label;
import main.gui.MessageBox;
import main.gui.Panel;

public class MenuPanel extends Panel implements KeyListener{
	
	private Button startGame, levelEditor, options, quit;
	private Label createdBy, ownedBy, title, version;
	
	private boolean[] keys;
	
	public MenuPanel(){
		keys = new boolean[1000];
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
				MessageBox levelEditorScum = new MessageBox("This feature is 2 sp00ky 4 you!", MessageBox.RETURN_TO_MAIN_MENU);
				Main.setMessageBox(levelEditorScum);
				Main.setMenuTheme("spooky");
				reloadBackground();
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
		
		//Set this as keylistener and make it focusable so the keylistener works
		addKeyListener(this);
		setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw background image
	}

	public void keyPressed(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = true;
		
		if(keys[KeyEvent.VK_M] && keys[KeyEvent.VK_L] && keys[KeyEvent.VK_G]){
			Main.setMenuTheme("mlg");
			this.reloadBackground();
		}
		else if(keys[KeyEvent.VK_S] && keys[KeyEvent.VK_P] && keys[KeyEvent.VK_0] && keys[KeyEvent.VK_0] && keys[KeyEvent.VK_K] && keys[KeyEvent.VK_Y]){
			Main.setMenuTheme("spooky");
			this.reloadBackground();
		}
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
