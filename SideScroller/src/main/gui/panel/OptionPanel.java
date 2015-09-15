package main.gui.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

import main.Main;
import main.gui.Button;
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
		c.anchor = GridBagConstraints.WEST;
		
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 2;		
		
		//Title
		title = new Label("Options", 32);
		title.setForeground(Color.WHITE);
		contentPanel.add(title, c);
		
		c.gridwidth = 1;
		c.gridy++;
		
		xy = addFillerLabelsY(2, c.gridx, c.gridy);
		c.gridx = xy[0];
		c.gridy = xy[1];
		c.gridy++;
		
		//FPS text
		fps = new Label("Max FPS: ", 20);
		fps.setForeground(Color.WHITE);
		contentPanel.add(fps, c);
		
		c.gridx++;
		
		fpsTextBox = new JTextField();
		fpsTextBox.setHorizontalAlignment(JTextField.RIGHT);
		fpsTextBox.setText(String.valueOf(Main.getMaxFPS()));
		fpsTextBox.setColumns(3);
		contentPanel.add(fpsTextBox, c);
		
		c.gridx--;
		c.gridy++;
		
		fpsCap = new Label("FPS cap: ", 20);
		fpsCap.setForeground(Color.WHITE);
		contentPanel.add(fpsCap, c);
		
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
		contentPanel.add(fpsButton, c);
		
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
				Main.setMaxFPS(Integer.parseInt(fpsTextBox.getText()));
				Main.setFPSCap(Boolean.parseBoolean(fpsButton.getText()));
				Main.setPanel(Main.getMenu());
			}
		});
		contentPanel.add(saveButton, c);
		
		addFillerLabelsY(9, c.gridx, c.gridy);
		
	}

}
