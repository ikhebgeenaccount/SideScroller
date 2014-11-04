package main.animation;

import java.awt.Image;

public class Animation {
	
	private OneScene[] scenes;
	private int frameOfAnimation;
	private int numberOfScenes;
	
	/* In the constructor we create an array for all the scenes that the animation will contain. Maximum of 30 scenes.
	 * We also set numberOfScenes to zero, because there are no scenes yet.
	 * And we set frameOfScene to zero, with reset().
	 */
	public Animation(){
		scenes = new OneScene[30];
		numberOfScenes = 0;
		reset();
	}
	
	//This method is called when the Animation should start. First, it's resetted, so it begins at the start. Then the first
	//scene is started.
	public void start(int startFrame){
		reset();
		scenes[frameOfAnimation].startScene(startFrame);
	}
	
	//This resets the animation.
	public void reset(){
		frameOfAnimation = 0;
	}
	
	//Method to add scenes to the Animation.
	public void addScene(Image sceneImage, int sceneLength){
		scenes[numberOfScenes] = new OneScene(sceneImage, sceneLength);
		System.out.println(sceneImage.hashCode());
		numberOfScenes++;
	}
	
	//This checks if the next scene has to be started, and if so, starts next scene.
	//If the next scene does not exist it resets the animation
	public void nextScene(int currentFrame){
		if(currentFrame - scenes[frameOfAnimation].getStartFrame() > scenes[frameOfAnimation].getLength()){
			frameOfAnimation++;			
			if(frameOfAnimation > 29){
				reset();
			}else if(scenes[frameOfAnimation].getImage() == null){
				reset();
			}
			scenes[frameOfAnimation].startScene(currentFrame);
		}
	}
	
	//This is the method used to get the image that has to be displayed
	public Image getCurrentSceneImage(){
		return scenes[frameOfAnimation].getImage();
	}
	
	//This class holds information about one scene of the animation. That information is: the image, the length in frames and the
	//start of the animation in frames
	private class OneScene{
		private Image sceneImage;
		private int sceneLength;
		private int startFrame;
		
		private OneScene(Image sceneImage, int sceneLength){
			this.sceneImage = sceneImage;
			System.out.println(sceneImage.hashCode());
			this.sceneLength = sceneLength;
		}
		
		public int getLength(){
			return sceneLength;
		}

		private Image getImage(){
			return sceneImage;
		}
		
		//Used to start the scene
		public void startScene(int startFrame){
			this.startFrame = startFrame;
		}
		
		public int getStartFrame(){
			return startFrame;
		}
	}
}
