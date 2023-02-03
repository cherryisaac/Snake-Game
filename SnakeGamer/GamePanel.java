package SnakeGamer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


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
    JButton retryButton, mainMenuButton;
    int alpha = 255;
    private Timer animationTimer;
    boolean gameOver = false;
    private OptionsMenu optionsMenu;
    private MusicSoundBoard musicSoundBoard;
    private ImageIcon backgroundImage;
    private ImageIcon gameOverTwo = new ImageIcon("./Images/Game-Over-Epic-MG.gif");


    public GamePanel(){
        random = new Random();
        optionsMenu = new OptionsMenu();
        musicSoundBoard = new MusicSoundBoard();
        musicSoundBoard.setMusicClip();
        switch (optionsMenu.getMusicChoice()) {
            case "on" -> musicSoundBoard.setMusicChoice();
            case "off" -> {}
        }
        backgroundImage = new ImageIcon(setBackgroundImage());
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        startGame();
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
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH/5 - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        } else {
            gameOver(g);
            musicSoundBoard.stopMusic();
            //Delay the appearance of the buttons
            int delayTime = 10000; // 10 seconds
            Timer delayTimer = new Timer(delayTime, e -> {
                retryButton.setBounds(230,325,150,50); //Position and size of buttons as they appear
                mainMenuButton.setBounds(230,375,150,50);
                retryButton.setVisible(true);
                mainMenuButton.setVisible(true);
            });
            delayTimer.start();
            running = false;

        }
    }

    public void newApple(){
        appleX = random.nextInt((SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
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
                try {
               musicSoundBoard.setSound(new URL("file:./Sound/eating-sound-effect.wav"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            newApple();
        }
    }

    public void checkCollisions(){
        //checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if((X[0] == X[i]) && (Y[0]==Y[i])){
                running = false;
            }
        }
        //Checks if head touches left border, Checks if head touches right border,
        //Checks if head touches top border, Checks if head touches bottom border
        if(X[0] < 0 || X[0] > SCREEN_WIDTH || Y[0] < 0 || Y[0] > SCREEN_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
            if(applesEaten < 20){
                try{
                    musicSoundBoard.setSound(new URL("file:./Sound/evil-game-over-quote.wav"));
                } catch (MalformedURLException e){
                    throw new RuntimeException();
                }
            } else {
                try{
                   musicSoundBoard.setSound(new URL("file:./Sound/Metal Gear Solid Game Over screen.wav"));
                } catch (MalformedURLException e){
                    throw new RuntimeException();
                }
            }
        }
    }

    public void gameOver(Graphics g){
        //Score
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        if(applesEaten < 20){
            //Background image/animation
            ImageIcon background = new ImageIcon("./Images/gif-blood.gif");
            g.drawImage(background.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
            //Game Over text
            ImageIcon gif = new ImageIcon("./Images/game-over-text.gif");
            g.drawImage(gif.getImage(), 50, 150, 500, 200, this);
        } else {
            g.drawImage(gameOverTwo.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);

        }
    }

    public void gameOverButtons(){
        //A button for the option to retry the game
        retryButton = new JButton("Retry");
        retryButton.setBorder(BorderFactory.createLineBorder(Color.black));
        retryButton.setForeground(Color.white);
        retryButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        add(retryButton);
        retryButton.addActionListener(e -> {
            try {
                musicSoundBoard.setSound(new URL("file:./Sound/retry-sound.wav"));
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
            retryButtonClicked();
        });
        //A button for the option to return to the main menu
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setBackground(Color.RED);
        mainMenuButton.setBorder(BorderFactory.createLineBorder(Color.black));
        mainMenuButton.setForeground(Color.white);
        mainMenuButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        add(mainMenuButton);
        mainMenuButton.addActionListener(e -> {
            menuButtonClicked();
            try {
                musicSoundBoard.setSound(new URL("file:./Sound/laserrocket-5984.wav"));
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
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
                flushIcon();
                //restart game
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
                //Return to main menu
                new MainMenu().setVisible(true);
            }

        });
        animationTimer.start();
    }
    //TODO: Fix issue below
    //Allows for repeat gif plays from start to finish (1 loop),
    // BUT not consecutively from one game over after another.
    public void flushIcon(){
        gameOverTwo = new ImageIcon("./Images/Game-Over-Epic-MG.gif");
        gameOverTwo.getImage().flush();
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
                        try {
                            musicSoundBoard.setSoundAndPause(new URL("file:./Sound/pause-sound.wav"), 260);
                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        }
                        togglePause();
                        musicSoundBoard.stopMusic();
                    } else {
                        timer.start();
                        musicSoundBoard.resumeMusic();
                    }
                }
            }
        }
    }

    public String setBackgroundImage(){
                try {
                    File [] listOfFiles = new File("./Images/Animated-bg/").listFiles();
                    ArrayList<String> images = new ArrayList<>();

                    assert listOfFiles != null;
                    for (File file : listOfFiles) {
                        if (file.isFile() && (file.getName().endsWith(".gif"))) {
                            images.add(file.getPath());
                        }
                    }
                    int index = random.nextInt(images.size());
                    return images.get(index);
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
            repaint();
            gameOverButtons();
            gameOver = true;
    }
}


