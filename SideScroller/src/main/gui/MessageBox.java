package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;

public class MessageBox extends Panel{
	
	public static final int RETURN_TO_MAIN_MENU = 0, START_GAME = 1;
	
	private int action;
	
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
	
	
	public MessageBox(String message, int action){		
		this.action = action;
		
		this.setPreferredSize(new Dimension(1000, 500));
		this.setBackground(new Color(0, 0, 0, 80));
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.setPreferredSize(new Dimension(200, 150));
		
		innerPanel.add(new JLabel(message), BorderLayout.CENTER);
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				executeAction();
			}
		});
		innerPanel.add(okButton, BorderLayout.PAGE_END);
		
		this.add(innerPanel, c);
	}

	public void executeAction(){
		//Execute method
		if(action == RETURN_TO_MAIN_MENU){
			Main.returnToMainMenu();
		}else if(action == START_GAME){
			Main.startGame();
		}
	}

}
