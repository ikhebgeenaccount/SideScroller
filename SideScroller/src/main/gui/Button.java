package main.gui;

import java.awt.Color;

import javax.swing.JButton;

import main.Main;

/**Creates a JButton with default Font.
 * @author ikhebgeenaccount
 * @version 18 sep. 2015
 */
public class Button extends JButton {
	
	/**Creates a JButton with default Font and specified text.
	 * @param text The text to be displayed on the Button.
	 */
	public Button(String text){
		setText(text);
		setFont(Main.getFont(20));
		setBackground(new Color(59, 89, 182));
        setForeground(Color.WHITE);
        setFocusPainted(false);
	}

}
