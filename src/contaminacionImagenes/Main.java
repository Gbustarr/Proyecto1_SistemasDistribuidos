public class Main {
    public static void main(String[] args) {

        String imagePath = "contaminacionImagenes\\img\\prueba.png"; // Windows

        GestionImagenes g = new GestionImagenes(imagePath);
        g.salPimienta(40f);

        g.guardar('R');
        g.guardar('G');
        g.guardar('B');
        g.guardar('Z');

    }
}
