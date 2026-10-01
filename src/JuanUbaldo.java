// JUAN UBALDO - TIE 2do año, el ultimo guardia antes del auditorio
// cuando le queda menos del 40% de vida se pone mas fuerte (+8 ataque)
// limitacion: al mismo tiempo pierde 5 de defensa
public class JuanUbaldo extends Villano {

    public JuanUbaldo() {
        super("Juan Ubaldo", "Nadie pasa al auditorio. Nadie.", 125, 21, 8, 10, 10, 35, 70);
    }

    public void actuar(Heroe heroe, Combate combate) {
        if (!isEnfurecido() && getVida() < getVidaMaxima() * 40 / 100) {
            System.out.println("EL ULTIMO GUARDIA: \"No vas a llegar a la profe!\"");
            enfurecer(8, 5);
        }
        atacarNormal(heroe);
    }
}
