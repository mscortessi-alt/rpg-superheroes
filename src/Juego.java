import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Controla la partida: crea el héroe, arma la fila de villanos y maneja
 * el menú y los turnos de combate.
 */
public class Juego {

    private Heroe heroe;
    private Queue<Villano> villanos;   // los villanos se enfrentan en orden (FIFO)
    private Scanner scanner;
    private boolean partidaTerminada;

    public Juego() {
        this.villanos = new LinkedList<>();
        this.scanner = new Scanner(System.in);
        this.partidaTerminada = false;
    }

    /** Punto de entrada de la partida. */
    public void iniciar() {
        System.out.println("==========================================");
        System.out.println("     CIUDAD EN PELIGRO - RPG Superhéroes   ");
        System.out.println("==========================================");
        crearHeroe();
        cargarVillanos();
        // TODO Sprint 2: mientras haya villanos y el héroe esté vivo y no haya huido:
        //                Villano v = villanos.poll(); combatir(v);
        mostrarResultado();
    }

    /** Pide el nombre y deja elegir un superpoder. */
    private void crearHeroe() {
        System.out.print("Nombre de tu héroe: ");
        String nombre = scanner.nextLine();
        System.out.println("Elegí tu superpoder:");
        System.out.println("1. Súper fuerza   (más ataque)");
        System.out.println("2. Escudo de energía (más defensa)");
        System.out.println("3. Regeneración   (más vida)");
        // TODO Sprint 1: leer la opción con leerOpcion(1, 3) y crear el Heroe con stats según el poder
        heroe = new Heroe(nombre, "Súper fuerza", 100, 20, 5);
        heroe.agregarItem(new Item("Botiquín de nanobots", "CURACION", 30));
    }

    /** Arma la fila de villanos (el último es el jefe final). */
    private void cargarVillanos() {
        villanos.add(new Villano("Doctor Voltio", 60, 12, 3,
                "¡Te voy a dejar sin batería!",
                new Item("Batería cósmica", "ENERGIA", 50)));
        villanos.add(new Villano("Sombra de Hierro", 80, 15, 6,
                "Nadie escapa de la oscuridad...",
                new Item("Guantes de plasma", "ATAQUE", 8)));
        villanos.add(new Villano("Capitán Caos", 120, 18, 8,
                "¡Esta ciudad ya es mía!",
                new Item("Suero de titanio", "CURACION", 50)));
    }

    /**
     * Combate por turnos contra un villano.
     * @return true si el héroe ganó, false si perdió o huyó
     */
    private boolean combatir(Villano villano) {
        // TODO Sprint 2: bucle de turnos: turnoJugador -> si el villano sigue vivo, turnoVillano
        //                capturar AccionInvalidaException y volver a pedir la acción
        //                al ganar: heroe.agregarItem(villano.getRecompensa())
        return false;
    }

    /** Menú de acciones del jugador en su turno. */
    private void turnoJugador(Villano villano) throws AccionInvalidaException {
        System.out.println("\n" + heroe + "  vs  " + villano);
        System.out.println("1. Atacar");
        System.out.println("2. Usar superpoder (" + heroe.getSuperpoder() + ")");
        System.out.println("3. Usar ítem");
        System.out.println("4. Ver inventario (ordenado por poder)");
        System.out.println("5. Huir");
        // TODO Sprint 2: int opcion = leerOpcion(1, 5); switch(opcion) {...}
    }

    /** El villano ataca al héroe. */
    private void turnoVillano(Villano villano) {
        // TODO Sprint 2: int danio = villano.atacar(heroe); mostrar el daño
    }

    /**
     * Lee un número del teclado y valida que esté entre min y max.
     * @throws AccionInvalidaException si no es un número o está fuera de rango
     */
    private int leerOpcion(int min, int max) throws AccionInvalidaException {
        // TODO Sprint 1: leer con scanner.nextLine(), Integer.parseInt dentro de try/catch
        //                (NumberFormatException) y validar el rango
        return min;
    }

    /** Muestra victoria, derrota o huida. */
    private void mostrarResultado() {
        // TODO Sprint 2: según el estado final, imprimir VICTORIA / DERROTA / HUISTE
        partidaTerminada = true;
        System.out.println("Fin de la partida.");
    }

    // ----- Getters -----

    public Heroe getHeroe() { return heroe; }
    public Queue<Villano> getVillanos() { return villanos; }
    public boolean isPartidaTerminada() { return partidaTerminada; }
}
