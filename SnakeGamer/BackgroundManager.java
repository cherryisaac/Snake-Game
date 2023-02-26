package SnakeGamer;


import java.util.Random;

public class BackgroundManager {
     Random random;

     public BackgroundManager(){
         random = new Random();
     }

    public String setBackgroundImages(String [] str){
        try {
            int index = random.nextInt(str.length);
            return str[index];
        } catch (Exception e) {
            System.err.println("Error loading image");
        }
        return null;
    }

    public String [] setStaticBackground(){
        return new String[]{
                "Animated-bg/blue-star.gif",
                "Animated-bg/cube-world.gif",
                "Animated-bg/hell-maze.gif",
                "Animated-bg/hexahedron-animated.gif",
                "Animated-bg/lines-and-sparks.gif",
                "Animated-bg/matrix.gif",
                "Animated-bg/pink-star.gif"
        };
    }

    public String [] setMovingBackground(){
        return new String [] {
                "Animated-bg/green-vortex.gif",
                "Animated-bg/light-ball.gif",
                "Animated-bg/light-hexahedron-tunnel.gif",
                "Animated-bg/light-squares.gif",
                "Animated-bg/light-squares2.gif",
                "Animated-bg/occult-triangle.gif",
                "Animated-bg/wireframe.gif"
        };
    }

    public String [] setAllBackground(){
        return new String [] {
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
    }
}
