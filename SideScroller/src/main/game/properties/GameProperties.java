package main.game.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import main.Main;

public class GameProperties {
	
	private static int maxFPS, ticksPS;
	private static boolean fpsCap;
	private static String menuTheme;
	
	/**Reads game properties from /resources/config/config.properties.
	 * 
	 */
	public static void loadProperties(){
		try {
			Properties properties = new Properties();
			InputStream propertiesFile = Main.class.getClassLoader().getResourceAsStream("config/config.properties");
			properties.load(propertiesFile);
			
			//Get properties from config.properties file
			maxFPS = Integer.parseInt(properties.getProperty("FPS"));
			ticksPS = Integer.parseInt(properties.getProperty("TPS"));
			fpsCap = Boolean.parseBoolean(properties.getProperty("fpsCap"));
			menuTheme = properties.getProperty("menuTheme");
			
			propertiesFile.close();
		}catch(IOException e){
			e.printStackTrace();
			
			//Manual
			maxFPS = 45;
			fpsCap = true;
			menuTheme = "default";
			ticksPS = 45;
		}catch(NullPointerException e){
			e.printStackTrace();
			
			//Manual
			maxFPS = 45;
			fpsCap = true;
			menuTheme = "default";
			ticksPS = 45;
		}
	}

	/**
	 * @return the maxFPS
	 */
	public static int getMaxFPS() {
		return maxFPS;
	}

	/**
	 * @param maxFPS the maxFPS to set
	 */
	public static void setMaxFPS(int maxFPS) {
		GameProperties.maxFPS = maxFPS;
	}

	/**
	 * @return the ticksPS
	 */
	public static int getTicksPS() {
		return ticksPS;
	}

	/**
	 * @param ticksPS the ticksPS to set
	 */
	public static void setTicksPS(int ticksPS) {
		GameProperties.ticksPS = ticksPS;
	}

	/**
	 * @return the fpsCap
	 */
	public static boolean hasFpsCap() {
		return fpsCap;
	}

	/**
	 * @param fpsCap the fpsCap to set
	 */
	public static void setFpsCap(boolean fpsCap) {
		GameProperties.fpsCap = fpsCap;
	}

	/**
	 * @return the menuTheme
	 */
	public static String getMenuTheme() {
		return menuTheme;
	}

	/**
	 * @param menuTheme the menuTheme to set
	 */
	public static void setMenuTheme(String menuTheme) {
		GameProperties.menuTheme = menuTheme;
	}

}
