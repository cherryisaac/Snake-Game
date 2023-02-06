My Snake-Game - Freelance Java Project
by Isaac Cherry

The goal of the player:
- To collect as many apples as possible

Latest Update 1/29/23 - 1/30/23:
- Added Options Menu with choices to change speed of snake and color (and more!)
- Added sound effects and music

Functionality:
- The arrows control the snake
- The space bar pauses the game
- The apple will appear randomly each time the snake eats it
- The snake grows longer as it eats more apples
- There is a scoreboard at the top left that lets you know how many apples you've collected in total so far
- When the snake goes out of bounds or runs into itself game over text will be shown to the user
- The option to restart or return to the main menu when player goes to the game screen
- Settings menu allows the player to change the snake's color, snake's speed, grid color, bg color, and toggle music

Bugs:
- Music does not loop...sometimes
- "Pause" word can be triggered with pause button after game over screen
- Snake's speed in the settings works the opposite as intended (the higher the number then the slower the snake moves)
- If retry & main menu buttons are both clicked within seconds of each other than the buttons will disappear

Future features I would like to implement:
- A high score tracker to keep track of the highest score so far or of the top 5 highest scores made for each mode
- Select difficulty between Easy, Medium, and Hard
- With easy mode the snake can go out of bounds and spawns on the opposite side, but can still fail if it runs into it's self
- With medium mode the user can lose if the snake goes out of bounds, or it runs into itself
- With hard mode I would like the same as medium, but with a few obstacles that have to be avoided
- Time attack mode where the snake collects as many apples in a given time frame (2 or 5 min)

What I learned from this project:
- How to utilize Java Swing API
- How to create a Jar file with resources (images & music included)
- Converting a Jar file to an exe application
