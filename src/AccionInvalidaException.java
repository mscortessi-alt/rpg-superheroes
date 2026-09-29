/**
 * Excepción personalizada del juego. Se lanza cuando el jugador intenta algo
 * no permitido: opción de menú fuera de rango, usar un ítem que no tiene,
 * o usar el superpoder sin energía suficiente.
 */
public class AccionInvalidaException extends Exception {

    public AccionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
