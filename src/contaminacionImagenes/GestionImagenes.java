package contaminacionImagenes;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GestionImagenes {
    int alto, ancho;
    String path;
    BufferedImage image = null;
    public GestionImagenes(String path){
        this.path = path;
        try {
            image = ImageIO.read(new File(path));
            System.out.println(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        alto = image.getWidth();
        ancho = image.getHeight();
    }
    public double[][] getValues(char tipo) {
        double[][] values = new double[alto][ancho];
        for(int y = 0; y < alto; y++) {
            for(int x = 0; x < ancho; x++) {
                Color color = new Color(image.getRGB(y, x));
                int gray = 0;
                if(tipo == 'R') //Rojo
                    gray = color.getRed();
                if(tipo == 'G') //Verde
                    gray = color.getGreen();
                if(tipo == 'B') //Azul
                    gray = color.getBlue();
                if(tipo == 'Z') //Gray
                    gray = (color.getRed()+color.getBlue()+color.getGreen())/3;
                values[y][x] = gray;
            }
        }
        return values;
    }
    public void guardar(char tipo){
        double canal[][] = getValues(tipo);
        BufferedImage imagen = new BufferedImage(canal.length,canal[0].length,1);

        for (int i = 0; i < canal.length; i++) {
            for (int j = 0; j < canal[0].length; j++) {
                int rgb = (int)canal[i][j]<<16 | (int)canal[i][j] << 8 | (int)canal[i][j];
                imagen.setRGB(i, j, rgb);
            }
        }
        try {
            ImageIO.write(imagen,"png", new File("img/tipo_"+tipo+".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
    int rgbToInt(int red, int green, int blue) {
        red = clamp(red, 0, 255);
        green = clamp(green, 0, 255);
        blue = clamp(blue, 0, 255);
        return (red << 16) | (green << 8) | blue;
    }
    public void salPimienta(float porcentaje){
        BufferedImage imagen = new BufferedImage(alto,ancho,1);
        int pixeles = (int)((alto*ancho)*(porcentaje/100));
        int imagens[][] = new int[alto][ancho];
        int original[][] = new int[alto][ancho];
        //System.out.println(pixeles);
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                Color color = new Color(image.getRGB(i, j));
                original[i][j] = color.getRGB();
                imagens[i][j] = -1;//rgbToInt(color.getRed(), color.getGreen(), color.getBlue());
           }
        }
        while(pixeles>0){
            int valorEntero = (int) (Math.floor(Math.random()*(1-0+1)+0));  // Valor entre M y N, ambos incluidos.
            int yy = (int) (Math.floor(Math.random()*alto));
            int xx = (int) (Math.floor(Math.random()*ancho));
            Color color = new Color(image.getRGB(yy,xx));
            //if(imagens[yy][xx]!= -1 && imagens[yy][xx] != rgbToInt(0, 0, 0) && imagens[yy][xx] != rgbToInt(255, 255, 255)){
            if(imagens[yy][xx] == -1 ){
                switch (valorEntero){
                    case 0:
                        imagens[yy][xx] = rgbToInt(0, 0, 0);
//                        pixeles--;
                        break;
                    case 1:
                        imagens[yy][xx] = rgbToInt(255, 255, 255);
//                        pixeles--;
                        break;
                    /*case 2:
                        imagens[yy][xx] = rgbToInt(color.getRed(), color.getGreen(),color.getBlue());
                        break;*/
                }
                pixeles--;
            }

        }
        for(int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                if(imagens[y][x]==-1)
                    imagens[y][x] = original[y][x];
                imagen.setRGB(y, x, imagens[y][x]);
            }
        }
        try {
            ImageIO.write(imagen,"png", new File("img/tipo_Ruido_"+porcentaje+".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}