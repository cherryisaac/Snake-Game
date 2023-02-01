package SnakeGamer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
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
    private int alpha = 255;
    private Timer animationTimer;
    boolean gameOver = false;
    private final OptionsMenu optionsMenu;
    ImageIcon backgroundImage;
    private ArrayList<String> images;
    private Clip musicClip;

    GamePanel(){
        random = new Random();
        highScoreTracker = new HighScoreTracker();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.optionsMenu = new OptionsMenu();
        setBackgroundImage();
        setMusicClip();
        setMusicChoice();
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        optionsMenu.setVisible(false);
        startGame();
    }

    public void startGame(){
        optionsMenu.loadOptions();
        newApple();
        running = true;
        timer = new Timer(optionsMenu.getGameSpeed(), this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
        if(paused){
            g.setColor(new Color(0, 0, 0, 128));
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Paused", (SCREEN_WIDTH - metrics.stringWidth("Paused"))/2, SCREEN_HEIGHT/2);
            timer.stop();
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
            musicClip.stop();
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
                setSound(new URL("file:./Sound/eating-sound-effect.wav"));
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
            if(applesEaten < 25){
                try{
                    setSound(new URL("file:./Sound/evil-game-over-quote.wav"));
                } catch (MalformedURLException e){
                    throw new RuntimeException();
                }
            } else if(applesEaten >= 25){
                try{
                    setSound(new URL("file:./Sound/Metal Gear Solid Game Over screen.wav"));
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
        if(applesEaten < 25){
            //Background image/animation
            ImageIcon background = new ImageIcon("./Images/gif-blood.gif");
            g.drawImage(background.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
            //Game Over text
            ImageIcon gif = new ImageIcon("./Images/game-over-text.gif");
            g.drawImage(gif.getImage(), 50, 150, 500, 200, this);
        } else if(applesEaten >= 25){
            ImageIcon gameOverTwo = new ImageIcon("./Images/Game-Over-Epic-MG.gif");
            g.drawImage(gameOverTwo.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
        }
    }
    
    public void gameOverButtons(){
        //A button for the option to retry the game
        retryButton = new JButton("Retry");
        //        retryButton.setBorder(BorderFactory.createLineBorder(Color.gray));
        retryButton.setForeground(Color.black);
        retryButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        add(retryButton);
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retryButtonClicked();
            }
        });
        //A button for the option to return to the main menu
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setBackground(Color.RED);
        //        mainMenuButton.setBorder(BorderFactory.createLineBorder(Color.gray));
        mainMenuButton.setForeground(Color.black);
        mainMenuButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        add(mainMenuButton);
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuButtonClicked();
            }
        });
            retryButton.setVisible(false);
            mainMenuButton.setVisible(false);
        //Delay the appearance of the buttons
        int delayTime =10000; // 10 seconds
        Timer delayTimer = new Timer(delayTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retryButton.setBounds(230,325,150,50); //Position and size of buttons as they appear
                mainMenuButton.setBounds(230,375,150,50);
                if(!running & gameOver){
                    retryButton.setVisible(true);
                    mainMenuButton.setVisible(true);
                }
            }
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }
    
     //Game over button animations
    private void retryButtonClicked() {
        animationTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 5;
                retryButton.setForeground(new Color(255, 0, 0, alpha));
                retryButton.setBackground(new Color(255, 0, 0, alpha));
                if (alpha <= 0) {
                    animationTimer.stop();
                    //restart game
                    new GameFrame();
                }
            }
        });
        animationTimer.start();
    }

    private void menuButtonClicked() {
        animationTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 3;
                mainMenuButton.setForeground(new Color(0, 188, 255, alpha));
                mainMenuButton.setBackground(new Color(0, 188, 255, alpha));
                if (alpha <= 0) {
                    animationTimer.stop();
                    //Return to main menu
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.setVisible(true);
                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(mainMenuButton);
                    currentFrame.dispose();
                }

            }
        });
        animationTimer.start();
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
                        togglePause();
                    } else {
                        timer.start();
                    }
                }
            }
        }
    }
    
    public void setBackgroundImage(){
        switch (optionsMenu.getBackgroundImages()){
            case "yes" -> {
                try {
                    File [] listOfFiles = new File("./Images/Animated-bg/").listFiles();
                    images = new ArrayList<String>();

                    assert listOfFiles != null;
                    for (File file : listOfFiles) {
                        if (file.isFile() && (file.getName().endsWith(".gif"))) {
                            images.add(file.getPath());
                        }
                    }
                    int index = random.nextInt(images.size());
                    String imagePath = images.get(index);
                    this.backgroundImage = new ImageIcon(imagePath);
                } catch (Exception e) {
                    System.err.println("Error loading image");
                }
            }
            case "no" -> setBackground(Color.BLACK); //Changes background color
        }
    }
    
   public void setSound(URL url){
        try {
            //Snake makes a sound when it eats an apple
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setMusicChoice(){
        switch (optionsMenu.getMusicChoice()) {
            case "on" -> {
                try {
                    File[] listOfFiles = new File("./Music/").listFiles();
                    ArrayList<String> musicFiles = new ArrayList<>();

                    assert listOfFiles != null;
                    for (File file : listOfFiles) {
                        if (file.isFile() && (file.getName().endsWith(".wav"))) {
                            musicFiles.add(file.getPath());
                        }
                    }
                    int index = random.nextInt(musicFiles.size());
                    String musicPath = musicFiles.get(index);
                    musicClip = AudioSystem.getClip();
                    musicClip.open(AudioSystem.getAudioInputStream(new File(musicPath)));
                    musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                    musicClip.start();
                } catch (Exception e) {
                    System.err.println("Error loading music");
                }
            }
            case "off" -> { }
        }
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
    
    //Located here because of all the code filler
    public void setMusicClip(){
        this.musicClip = new Clip() {
            @Override
            public void open(AudioFormat format, byte[] data, int offset, int bufferSize) throws LineUnavailableException {
            }
            @Override
            public void open(AudioInputStream stream) throws LineUnavailableException, IOException {
            }
            @Override
            public int getFrameLength() {
                return 0;
            }
            @Override
            public long getMicrosecondLength() {
                return 0;
            }
            @Override
            public void setFramePosition(int frames) {
            }
            @Override
            public void setMicrosecondPosition(long microseconds) {
            }
            @Override
            public void setLoopPoints(int start, int end) {
            }
            @Override
            public void loop(int count) {
            }
            @Override
            public void drain() {
            }
            @Override
            public void flush() {
            }
            @Override
            public void start() {
            }
            @Override
            public void stop() {
            }
            @Override
            public boolean isRunning() {
                return false;
            }
            @Override
            public boolean isActive() {
                return false;
            }
            @Override
            public AudioFormat getFormat() {
                return null;
            }
            @Override
            public int getBufferSize() {
                return 0;
            }
            @Override
            public int available() {
                return 0;
            }
            @Override
            public int getFramePosition() {
                return 0;
            }
            @Override
            public long getLongFramePosition() {
                return 0;
            }
            @Override
            public long getMicrosecondPosition() {
                return 0;
            }
            @Override
            public float getLevel() {
                return 0;
            }
            @Override
            public Line.Info getLineInfo() {
                return null;
            }
            @Override
            public void open() throws LineUnavailableException {
            }
            @Override
            public void close() {
            }
            @Override
            public boolean isOpen() {
                return false;
            }
            @Override
            public Control[] getControls() {
                return new Control[0];
            }
            @Override
            public boolean isControlSupported(Control.Type control) {
                return false;
            }
            @Override
            public Control getControl(Control.Type control) {
                return null;
            }
            @Override
            public void addLineListener(LineListener listener) {
            }
            @Override
            public void removeLineListener(LineListener listener) {
            }
        };
    }
}


