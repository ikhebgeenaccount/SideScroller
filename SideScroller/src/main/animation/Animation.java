package main.animation;

import java.awt.Image;

public class Animation {
	
	private OneScene[] scenes;
	private int frameOfScene;
	private int numberOfScenes;
	
	public Animation(){
		scenes = new OneScene[30];
		numberOfScenes = 0;
		reset();
	}
	
	public void reset(){
		frameOfScene = 0;
	}
	
	public void addScene(Image sceneImage, int sceneLength){
		scenes[numberOfScenes] = new OneScene(sceneImage, sceneLength);
		numberOfScenes++;
	}
	
	public void nextScene(){
		frameOfScene++;
	}
	
	public Image getCurrentSceneImage(){
		return scenes[frameOfScene].getImage();
	}
	
	public int getCurrentSceneLength(){
		return scenes[frameOfScene].getLength();
	}
	
	private class OneScene{
		private Image sceneImage;
		private int sceneLength;
		
		private OneScene(Image sceneImage, int sceneLength){
			this.sceneImage = sceneImage;
			this.sceneLength = sceneLength;
		}
		
		public int getLength(){
			return sceneLength;
		}

		private Image getImage(){
			return sceneImage;
		}
	}
}
