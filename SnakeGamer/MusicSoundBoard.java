package SnakeGamer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class MusicSoundBoard {
    private Clip musicClip;
    private Random random;
    private Clip menuMusic;
    private OptionsMenu optionsMenu;

    public MusicSoundBoard() {
        random = new Random();
        try {
            musicClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void mainMenuMusic() {
        try {
            menuMusic = AudioSystem.getClip();
            menuMusic.open(AudioSystem.getAudioInputStream(new File("/Users/isaaccherry/Documents/FolderTwo/Snake-Game/Sound/main-menu-theme.wav")));
            FloatControl volume = (FloatControl) menuMusic.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-15.0f); //Lower the volume by 15 decibels
            menuMusic.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopMenuMusic() {
        menuMusic.stop();
    }

    public void setSound(URL url) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSoundAndPause(URL url, int timer) {
        try {
            Thread.sleep(timer);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        setSound(url);
    }

    public void setMusicChoice() {
        optionsMenu = new OptionsMenu();
        if(optionsMenu.getDifficultyChoice().equals("Hard") || optionsMenu.getDifficultyChoice().equals("Insane")){
            String [] musicFiles2 = {
                    "/Path of Pain.wav",
                    "/Lethal Encounter.wav",
                    "/NieR Unreleased OST - The Wretched Automatons FULL INSTRUMENTAL 11.48.25 AM.wav"
            };
            try {
                int index = random.nextInt(musicFiles2.length);
                String musicPath = musicFiles2[index];
                musicClip = AudioSystem.getClip();
                musicClip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource(musicPath))));
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                musicClip.start();
            }catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error loading music");
            }
        } else {
            String[] musicFiles = {
                    "/Awesome violin beat visualisation.wav",
                    "/Kaine-Salvation.wav",
                    "/KBT Expressway - GBA Pokémon Unbound_ Super Music Collection.wav",
                    "/Mind-Power-Ki (Instrumental).wav"
            };
            try {
                int index = random.nextInt(musicFiles.length);
                String musicPath = musicFiles[index];
                musicClip = AudioSystem.getClip();
                musicClip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource(musicPath))));
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                musicClip.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error loading music");
            }
        }
    }

    public void stopMusic() {
        musicClip.stop();
    }

    public void resumeMusic() {
        musicClip.start();
    }

    public void setMusicClip() {
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