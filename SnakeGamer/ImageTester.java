package SnakeGamer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class ImageTester extends JFrame{
    private OptionsMenu optionsMenu;
    private Random random;
     ImageIcon backgroundImage;

    public ImageTester(){
        optionsMenu = new OptionsMenu();
        backgroundImage = new ImageIcon();
        random = new Random();
    }

    public void paintComponents(Graphics g){
        super.paintComponents(g);
    }

    public void setBackgroundImage(){
        switch (optionsMenu.getBackgroundImages()){
            case "yes" -> {
                try {
                    File[] listOfFiles = new File("./Images/Animated-bg/").listFiles();
                    ArrayList<String> images = new ArrayList<>();

                    assert listOfFiles != null;
                    for (File file : listOfFiles) {
                        if (file.isFile() && (file.getName().endsWith(".gif"))) {
                            images.add(file.getPath());
                        }
                    }
                    int index = random.nextInt(images.size());
                    String imagePath = images.get(index);
                    backgroundImage = new ImageIcon(imagePath);
                } catch (Exception e) {
                    System.err.println("Error loading image");
                }
            }
            case "no" -> setBackground(Color.green); //Changes background color
        }
    }

//    public void getRandomGif(String folderPath) {
//        switch (optionsMenu.getBackgroundImages()){
//           case "yes" -> {
//               File folder = new File(folderPath);
//               ArrayList<File> gifFiles = new ArrayList<>();
//
//               for (final File file : folder.listFiles()) {
//                   if (file.getName().endsWith(".gif")) {
//                       gifFiles.add(file);
//                   }
//               }
//
//               Random random = new Random();
//               int randomIndex = random.nextInt(gifFiles.size());
//               File randomGif = gifFiles.get(randomIndex);
//               backgroundImage = new ImageIcon(randomGif.getAbsolutePath());
//           }
//           case "no" -> setBackground(Color.black);
//        }
//    }
}
