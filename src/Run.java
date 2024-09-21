import utils.Image;
import utils.ImageLoader;
import java.io.File;
import tech.ImageProcessor;

public class Run {
    public static void main(String[] args) {
        System.out.println("Papu run");

        String imagePath = "img\\tipo_Ruido_40.0.png"; // Windows
        String imagePath2 = "src/images/img_1.png"; // Linux
        File imageFile = new File(imagePath);
        File imageFile2 = new File(imagePath2);

        ImageLoader imgLoader = null;
        Image img = null;
        
        if (imageFile.exists()) {
            try {
                imgLoader = new ImageLoader(imagePath);
                img = imgLoader.getImage();

                img.setOS(0);
                img.setRGBChannels();

                ImageProcessor processor = new ImageProcessor(img);
                
                //Eroder paralelo
                processor.processEroderP("img/Prueba1.png",3);
                
                // EroderSecuencial
                //processor.processEroderS("img/tipo_Erocionada.png",1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if(imageFile2.exists()){
                try {
                    System.out.println("Image file exists: " + imageFile2.getAbsolutePath());
                    imgLoader = new ImageLoader(imagePath2);
                    System.out.println(imgLoader.getImage().getAncho());

                    img = imgLoader.getImage();

                    img.setOS(1);

                    img.setRGBChannels();

                    img.saveRGBtoImages();
                    ImageProcessor processor = new ImageProcessor(img);
                
                    //Eroder paralelo
                    processor.processEroderP("img/Prueba1.png",3);
                    
                    // EroderSecuencial
                    //processor.processEroderS("img/tipo_Erocionada.png",1);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
            }else{
                System.out.println("Image file does not exist");
            }
        }
    }
}