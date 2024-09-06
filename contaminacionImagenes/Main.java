public class Main {
    public static void main(String[] args) {
        GestionImagenes g = new GestionImagenes("img/img_1.png");
        //g.salPimienta(100f);

        g.guardar('R');
        g.guardar('G');
        g.guardar('B');
        g.guardar('Z');

    }
}
