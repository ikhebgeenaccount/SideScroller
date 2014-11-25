package main.gui.panel;

public class StatusBar extends JPanel{
	
	private Image qIcon, wIcon, eIcon, rIcon;
	
	public StatusBar(){
		setPreferredSize(new Dimension(1000, 80));
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
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
