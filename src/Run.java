import utils.Image;
import utils.ImageLoader;
import java.io.File;
import tech.ImageProcessor;

public class Run {
    public static void main(String[] args) {
        System.out.println("Papu run");

        // Para detectar el tipo de sistema operativo
        String osName = System.getProperty("os.name").toLowerCase();
        String imagePath;
        String savePath;

        if (osName.contains("win")) {
            imagePath = "img\\tipo_Ruido_40.0.png"; // Windows
            savePath = "img\\Prueba1.png";
        } else {
            imagePath = "src/images/img_1.png"; // Linux
            savePath = "img/Prueba1.png";
        }

        File imageFile = new File(imagePath);

        ImageLoader imgLoader = null;
        Image img = null;

        if (imageFile.exists()) {
            try {
                imgLoader = new ImageLoader(imagePath);
                img = imgLoader.getImage();

                img.setOS(osName.contains("win") ? 0 : 1);
                img.setRGBChannels();

                ImageProcessor processor = new ImageProcessor(img);

                // Eroder paralelo
                processor.processEroderP(savePath, 3);

                // EroderSecuencial
                // processor.processEroderS("img/tipo_Erocionada.png", 1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Image file does not exist");
        }
    }
}
