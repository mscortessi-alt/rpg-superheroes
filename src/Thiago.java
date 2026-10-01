// THIAGO - Rayo Acumulado
// cada ataque normal que pega le da una carga (max 5)
// el poder suelta todas las cargas juntas
// limitacion: si le pegan un critico pierde todas las cargas
public class Thiago extends Heroe {

    private int cargas;

    public Thiago() {
        super("Thiago", "Junta rayos con cada golpe y los suelta todos juntos.", 110, 20, 6, 15, 10);
        cargas = 0;
    }

    public int atacar(Personaje objetivo) {
        int danio = super.atacar(objetivo);
        if (danio > 0 && cargas < 5) {
            cargas++;
            System.out.println("   Thiago junta una carga (" + cargas + "/5)");
        }
        return danio;
    }

    public int recibirDanio(int danio, boolean critico) {
        if (critico && cargas > 0) {
            cargas = 0;
            System.out.println("   El critico descargo a Thiago, pierde todas sus cargas!");
        }
        return super.recibirDanio(danio, critico);
    }

    public String[] getPoderes() {
        String[] poderes = {"Rayo Acumulado"};
        return poderes;
    }

    public int getCostoPoder(int numero) {
        return 20;
    }

    public String getDescripcionPoder(int numero) {
        return "suelta " + cargas + " carga(s), no se puede esquivar";
    }

    // ademas de la energia necesita tener cargas
    public void validarPoder(int numero) throws AccionInvalidaException {
        super.validarPoder(numero);
        if (cargas == 0) {
            throw new AccionInvalidaException("No tenes cargas, primero ataca normal para juntar rayos");
        }
    }

    protected void usarPoder(int numero, Villano objetivo, Combate combate) {
        double multiplicador = 1.0 + cargas * 0.6;
        System.out.println("RAYO ACUMULADO! Thiago suelta " + cargas + " cargas sobre " + objetivo.getNombre());
        golpear(objetivo, multiplicador, false);
        cargas = 0;
    }

    public void finDeCombate() {
        super.finDeCombate();
        cargas = 0;
    }

    public int getCargas() {
        return cargas;
    }
}
