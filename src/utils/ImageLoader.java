package utils;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageLoader {
    // Esta clase se encarga de cargar la imagen y devolverla
    BufferedImage image = null;

    Image imageLoaded = null;

    ImageLoader(String path){
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.imageLoaded = new Image(image.getWidth(), image.getHeight(), path);
    }

    public Image getImage() {
        return this.imageLoaded;
    }
}
