/**
 * Clase base abstracta para todos los personajes del juego (héroe y villanos).
 * Agrupa lo que tienen en común: nombre, vida, ataque y defensa.
 */
public abstract class Personaje {

    private String nombre;
    private int vidaMaxima;
    private int vida;
    private int ataque;
    private int defensa;

    public Personaje(String nombre, int vidaMaxima, int ataque, int defensa) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima; // todo personaje arranca con la vida llena
        this.ataque = ataque;
        this.defensa = defensa;
    }

    /**
     * Ataca a otro personaje. El daño es el ataque propio menos la defensa del
     * objetivo (mínimo 1 para que el combate siempre avance).
     * @return el daño realizado
     */
    public int atacar(Personaje objetivo) {
        int danio = Math.max(1, this.ataque - objetivo.getDefensa());
        objetivo.recibirDanio(danio);
        return danio;
    }

    /** Resta vida sin dejarla por debajo de 0. */
    public void recibirDanio(int danio) {
        vida = Math.max(0, vida - danio);
    }

    /** Suma vida sin pasar de la vida máxima. */
    public void curar(int cantidad) {
        vida = Math.min(vidaMaxima, vida + cantidad);
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    // ----- Getters y setters -----

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getVidaMaxima() { return vidaMaxima; }
    public void setVidaMaxima(int vidaMaxima) { this.vidaMaxima = vidaMaxima; }

    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = Math.max(0, Math.min(vidaMaxima, vida)); }

    public int getAtaque() { return ataque; }
    public void setAtaque(int ataque) { this.ataque = ataque; }

    public int getDefensa() { return defensa; }
    public void setDefensa(int defensa) { this.defensa = defensa; }

    @Override
    public String toString() {
        return nombre + " [Vida: " + vida + "/" + vidaMaxima + "]";
    }
}
