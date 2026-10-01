import java.util.ArrayList;

// LUCIANA - TIE 2do año, trabaja para la profe Diana
// Apuntes Robados: cada 3 turnos te roba un item
// limitacion: si la vences te devuelve todo
public class Luciana extends Villano {

    private ArrayList<Item> robados;
    private int turnosParaRobar;

    public Luciana() {
        super("Luciana", "Lo que es tuyo... ahora esta en mis apuntes.", 70, 16, 4, 5, 10, 20, 35);
        robados = new ArrayList<Item>();
        turnosParaRobar = 0;
    }

    public void actuar(Heroe heroe, Combate combate) {
        if (turnosParaRobar == 0) {
            Item robado = heroe.quitarItemAlAzar();
            if (robado != null) {
                robados.add(robado);
                System.out.println("APUNTES ROBADOS: Luciana te saca " + robado.getNombre() + " de la mochila");
                turnosParaRobar = 3;
                return;
            }
        } else {
            turnosParaRobar--;
        }
        atacarNormal(heroe);
    }

    public void alSerDerrotado(Heroe heroe) {
        for (int i = 0; i < robados.size(); i++) {
            heroe.agregarItem(robados.get(i));
            System.out.println("   Recuperas tu " + robados.get(i).getNombre());
        }
        robados.clear();
    }
}
