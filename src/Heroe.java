import java.util.ArrayList;

// Clase padre de los heroes. Tiene energia, poderes, nivel, plata e inventario.
// Cada heroe (Thiago, Patus, etc) programa su propio poder.
public abstract class Heroe extends Personaje {

    public static final int ENERGIA_MAXIMA = 100;
    public static final int ENERGIA_POR_TURNO = 10;

    private String descripcion;
    private int energia;
    private int nivel;
    private int experiencia;
    private int monedas;
    private ArrayList<Item> inventario;
    private int turnosPoderBloqueado;  // lo usa la profe Diana
    private int turnosAtaqueReducido;  // lo usa Ruben
    private boolean turnoExtra;        // lo usa Mayra

    public Heroe(String nombre, String descripcion, int vidaMaxima, int ataque, int defensa, int probCritico, int probEsquivar) {
        super(nombre, vidaMaxima, ataque, defensa, probCritico, probEsquivar);
        this.descripcion = descripcion;
        energia = ENERGIA_MAXIMA;
        nivel = 1;
        experiencia = 0;
        monedas = 30;
        inventario = new ArrayList<Item>();
    }

    // ---------- PODERES ----------

    // nombres de los poderes del heroe
    public abstract String[] getPoderes();

    public abstract int getCostoPoder(int numero);

    public abstract String getDescripcionPoder(int numero);

    // lo que hace el poder, cada heroe lo hace distinto
    protected abstract void usarPoder(int numero, Villano objetivo, Combate combate) throws AccionInvalidaException;

    // si el poder necesita elegir a quien pegarle
    public boolean poderEsOfensivo(int numero) {
        return true;
    }

    // revisa si se puede usar el poder
    public void validarPoder(int numero) throws AccionInvalidaException {
        if (turnosPoderBloqueado > 0) {
            throw new AccionInvalidaException("Tu poder esta bloqueado por " + turnosPoderBloqueado + " turno(s)");
        }
        if (energia < getCostoPoder(numero)) {
            throw new AccionInvalidaException("No tenes energia suficiente (cuesta " + getCostoPoder(numero) + " y tenes " + energia + ")");
        }
    }

    public void lanzarPoder(int numero, Villano objetivo, Combate combate) throws AccionInvalidaException {
        validarPoder(numero);
        usarPoder(numero, objetivo, combate);
        energia = energia - getCostoPoder(numero);
    }

    public void gastarEnergia(int cantidad) {
        energia = energia - cantidad;
        if (energia < 0) {
            energia = 0;
        }
    }

    // ---------- INVENTARIO ----------

    public void agregarItem(Item item) {
        inventario.add(item);
    }

    // BUSQUEDA LINEAL: recorre la lista uno por uno hasta encontrarlo
    public Item buscarItem(String nombre) throws AccionInvalidaException {
        for (int i = 0; i < inventario.size(); i++) {
            if (inventario.get(i).seLlama(nombre)) {
                return inventario.get(i);
            }
        }
        throw new AccionInvalidaException("No tenes ningun item que se llame " + nombre);
    }

    public void usarItem(String nombre) throws AccionInvalidaException {
        Item item = buscarItem(nombre);
        System.out.println("   " + getNombre() + " usa " + item.getNombre());
        if (item.getTipo().equals(Item.CURACION)) {
            curar(item.getPoder());
        } else if (item.getTipo().equals(Item.ENERGIA)) {
            setEnergia(energia + item.getPoder());
            System.out.println("   Energia: " + energia + "/" + ENERGIA_MAXIMA);
        } else if (item.getTipo().equals(Item.ATAQUE)) {
            setAtaque(getAtaque() + item.getPoder());
            System.out.println("   Ataque sube a " + getAtaque());
        } else {
            setDefensa(getDefensa() + item.getPoder());
            System.out.println("   Defensa sube a " + getDefensa());
        }
        inventario.remove(item);
    }

