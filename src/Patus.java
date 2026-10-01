// PATUS - Gravedad
// le saca el 35% de la vida que le queda al villano (no importa la defensa)
// sirve mucho contra villanos con mucha vida como la profe Diana
// limitacion: si al villano le queda poca vida casi no hace nada (minimo 12)
public class Patus extends Heroe {

    public Patus() {
        super("Patus", "Controla la gravedad, mientras mas grande el enemigo mas fuerte cae.", 115, 20, 6, 10, 20);
    }

    public String[] getPoderes() {
        String[] poderes = {"Gravedad"};
        return poderes;
    }

    public int getCostoPoder(int numero) {
        return 30;
    }

    public String getDescripcionPoder(int numero) {
        return "le saca el 35% de la vida que le queda al villano";
    }

    protected void usarPoder(int numero, Villano objetivo, Combate combate) {
        int danio = objetivo.getVida() * 35 / 100;
        if (danio < 12) {
            danio = 12;
        }
        System.out.println("GRAVEDAD! Patus aplasta a " + objetivo.getNombre() + " contra el piso");
        objetivo.recibirDanioDirecto(danio);
    }
}
