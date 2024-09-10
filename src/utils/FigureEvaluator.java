package utils;
import figure.*;

public class FigureEvaluator {
    // Esta clase permite identificar cuantos pixeles se debe expandir la imagen para que
    // la figura no tenga problemas en su desplazamiento

    Image img;
    Figure fig;

    public FigureEvaluator(Image img, Figure fig){
        this.img = img;
        this.fig = fig;
    }

    public int getExpandAmount(){
        // Implementación aquí
        return 0; // Devuelve la cantidad de pixeles que se debe expandir
    }
}
