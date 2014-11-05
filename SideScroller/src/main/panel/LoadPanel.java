package main.panel

public LoadPanel extends JPanel{
    
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
        partsToLoad = parts;
        
        //Setting bar properties
        barWidth = ;
        barHeight = ;
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
        barPart = bar.getSubimage(0, 0, 1, barHeight); 
    }
    
    public void setNextLoadPart(String description){
        //Set new description for this loadpart
        loadingLabel.setText(description);
        
        //Remove old description and bar
        this.remove(loadingLabel);
        this.remove(barPart);
        this.revalidate;
        
        //One less part to load since we do 'setNextLoadPart()'
        loadedParts++;
        barPart = bar.getSubimage(0, 0, barPartWidth * loadedParts, barHeight);
    }

}