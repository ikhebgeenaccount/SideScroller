package main.game.engine.handlers;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import main.game.engine.GameEngine;

public class FrameUpdater implements Runnable, Handler{
	
	private int maxFPS, currentFPS;
	
	private boolean running, fpsCap;
	
	private GameEngine game;
	private Canvas canvas;
	
	private Thread thread;
	
	public FrameUpdater(GameEngine game, Canvas canvas){
		this.game = game;
		this.canvas = canvas;
		thread = new Thread(this);
	}
	
	public void run(){
		long startTime;
		long frameTime = 1000/maxFPS;
		long startTimeFPS = System.currentTimeMillis();
		long endTime;
		while(running){
			//The startTime of this loop
			startTime = System.currentTimeMillis();
			java.awt.image.BufferedImage bufferedImage = game.getCurrentFrame((int)canvas.getWidth(), (int)canvas.getHeight());
			javafx.scene.image.Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			canvas.getGraphicsContext2D().drawImage(image, (double)0, (double)0);
			endTime = System.currentTimeMillis();
			try{
				//If the time it took to paint this frame is bigger than the time set for one frame, it needs to instantly
				//repaint(), since it is behind on schedule
				if(endTime - startTime > frameTime){
					
				 //If the time it took to paint this frame is equal to the time set to paint one frame, it needs to 
				 //instantly repaint(), since it is perfect on schedule
				}else if(endTime - startTime == frameTime){
					
				 //If it took less time, we need to sleep the remaining millis of the loop time	
				}else if(fpsCap){
					Thread.sleep(frameTime - (endTime - startTime));
				}
				if(System.currentTimeMillis() - startTimeFPS >= 500){
					if(System.currentTimeMillis() - startTime == 0){
						currentFPS = maxFPS;
						startTimeFPS = System.currentTimeMillis();
					}else{
						currentFPS = (int)(1000 / (System.currentTimeMillis() - startTime));
						startTimeFPS = System.currentTimeMillis();
					}
				}
									
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public void start(){
		thread.start();
	}
	
	public void stop(){
		thread.interrupt();
		thread = new Thread(this);
	}
	
	public void setMaxFPS(int fps){
		this.maxFPS = fps;
	}
	
	public void setFPSCap(boolean cap){
		this.fpsCap = cap;
	}

}
