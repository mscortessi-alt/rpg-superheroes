// MAYRA - Detener el Tiempo
// congela todo y juega 2 turnos seguidos
// limitacion: cuesta 50 de energia y despues tiene que esperar 3 rondas para usarlo otra vez
public class Mayra extends Heroe {

    private int esperaTiempo; // rondas que faltan para poder detener el tiempo otra vez

    public Mayra() {
        super("Mayra", "Puede detener el tiempo y jugar dos veces seguidas.", 120, 21, 8, 15, 12);
        esperaTiempo = 0;
    }

    public String[] getPoderes() {
        String[] poderes = {"Detener el Tiempo"};
        return poderes;
    }

    public int getCostoPoder(int numero) {
        return 50;
    }

    public String getDescripcionPoder(int numero) {
        if (esperaTiempo > 0) {
            return "jugas 2 turnos seguidos (faltan " + esperaTiempo + " rondas)";
        }
        return "jugas 2 turnos seguidos (listo)";
    }

    // no necesita elegir a quien, solo congela el tiempo
    public boolean poderEsOfensivo(int numero) {
        return false;
    }

    // ademas de la energia tiene que haber pasado la espera
    public void validarPoder(int numero) throws AccionInvalidaException {
        super.validarPoder(numero);
        if (esperaTiempo > 0) {
            throw new AccionInvalidaException("Todavia no podes detener el tiempo, faltan " + esperaTiempo + " rondas");
        }
    }

    protected void usarPoder(int numero, Villano objetivo, Combate combate) {
        System.out.println("DETENER EL TIEMPO! Todo se congela menos Mayra...");
        setTurnoExtra(true);
        esperaTiempo = 3;
    }

    public void finDeRonda() {
        super.finDeRonda();
        if (esperaTiempo > 0) {
            esperaTiempo--;
        }
    }

    public void finDeCombate() {
        super.finDeCombate();
        esperaTiempo = 0;
    }
}
