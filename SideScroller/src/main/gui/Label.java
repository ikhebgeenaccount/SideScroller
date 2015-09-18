package main.gui;

import javax.swing.JLabel;

import main.Main;

/**Creates a JLabel with default Font.
 * @author ikhebgeenaccount
 * @version 18 sep. 2015
 */
public class Label extends JLabel {
	
	/**Creates a JLabel with default Font displaying text.
	 * @param text The text to be displayed.
	 * @param size The size of the Font.
	 */
	public Label(String text, int size){
		setText(text);
		setFont(Main.getFont(size));
	}

}
