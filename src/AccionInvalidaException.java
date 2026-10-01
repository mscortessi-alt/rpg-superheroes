// Excepcion propia del juego.
// La usamos cuando el jugador hace algo que no se puede: poner una letra en el menu,
// usar el poder sin energia, buscar un item que no tiene, comprar sin plata, etc.
public class AccionInvalidaException extends Exception {

    public AccionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
