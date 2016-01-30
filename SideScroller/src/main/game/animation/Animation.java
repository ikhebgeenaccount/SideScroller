package main.game.animation;

import javafx.scene.image.Image;

/**This class contains all methods and variables to create animations. This class only supports animations with frames that all have the same size.
 * @author ikhebgeenaccount
 * @version 17 sep. 2015
 */
public class Animation {
	
	private OneScene[] scenes;
	private int sceneOfAnimation;
	private int numberOfScenes;
	private int frameWidth, frameHeight;
	
	/* In the constructor we create an array for all the scenes that the animation will contain. Maximum of 30 scenes.
	 * We also set numberOfScenes to zero, because there are no scenes yet.
	 * And we set frameOfScene to zero, with reset().
	 */
	/**Default constructor. This constructor can be called, but it is recommended to use a more specified constructor since this constructor uses default values for all variables.
	 * 
	 */
	public Animation(){
		scenes = new OneScene[30];
		numberOfScenes = 0;
		reset();
		frameWidth = 50;
		frameHeight = 100;
	}
	
	/**Constructor for an animation. Note that all frames have to be the same size.
	 * @param frameWidth The width of a frame in pixels.
	 * @param frameHeight The height of a frame in pixels.
	 */
	public Animation(int frameWidth, int frameHeight){
		scenes = new OneScene[30];
		numberOfScenes = 0;
		reset();
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
	}
	
	//This method is called when the Animation should start. First, it's resetted, so it begins at the start. Then the first
	//scene is started.
	/**Starts an animation from its first frame.
	 * 
	 */
	public void start(){
		reset();
		scenes[sceneOfAnimation].startScene();
	}
	
	//This resets the animation.
	/**Resets an animation to its first frame.
	 * 
	 */
	public void reset(){
		sceneOfAnimation = 0;
	}
	
	//Method to add scenes to the Animation.
	/**Add a scene, one frame, to an Animation.
	 * @param sceneImage The image that the frame consists of
	 * @param sceneLength The length of the scene in milliseconds
	 */
	public void addScene(Image sceneImage, long sceneLength){
		scenes[numberOfScenes] = new OneScene(sceneImage, sceneLength);
		//Set numberOfScenes one higher to add the next scene one further in the scenes[] array
		numberOfScenes++;
	}
	
	//This checks if the next scene has to be started, and if so, starts next scene.
	//If the next scene does not exist it resets the animation
	/**This method checks if the next scene should be started. If so, does so. Otherwise, it does nothing. When it played the last scene of an animation, it resets the animation.
	 * 
	 */
	public void nextScene(){
		if(System.currentTimeMillis() - scenes[sceneOfAnimation].getStartTime() >= scenes[sceneOfAnimation].getLength()){
			sceneOfAnimation++;			
			if(sceneOfAnimation > 29){
				reset();
			}else if(sceneOfAnimation > numberOfScenes - 1){
				reset();
			}
			scenes[sceneOfAnimation].startScene();
		}
	}
	
	//This is the method used to get the image that has to be displayed
	/**Returns the image corresponding to the scene that currently plays.
	 * @return Image currentSceneImage
	 */
	public Image getCurrentSceneImage(){
		return scenes[sceneOfAnimation].getImage();
	}
	
	/**Returns the width of a frame in pixels.
	 * @return int frameWidth
	 */
	public int getFrameWidth(){
		return frameWidth;
	}
	
	/**Returns the height of a frame in pixels.
	 * @return int frameHeight
	 */
	public int getFrameHeight(){
		return frameHeight;
	}
	
	//This class holds information about one scene of the animation. That information is: the image, the length in frames and the
	//start of the animation in frames
	private class OneScene{
		private Image sceneImage;
		private long sceneLength;
		private long startTime;
		
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
		public void startScene(){
			this.startTime = System.currentTimeMillis();
		}
		
		public long getStartTime(){
			return startTime;
		}
	}
}
