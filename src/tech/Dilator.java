package tech;

public class Dilator {
    private short[][] matrizOriginal;
    private short[][] matrizDilatada;
    private int alto, ancho;
    private int figura;

    public Dilator(short[][] matrizOriginal, int figura) {
        this.matrizOriginal = matrizOriginal;
        this.alto = matrizOriginal.length;
        this.ancho = matrizOriginal[0].length;
        this.matrizDilatada = new short[alto][ancho];
        this.figura = figura;
    }
    //Agrandar la matriz del canal en +1 en todas las direcciones
    public short[][] expandMatrix(short[][] original) {
        short[][] expandedMatrix = new short[alto + 2][ancho + 2];
        // Llenar la nueva matriz con 0 (negro)
        for (int i = 0; i < alto + 2; i++) {
            for (int j = 0; j < ancho + 2; j++) {
                expandedMatrix[i][j] = 0;
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
    public short[][] dilatacionSecuencial(int startRow, int endRow) {
        // Expandir la matriz original
        short[][] matrizExpandida = expandMatrix(matrizOriginal);
        
        switch (figura) {
            case 1: // Figura 1: max[(x,y), (x-1,y), (x+1,y), (x,y-1), (x,y+1)]
                for (int i = startRow; i <= endRow; i++)  {
                    for (int j = 1; j <= ancho; j++) {
                        matrizDilatada[i - 1][j - 1] = (short)Math.max(
                            matrizExpandida[i][j],
                            Math.max(
                                matrizExpandida[i - 1][j],
                                Math.max(
                                    matrizExpandida[i + 1][j],
                                    Math.max(matrizExpandida[i][j - 1], matrizExpandida[i][j + 1])
                                )
                            )
                        );
                    }
                }
                break;

            case 2: // Figura 2: max[(x,y), (x-1,y), (x,y-1)]
                for (int i = startRow; i <= endRow; i++)  {
                    for (int j = 1; j <= ancho; j++) {
                        matrizDilatada[i - 1][j - 1] = (short)Math.max(
                            matrizExpandida[i][j],
                            Math.max(matrizExpandida[i - 1][j], matrizExpandida[i][j - 1])
                        );
                    }
                }
                break;

            case 3: // Figura 3: max[(x,y), (x-1,y), (x,y+1)]
                for (int i = startRow; i <= endRow; i++)  {
                    for (int j = 1; j <= ancho; j++) {
                        matrizDilatada[i - 1][j - 1] =(short) Math.max(
                            matrizExpandida[i][j],
                            Math.max(matrizExpandida[i - 1][j], matrizExpandida[i][j + 1])
                        );
                    }
                }
                break;
            case 4: // Figura 4: max[(x,y), (x-1,y), (x+1,y)]
                for (int i = startRow; i <= endRow; i++)  {
                    for (int j = 1; j <= ancho; j++) {
                        matrizDilatada[i - 1][j - 1] = (short)Math.max(
                            matrizExpandida[i][j],
                            Math.max(matrizExpandida[i - 1][j], matrizExpandida[i+1][j])
                        );
                    }
                }
                break;    
            case 5: // Figura 5: max[(x,y), (x,y-1)]
            for (int i = startRow; i <= endRow; i++)  {
                for (int j = 1; j <= ancho; j++) {
                    matrizDilatada[i - 1][j - 1] = (short)Math.max(
                        matrizExpandida[i][j],matrizExpandida[i][j-1]);
                    }
                }
                break;
            case 6: // Figura 1: max[(x,y), (x-1,y), (x+1,y), (x,y-1), (x,y+1)]
                for (int i = startRow; i <= endRow; i++)  {
                    for (int j = 1; j <= ancho; j++) {
                        matrizDilatada[i - 1][j - 1] =(short) Math.max(
                            matrizExpandida[i][j],
                            Math.max(
                                matrizExpandida[i - 1][j+1],
                                Math.max(
                                    matrizExpandida[i - 1][j - 1],
                                    Math.max(matrizExpandida[i + 1][j + 1], matrizExpandida[i + 1][j - 1])
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
