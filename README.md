#SideScroller

SideScroller aims to create a League of Legends themed 2D platformer, including champions with abilities, minions and jungle monsters. 

SideScroller isn’t endorsed by Riot Games and doesn’t reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc. 

###Download
[Download the latest version.](https://github.com/ikhebgeenaccount/SideScroller/releases)  
You need to have [Java 8](http://www.java.com) installed to play this game!

When experiencing bugs or game freezes, download **runlolsidescroller.bat** in the same folder as **LoLSidescroller.jar** and run the **.bat**. When you are given errors or experiencing bugs please report them to me on [reddit](http://www.reddit.com/u/ikhebgeenaccount) or via GitHub. Be sure to check the [known issues](https://github.com/ikhebgeenaccount/SideScroller/blob/master/BUGS.md) before reporting any bugs!

##Contributing
Would you like to help me with this project, or have a great idea?
I am currently looking for artists, to create animations and environments.
PM [/u/ikhebgeenaccount](http://www.reddit.com/u/ikhebgeenaccount) or if you have no reddit, add me ingame on EUW: ikhebgeenaccount.

##Planned features:
  - Levels with ending boss fights against Baron, Dragon, etc.
  - Passive and abilities from champions created in a 2D platformer
  - Great graphics 

##Changelog
  - *v0.5.3*
    - Ezreal R and W now move through minions
	- Fixed a bug where an attacking minion was always attacking to the left
  - *v0.5.2* - **current release**
    - Fixed a bug that caused the game to crash
  - *v0.5.1*
    - Added minions
	- Added healthbars
	- Added a way to win
	- Added popups that display win condition, when you die, etc
	- Fixed a bug where casting Ezreal's E to the left extended its range
	- Fixed a bug where trying to teleport out of the level with Ezreal's E caused the game to crash
	- Fixed a rare bug where moving between levels caused the game to crash
	- Fixed a bug where spells were cast from the top left of the character, instead from the middle
	- Fixed a bug where Ezreal's R displayed the wrong animation when fired to the left
  - *v0.5.0*
    - Completely rewritten the way collisions and gravity are detected:
      - Added acceleration and deceleration when falling and jumping
      - Fixed a bug where you could move out of the level
      - Fixed a bug where you could move out of the level and float in the air
      - Fixed a bug where you could get stuck on the edge of a tile
      - Increased performance
      - Increased accuracy
	- Added shrinking bar to display cooldowns
	- Fixed several issues with Ezreal's E
  - *v0.4.0*
    - Added statusbar with spellcooldowns
    - Added grass
    - Fixed a bug where you could not start the game again after returning to the menu once
    - Fixed a bug where the current FPS wasn't completely visible when >= 100
    - Fixed a bug where the character could teleport into ground with Ezreals E
    - Changed color of ground
    - Changed the way levels are loaded, which gives more customizability
  - *v0.3.0*
    - Added spells
    - Added spell animations
    - Added casting animations
    - ~~Added a statusbar with spellicons and cooldowns~~
    - Removed loading screen
  - *v0.2.3*
    - Added 'death point' to jump, character will not fall nor jump in this period
    - Fixed a bug where the game would freeze when trying to move outside of the level
    - Fixed a rare bug where the game would freeze
  - *v0.2.2*
    - Added jump animation
    - Added fall animation
    - Added background image
    - Added font
    - Added title in main menu
    - Added version in main menu
    - Added option menu
    - Fixed menu aligning
    - Fixed config file not working
    - Fixed bug where the character occasionally could jump on air
    - Fixed movement between levels
  - *v0.2.1*
    - Added next levels in y-axis
    - Fixed a bug where the character would detect collision from the level before current level
    - Fixed a bug where the character wasn't able to jump in next level
    - Fixed next level, now you are able to walk back to previous level
    - Fixed sloppy movement between levels
  - *v0.2.0*
    - Added animations
    - Added menu
    - Added loading screen
    - Added character selection screen
    - Added max FPS option, can also be set on uncapped
    - Added current FPS counter
    - Added config file
    - Added next level
    - Fixed sloppy jumping mechanism
    - Fixed non-fluid movement, now fluid
    - Fixed bug where the character wouldn't fall when jumping against a platform
    - Fixed bug where the character couldn't move when jumping against the top of the screen
  - *v0.1.1*
    - Fixed images not working
  - *v0.1.0*
    - Added graphics
    - Added gravity
    - Added movement