    // ORDENAMIENTO BURBUJA: ordena de mayor a menor poder
    // compara de a dos y si estan al reves los cambia
    public void ordenarInventarioPorPoder() {
        for (int i = 0; i < inventario.size() - 1; i++) {
            for (int j = 0; j < inventario.size() - 1 - i; j++) {
                if (inventario.get(j).getPoder() < inventario.get(j + 1).getPoder()) {
                    Item aux = inventario.get(j);
                    inventario.set(j, inventario.get(j + 1));
                    inventario.set(j + 1, aux);
                }
            }
        }
    }

    // saca un item al azar (Luciana roba)
    public Item quitarItemAlAzar() {
        if (inventario.size() == 0) {
            return null;
        }
        int pos = Dado.entre(0, inventario.size() - 1);
        return inventario.remove(pos);
    }

    // ---------- NIVEL Y PLATA ----------

    public int getExperienciaParaSubir() {
        return nivel * 60;
    }

    public void ganarExperiencia(int cantidad) {
        experiencia = experiencia + cantidad;
        System.out.println("   +" + cantidad + " de experiencia");
        while (experiencia >= getExperienciaParaSubir()) {
            experiencia = experiencia - getExperienciaParaSubir();
            subirNivel();
        }
    }

    private void subirNivel() {
        nivel++;
        setVidaMaxima(getVidaMaxima() + 12);
        setAtaque(getAtaque() + 3);
        setDefensa(getDefensa() + 1);
        energia = ENERGIA_MAXIMA;
        System.out.println("   *** " + getNombre() + " SUBIO A NIVEL " + nivel + "! (+12 vida max, +3 ataque, +1 defensa)");
        curar(getVidaMaxima() / 4);
    }

    public void ganarMonedas(int cantidad) {
        monedas = monedas + cantidad;
    }

    public void pagar(int cantidad) throws AccionInvalidaException {
        if (cantidad > monedas) {
            throw new AccionInvalidaException("No te alcanza la plata (tenes " + monedas + " monedas)");
        }
        monedas = monedas - cantidad;
    }

    // pierde monedas (la Cantinera cobra de mas), devuelve cuantas perdio
    public int perderMonedas(int cantidad) {
        if (cantidad > monedas) {
            cantidad = monedas;
        }
        monedas = monedas - cantidad;
        return cantidad;
    }

    // ---------- EFECTOS ----------

    // si Ruben le bajo el ataque pega con el 70%
    public int getAtaqueEfectivo() {
        if (turnosAtaqueReducido > 0) {
            return getAtaque() * 70 / 100;
        }
        return getAtaque();
    }

    public void bloquearPoder(int turnos) {
        turnosPoderBloqueado = turnos;
    }

    public void reducirAtaque(int turnos) {
        turnosAtaqueReducido = turnos;
    }

    // al final de su turno bajan los efectos que le pusieron
    public void finDeTurno() {
        if (turnosPoderBloqueado > 0) turnosPoderBloqueado--;
        if (turnosAtaqueReducido > 0) turnosAtaqueReducido--;
    }

    // al final de la ronda recupera energia
    public void finDeRonda() {
        setEnergia(energia + ENERGIA_POR_TURNO);
    }

    // cuando termina una pelea se limpia todo
    public void finDeCombate() {
        turnosPoderBloqueado = 0;
        turnosAtaqueReducido = 0;
        turnoExtra = false;
    }

    public String getEstado() {
        String estado = "Vida " + getBarraVida() + " | Energia " + energia + " | Nivel " + nivel + " | Monedas " + monedas;
        if (turnosAtaqueReducido > 0) {
            estado = estado + " | ataque bajo (" + turnosAtaqueReducido + ")";
        }
        if (turnosPoderBloqueado > 0) {
            estado = estado + " | poder bloqueado (" + turnosPoderBloqueado + ")";
        }
        return estado;
    }

    // getters y setters

    public String getDescripcion() { return descripcion; }

    public int getEnergia() { return energia; }
    public void setEnergia(int energia) {
        if (energia < 0) energia = 0;
        if (energia > ENERGIA_MAXIMA) energia = ENERGIA_MAXIMA;
        this.energia = energia;
    }

    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }
    public int getMonedas() { return monedas; }
    public ArrayList<Item> getInventario() { return inventario; }

    public boolean isTurnoExtra() { return turnoExtra; }
    public void setTurnoExtra(boolean turnoExtra) { this.turnoExtra = turnoExtra; }
}
