// MEYBEL - TIE 2do año, trabaja para la profe Diana
// Curita: le cura 25 de vida al compañero mas herido
// limitacion: solo puede curar 2 veces
public class Meybel extends Villano {

    private int curasUsadas;

    public Meybel() {
        super("Meybel", "Mientras yo este aca nadie de mi equipo cae.", 62, 14, 4, 5, 10, 20, 35);
        curasUsadas = 0;
    }

    public void actuar(Heroe heroe, Combate combate) {
        if (curasUsadas < 2) {
            // busca al compañero con menos de la mitad de vida que este mas herido
            Villano masHerido = null;
            for (Villano v : combate.getEnemigosVivos()) {
                if (v != this && v.getVida() < v.getVidaMaxima() / 2) {
                    if (masHerido == null || v.getVida() < masHerido.getVida()) {
                        masHerido = v;
                    }
                }
            }
            if (masHerido != null) {
                curasUsadas++;
                System.out.println("CURITA: Meybel cura a " + masHerido.getNombre() + " (" + curasUsadas + "/2)");
                masHerido.curar(25);
                return;
            }
        }
        atacarNormal(heroe);
    }
}
