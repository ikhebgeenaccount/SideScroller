package main.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import main.Main;
import main.gui.Label;
import main.gui.Panel;

public class OptionPanel extends Panel{
	
	//GUI elements
	private Label title, fps, fpsCap;
	private JTextField fpsTextBox;
	private Button fpsButton, saveButton;
	
	private int[] xy;
	
	public OptionPanel(){
		
		//Set layout properties
		c.anchor = GridBagConstraints.NORTHWEST;
		
		xy = addFillerLabelsX(30, 0, 0);
		
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 2;		
		
		//Title
		title = new Label("Options", 32);
		title.setForeground(Color.WHITE);
		add(title, c);
		
		c.gridwidth = 1;
		c.gridy++;
		
		xy = addFillerLabelsY(2, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		c.gridy++;
		
		//FPS text
		fps = new Label("Max FPS: ", 20);
		fps.setForeground(Color.WHITE);
		add(fps, c);
		
		c.gridx++;
		
		fpsTextBox = new JTextField();
		fpsTextBox.setText(String.valueOF(Main.getMaxFPS()));
		fpsTextBox.setSize(50, fps.getHeight());
		add(fpsTextBox, c);
		
		c.gridx--;
		c.gridy++;
		
		fpsCap = new Label("FPS cap: ", 20);
		fpsCap.setForeground(Color.WHITE);
		add(fpsCap, c);
		
		c.gridx++;
		
		fpsButton = new Button(String.valueOf(Main.getFPSCap()));
		fpsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(fpsButton.getText().contains("true")){
					fpsButton.setText("false");
				}else{
					fpsButton.setText("true");
				}
			}
		});
		add(fpsButton, c);
		
		c.gridx--;
		c.gridy++;
		
		xy = addFillerLabelsY(3, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		c.gridy++;
		
		saveButton = new Button("Save");
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Save settings
			}
		});
		add(saveButton, c);
		
		addFillerLabelsY(9, c.gridx, c.gridy);
		
	}

}
