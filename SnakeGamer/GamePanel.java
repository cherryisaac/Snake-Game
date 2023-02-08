package SnakeGamer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Random;
import java.util.TimerTask;


public class GamePanel extends JPanel implements ActionListener {
    HighScoreTracker highScoreTracker;
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    //How big do we want the objects in this game?
    static final int UNIT_SIZE = 20;
    //Calculates how many objects can fit into the game
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    final int [] X = new int[GAME_UNITS];
    final int [] Y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX, appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    boolean paused = false;
    JButton retryButton, mainMenuButton, continueButton;
    int alpha = 255;
    private Timer animationTimer;
    boolean gameOver = false;
    private OptionsMenu optionsMenu;
    private MusicSoundBoard musicSoundBoard;
    private ImageIcon backgroundImage;
    private ImageIcon gameOverThree = new ImageIcon(getClass().getClassLoader().getResource("Game-Over-Epic-MG.gif"));
    private ImageIcon gameOverNot = new ImageIcon(getClass().getClassLoader().getResource("Snake.....gif"));
    private ImageIcon mazeGameOver = new ImageIcon( getClass().getClassLoader().getResource("maze-game-over-loop-1.gif"));

    private boolean retryClicked = false;
    private boolean mainMenuClicked = false;
    private long startTime;
    private Timer hardTimer;
    private boolean timerZero = false;
    private Timer insaneTimer;
    int score;


