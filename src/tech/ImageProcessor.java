package tech;

import utils.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ImageProcessor {
    
    private Image img;

    public void setImg(BufferedImage img) {
        this.img = new Image(img.getHeight(), img.getWidth(), "", img);
        this.img.setRGBChannels();
        //System.out.println("Image loaded: "+ this.img.getAlto()+"x"+ this.img.getAncho());
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

    public ArrayList<Object> processDilatorP(String outputPath, Integer figura, Integer numThreads) throws Exception {
        // Crear arrays para almacenar los resultados de cada canal
        final double[][][] results = new double[3][][];
    
        // Crear hilos para procesar cada canal en paralelo
        Thread redThread = new Thread(() -> {
            try {
                results[0] = parallelChannelDilator(img.getChannel('R'), figura, numThreads);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        Thread greenThread = new Thread(() -> {
            try {
                results[1] = parallelChannelDilator(img.getChannel('G'), figura, numThreads);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        Thread blueThread = new Thread(() -> {
            try {
                results[2] = parallelChannelDilator(img.getChannel('B'), figura, numThreads);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        long start = System.currentTimeMillis();
    
        // Iniciar los hilos
        redThread.start();
        greenThread.start();
        blueThread.start();
    
        // Esperar a que los tres hilos terminen
        redThread.join();
        greenThread.join();
        blueThread.join();
        long end = System.currentTimeMillis();
        System.out.println("[Dilator] Tiempo de ejecución: " + (end - start) + "ms");
    
        // Combinar los resultados y guardar la imagen resultante

        ArrayList<Object> resultados = new ArrayList<Object>();
        resultados.add(saveImage(outputPath, results[0], results[1], results[2]));
        resultados.add(end - start);

        return resultados;
    }
    
    // Proceso de erosión paralelo
    public ArrayList<Object> processEroderP(String outputPath, Integer figura, Integer numThreads) throws Exception {
        // Crear arrays para almacenar los resultados de cada canal
        final double[][][] results = new double[3][][];
    
        // Crear hilos para procesar cada canal en paralelo
        Thread redThread = new Thread(() -> {
            try {
                results[0] = parallelChannelEroder(img.getChannel('R'), figura, numThreads);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        Thread greenThread = new Thread(() -> {
            try {
                results[1] = parallelChannelEroder(img.getChannel('G'), figura, numThreads);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        Thread blueThread = new Thread(() -> {
            try {
                results[2] = parallelChannelEroder(img.getChannel('B'), figura,numThreads);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        long start = System.currentTimeMillis();
        // Iniciar los hilos
        redThread.start();
        greenThread.start();
        blueThread.start();
    
        // Esperar a que los tres hilos terminen
        redThread.join();
        greenThread.join();
        blueThread.join();
        long end = System.currentTimeMillis();
        System.out.println("[Eroder] Tiempo de ejecución: " + (end - start) + "ms");
    
        ArrayList<Object> resultados = new ArrayList<Object>();
        resultados.add(saveImage(outputPath, results[0], results[1], results[2]));
        resultados.add(end - start);

        return resultados;
    }

     // Método para procesar un canal de color en paralelo utilizando Dilator
     private double[][] parallelChannelDilator(double[][] channel, Integer figura, Integer numThreads) throws InterruptedException {
        
        int rowsPerThread = channel.length / numThreads;
        double[][] result = new double[channel.length][channel[0].length];
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int startRow = (i * rowsPerThread != 0) ? i * rowsPerThread : 1 ; // Para que inicie en la fila 1
            int endRow = (i == numThreads - 1) ? channel.length : startRow + rowsPerThread;
            threads[i] = new Thread(() -> processPartDilator(channel, result, startRow, endRow, figura));
        }

        

        // Iniciar los hilos
        for (Thread thread : threads) {
            thread.start();
        }

        // Esperar que los hilos terminen
        for (Thread thread : threads) {
            thread.join();
        }



        return result;

    }

    // Método para procesar un canal de color en paralelo utilizando Eroder
    private double[][] parallelChannelEroder(double[][] channel, Integer figura, Integer numThreads) throws InterruptedException {
        int rowsPerThread = channel.length / numThreads;
        double[][] result = new double[channel.length][channel[0].length];
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int startRow = (i * rowsPerThread != 0) ? i * rowsPerThread : 1 ; // Para que inicie en la fila 1
            int endRow = (i == numThreads - 1) ? channel.length : startRow + rowsPerThread;
            threads[i] = new Thread(() -> processPartEroder(channel, result, startRow, endRow, figura));
        }

        // Iniciar los hilos
        for (Thread thread : threads) {
            thread.start();
        }

        // Esperar que los hilos terminen
        for (Thread thread : threads) {
            thread.join();
        }

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
