package main.gui.panel;

public class StatusBar extends JPanel{
	
	private Image qIcon, wIcon, eIcon, rIcon;
	
	public StatusBar(){
		setPreferredSize(new Dimension(1000, 80));
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		
		//Load pics
		try{
			qIcon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "qIcon.png"));
			wIcon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "wIcon.png"));
			eIcon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "eIcon.png"));
			rIcon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "rIcon.png"));
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		//Draw spell icons
		g.drawImage(qIcon, 385, 0, null);
		g.drawImage(wIcon, 445, 0, null);
		g.drawImage(eIcon, 505, 0, null);
		g.drawImage(rIcon, 565, 0, null);
		
		//Draw remaining cooldowns
		
	}
}
