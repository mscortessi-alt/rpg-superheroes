import java.util.ArrayList;

// La tienda que aparece entre piso y piso
// muestra las cosas ordenadas por precio (ORDENAMIENTO POR INSERCION)
public class Tienda {

    private ArrayList<Item> productos;

    public Tienda() {
        productos = new ArrayList<Item>();
        productos.add(new Item("Sandwich", Item.CURACION, 70, 30));
        productos.add(new Item("Cafe", Item.ENERGIA, 50, 20));
        productos.add(new Item("Calculadora", Item.ATAQUE, 4, 45));
        productos.add(new Item("Empanada", Item.CURACION, 35, 15));
        productos.add(new Item("Mochila reforzada", Item.DEFENSA, 3, 40));
        productos.add(new Item("Alfajor", Item.ENERGIA, 25, 10));
        ordenarPorPrecio();
    }

    // ORDENAMIENTO POR INSERCION: agarra cada producto y lo mete
    // en su lugar entre los que ya estan ordenados (de mas barato a mas caro)
    public void ordenarPorPrecio() {
        for (int i = 1; i < productos.size(); i++) {
            Item actual = productos.get(i);
            int j = i - 1;
            while (j >= 0 && productos.get(j).getPrecio() > actual.getPrecio()) {
                productos.set(j + 1, productos.get(j));
                j--;
            }
            productos.set(j + 1, actual);
        }
    }

    public void comprar(int numero, Heroe heroe) throws AccionInvalidaException {
        Item elegido = productos.get(numero - 1);
        heroe.pagar(elegido.getPrecio());
        heroe.agregarItem(elegido.copiar());
        System.out.println("   Compraste " + elegido.getNombre() + ". Te quedan " + heroe.getMonedas() + " monedas.");
    }

    public void visitar(Heroe heroe, Consola consola) {
        consola.titulo("CANTINA");
        while (true) {
            System.out.println("Tenes " + heroe.getMonedas() + " monedas | Vida " + heroe.getBarraVida()
                    + " | Energia " + heroe.getEnergia());
            for (int i = 0; i < productos.size(); i++) {
                Item p = productos.get(i);
                System.out.println("  " + (i + 1) + ". " + p.getNombre() + " (" + p.getEfecto() + ") - " + p.getPrecio() + " monedas");
            }
            System.out.println("  0. Seguir");
            try {
                int opcion = consola.leerOpcion(0, productos.size());
                if (opcion == 0) {
                    return;
                }
                comprar(opcion, heroe);
            } catch (AccionInvalidaException e) {
                consola.mostrarError(e.getMessage());
            }
        }
    }
}
