import java.util.Random;

// Clase para todo lo que es al azar (criticos, esquivar, habilidades)
public class Dado {

    private static Random random = new Random();

    // devuelve true con esa probabilidad (ej: chance(30) es 30%)
    public static boolean chance(int porcentaje) {
        return random.nextInt(100) < porcentaje;
    }

    // numero al azar entre min y max
    public static int entre(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }
}
