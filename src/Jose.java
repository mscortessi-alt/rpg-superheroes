import java.util.ArrayList;

// JOSE - Grito Sonico
// le pega, lo aturde 1 turno y le baja 3 de defensa
// limitacion: el grito tambien le hace danio a el (6)
// y a cada villano solo le puede bajar la defensa una vez
public class Jose extends Heroe {

    private ArrayList<Villano> debilitados; // a los que ya les bajo la defensa

    public Jose() {
        super("Jose", "Su grito aturde a cualquiera, hasta a el mismo.", 105, 19, 7, 12, 12);
        debilitados = new ArrayList<Villano>();
    }

    public String[] getPoderes() {
        String[] poderes = {"Grito Sonico"};
        return poderes;
    }

    public int getCostoPoder(int numero) {
        return 45;
    }

    public String getDescripcionPoder(int numero) {
        return "danio normal + aturde 1 turno + baja 3 de defensa (te quita 6 de vida)";
    }

    protected void usarPoder(int numero, Villano objetivo, Combate combate) {
        System.out.println("GRITO SONICO! Jose grita tan fuerte que tiemblan las ventanas");
        golpear(objetivo, 1.0, false);
        if (objetivo.estaVivo()) {
            objetivo.aturdir(1);
        }
        if (!debilitados.contains(objetivo)) {
            debilitados.add(objetivo);
            objetivo.setDefensa(objetivo.getDefensa() - 3);
            System.out.println("   La defensa de " + objetivo.getNombre() + " baja a " + objetivo.getDefensa());
        }
        System.out.println("   El eco tambien le duele a Jose...");
        recibirDanioDirecto(6);
    }
}
