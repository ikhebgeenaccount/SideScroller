package main.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class Panel extends JPanel{
	
	public GridBagConstraints c;
	public int[] xy;
	
	public Panel(){
		setLayout(new GridBagLayout());
		
		c = new GridBagConstraints();
		
		Dimension dim = new Dimension(1000, 500);
		setPreferredSize(dim);
	}
	
	//Add amount FillerLabels from start in y
	public int[] addFillerLabelsY(int amount, int startX, int startY){
		c.gridx = startX;
		for(c.gridy = startY; c.gridy <= amount; c.gridy++){
			add(new FillerLabel(), c);
		}	
		xy = new int[]{c.gridx, c.gridy};
		return xy;
	}
	
	//Add amount FillerLabels from start in x
	public int[] addFillerLabelsX(int amount, int startX, int startY){
		c.gridy = startY;
		for(c.gridx = startX; c.gridx <= amount; c.gridx++){
			add(new FillerLabel(), c);
		}
		xy = new int[]{c.gridx, c.gridy};
		return xy;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw default disclaimers
		g.drawString("Developed by ikhebgeenaccount", 0, 483);
		g.drawString("League of Legends is owned by Riot Games", 0, 497);
		
		//Draw default background image
	}

}
