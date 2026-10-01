// Clase padre de los villanos.
// Cada villano hace su turno distinto en actuar() (polimorfismo).
// Los otros metodos estan vacios y cada villano sobreescribe el que necesita.
public abstract class Villano extends Personaje {

    private String frase;
    private int recompensaMonedas;
    private int recompensaExperiencia;
    private boolean enfurecido;
    private boolean recompensaEntregada;

    public Villano(String nombre, String frase, int vidaMaxima, int ataque, int defensa, int probCritico,
                   int probEsquivar, int recompensaMonedas, int recompensaExperiencia) {
        super(nombre, vidaMaxima, ataque, defensa, probCritico, probEsquivar);
        this.frase = frase;
        this.recompensaMonedas = recompensaMonedas;
        this.recompensaExperiencia = recompensaExperiencia;
    }

    // el turno del villano
    public abstract void actuar(Heroe heroe, Combate combate);

    protected void atacarNormal(Heroe heroe) {
        System.out.println(getNombre() + " ataca a " + heroe.getNombre());
        atacar(heroe);
    }

    // antes de que el heroe le pegue. Si devuelve false el ataque se pierde (lo usa la Frau)
    public boolean interceptarAtaque(Heroe heroe, Consola consola) {
        return true;
    }

    // despues de que el heroe le pega (lo usa Johan)
    public void alSerAtacado(Heroe heroe, boolean fuePoder) {
    }

    // cuando cae otro villano de la misma pelea (lo usan los gemelos)
    public void companeroCaido(Villano caido) {
    }

    // cuando el heroe lo derrota (lo usan Luciana y la Cantinera)
    public void alSerDerrotado(Heroe heroe) {
    }

    // mas ataque y menos defensa
    protected void enfurecer(int masAtaque, int menosDefensa) {
        if (!enfurecido) {
            enfurecido = true;
            setAtaque(getAtaque() + masAtaque);
            setDefensa(getDefensa() - menosDefensa);
            System.out.println("   " + getNombre() + " SE ENFURECE! (+" + masAtaque + " ataque, -" + menosDefensa + " defensa)");
        }
    }

    public String getFrase() { return frase; }
    public void setFrase(String frase) { this.frase = frase; }

    public int getRecompensaMonedas() { return recompensaMonedas; }
    public int getRecompensaExperiencia() { return recompensaExperiencia; }

    public boolean isEnfurecido() { return enfurecido; }

    public boolean isRecompensaEntregada() { return recompensaEntregada; }
    public void setRecompensaEntregada(boolean recompensaEntregada) { this.recompensaEntregada = recompensaEntregada; }
}
