import utils.Image;
import utils.ImageLoader;
import java.io.File;

import javax.imageio.ImageIO;

import tech.Dilator;
import tech.Eroder;
import java.awt.image.BufferedImage;

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
                System.out.println("Image file exists: " + imageFile.getAbsolutePath());
                imgLoader = new ImageLoader(imagePath);

                System.out.println(imgLoader.getImage().getAncho());
                System.out.println(imgLoader.getImage().getAlto());

                    img = imgLoader.getImage();

                    img.setOS(0);

                    img.setRGBChannels();

                    img.saveRGBtoImages();

                // Procesar los tres canales con dilatación
                Dilator dilatacionRed = new Dilator(img.getChannel('R'), 3);
                Dilator dilatacionGreen = new Dilator(img.getChannel('G'), 3);
                Dilator dilatacionBlue = new Dilator(img.getChannel('B'), 3);

                //Dilatarlos Secuencialmente
                double[][] redDilatado = dilatacionRed.dilatacionSecuencial();
                double[][] greenDilatado = dilatacionGreen.dilatacionSecuencial();
                double[][] blueDilatado = dilatacionBlue.dilatacionSecuencial();

                // Crear una nueva imagen combinando los canales dilatados
                int width = img.getAncho();
                int height = img.getAlto();
                BufferedImage imagenDilatada = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                // Combinar los canales en la imagen final
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        int red = (int) redDilatado[i][j];
                        int green = (int) greenDilatado[i][j];
                        int blue = (int) blueDilatado[i][j];

                        // Combinar los valores de los tres canales en un solo color
                        int rgb = (red << 16) | (green << 8) | blue;
                        imagenDilatada.setRGB(j, i, rgb);
                    }
                }

                // Guardar la imagen dilatada
                String outputPath = "img/tipo_Dilatada.png";
                ImageIO.write(imagenDilatada, "png", new File(outputPath));
                System.out.println("Imagen dilatada guardada en: " + outputPath);   

                //Erocion
                Eroder erocionRed = new Eroder(img.getChannel('R'), 1);
                Eroder erocionGreen = new Eroder(img.getChannel('G'), 1);
                Eroder erocionBlue = new Eroder(img.getChannel('B'), 1);

                //Dilatarlos Secuencialmente
                double[][] rederocion = erocionRed.erocionSecuencial();
                double[][] greenerocion = erocionGreen.erocionSecuencial();
                double[][] blueerocion = erocionBlue.erocionSecuencial();

                BufferedImage imagenErocion = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                // Combinar los canales en la imagen final
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        int red = (int) rederocion[i][j];
                        int green = (int) greenerocion[i][j];
                        int blue = (int) blueerocion[i][j];

                        // Combinar los valores de los tres canales en un solo color
                        int rgb = (red << 16) | (green << 8) | blue;
                        imagenErocion.setRGB(j, i, rgb);
                    }
                }

                // Guardar la imagen erocionada
                String outputPath2 = "img/tipo_Erocion.png";
                ImageIO.write(imagenErocion, "png", new File(outputPath2));
                System.out.println("Imagen erocionada guardada en: " + outputPath2);   

            } catch (Exception e) {
                // TODO: handle exception
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
                    
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }else{
                System.out.println("Image file does not exist");
            }
            
        }
    }
}