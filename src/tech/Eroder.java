package tech;

public class Eroder {
    private double[][] matrizOriginal;
    private double[][] matrizDilatada;
    private int alto, ancho;
    private int figura;

    public Eroder(double[][] matrizOriginal, int figura) {
        this.matrizOriginal = matrizOriginal;
        this.alto = matrizOriginal.length;
        this.ancho = matrizOriginal[0].length;
        this.matrizDilatada = new double[alto][ancho];
        this.figura = figura;
    }
    //Agrandar la matriz del canal en +1 en todas las direcciones
    public double[][] expandMatrix(double[][] original) {
        double[][] expandedMatrix = new double[alto + 2][ancho + 2];
        // Llenar la nueva matriz con 255 (blanco)
        for (int i = 0; i < alto + 2; i++) {
            for (int j = 0; j < ancho + 2; j++) {
                expandedMatrix[i][j] = 255.0;
            }
        }
        // Copiar la matriz original en el centro de la nueva matriz
        for (int i = 1; i <= alto; i++) {
            for (int j = 1; j <= ancho; j++) {
                expandedMatrix[i][j] = original[i - 1][j - 1];
            }
        }
        return expandedMatrix;
    }

    // Método de dilatación secuencial usando la matriz expandida
    public double[][] erocionSecuencial(int startRow, int endRow) {
        // Expandir la matriz original
        double[][] matrizExpandida = expandMatrix(matrizOriginal);
        
        switch (figura) {
            case 1: // Figura 1: min[(x,y), (x-1,y), (x+1,y), (x,y-1), (x,y+1)]
                for (int i = startRow; i <= endRow; i++) {
                    for (int j = 1; j <= ancho; j++) {
                        matrizDilatada[i - 1][j - 1] = Math.min(
                            matrizExpandida[i][j],
                            Math.min(
                                matrizExpandida[i - 1][j],
                                Math.min(
                                    matrizExpandida[i + 1][j],
                                    Math.min(matrizExpandida[i][j - 1], matrizExpandida[i][j + 1])
                                )
                            )
                        );
                    }
                }
                break;

            case 2: // Figura 2: min[(x,y), (x-1,y), (x,y-1)]
                for (int i = startRow; i <= endRow; i++) {
                    for (int j = 1; j <= ancho; j++) {
                        matrizDilatada[i - 1][j - 1] = Math.min(
                            matrizExpandida[i][j],
                            Math.min(matrizExpandida[i - 1][j], matrizExpandida[i][j - 1])
                        );
                    }
                }
                break;

            case 3: // Figura 3: min[(x,y), (x-1,y), (x,y+1)]
                for (int i = startRow; i <= endRow; i++) {
                    for (int j = 1; j <= ancho; j++) {
                        matrizDilatada[i - 1][j - 1] = Math.min(
                            matrizExpandida[i][j],
                            Math.min(matrizExpandida[i - 1][j], matrizExpandida[i][j + 1])
                        );
                    }
                }
                break;
            case 4: // Figura 4: min[(x,y), (x-1,y), (x+1,y)]
                for (int i = startRow; i <= endRow; i++) {
                    for (int j = 1; j <= ancho; j++) {
                        matrizDilatada[i - 1][j - 1] = Math.min(
                            matrizExpandida[i][j],
                            Math.min(matrizExpandida[i - 1][j], matrizExpandida[i+1][j])
                        );
                    }
                }
                break;    
            case 5: // Figura 5: min[(x,y), (x,y-1)]
            for (int i = startRow; i <= endRow; i++) {
                for (int j = 1; j <= ancho; j++) {
                    matrizDilatada[i - 1][j - 1] = Math.min(
                        matrizExpandida[i][j],matrizExpandida[i][j-1]);
                    }
                }
                break;
            case 6: // Figura 1: min[(x,y), (x-1,y), (x+1,y), (x,y-1), (x,y+1)]
                for (int i = startRow; i <= endRow; i++) {
                    for (int j = 1; j <= ancho; j++) {
                        matrizDilatada[i - 1][j - 1] = Math.min(
                            matrizExpandida[i][j],
                            Math.min(
                                matrizExpandida[i - 1][j+1],
                                Math.min(
                                    matrizExpandida[i - 1][j - 1],
                                    Math.min(matrizExpandida[i + 1][j + 1], matrizExpandida[i + 1][j - 1])
                                )
                            )
                        );
                    }
                }
            break;    

            default:
                throw new IllegalArgumentException("Figura no válida. Solo se permiten figuras 1, 2 , 3 , 4 , 5  , 6.");
        }
        return matrizDilatada;
    }
}