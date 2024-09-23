package tech;

import utils.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageProcessor {
    
    private Image img;

    public void setImg(BufferedImage img) {
        this.img = new Image(img.getHeight(), img.getWidth(), "", img);
        this.img.setRGBChannels();
        System.out.println("Image loaded: "+ this.img.getAlto()+"x"+ this.img.getAncho());
    }

    public void processDilatorS(String outputPath , Integer figura) throws Exception {
        // Procesar los tres canales con dilatación
        Dilator dilatacionRed = new Dilator(img.getChannel('R'), figura);
        Dilator dilatacionGreen = new Dilator(img.getChannel('G'), figura);
        Dilator dilatacionBlue = new Dilator(img.getChannel('B'), figura);

        // Dilatar los canales secuencialmente
        double[][] redDilatado = dilatacionRed.dilatacionSecuencial(1,img.getAlto());
        double[][] greenDilatado = dilatacionGreen.dilatacionSecuencial(1,img.getAlto());
        double[][] blueDilatado = dilatacionBlue.dilatacionSecuencial(1,img.getAlto());

        // Combinar los canales en la imagen final
        saveImage(outputPath, redDilatado, greenDilatado, blueDilatado);

    }

    public void processEroderS(String outputPath,Integer figura) throws Exception {
        // Procesar los tres canales con erosión
        Eroder erosionRed = new Eroder(img.getChannel('R'), figura);
        Eroder erosionGreen = new Eroder(img.getChannel('G'), figura);
        Eroder erosionBlue = new Eroder(img.getChannel('B'), figura);

        // Erosionar los canales secuencialmente
        double[][] rederosion = erosionRed.erocionSecuencial(1,img.getAlto());
        double[][] greenerosion = erosionGreen.erocionSecuencial(1,img.getAlto());
        double[][] blueerosion = erosionBlue.erocionSecuencial(1,img.getAlto());

        saveImage(outputPath, rederosion, greenerosion, blueerosion);

    }

    public void processDilatorP(String outputPath, Integer figura) throws Exception {
        // Crear arrays para almacenar los resultados de cada canal
        final double[][][] results = new double[3][][];
    
        // Crear hilos para procesar cada canal en paralelo
        Thread redThread = new Thread(() -> {
            try {
                results[0] = parallelChannelDilator(img.getChannel('R'), figura);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        Thread greenThread = new Thread(() -> {
            try {
                results[1] = parallelChannelDilator(img.getChannel('G'), figura);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        Thread blueThread = new Thread(() -> {
            try {
                results[2] = parallelChannelDilator(img.getChannel('B'), figura);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        // Iniciar los hilos
        redThread.start();
        greenThread.start();
        blueThread.start();
    
        // Esperar a que los tres hilos terminen
        redThread.join();
        greenThread.join();
        blueThread.join();
    
        // Combinar los resultados y guardar la imagen resultante
        saveImage(outputPath, results[0], results[1], results[2]);
    }
    
    // Proceso de erosión paralelo
    public BufferedImage processEroderP(String outputPath, Integer figura) throws Exception {
        // Crear arrays para almacenar los resultados de cada canal
        final double[][][] results = new double[3][][];
    
        // Crear hilos para procesar cada canal en paralelo
        Thread redThread = new Thread(() -> {
            try {
                results[0] = parallelChannelEroder(img.getChannel('R'), figura);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        Thread greenThread = new Thread(() -> {
            try {
                results[1] = parallelChannelEroder(img.getChannel('G'), figura);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        Thread blueThread = new Thread(() -> {
            try {
                results[2] = parallelChannelEroder(img.getChannel('B'), figura);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        // Iniciar los hilos
        redThread.start();
        greenThread.start();
        blueThread.start();
    
        // Esperar a que los tres hilos terminen
        redThread.join();
        greenThread.join();
        blueThread.join();
    
        // Combinar los resultados y guardar la imagen resultante
        return saveImage(outputPath, results[0], results[1], results[2]);
    }

     // Método para procesar un canal de color en paralelo utilizando Dilator
     private double[][] parallelChannelDilator(double[][] channel, Integer figura) throws InterruptedException {
        int mid = channel.length / 2;
        double[][] result = new double[channel.length][channel[0].length];

        // Crear dos hilos para procesar la mitad de cada canal
        Thread thread1 = new Thread(() -> processPartDilator(channel, result, 1, mid, figura));
        Thread thread2 = new Thread(() -> processPartDilator(channel, result, mid, channel.length, figura));

        // Iniciar los hilos
        thread1.start();
        thread2.start();

        // Esperar que los hilos terminen
        thread1.join();
        thread2.join();

        return result;
    }

    // Método para procesar un canal de color en paralelo utilizando Eroder
    private double[][] parallelChannelEroder(double[][] channel, Integer figura) throws InterruptedException {
        int mid = channel.length / 2;
        double[][] result = new double[channel.length][channel[0].length];
    
        // Crear dos hilos para procesar la mitad de cada canal
        Thread thread1 = new Thread(() -> processPartEroder(channel, result, 1, mid, figura));
        Thread thread2 = new Thread(() -> processPartEroder(channel, result, mid, channel.length, figura));
    
        // Iniciar los hilos
        thread1.start();
        thread2.start();
    
        // Esperar que los hilos terminen
        thread1.join();
        thread2.join();
    
        return result;
    }

    // Procesar parte de un canal con dilatación
    private void processPartDilator(double[][] original, double[][] result, int startRow, int endRow, int figura) {
        Dilator dilator = new Dilator(original, figura);
        double[][] partialResult = dilator.dilatacionSecuencial(startRow, endRow);
        // Guardar la parte procesada en el arreglo result
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < original[0].length; j++) {
                result[i][j] = partialResult[i][j];
            }
        }
    }

    // Procesar parte de un canal con erosión
    private void processPartEroder(double[][] original, double[][] result, int startRow, int endRow, int figura) {
        Eroder eroder = new Eroder(original, figura);
        double[][] partialResult = eroder.erocionSecuencial(startRow, endRow);
        // Guardar la parte procesada en el arreglo result
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < original[0].length; j++) {
                result[i][j] = partialResult[i][j];
            }
        }
    }

    private BufferedImage saveImage(String outputPath, double[][] redChannel, double[][] greenChannel, double[][] blueChannel) throws Exception {
        int width = img.getAncho();
        int height = img.getAlto();
        BufferedImage imagen = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB); // Cambiados a proposito

        // Combinar los canales en la imagen final
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int red = (int) redChannel[i][j];
                int green = (int) greenChannel[i][j];
                int blue = (int) blueChannel[i][j];

                // Combinar los valores de los tres canales en un solo color
                int rgb = (red << 16) | (green << 8) | blue;
                imagen.setRGB(i, j, rgb);
            }
        }

        // Guardar la imagen procesada
        ImageIO.write(imagen, "png", new File(outputPath));
        
        System.out.println("Imagen guardada en: " + outputPath);
        return imagen;
    }
}
