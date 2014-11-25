package main.gui.panel;

public class StatusBar extends JPanel{
	
	private Image qIcon, wIcon, eIcon, rIcon;
	
	public StatusBar(){
		setPreferredSize(new Dimension(1000, 50));
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
	}
}
