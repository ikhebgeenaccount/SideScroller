package main.game.navmesh;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**This class contains a test for NavMesh. It creates a separate JFrame with the complete NavMesh in it for testing and debugging purposes.
 * @author ikhebgeenaccount
 * 17 sep. 2015
 */
public class NavMeshTest extends JFrame {
		
	/**Creates a JFrame with a NavMesh
	 * @param navMesh The NavMesh that is shown in the JFrame
	 */
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
