import java.util.ArrayList;

// Maneja una pelea por turnos entre el heroe y uno o mas villanos.
// En cada ronda primero juega el heroe (2 veces si Mayra detuvo el tiempo)
// y despues juega cada villano que siga vivo.
public class Combate {

    public static final int SIGUE = 0;
    public static final int GANADO = 1;
    public static final int PERDIDO = 2;
    public static final int HUIDO = 3;

    private Heroe heroe;
    private ArrayList<Villano> enemigos;
    private Consola consola;
    private int ronda;

    public Combate(Heroe heroe, ArrayList<Villano> enemigos, Consola consola) {
        this.heroe = heroe;
        this.enemigos = enemigos;
        this.consola = consola;
        ronda = 0;
    }

    // juega toda la pelea y devuelve GANADO, PERDIDO o HUIDO
    public int iniciar() {
        for (Villano v : enemigos) {
            System.out.println(v.getNombre() + ": \"" + v.getFrase() + "\"");
        }
        int resultado = SIGUE;
        while (resultado == SIGUE) {
            resultado = jugarRonda();
        }
        heroe.finDeCombate();
        return resultado;
    }

    private int jugarRonda() {
        ronda++;
        System.out.println("\n---------------- Ronda " + ronda + " ----------------");

        // turno del heroe
        if (heroe.empezarTurno()) {
            boolean juegaOtraVez = true;
            while (juegaOtraVez) {
                juegaOtraVez = false;
                boolean huyo = turnoHeroe();
                if (huyo) {
                    return HUIDO;
                }
                revisarDerrotados();
                if (getEnemigosVivos().size() == 0) {
                    return GANADO;
                }
                // si Mayra detuvo el tiempo juega de nuevo
                if (heroe.isTurnoExtra()) {
                    heroe.setTurnoExtra(false);
                    System.out.println("\nEl tiempo sigue detenido... " + heroe.getNombre() + " juega otra vez!");
                    juegaOtraVez = true;
                }
            }
        }
        heroe.finDeTurno();
        if (!heroe.estaVivo()) {
            return PERDIDO;
        }

        // turno de los villanos
        System.out.println();
        for (Villano v : getEnemigosVivos()) {
            if (!heroe.estaVivo()) {
                break;
            }
            if (v.empezarTurno()) {
                v.actuar(heroe, this);
            }
        }
        revisarDerrotados();
        if (!heroe.estaVivo()) {
            return PERDIDO;
        }
        if (getEnemigosVivos().size() == 0) {
            return GANADO;
        }
        heroe.finDeRonda();
        return SIGUE;
    }

    // menu del heroe, repite hasta que haga algo valido
    // devuelve true si eligio huir
    private boolean turnoHeroe() {
        while (true) {
            mostrarEstado();
            System.out.println("Que hace " + heroe.getNombre() + "?");
            System.out.println("  1. Atacar");
            System.out.println("  2. Usar poder");
            System.out.println("  3. Usar item");
            System.out.println("  4. Defenderse (recibis la mitad)");
            System.out.println("  5. Ver mochila (no gasta el turno)");
            System.out.println("  6. Huir (se termina la partida)");
            try {
                int opcion = consola.leerOpcion(1, 6);
                if (opcion == 1) {
                    atacar();
                    return false;
                } else if (opcion == 2) {
                    usarPoder();
                    return false;
                } else if (opcion == 3) {
                    usarItem();
                    return false;
                } else if (opcion == 4) {
                    heroe.setDefendiendo(true);
                    System.out.println(heroe.getNombre() + " se pone en guardia");
                    return false;
                } else if (opcion == 5) {
                    mostrarMochila(); // no gasta turno, vuelve al menu
                } else {
                    System.out.println(heroe.getNombre() + " sale corriendo... la UPA sigue tomada");
                    return true;
                }
            } catch (AccionInvalidaException e) {
                consola.mostrarError(e.getMessage());
            }
        }
    }

    private void atacar() throws AccionInvalidaException {
        Villano objetivo = elegirObjetivo();
        if (objetivo.interceptarAtaque(heroe, consola)) {
            System.out.println(heroe.getNombre() + " ataca a " + objetivo.getNombre());
            int danio = heroe.atacar(objetivo);
            if (danio > 0) {
                objetivo.alSerAtacado(heroe, false);
            }
        }
    }

