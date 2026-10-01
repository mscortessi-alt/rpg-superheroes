// RUBEN - presidente del CEFIUPA
// Discurso Eterno: habla tanto que el heroe se aburre y pega con el 70% por 3 turnos
// limitacion: mientras habla no ataca, y lo hace cada 3 turnos
public class Ruben extends Villano {

    private int turnosParaDiscurso;

    public Ruben() {
        super("Ruben", "Como presidente del CEFIUPA declaro esta universidad... TOMADA!", 78, 16, 4, 5, 5, 30, 45);
        turnosParaDiscurso = 1;
    }

    public void actuar(Heroe heroe, Combate combate) {
        if (turnosParaDiscurso == 0) {
            System.out.println("Ruben empieza su DISCURSO ETERNO: \"Companeros, en primer lugar, en segundo lugar, en vigesimo lugar...\"");
            System.out.println("   " + heroe.getNombre() + " se aburre, su ataque baja al 70% por 3 turnos");
            heroe.reducirAtaque(3);
            turnosParaDiscurso = 3;
        } else {
            atacarNormal(heroe);
            turnosParaDiscurso--;
        }
    }
}
