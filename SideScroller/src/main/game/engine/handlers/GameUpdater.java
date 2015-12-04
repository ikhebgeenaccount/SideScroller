package main.game.engine.handlers;

import main.game.engine.GameEngine;

public class GameUpdater implements Runnable, Handler{
	
	private int maxTPS, currentTPS;
	
	private boolean running, tpsCap;
	
	private GameEngine game;
	
	private Thread thread;
	
	public GameUpdater(GameEngine game){
		this.game = game;
		this.thread = new Thread(this);
	}
	
	public void run(){
		long startTime;
		long frameTime = 1000/maxTPS;
		long startTimeFPS = System.currentTimeMillis();
		long endTime;
		while(running){
			//The startTime of this loop
			startTime = System.currentTimeMillis();
			game.update();
			endTime = System.currentTimeMillis();
			try{
				//If the time it took to paint this frame is bigger than the time set for one frame, it needs to instantly
				//repaint(), since it is behind on schedule
				if(endTime - startTime > frameTime){
					
				 //If the time it took to paint this frame is equal to the time set to paint one frame, it needs to 
				 //instantly repaint(), since it is perfect on schedule
				}else if(endTime - startTime == frameTime){
					
				 //If it took less time, we need to sleep the remaining millis of the loop time	
				}else if(tpsCap){
					Thread.sleep(frameTime - (endTime - startTime));
				}
				if(System.currentTimeMillis() - startTimeFPS >= 500){
					if(System.currentTimeMillis() - startTime == 0){
						currentTPS = maxTPS;
						startTimeFPS = System.currentTimeMillis();
					}else{
						currentTPS = (int)(1000 / (System.currentTimeMillis() - startTime));
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
	
	public void setMaxTPS(int tps){
		this.maxTPS = tps;
	}
	
	public void setTPSCap(boolean cap){
		this.tpsCap = cap;
	}

}
