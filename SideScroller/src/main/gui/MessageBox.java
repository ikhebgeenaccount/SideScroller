package main.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageBox extends JPanel{
	
	/*
	 * 			.....................................
	 * 			.									.
	 * 			.			  <Message>				.
	 * 			.									.
	 * 			.									.
	 * 			.									.
	 * 			.									.
	 * 			.			   ......				.
	 * 			.			   . OK .				.
	 * 			.			   ......				.
	 * 			.....................................
	 */
	
	
	public MessageBox(String message/*, void methodToExecute*/){
		
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(200, 150));
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 0;
		c.gridx = 0;
		
		this.add(new JLabel(message), c);
		
		c.gridy++;
		
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Execute method
			}
		});
		
		this.add(okButton, c);
		
	}

}
