package main.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import main.Main;

/**
 * @author ikhebgeenaccount
 * @version 18 sep. 2015
 */
public class Panel extends JPanel{
	
	public GridBagConstraints c;
	public int[] xy;
	public Image background;
	public JPanel contentPanel;
	
	public Panel(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setOpaque(false);
		
		add(contentPanel);
		
		c = new GridBagConstraints();
		
		Dimension dim = new Dimension(1000, 500);
		setPreferredSize(dim);
		
		background = new ImageIcon(this.getClass().getClassLoader().getResource("img/background/" + Main.getMenuTheme() + "/background.png")).getImage();
	}
	
	//Add amount FillerLabels from start in y
	public int[] addFillerLabelsY(int amount, int startX, int startY){
		c.gridx = startX;
		for(c.gridy = startY; c.gridy - startY < amount; c.gridy++){
			contentPanel.add(new FillerLabel(), c);
		}	
		xy = new int[]{c.gridx, c.gridy};
		return xy;
	}
	
	//Add amount FillerLabels from start in x
	public int[] addFillerLabelsX(int amount, int startX, int startY){
		c.gridy = startY;
		for(c.gridx = startX; c.gridx <= amount; c.gridx++){
			contentPanel.add(new FillerLabel(), c);
		}
		xy = new int[]{c.gridx, c.gridy};
		return xy;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw default disclaimers
		//g.drawString("Developed by ikhebgeenaccount", 0, 483);
		//g.drawString("League of Legends is owned by Riot Games", 0, 497);
		
		//Draw default background image
		g.drawImage(background, 0, 0, null);
	}
	
	public void reloadBackground(){
		background = new ImageIcon(this.getClass().getClassLoader().getResource("img/background/" + Main.getMenuTheme() + "/background.png")).getImage();
		repaint();
	}

}
