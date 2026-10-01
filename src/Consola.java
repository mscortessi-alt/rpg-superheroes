import java.util.Scanner;

// Se encarga de leer lo que escribe el jugador
public class Consola {

    private Scanner scanner;

    public Consola() {
        scanner = new Scanner(System.in);
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    // lee un numero entre min y max, si no es valido tira la excepcion
    public int leerOpcion(int min, int max) throws AccionInvalidaException {
        String texto = leerTexto("Opcion: ");
        int numero;
        try {
            numero = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new AccionInvalidaException("Eso no es un numero. Escribi un numero del " + min + " al " + max);
        }
        if (numero < min || numero > max) {
            throw new AccionInvalidaException("Tiene que ser un numero entre " + min + " y " + max);
        }
        return numero;
    }

    // igual que leerOpcion pero pregunta de nuevo hasta que ponga algo valido
    public int leerOpcionValida(int min, int max) {
        while (true) {
            try {
                return leerOpcion(min, max);
            } catch (AccionInvalidaException e) {
                mostrarError(e.getMessage());
            }
        }
    }

    public void mostrarError(String mensaje) {
        System.out.println("  [!] " + mensaje);
    }

    public void pausa() {
        leerTexto("\n(Enter para seguir)");
    }

    public void titulo(String texto) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("  " + texto);
        System.out.println("==================================================");
    }
}
