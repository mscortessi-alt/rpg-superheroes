// LA CANTINERA
// Menu del Dia: te ofrece comida y elegis si aceptar. 50% te cura y 50% te envenena.
// Precio Inflado: cada vez que te pega te saca 10 monedas.
// limitacion: si la vences te devuelve todo lo que te saco
public class Cantinera extends Villano {

    private int monedasRobadas;
    private int turnosParaMenu;

    public Cantinera() {
        super("La Cantinera", "Vas a querer algo o vas a seguir haciendo fila?", 105, 19, 6, 10, 5, 40, 60);
        monedasRobadas = 0;
        turnosParaMenu = 1;
    }

    public void actuar(Heroe heroe, Combate combate) {
        if (turnosParaMenu == 0) {
            ofrecerMenu(heroe, combate.getConsola());
            turnosParaMenu = 3;
        } else {
            turnosParaMenu--;
            System.out.println("La Cantinera te pega con el cucharon");
            int danio = atacar(heroe);
            if (danio > 0) {
                int sacadas = heroe.perderMonedas(10);
                if (sacadas > 0) {
                    monedasRobadas = monedasRobadas + sacadas;
                    System.out.println("   PRECIO INFLADO: te cobra " + sacadas + " monedas");
                }
            }
        }
    }

    private void ofrecerMenu(Heroe heroe, Consola consola) {
        System.out.println("MENU DEL DIA: \"Tengo un guiso especial... queres?\"");
        System.out.println("   1. Aceptar (50% te cura, 50% te envenena)");
        System.out.println("   2. No gracias");
        int opcion = consola.leerOpcionValida(1, 2);
        if (opcion == 2) {
            System.out.println("   La Cantinera se ofende y no hace nada este turno");
        } else if (Dado.chance(50)) {
            System.out.println("   Estaba riquisimo!");
            heroe.curar(30);
        } else {
            System.out.println("   Algo no estaba bien en ese guiso...");
            heroe.envenenar(3, 6);
        }
    }

    public void alSerDerrotado(Heroe heroe) {
        if (monedasRobadas > 0) {
            heroe.ganarMonedas(monedasRobadas);
            System.out.println("   Recuperas las " + monedasRobadas + " monedas que te cobro de mas");
            monedasRobadas = 0;
        }
    }
}
