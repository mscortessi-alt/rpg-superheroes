// ROGER - TIE 2do año, trabaja para la profe Diana
// Golpe Pesado: pega el doble
// limitacion: despues queda cansado y pierde el siguiente turno
public class Roger extends Villano {

    private int turnosParaGolpe;
    private boolean cansado;

    public Roger() {
        super("Roger", "Un solo golpe. Es todo lo que necesito.", 82, 18, 4, 5, 5, 20, 40);
        turnosParaGolpe = 1;
        cansado = false;
    }

    public void actuar(Heroe heroe, Combate combate) {
        if (cansado) {
            System.out.println("Roger esta cansado despues del golpe y no hace nada");
            cansado = false;
        } else if (turnosParaGolpe == 0) {
            System.out.println("GOLPE PESADO! Roger levanta los dos punos...");
            golpear(heroe, 2.0, true);
            cansado = true;
            turnosParaGolpe = 2;
        } else {
            turnosParaGolpe--;
            atacarNormal(heroe);
        }
    }
}
