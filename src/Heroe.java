import java.util.ArrayList;

/**
 * El personaje que controla el jugador. Además de lo de Personaje tiene
 * un superpoder que gasta energía y un inventario de ítems.
 */
public class Heroe extends Personaje {

    public static final int ENERGIA_MAXIMA = 100;
    public static final int COSTO_SUPERPODER = 40;

    private String superpoder;
    private int energia;
    private ArrayList<Item> inventario;   // estructura de datos estándar de Java

    public Heroe(String nombre, String superpoder, int vidaMaxima, int ataque, int defensa) {
        super(nombre, vidaMaxima, ataque, defensa);
        this.superpoder = superpoder;
        this.energia = ENERGIA_MAXIMA;
        this.inventario = new ArrayList<>();
    }

    /**
     * Ataque especial: hace el doble de daño pero gasta energía.
     * @throws AccionInvalidaException si no alcanza la energía
     */
    public int usarSuperpoder(Villano objetivo) throws AccionInvalidaException {
        // TODO Sprint 2: si energia < COSTO_SUPERPODER -> throw new AccionInvalidaException(...)
        //                si alcanza: restar energía y hacer (ataque * 2 - defensa del villano) de daño
        return 0;
    }

    public void agregarItem(Item item) {
        inventario.add(item);
    }

    /**
     * BÚSQUEDA LINEAL: recorre el inventario hasta encontrar el ítem por nombre.
     * @throws AccionInvalidaException si el ítem no está
     */
    public Item buscarItem(String nombre) throws AccionInvalidaException {
        // TODO Sprint 2: recorrer inventario con un for, comparar con equalsIgnoreCase
        //                y si no se encuentra -> throw new AccionInvalidaException(...)
        return null;
    }

    /**
     * Usa un ítem (lo busca, aplica su efecto según el tipo y lo saca del inventario).
     */
    public void usarItem(String nombre) throws AccionInvalidaException {
        // TODO Sprint 2: Item item = buscarItem(nombre);
        //                CURACION -> curar(poder) | ATAQUE -> setAtaque(+poder) | ENERGIA -> recargar
        //                inventario.remove(item);
    }

    /**
     * ORDENAMIENTO BURBUJA: ordena el inventario de mayor a menor poder.
     */
    public void ordenarInventarioPorPoder() {
        // TODO Sprint 2: dos for anidados, comparar getPoder() y usar inventario.set(...) para intercambiar
    }

    // ----- Getters y setters -----

    public String getSuperpoder() { return superpoder; }
    public void setSuperpoder(String superpoder) { this.superpoder = superpoder; }

    public int getEnergia() { return energia; }
    public void setEnergia(int energia) { this.energia = Math.max(0, Math.min(ENERGIA_MAXIMA, energia)); }

    public ArrayList<Item> getInventario() { return inventario; }

    @Override
    public String toString() {
        return super.toString() + " [Energía: " + energia + "/" + ENERGIA_MAXIMA + "]";
    }
}
