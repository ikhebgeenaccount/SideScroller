package main.gui.panel;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import main.gui.Panel;

public class LoadPanel extends Panel{
    
    //Numbers of total parts and parts loaded
    private int parts;
    private int loadedParts;
    
    //Layout
    private JLabel loadingLabel;
    private GridBagConstraints c;
    
    //Images for the loading screen
    private BufferedImage barOutline, barPart, bar;
    
    //Bar properties
    private int barPartWidth;
    private int barWidth;
    private int barHeight;
    private int barX;
    private int barY;
    
    public LoadPanel(int parts){
        this.parts = parts;
        loadedParts = 0;
        
        //Here we load the loading bar images and add them beneath the label
        //The outline of the loading bar
        //barOutline = 
        		
        //The full bar
        //bar = 
        		
        //Setting bar properties
        barWidth = bar.getWidth();
        barHeight = bar.getHeight();
        barPartWidth = barWidth/parts;
        barX = 500 - (barHeight / 2);
        barY = 250 - (barWidth /2);
        
        //Set layout
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        
        //Create label without text
        loadingLabel = new JLabel();
        add(loadingLabel, c);
        
        
        
        //The to-display part of the loading screen, starts at zero
        barPart = bar.getSubimage(0, 0, barWidth * loadedParts, barHeight); 
    }
    
    public void setNextLoadPart(String description){
        //Set new description for this loadpart
        loadingLabel.setText(description);
        
        //One more part loaded
        loadedParts++;
        barPart = bar.getSubimage(0, 0, barPartWidth * loadedParts, barHeight);
    }
    
    @Override
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	g.drawImage(barPart, barX, barY, null);
    }

}
