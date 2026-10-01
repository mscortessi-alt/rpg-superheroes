// Clase padre de todos los personajes (heroes y villanos).
// Es abstracta porque nunca se crea un "Personaje" solo.
public abstract class Personaje {

    private String nombre;
    private int vidaMaxima;
    private int vida;
    private int ataque;
    private int defensa;
    private int probCritico;   // % de hacer doble danio
    private int probEsquivar;  // % de esquivar
    private int turnosAturdido;
    private int turnosVeneno;
    private int danioVeneno;
    private boolean defendiendo;

    public Personaje(String nombre, int vidaMaxima, int ataque, int defensa, int probCritico, int probEsquivar) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima;
        this.ataque = ataque;
        this.defensa = defensa;
        this.probCritico = probCritico;
        this.probEsquivar = probEsquivar;
    }

    // ataque normal
    public int atacar(Personaje objetivo) {
        return golpear(objetivo, 1.0, true);
    }

    // golpe general que usan los ataques y varios poderes
    // multiplicador: 1.0 es normal, 2.0 es doble
    // devuelve el danio que hizo (0 si lo esquivaron)
    public int golpear(Personaje objetivo, double multiplicador, boolean sePuedeEsquivar) {
        if (sePuedeEsquivar && Dado.chance(objetivo.getProbEsquivar())) {
            System.out.println("   " + objetivo.getNombre() + " esquivo el golpe!");
            return 0;
        }
        int danio = (int) (getAtaqueEfectivo() * multiplicador) - objetivo.getDefensa();
        if (danio < 1) {
            danio = 1;
        }
        boolean critico = Dado.chance(probCritico);
        if (critico) {
            danio = danio * 2;
            System.out.println("   GOLPE CRITICO de " + nombre + "!");
        }
        return objetivo.recibirDanio(danio, critico);
    }

    // si se esta defendiendo recibe la mitad
    public int recibirDanio(int danio, boolean critico) {
        if (defendiendo) {
            danio = danio / 2;
            if (danio < 1) {
                danio = 1;
            }
            System.out.println("   " + nombre + " se cubrio y recibe la mitad.");
        }
        restarVida(danio);
        return danio;
    }

    // danio que no se puede bajar con defensa (veneno, gravedad...)
    public void recibirDanioDirecto(int danio) {
        restarVida(danio);
    }

    private void restarVida(int danio) {
        vida = vida - danio;
        if (vida < 0) {
            vida = 0;
        }
        System.out.println("   -> " + nombre + " pierde " + danio + " de vida (" + vida + "/" + vidaMaxima + ")");
        if (vida == 0) {
            alQuedarSinVida();
        }
    }

    // Edisson sobreescribe esto para revivir
    protected void alQuedarSinVida() {
        System.out.println("   " + nombre + " cayo.");
    }

    public void curar(int cantidad) {
        int antes = vida;
        vida = vida + cantidad;
        if (vida > vidaMaxima) {
            vida = vidaMaxima;
        }
        System.out.println("   " + nombre + " recupera " + (vida - antes) + " de vida (" + vida + "/" + vidaMaxima + ")");
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    // el ataque que se usa para calcular el danio (Heroe lo cambia por el discurso de Ruben)
    public int getAtaqueEfectivo() {
        return ataque;
    }

    public void aturdir(int turnos) {
        if (turnos > turnosAturdido) {
            turnosAturdido = turnos;
        }
        System.out.println("   " + nombre + " queda aturdido por " + turnos + " turno(s).");
    }

    public void envenenar(int turnos, int danioPorTurno) {
        turnosVeneno = turnos;
        danioVeneno = danioPorTurno;
        System.out.println("   " + nombre + " queda envenenado por " + turnos + " turnos.");
    }

    // se llama al empezar su turno, devuelve false si no puede jugar
    public boolean empezarTurno() {
        defendiendo = false;
        if (turnosVeneno > 0) {
            System.out.println("   El veneno le hace danio a " + nombre);
            turnosVeneno--;
            recibirDanioDirecto(danioVeneno);
            if (!estaVivo()) {
                return false;
            }
        }
        if (turnosAturdido > 0) {
            turnosAturdido--;
            System.out.println("   " + nombre + " esta aturdido y pierde el turno.");
            return false;
        }
        return true;
    }

    // barrita de vida tipo [#######---]
    public String getBarraVida() {
        int llenos = vida * 10 / vidaMaxima;
        String barra = "[";
        for (int i = 0; i < 10; i++) {
            if (i < llenos) {
                barra = barra + "#";
            } else {
                barra = barra + "-";
            }
        }
        return barra + "] " + vida + "/" + vidaMaxima;
    }

    // getters y setters

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getVidaMaxima() { return vidaMaxima; }
    public void setVidaMaxima(int vidaMaxima) { this.vidaMaxima = vidaMaxima; }

    public int getVida() { return vida; }
    public void setVida(int vida) {
        if (vida < 0) vida = 0;
        if (vida > vidaMaxima) vida = vidaMaxima;
        this.vida = vida;
    }

    public int getAtaque() { return ataque; }
    public void setAtaque(int ataque) {
        if (ataque < 1) ataque = 1;
        this.ataque = ataque;
    }

    public int getDefensa() { return defensa; }
    public void setDefensa(int defensa) {
        if (defensa < 0) defensa = 0;
        this.defensa = defensa;
    }

    public int getProbCritico() { return probCritico; }
    public void setProbCritico(int probCritico) { this.probCritico = probCritico; }

    public int getProbEsquivar() { return probEsquivar; }
    public void setProbEsquivar(int probEsquivar) { this.probEsquivar = probEsquivar; }

    public boolean isDefendiendo() { return defendiendo; }
    public void setDefendiendo(boolean defendiendo) { this.defendiendo = defendiendo; }
}
