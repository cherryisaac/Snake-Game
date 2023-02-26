My Snake-Game - Freelance Java Project
by Isaac Cherry

How this game came to be...completed: I started following this YouTube tutorial on creating a Snake Game in Java by 
Bro Code (https://www.youtube.com/watch?v=bI6e6qjJ8JQ). By the end of it though I was happy with the outcome there was 
no way to pause the game. So I spent about a week or so trying to get the actual pause function to match in tandem with 
the "Pause" text overlay. After I finally did that I was so happy to have something of my own created in a game it made 
me want to add more to it. So I wrote a README.md on what I wanted to develop. Fast-forward about a few months later I 
felt inspired again to tackle this and yeah. My first passion project in coding.

How to play the game: In order to play this game currently, you need to install a Java (https://www.java.com/en/download/)
to open a Java archive (jar) file. 1. Once you have downloaded and installed Java double-click the file, and it should 
open. If not right-click the file and navigate to the installed java folder and go to the "bin" folder and open it with 
a file that says something like javac.exe or javaw.exe. 2. You can also run this file through the terminal or command 
line by typing "cd " then drag the folder containing the jar file to the command line and press enter. After type 
"java -jar jarfilename.jar" to execute the file.

The goal of the player:
- To collect as many apples as possible

Latest update 2/26/23:
-Implemented High score tracker which keep track of the player's high score and what difficulty they were 
playing on when they got the high score

Update 2/6/23:
- Added cardboard box which allows the snake to get apples at a further range when it eats a certain amount of apples
  (depending upon difficulty)

Update 2/7/23:
- Added difficulty modes Easy, Normal, Hard, Insane

Update 1/29/23 - 1/30/23:
- Added Options Menu with choices to change speed of snake and color (and more!)
- Added sound effects and music

Functionality:
- The arrows control the snake
- The space bar pauses the game
- The apple will appear randomly each time the snake eats it
- The snake grows longer as it eats more apples
- There is a scoreboard at the top left that lets you know how many apples you've collected in total so far
- When the snake goes out of bounds or runs out of time (dependent on difficulty) or runs into itself Game Over text/a 
game over sequence will be shown to the user
- If a certain amount of apples has been eaten (depending on the difficulty) then the shift key allows Snake to use a 
cardboard box to increase the range of how far it can get apples. Increases range at the cost of a higher likelihood of
running into itself.
- There is a sound prompt that lets the player know that the box is now usable
- The option to restart or return to the main menu when player goes to the game over screen 
(Enter key = Retry button, Shift key = Main Menu button)
- Settings menu allows the player to change the snake's color, snake's speed, grid color, bg color, toggle music, choose
difficulty
- Pressing the shift key in the main menu allows the player to view the high score and the difficulty each player was 
playing on when they got those scores

Bugs:
- If the retry & main menu buttons are both clicked within seconds of each other than the buttons will disappear leaving
the player stuck at the Game Over screen
- Timer indicator does not resume where it left off after game unpauses on Hard mode

Future features I would like to implement:
- Time attack mode where the snake collects as many apples in a given time frame (2 or 5 or 10 min)
- BONUS: Maybe add an intro sequence to the game (not likely, but interested in the concept)

TODOs:
- Set up time mode settings
- Fix High score menu does not close when the shift button is pressed again
- Fix high score prompt position after a player gets a new high score
- Fix player's difficulty being overwritten when the player changes difficulties and gets a new high score
- If the player gets 100 or more apples than a victory sequence is triggered
- Set the cardboard box mechanic as an unlockable for getting 75 or more apples on Normal difficulty, 50 or more on 
Hard mode or 35 or more on Insane mode.
- Set Hard mode & Insane mode as unlockables for getting 100 or more apples on Easy or 50 or more apples on Normal mode
- Figure out how to publish the finished product (game) so it's playable on all OS's without having to download extra 
resources/software. Ideally, maybe I can figure out a way to convert this into a web application or more likely I'll 
make a .exe file for windows and .dmg for mac.
