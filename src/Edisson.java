// EDISSON - Fenix
// si se queda sin vida revive con el 40%
// limitacion: solo una vez en toda la partida
// su poder es la Llamarada del Fenix (golpe fuerte de fuego)
public class Edisson extends Heroe {

    private boolean fenixUsado;

    public Edisson() {
        super("Edisson", "Es un fenix, si cae revive una vez.", 115, 17, 7, 10, 10);
        fenixUsado = false;
    }

    // en vez de morir, revive (si todavia no lo uso)
    protected void alQuedarSinVida() {
        if (!fenixUsado) {
            fenixUsado = true;
            setVida(getVidaMaxima() * 40 / 100);
            System.out.println("   EDISSON REVIVE DE SUS CENIZAS COMO UN FENIX! (" + getVida() + "/" + getVidaMaxima() + ")");
            System.out.println("   (ya no puede revivir otra vez)");
        } else {
            super.alQuedarSinVida();
        }
    }

    public String[] getPoderes() {
        String[] poderes = {"Llamarada del Fenix"};
        return poderes;
    }

    public int getCostoPoder(int numero) {
        return 40;
    }

    public String getDescripcionPoder(int numero) {
        if (fenixUsado) {
            return "golpe de fuego x1.6 (fenix ya usado)";
        }
        return "golpe de fuego x1.6 (fenix disponible)";
    }

    protected void usarPoder(int numero, Villano objetivo, Combate combate) {
        System.out.println("LLAMARADA DEL FENIX! Edisson prende fuego a " + objetivo.getNombre());
        golpear(objetivo, 1.6, true);
    }

    public boolean isFenixUsado() {
        return fenixUsado;
    }
}
