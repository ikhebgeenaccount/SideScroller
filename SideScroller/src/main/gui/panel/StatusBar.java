package main.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.Main;
import main.game.object.champion.Champion;

public class StatusBar extends JPanel{
	
	private Image qIcon, wIcon, eIcon, rIcon;
	private Image qIconGrayed, wIconGrayed, eIconGrayed, rIconGrayed;
	
	public StatusBar(){
		setPreferredSize(new Dimension(200, 40));
		
		setBackground(Color.BLUE);
		
		//Load pics
		try{
			qIcon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "qIcon.png"));
			qIconGrayed = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "qIconGrayed.png"));
			wIcon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "wIcon.png"));
			wIconGrayed = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "wIconGrayed.png"));
			eIcon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "eIcon.png"));
			eIconGrayed = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "eIconGrayed.png"));
			rIcon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "rIcon.png"));
			rIconGrayed = ImageIO.read(getClass().getClassLoader().getResourceAsStream("img/char" + Main.getGamePanel().getCharacterName() + "rIconGrayed.png"));
		}catch(IOException e){
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		//Get cooldowns
		long qcd = Main.getGamePanel().getCharacter().getRemainingCooldown(Champion.Q);
		long wcd = Main.getGamePanel().getCharacter().getRemainingCooldown(Champion.W);
		long ecd = Main.getGamePanel().getCharacter().getRemainingCooldown(Champion.E);
		long rcd = Main.getGamePanel().getCharacter().getRemainingCooldown(Champion.R);
		/*
		//Draw spell icons
		if(qcd == 0){
			g.drawImage(qIcon, 385, 0, null);
		}else{
			g.drawImage(qIconGrayed, 385, 0, null);
		}
		if(wcd == 0){
			g.drawImage(wIcon, 385, 0, null);
		}else{
			g.drawImage(wIconGrayed, 385, 0, null);
		}
		if(ecd == 0){
			g.drawImage(eIcon, 385, 0, null);
		}else{
			g.drawImage(eIconGrayed, 385, 0, null);
		}
		if(rcd == 0){
			g.drawImage(rIcon, 385, 0, null);
		}else{
			g.drawImage(rIconGrayed, 385, 0, null);
		}*/
		
		//Draw remaining cooldowns
		g.drawString("Q: " + String.valueOf(qcd), 25, 5);
		g.drawString("W: " + String.valueOf(wcd), 75, 52);
		//g.drawString("E: " + String.valueOf(ecd), 125, 52);
		g.drawString("R: " + String.valueOf(rcd), 175, 52);
	}
}
