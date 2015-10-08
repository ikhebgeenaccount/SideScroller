package main.game.level.levelimage;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LevelImageTest extends JFrame {
	
	public LevelImageTest(LevelImage LevelImage){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new ContentPanel(LevelImage));
		pack();
		setVisible(true);
	}
	
	private static class ContentPanel extends JPanel{
		
		private LevelImage LevelImage;
		
		ContentPanel(LevelImage LevelImage){
			 setPreferredSize(new Dimension(LevelImage.getWidth(), LevelImage.getHeight()));
			 this.LevelImage = LevelImage;
		}
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(LevelImage, 0, 0, null);
			g.dispose();
		}
	}

}
