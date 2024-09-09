import utils.ImageLoader;
import java.io.File;

public class Run {
    public static void main(String[] args) {
        System.out.println("Papu run");

        String imagePath = "src\\images\\img_1.png";
        File imageFile = new File(imagePath);
        
        if (imageFile.exists()) {
            System.out.println("Image file exists: " + imageFile.getAbsolutePath());
            ImageLoader imgLoader = new ImageLoader(imagePath);
            System.out.println(imgLoader.getImage().getAncho());
        } else {
            System.out.println("Image file does not exist: " + imageFile.getAbsolutePath());
        }
    }
}