package main.animation;

import java.awt.Image;

public class Animation {
	
	private OneScene[] scenes;
	private int sceneOfAnimation;
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
	public void start(long startTime){
		reset();
		scenes[sceneOfAnimation].startScene(startTime);
	}
	
	//This resets the animation.
	public void reset(){
		sceneOfAnimation = 0;
	}
	
	//Method to add scenes to the Animation.
	public void addScene(Image sceneImage, long sceneLength){
		scenes[numberOfScenes] = new OneScene(sceneImage, sceneLength);
		
		//Set numberOfScenes one higher to add the next scene one further in the scenes[] array
		numberOfScenes++;
	}
	
	//This checks if the next scene has to be started, and if so, starts next scene.
	//If the next scene does not exist it resets the animation
	public void nextScene(long currentTime){
		if(currentTime - scenes[sceneOfAnimation].getStartFrame() >= scenes[sceneOfAnimation].getLength()){
			sceneOfAnimation++;			
			if(sceneOfAnimation > 29){
				reset();
			}else if(scenes[sceneOfAnimation].getImage() == null){
				reset();
			}
			scenes[sceneOfAnimation].startScene(currentTime);
		}
	}
	
	//This is the method used to get the image that has to be displayed
	public Image getCurrentSceneImage(){
		return scenes[sceneOfAnimation].getImage();
	}
	
	//This class holds information about one scene of the animation. That information is: the image, the length in frames and the
	//start of the animation in frames
	private class OneScene{
		private Image sceneImage;
		private long sceneLength;
		private long startFrame;
		
		private OneScene(Image sceneImage, long sceneLength){
			this.sceneImage = sceneImage;
			this.sceneLength = sceneLength;
		}
		
		public long getLength(){
			return sceneLength;
		}

		private Image getImage(){
			return sceneImage;
		}
		
		//Used to start the scene
		public void startScene(long startFrame){
			this.startFrame = startFrame;
		}
		
		public long getStartFrame(){
			return startFrame;
		}
	}
}
