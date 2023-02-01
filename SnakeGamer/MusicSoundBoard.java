package SnakeGamer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class MusicSoundBoard {
    private Clip musicClip;
    private Random random;

    public MusicSoundBoard(){
        random = new Random();
    }

    public void setSound(URL url){
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMusicChoice(){
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

    public void stopMusic(){
        musicClip.stop();
    }

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
