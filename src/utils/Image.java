package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
    // Esta clase permite guardar la información de la imagen cargada
    int alto, ancho;
    String path;
    BufferedImage img;

    int OS = 0; // 0 = Windows, 1 = Linux

    double[][] redChannel, greenChannel, blueChannel;

    public Image(int alto,int ancho,String path,BufferedImage img){
        this.alto = alto;
        this.ancho = ancho;
        this.path = path;
        this.img = img;
    }
    public int getAlto() {
        return alto;
    }
    public int getAncho() {
        return ancho;
    }
    public String getPath() {
        return path;
    }
    public BufferedImage getImg() {
        return img;
    }

    public void setRGBChannels(){
        this.redChannel = getChannel('R');
        System.out.println("Red channel setted");
        this.greenChannel = getChannel('G');
        System.out.println("Green channel setted");
        this.blueChannel = getChannel('B');
        System.out.println("Blue channel setted");
    }

    public void setOS(int OS) {
        this.OS = OS;
    }

    public double[][] getChannel(char tipo) {
        double[][] values = new double[alto][ancho];
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                Color color = new Color(img.getRGB(y, x));
                int gray = 0;
                if(tipo == 'R') //Rojo
                    gray = color.getRed();
                if(tipo == 'G') //Verde
                    gray = color.getGreen();
                if(tipo == 'B') //Azul
                    gray = color.getBlue();
                if(tipo == 'Z') //Gris
                    gray = (color.getRed()+color.getBlue()+color.getGreen())/3;
                values[y][x] = gray;
            }
        }
        return values;
    }

    public void saveRGBtoImages(){

        // Conseguir el directorio actual

        String outPath;

        if(OS == 0){
            outPath = System.getProperty("user.dir") + "\\img\\saved_images\\";

        }else{
            outPath = System.getProperty("user.dir") + "/img/saved_images/";
        }

        if (!new File(outPath).exists()) {
            new File(outPath).mkdirs();
        }

        // Guardando canal rojo
        BufferedImage redImagen = new BufferedImage(redChannel.length,redChannel[0].length,1);

        for (int i = 0; i < redChannel.length; i++) {
            for (int j = 0; j < redChannel[0].length; j++) {
                int rgb = (int)redChannel[i][j]<<16 | (int)redChannel[i][j] << 8 | (int)redChannel[i][j];
                redImagen.setRGB(i, j, rgb);
            }
        }
        try {
            ImageIO.write(redImagen,"png", new File(outPath+"/channel_"+"RED.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Guardando canal verde
        BufferedImage greenImage = new BufferedImage(greenChannel.length,greenChannel[0].length,1);

        for (int i = 0; i < greenChannel.length; i++) {
            for (int j = 0; j < greenChannel[0].length; j++) {
                int rgb = (int)greenChannel[i][j]<<16 | (int)greenChannel[i][j] << 8 | (int)greenChannel[i][j];
                greenImage.setRGB(i, j, rgb);
            }
        }
        try {
            ImageIO.write(greenImage,"png", new File(outPath+"/channel_"+"GREEN.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Guardando canal azul
        BufferedImage blueImage = new BufferedImage(blueChannel.length,blueChannel[0].length,1);

        for (int i = 0; i < blueChannel.length; i++) {
            for (int j = 0; j < blueChannel[0].length; j++) {
                int rgb = (int)blueChannel[i][j]<<16 | (int)blueChannel[i][j] << 8 | (int)blueChannel[i][j];
                blueImage.setRGB(i, j, rgb);
            }
        }
        try {
            ImageIO.write(blueImage,"png", new File(outPath+"/channel_"+"BLUE.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    

}