    private void usarPoder() throws AccionInvalidaException {
        String[] poderes = heroe.getPoderes();
        int numero = 0;
        if (poderes.length > 1) {
            System.out.println("Que poder usas?");
            for (int i = 0; i < poderes.length; i++) {
                System.out.println("  " + (i + 1) + ". " + poderes[i] + " [" + heroe.getCostoPoder(i) + " energia] - "
                        + heroe.getDescripcionPoder(i));
            }
            numero = consola.leerOpcion(1, poderes.length) - 1;
        }
        heroe.validarPoder(numero);

        Villano objetivo = null;
        if (heroe.poderEsOfensivo(numero)) {
            objetivo = elegirObjetivo();
            if (!objetivo.interceptarAtaque(heroe, consola)) {
                heroe.gastarEnergia(heroe.getCostoPoder(numero)); // se gasto en una copia de la Frau
                return;
            }
        }
        heroe.lanzarPoder(numero, objetivo, this);
        if (objetivo != null && objetivo.estaVivo()) {
            objetivo.alSerAtacado(heroe, true);
        }
    }

    private void usarItem() throws AccionInvalidaException {
        if (heroe.getInventario().size() == 0) {
            throw new AccionInvalidaException("Tu mochila esta vacia");
        }
        mostrarMochila();
        String nombre = consola.leerTexto("Escribi el nombre del item: ");
        heroe.usarItem(nombre);
    }

    // si hay un solo villano lo devuelve, si hay dos pregunta a cual
    private Villano elegirObjetivo() throws AccionInvalidaException {
        ArrayList<Villano> vivos = getEnemigosVivos();
        if (vivos.size() == 1) {
            return vivos.get(0);
        }
        System.out.println("A quien?");
        for (int i = 0; i < vivos.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + vivos.get(i).getNombre() + " " + vivos.get(i).getBarraVida());
        }
        int opcion = consola.leerOpcion(1, vivos.size());
        return vivos.get(opcion - 1);
    }

    // da la recompensa de los villanos que cayeron y le avisa a los otros
    private void revisarDerrotados() {
        for (Villano caido : enemigos) {
            if (!caido.estaVivo() && !caido.isRecompensaEntregada()) {
                caido.setRecompensaEntregada(true);
                System.out.println("\n*** Venciste a " + caido.getNombre() + "! ***");
                if (caido.getRecompensaMonedas() > 0) {
                    heroe.ganarMonedas(caido.getRecompensaMonedas());
                    System.out.println("   +" + caido.getRecompensaMonedas() + " monedas");
                }
                caido.alSerDerrotado(heroe);
                if (caido.getRecompensaExperiencia() > 0) {
                    heroe.ganarExperiencia(caido.getRecompensaExperiencia());
                }
                for (Villano otro : getEnemigosVivos()) {
                    otro.companeroCaido(caido);
                }
            }
        }
    }

    private void mostrarEstado() {
        System.out.println();
        System.out.println(heroe.getNombre() + " -> " + heroe.getEstado());
        for (Villano v : getEnemigosVivos()) {
            String extra = "";
            if (v.isEnfurecido()) {
                extra = " (ENFURECIDO)";
            }
            System.out.println(v.getNombre() + " -> Vida " + v.getBarraVida() + extra);
        }
    }

    private void mostrarMochila() {
        heroe.ordenarInventarioPorPoder();
        System.out.println("Mochila (ordenada por poder):");
        if (heroe.getInventario().size() == 0) {
            System.out.println("   (vacia)");
        }
        for (Item item : heroe.getInventario()) {
            System.out.println("   - " + item);
        }
    }

    public ArrayList<Villano> getEnemigosVivos() {
        ArrayList<Villano> vivos = new ArrayList<Villano>();
        for (Villano v : enemigos) {
            if (v.estaVivo()) {
                vivos.add(v);
            }
        }
        return vivos;
    }

    public Consola getConsola() {
        return consola;
    }
}
