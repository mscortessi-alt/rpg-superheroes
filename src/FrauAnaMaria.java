// FRAU ANA MARIA - coordinadora de aleman
// Ilusion: hace 2 copias de ella. Mientras esten, para pegarle tenes que adivinar
// cual es la real (1, 2 o 3). Si le erras le pegas a una copia y perdes el ataque.
// limitacion: las copias duran 2 turnos y despues tiene que esperar 2 turnos
public class FrauAnaMaria extends Villano {

    private int turnosIlusion;
    private int esperaIlusion;

    public FrauAnaMaria() {
        super("Frau Ana Maria", "Ruhe, bitte! Cual de nosotras es la verdadera?", 92, 18, 5, 10, 10, 35, 55);
        turnosIlusion = 0;
        esperaIlusion = 1;
    }

    public void actuar(Heroe heroe, Combate combate) {
        if (turnosIlusion > 0) {
            turnosIlusion--;
            if (turnosIlusion == 0) {
                System.out.println("   Las copias de la Frau desaparecen");
                esperaIlusion = 2;
            }
            atacarNormal(heroe);
        } else if (esperaIlusion == 0) {
            turnosIlusion = 2;
            System.out.println("ILUSION! Aparecen TRES Frau Ana Maria iguales...");
            System.out.println("   Para pegarle vas a tener que adivinar cual es la real");
        } else {
            esperaIlusion--;
            atacarNormal(heroe);
        }
    }

    public boolean interceptarAtaque(Heroe heroe, Consola consola) {
        if (turnosIlusion == 0) {
            return true;
        }
        System.out.println("Hay tres Frau Ana Maria: [1] [2] [3]");
        System.out.println("Cual es la verdadera?");
        int eleccion = consola.leerOpcionValida(1, 3);
        int real = Dado.entre(1, 3);
        if (eleccion == real) {
            System.out.println("   Bien! Era la " + real);
            return true;
        }
        System.out.println("   Le pegaste a una copia... la verdadera era la " + real);
        return false;
    }
}
