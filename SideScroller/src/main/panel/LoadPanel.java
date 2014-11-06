package main.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadPanel extends JPanel{
    
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
    
    public LoadPanel(int parts){
        this.parts = parts;
        loadedParts = 0;
        
        //Setting bar properties
        //barWidth = ;
        //barHeight = ;
        barPartWidth = barWidth/parts;
        
        //Set layout
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        
        //Create label without text
        loadingLabel = new JLabel();
        add(loadingLabel, c);
        
        //Here we load the loading bar images and add them beneath the label
        //The outline of the loading bar
        barOutline = 
        
        //The full bar
        bar = 
        
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

}