    public GamePanel(){
        defaultPanel();
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource(setBackgroundImage()));
        switch (optionsMenu.getMusicChoice()) {
            case "on" -> musicSoundBoard.setMusicChoice();
            case "off" -> {}
        }
        startGame();
    }
    public void defaultPanel(){
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        random = new Random();
        optionsMenu = new OptionsMenu();
        musicSoundBoard = new MusicSoundBoard();
        musicSoundBoard.setMusicClip();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(optionsMenu.getGameSpeed(), this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        switch (optionsMenu.getBackgroundImages()){
            case "on" -> g.drawImage(backgroundImage.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
            case "off" -> setBackground(Color.black);
        }
        draw(g);
        if(paused){
            timer.stop();
            stopDifficultyTimer();
            g.setColor(new Color(0, 0, 0, 128));
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Paused", (SCREEN_WIDTH - metrics.stringWidth("Paused"))/2, SCREEN_HEIGHT/2);
        }
            paused = false;
    }

    public void draw(Graphics g){
        if(running){
            for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++){   //Adds grid lines
                g.drawLine(i*UNIT_SIZE,0, i*UNIT_SIZE, SCREEN_HEIGHT );
                g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
                    switch (optionsMenu.getGridColor()){ //Changes grid lines color
                    case "white" -> g.setColor(Color.white);
                    case "gray" -> g.setColor(Color.gray);
                    case "black" -> g.setColor(Color.black);
                }
            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if(i == 0){
                    //Maps corresponding options values to colors
                    switch (optionsMenu.getSnakeColor()) {
                        case "green" -> g.setColor(Color.green);
                        case "blue" -> g.setColor(Color.blue);
                        case "orange" -> g.setColor(Color.orange);
                        case "purple" -> g.setColor(Color.magenta);
                        case "cyan" -> g.setColor(Color.cyan);
                        case "yellow" -> g.setColor(Color.yellow);
                        case "pink" -> g.setColor(Color.pink);
                        case "random" -> g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    }
                } else {
                    switch (optionsMenu.getSnakeColor()){
                        case "green" -> g.setColor(new Color(45, 100, 0));
                        case "blue" -> g.setColor(new Color(0, 0, 100));
                        case "orange" -> g.setColor(new Color(100, 66, 0));
                        case "purple" -> g.setColor(new Color(100, 0, 100));
                        case "cyan" -> g.setColor(new Color(0, 100, 100));
                        case "yellow" -> g.setColor(new Color(100, 100, 0));
                        case "pink" -> g.setColor(new Color(100, 0, 45));
                        case "random"-> g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    }
                }
                g.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);
            }
            //Score
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH/5 - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

            //Hard mode timer
            Font font = new Font("Verdana", Font.BOLD, 18);
            g.setFont(font);
            g.setColor(Color.white);
            if(optionsMenu.getDifficultyChoice().equals( "Hard")){
                long elapsedTime = System.currentTimeMillis() - startTime;
                long timeRemaining = 7 * 1000 - elapsedTime;

                FontMetrics metrics2 = getFontMetrics(g.getFont());
                g.drawString("Time Remaining: " + timeRemaining / 1000 + " seconds", (SCREEN_WIDTH -
                        metrics2.stringWidth("Time Remaining: " + timeRemaining / 1000 + " seconds")), g.getFont().getSize());
            }
        } else{
            gameOver(g);
            musicSoundBoard.stopMusic();
            gameOverButtonsTimer();
        }
    }

    public void gameOverButtonsTimer(){
        //Delay the appearance of the buttons
        int delayTime;
        if(applesEaten < 10){
            delayTime = 5000; // 5 seconds
        } else if(applesEaten <= 39) {
            delayTime = 10000; // 10 seconds
        } else {
            delayTime = 19500; // 20 seconds
        }
        Timer delayTimer = new Timer(delayTime, e -> {
            retryButton.setBounds(230,325,150,50); //Position and size of buttons as they appear
            mainMenuButton.setBounds(230,375,150,50);
            retryButton.setVisible(true);
            mainMenuButton.setVisible(true);
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    public void newApple(){
        appleX = random.nextInt((SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        if(optionsMenu.getDifficultyChoice().equals("Hard")){
            startTime = System.currentTimeMillis();
            hardTimer = new Timer(7 * 1000, e -> {
                running = false;
            });
            hardTimer.start();
        } else if(optionsMenu.getDifficultyChoice().equals("Insane")){
            insaneTimer = new Timer(5 * 1000, e -> running = false);
            insaneTimer.start();
        }
    }

    public void move(){
        for (int i = bodyParts; i > 0 ; i--) {
            X[i] = X[i-1];
            Y[i] = Y[i-1];
        }
        switch (direction){
            case 'U'-> Y[0] = Y[0] - UNIT_SIZE;
            case 'D'-> Y[0] = Y[0] + UNIT_SIZE;
            case 'L'-> X[0] = X[0] - UNIT_SIZE;
            case 'R'-> X[0] = X[0] + UNIT_SIZE;
            case ' ' -> {
                X[0]= 0;
                Y[0]= 0;
            }
        }
    }

    public void checkApple(){
        if((X[0]==appleX) && (Y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            musicSoundBoard.setSound(getClass().getResource("/eating-sound-effect.wav"));
            if(optionsMenu.getDifficultyChoice().equals("Hard")){
                if (hardTimer != null && running) {
                    hardTimer.stop();
                }
            } else if (optionsMenu.getDifficultyChoice().equals("Insane")){
               if(insaneTimer != null && running){
                   insaneTimer.stop();
               }
            }
            newApple();
        }
    }

    public void checkCollisions(){
        //checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((X[0] == X[i]) && (Y[0] == Y[i])) {
                running = false;
            }
        }
        //Checks if head touches left border, Checks if head touches right border,
        //Checks if head touches top border, Checks if head touches bottom border
        switch (optionsMenu.getDifficultyChoice()){
            case "Easy" -> {
                X[0] = (X[0] < 0) ? SCREEN_WIDTH :
                        (X[0] > SCREEN_WIDTH - 1) ? 0 : X[0];
                Y[0] = (Y[0] < 0) ? SCREEN_HEIGHT :
                        (Y[0] > SCREEN_HEIGHT - 1) ? 0 : Y[0];
            }
            case "Normal", "Hard", "Insane" -> {
                if(X[0] < 0 || X[0] > SCREEN_WIDTH || Y[0] < 0 || Y[0] > SCREEN_HEIGHT){
                    running = false;
                }
            }
        }
    }

    public void gameOver(Graphics g){
        if(!running){
            timer.stop();
            if(applesEaten < 10){
                //Background image/animation
                ImageIcon background = new ImageIcon( getClass().getClassLoader().getResource("gif-blood.gif"));
                g.drawImage(background.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
                //Game Over text
                ImageIcon gif = new ImageIcon(getClass().getClassLoader().getResource("game-over-text.gif"));
                g.drawImage(gif.getImage(), 50, 150, 500, 200, this);
            } else if(applesEaten <=19){
                g.drawImage(mazeGameOver.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
            } else if (applesEaten <= 39) {
                    g.drawImage(gameOverThree.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
            } else {
                g.drawImage(gameOverNot.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
            }
        }
    }

    public void gameOverButtons(){
        soundBuffer();
        //A button for the option to retry the game
        retryButton = new JButton("Retry");
        retryButton.setBackground(Color.black);
        retryButton.setBorder(BorderFactory.createLineBorder(Color.black));
        retryButton.setForeground(Color.white);
        retryButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        add(retryButton);
        retryButton.addActionListener(e -> {
            musicSoundBoard.setSound(getClass().getResource("/retry-sound.wav"));
            retryButtonClicked();
        });

        //A button for the option to return to the main menu
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setBackground(Color.black);
        mainMenuButton.setBorder(BorderFactory.createLineBorder(Color.black));
        mainMenuButton.setForeground(Color.white);
        mainMenuButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        add(mainMenuButton);
        mainMenuButton.addActionListener(e -> {
            musicSoundBoard.setSound(getClass().getResource("/laserrocket-5984.wav"));
            menuButtonClicked();
        });
            retryButton.setVisible(false);
            mainMenuButton.setVisible(false);
    }
    
     //Game over button animations
    private void retryButtonClicked() {
        animationTimer = new Timer(15, e -> {
            alpha -= 5;
            retryButton.setForeground(new Color(255, 0, 0, alpha));
            retryButton.setBackground(new Color(255, 0, 0, alpha));
            if (alpha <= 0) {
                animationTimer.stop();
                new GameFrame();
            }
        });
        animationTimer.start();
    }

    private void menuButtonClicked() {
        animationTimer = new Timer(10, e -> {
            alpha -= 3;
            mainMenuButton.setForeground(new Color(33, 145, 89, alpha));
            mainMenuButton.setBackground(new Color(33, 145, 89, alpha));
            if (alpha <= 0) {
                animationTimer.stop();
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(mainMenuButton);
                currentFrame.dispose();
                new MainMenu().setVisible(true);
            }
        });
        animationTimer.start();
    }

    public void flushIcon(){
        new ImageIcon(getClass().getClassLoader().getResource("Snake.....gif")).getImage().flush();
        new ImageIcon(getClass().getClassLoader().getResource("Game-Over-Epic-MG.gif")).getImage().flush();
        new ImageIcon(getClass().getClassLoader().getResource("maze-game-over-loop-1.gif")).getImage().flush();
    }

    public void togglePause(){
        paused = !paused;
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT -> {
                    if(direction != 'R'){
                        direction = 'L';
                    }
                } case KeyEvent.VK_RIGHT -> {
                    if(direction != 'L'){
                        direction = 'R';
                    }
                } case KeyEvent.VK_UP -> {
                    if(direction != 'D'){
                        direction = 'U';
                    }
                } case KeyEvent.VK_DOWN -> {
                    if(direction != 'U'){
                        direction = 'D';
                    }
                } case KeyEvent.VK_SPACE -> { //Adds pause button with space bar
                    if (timer.isRunning()) {
                        musicSoundBoard.setSoundAndPause(getClass().getResource("/Sound/pause-sound.wav"), 260);
                        togglePause();
                        musicSoundBoard.stopMusic();
                    } else {
                        timer.start();
                        startDifficultyTimer();
                        musicSoundBoard.resumeMusic();
                    }
                }
            }
        }
    }

    public void soundBuffer(){
        if(!running){
            timer.stop();
            if(applesEaten < 10){
                musicSoundBoard.setSound(getClass().getResource("/evil-game-over-quote.wav"));
            } else if(applesEaten <= 20){
                musicSoundBoard.setSound(getClass().getResource("/tunnel-bang-sound.wav"));
            } else if (applesEaten <= 39) {
                musicSoundBoard.setSound(getClass().getResource("/Metal Gear Solid Game Over screen.wav"));
            } else {
                musicSoundBoard.setSound(getClass().getResource("/continue.wav"));
            }
        }
    }

    public void stopDifficultyTimer(){
        switch (optionsMenu.getDifficultyChoice()){
            case "Hard"-> hardTimer.stop();
            case "Insane" -> insaneTimer.stop();
        }
    }
    public void startDifficultyTimer(){
        switch (optionsMenu.getDifficultyChoice()){
            case "Hard"-> hardTimer.start();
            case "Insane" -> insaneTimer.start();
        }
    }

    public String setBackgroundImage(){
                try {
                    String [] images = {
                            "Animated-bg/blue-star.gif",
                            "Animated-bg/cube-world.gif",
                            "Animated-bg/green-vortex.gif",
                            "Animated-bg/hell-maze.gif",
                            "Animated-bg/hexahedron-animated.gif",
                            "Animated-bg/light-ball.gif",
                            "Animated-bg/light-hexahedron-tunnel.gif",
                            "Animated-bg/light-squares.gif",
                            "Animated-bg/light-squares2.gif",
                            "Animated-bg/lines-and-sparks.gif",
                            "Animated-bg/matrix.gif",
                            "Animated-bg/occult-triangle.gif",
                            "Animated-bg/wireframe.gif"
                    };
                    int index = random.nextInt(images.length);
                    return images[index];
                } catch (Exception e) {
                    System.err.println("Error loading image");
                }
                    return null;
            }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }

            flushIcon();
            repaint();
            gameOverButtons();
            gameOver = true;
     }

     //TODO: Figure out how to incorporate key interaction for game over buttons
    public class MyKeyAdapterTwo extends KeyAdapter{
        public void setActionListener() {//For interacting with game over screen with keys
            retryButton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_ENTER -> retryButton.doClick();
                        case KeyEvent.VK_DOWN, KeyEvent.VK_UP -> retryButton.requestFocus();
                    }
                }
            });
            mainMenuButton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_ENTER -> mainMenuButton.doClick();
                        case KeyEvent.VK_DOWN, KeyEvent.VK_UP -> mainMenuButton.requestFocus();
                    }
                }
            });
        }
    }

}