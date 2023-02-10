My Snake-Game - Freelance Java Project
by Isaac Cherry

How this game came to be...completed: I started following this YouTube tutorial on creating a Snake Game in Java by 
Bro Code (https://www.youtube.com/watch?v=bI6e6qjJ8JQ). By the end of it though I was happy with the outcome there was 
no way to pause the game. So I spent about a week or so trying to get the actual pause function to match in tandem with 
the "Pause" text overlay. After I finally did that I was so happy to have something of my own created in a game it made 
me want to add more to it. So I wrote a README.md on what I wanted to develop. Fast-forward about a few months later I 
felt inspired again to tackle this and yeah...I'm just about done with this project. My first passion project in coding.

The goal of the player:
- To collect as many apples as possible

Latest Update 2/7/23:
- Added difficulty modes Easy, Normal, Hard, Insane

Latest Update 1/29/23 - 1/30/23:
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
- The option to restart or return to the main menu when player goes to the game over screen 
(Enter key = Retry button, Shift key = Main Menu button)
- Settings menu allows the player to change the snake's color, snake's speed, grid color, bg color, toggle music, choose
difficulty

Bugs:
- If the retry & main menu buttons are both clicked within seconds of each other than the buttons will disappear leaving
the player stuck at the Game Over screen

Future features I would like to implement:
- A high score tracker to keep track of the highest score so far or of the top 5 highest scores made for each mode
- Time attack mode where the snake collects as many apples in a given time frame (2 or 5 or 10 min)
- BONUS: Maybe add an intro sequence to the game (not likely, but interested in the concept)

TODOs: 
- Fix timer indicator not resuming where it left off after game unpauses on Hard mode
- Set up time mode settings
- Set up High Scores prompt after Game Over
- Set up High Score Menu selection in Main Menu
- Figure out how to publish the finished product (game) so it's playable on all OS's without having to download extra 
resources/software. Ideally, maybe I can figure out a way to put the game on a cloud server for the most accessibility.

What I learned from this project:
- How to utilize Java Swing API
- How to create a Jar file with resources (images & music included)
- Converting a Jar file to an exe application
