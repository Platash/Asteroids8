package asteroids;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.imageio.ImageIO;

/**
 * @author AP
 * Static class, contains all the game graphics which are downloaded at the beginning.
 * Graphics are stored as final BufferedImage.
 */
public class Graphics {
    protected static final BufferedImage[] STARS  = new BufferedImage[ObjectsController.STARS_COUNT];
    protected static final BufferedImage[] MISTS = new BufferedImage[ObjectsController.MIST_COUNT];;
    protected static final BufferedImage ASTEROIDS;
    protected static final BufferedImage EXPLOSIONS;
    protected static final BufferedImage SHIPS;
    protected static final BufferedImage ROCKETS;
    protected static final BufferedImage GAME_OVER;
    private static final String path = "./src/graphics/";
    
    static {
        ASTEROIDS = downloadImage(path + ObjectsController.ASTEROID_NAME + "_0.png");
        EXPLOSIONS = downloadImage(path + ObjectsController.EXPLOSION_NAME + "_0.png");
        SHIPS = downloadImage(path + ObjectsController.SHIP_NAME + "_0.png");
        ROCKETS = downloadImage(path + ObjectsController.ROCKET_NAME + "_0.png");
        GAME_OVER = downloadImage(path + "game_over.png");
        
        for(int i = 0; i < ObjectsController.STARS_COUNT; i++) {
            STARS[i] = downloadImage(path + ObjectsController.STARS_NAME + "_" + i + ".png");
        }
        for(int i = 0; i < ObjectsController.MIST_COUNT; i++) {
            MISTS[i] = downloadImage(path + ObjectsController.MIST_NAME + "_" + i + ".png");
        }
    }
    
    /**
     * Downloads image using given path
     * @param path String 
     * @return BufferedImage downloaded image
     */
    public static BufferedImage downloadImage(String path) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.printf("No %s file/n", path);
            return null;
        }
        return img;
    }
}
