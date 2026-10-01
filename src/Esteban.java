// ESTEBAN - gemelo de Sebas
// pelea al lado de su hermano (2 contra 1)
// si Sebas cae, Esteban se enfurece (mas ataque, menos defensa)
public class Esteban extends Villano {

    public Esteban() {
        super("Esteban", "Donde va Sebas voy yo. Somos dos contra uno.", 92, 18, 7, 10, 10, 30, 50);
    }

    public void actuar(Heroe heroe, Combate combate) {
        atacarNormal(heroe);
    }

    public void companeroCaido(Villano caido) {
        if (caido instanceof Sebas) {
            System.out.println("   Esteban: \"SEBAS!! Ahora es personal.\"");
            enfurecer(7, 3);
        }
    }
}
