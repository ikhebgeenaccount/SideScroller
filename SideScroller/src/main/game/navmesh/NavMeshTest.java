package main.game.navmesh;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Main;

public class NavMeshTest extends JFrame {
		
	public NavMeshTest(NavMesh navMesh){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new ContentPanel(navMesh));
		pack();
		setVisible(true);
	}
	
	private static class ContentPanel extends JPanel{
		
		private NavMesh navMesh;
		
		ContentPanel(NavMesh navMesh){
			 setPreferredSize(new Dimension(navMesh.getWidth(), navMesh.getHeight()));
			 this.navMesh = navMesh;
		}
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(navMesh, 0, 0, null);
			g.dispose();
		}
	}

}
