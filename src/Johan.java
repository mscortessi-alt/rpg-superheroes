// JOHAN - TIE 2do año, trabaja para la profe Diana
// Contraataque: cuando le pegas con ataque normal, 35% de que te lo devuelva
// limitacion: no puede contraatacar los poderes
public class Johan extends Villano {

    public Johan() {
        super("Johan", "Pegame si te animas. Te lo devuelvo.", 75, 16, 5, 5, 10, 20, 40);
    }

    public void actuar(Heroe heroe, Combate combate) {
        atacarNormal(heroe);
    }

    public void alSerAtacado(Heroe heroe, boolean fuePoder) {
        if (!fuePoder && estaVivo() && Dado.chance(35)) {
            System.out.println("CONTRAATAQUE! Johan te devuelve el golpe");
            golpear(heroe, 0.8, false);
        }
    }
}
