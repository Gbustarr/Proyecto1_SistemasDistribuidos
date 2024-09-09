package utils;
public class Image {
    // Esta clase permite guardar la información de la imagen cargada
    
    int alto, ancho;
    String path;

    public Image(int alto,int ancho,String path){
        this.alto = alto;
        this.ancho = ancho;
        this.path = path;
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
}
