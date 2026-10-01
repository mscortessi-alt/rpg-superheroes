// SEBAS - el copiador (no tiene poderes propios)
// si te toca copia tu poder
// limitaciones: si esquivas no copia nada, el turno que copia no pega,
// y lo copiado lo usa una sola vez y a la mitad de fuerza
// pelea con su gemelo Esteban, si Esteban cae Sebas se enfurece
public class Sebas extends Villano {

    private boolean yaCopio;
    private String poderCopiado; // null si no tiene nada

    public Sebas() {
        super("Sebas", "No necesito poderes... me alcanza con los tuyos.", 92, 19, 6, 10, 10, 30, 50);
        yaCopio = false;
        poderCopiado = null;
    }

    public void actuar(Heroe heroe, Combate combate) {
        if (!yaCopio) {
            System.out.println("Sebas intenta tocarte para copiar tu poder...");
            if (Dado.chance(heroe.getProbEsquivar() + 15)) {
                System.out.println("   Lo esquivaste! No pudo copiar nada");
            } else {
                yaCopio = true;
                String[] poderes = heroe.getPoderes();
                poderCopiado = poderes[poderes.length - 1];
                System.out.println("   Te toco! Sebas copio tu " + poderCopiado);
            }
        } else if (poderCopiado != null) {
            System.out.println("Sebas usa TU " + poderCopiado + " contra vos (a la mitad de fuerza)");
            golpear(heroe, 1.3, false);
            poderCopiado = null; // ya no lo puede usar mas
        } else {
            atacarNormal(heroe);
        }
    }

    public void companeroCaido(Villano caido) {
        if (caido instanceof Esteban) {
            System.out.println("   Sebas: \"ESTEBAN!! Me las vas a pagar...\"");
            enfurecer(7, 3);
        }
    }
}
