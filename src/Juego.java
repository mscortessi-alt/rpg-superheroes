import java.util.LinkedList;
import java.util.Queue;

// Controla todo el juego: menu, elegir heroe, el recorrido por la UPA,
// la tienda y el ranking.
public class Juego {

    private Consola consola;
    private Ranking ranking;
    private Tienda tienda;
    private Heroe heroe;
    private String jugador;
    private Queue<Parada> recorrido; // cola: los lugares se visitan en orden
    private int villanosVencidos;

    public Juego() {
        consola = new Consola();
        ranking = new Ranking();
        tienda = new Tienda();
        recorrido = new LinkedList<Parada>();
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println();
            System.out.println("==================================================");
            System.out.println("              LA UPA FUE TOMADA");
            System.out.println("        RPG de superheroes - Grupo 8");
            System.out.println("==================================================");
            System.out.println("  1. Jugar");
            System.out.println("  2. Ver ranking");
            System.out.println("  3. Como se juega");
            System.out.println("  4. Salir");
            int opcion = consola.leerOpcionValida(1, 4);
            if (opcion == 1) {
                jugarPartida();
            } else if (opcion == 2) {
                System.out.println();
                ranking.mostrar();
                consola.pausa();
            } else if (opcion == 3) {
                mostrarAyuda();
            } else {
                System.out.println("Chau! La UPA te necesita.");
                salir = true;
            }
        }
    }

    private void jugarPartida() {
        jugador = consola.leerTexto("\nTu nombre (para el ranking): ");
        if (jugador.equals("")) {
            jugador = "Anonimo";
        }
        jugador = jugador.replace(";", ""); // el ; lo usamos en el archivo

        contarHistoria();
        heroe = elegirHeroe();
        heroe.agregarItem(new Item("Empanada", Item.CURACION, 35, 15));
        heroe.agregarItem(new Item("Alfajor", Item.ENERGIA, 25, 10));
        armarRecorrido();
        villanosVencidos = 0;

        boolean gano = false;
        boolean termino = false;
        while (!recorrido.isEmpty() && !termino) {
            Parada parada = recorrido.poll(); // saca el siguiente lugar de la cola
            consola.titulo(parada.getLugar());
            System.out.println(parada.getHistoria());
            consola.pausa();

            Combate combate = new Combate(heroe, parada.getVillanos(), consola);
            int resultado = combate.iniciar();

            if (resultado == Combate.GANADO) {
                villanosVencidos = villanosVencidos + parada.getVillanos().size();
                System.out.println();
                System.out.println(parada.getTextoFinal());
                if (recorrido.isEmpty()) {
                    gano = true;
                } else {
                    consola.pausa();
                    tienda.visitar(heroe, consola);
                }
            } else {
                termino = true;
            }
        }
        terminarPartida(gano);
    }

    private void contarHistoria() {
        consola.titulo("LA HISTORIA");
        System.out.println("Hace semanas que en el laboratorio de fisica pasan cosas raras:");
        System.out.println("luces prendidas a la madrugada, ruidos, olor a quemado.");
        System.out.println();
        System.out.println("Nadie sabia que la PROFE DIANA estaba construyendo el NUCLEO CUANTICO,");
        System.out.println("una maquina capaz de controlar la mente de las personas.");
        System.out.println("Su plan: convertir la UPA en SU universidad, donde todos obedezcan sin preguntar.");
        consola.pausa();
        System.out.println("El lunes a las 7 de la manana lo encendio.");
        System.out.println("Una onda de luz violeta recorrio todo el campus.");
        System.out.println();
        System.out.println("A casi todos los dejo bajo su control: Ruben, la Frau Ana Maria, la Cantinera,");
        System.out.println("los gemelos Sebas y Esteban y sus alumnos de TIE de 2do ano ahora le obedecen.");
        System.out.println();
        System.out.println("Pero a unos pocos estudiantes la onda les hizo algo distinto...");
        System.out.println("les dio SUPERPODERES.");
        consola.pausa();
        System.out.println("Ahora la UPA esta cerrada con candados y los pasillos estan vigilados.");
        System.out.println("La profe Diana espera en el auditorio con el Nucleo en sus manos.");
        System.out.println("Si no la detenes antes de que termine el dia, el control va a ser para siempre.");
        consola.pausa();
    }

    private Heroe elegirHeroe() {
        Heroe[] heroes = {new Thiago(), new Patus(), new Edisson(), new Jose(), new Mayra()};
        consola.titulo("ELEGI TU HEROE");
        for (int i = 0; i < heroes.length; i++) {
            Heroe h = heroes[i];
            System.out.println((i + 1) + ". " + h.getNombre().toUpperCase());
            System.out.println("   " + h.getDescripcion());
            System.out.println("   Vida " + h.getVidaMaxima() + " | Ataque " + h.getAtaque() + " | Defensa " + h.getDefensa()
                    + " | Critico " + h.getProbCritico() + "% | Esquivar " + h.getProbEsquivar() + "%");
            String[] poderes = h.getPoderes();
            for (int p = 0; p < poderes.length; p++) {
                System.out.println("   Poder: " + poderes[p] + " [" + h.getCostoPoder(p) + " energia] - " + h.getDescripcionPoder(p));
            }
            System.out.println();
        }
        int opcion = consola.leerOpcionValida(1, heroes.length);
        Heroe elegido = heroes[opcion - 1];
        System.out.println("\n" + elegido.getNombre() + " entra a la UPA por la puerta de atras...");
        return elegido;
    }

    // arma la cola con los lugares en orden
    private void armarRecorrido() {
        recorrido.clear();

        Parada p1 = new Parada("EDIFICIO 1 - PISO 1 - Oficina del CEFIUPA",
                "Hay carteles nuevos por todos lados: \"LA PROFE DIANA SIEMPRE TIENE RAZON\".\n"
                + "Ruben esta parado arriba de una silla, con un microfono y los ojos brillando violeta.",
                "Ruben se cae de la silla y se agarra la cabeza.\n"
                + "\"Que paso? Lo ultimo que me acuerdo es una luz violeta... y la profe Diana sonriendo.\"");
        p1.agregarVillano(new Ruben());
        recorrido.add(p1);

        Parada p2 = new Parada("EDIFICIO 1 - PISO 2 - Sala de idiomas",
                "En el pizarron dice \"Willkommen\" escrito cien veces.\n"
                + "La Frau Ana Maria cierra la puerta detras tuyo... y de repente hay tres de ella.",
                "Las copias desaparecen y la Frau se sienta, cansada.\n"
                + "\"Die Professorin Diana... ella tiene algo en el laboratorio de fisica. Anda rapido.\"");
        p2.agregarVillano(new FrauAnaMaria());
        recorrido.add(p2);

        Parada p3 = new Parada("PATIO - La Cantina",
                "Para cruzar al Edificio 2 tenes que pasar por la cantina.\n"
                + "Huele a empanadas... pero la Cantinera te mira fijo y afila su cucharon.",
                "La Cantinera suelta el cucharon.\n"
                + "\"Perdon, mi amor... tomate un cafe, invita la casa. Los gemelos estan en el laboratorio.\"");
        p3.agregarVillano(new Cantinera());
        recorrido.add(p3);

        Parada p4 = new Parada("EDIFICIO 2 - PISO 1 - Laboratorio de Fisica",
                "Aca empezo todo. Hay cables quemados y una vitrina vacia donde estaba el Nucleo.\n"
                + "Dos chicos identicos te esperan. Uno sonrie: \"Tu poder se ve lindo... lo quiero.\"",
                "Sebas y Esteban se miran confundidos.\n"
                + "\"La profe se llevo el Nucleo arriba. Dijo que si alguien llegaba hasta aca... lo paremos.\"\n"
                + "En el piso encontras un papel: \"Plan de la Profe D. - Paso 2: controlar a todo Paraguay.\"");
        p4.agregarVillano(new Sebas());
        p4.agregarVillano(new Esteban());
        recorrido.add(p4);

        Parada p5 = new Parada("EDIFICIO 2 - PISO 2",
                "Dos alumnas de TIE de 2do ano bloquean el pasillo.\n"
                + "\"La profe dijo que si te paramos nos exonera del final.\"",
                "Luciana y Meybel se despiertan. \"Exonerar?? La profe Diana NUNCA exonera...\"");
        p5.agregarVillano(new Luciana());
        p5.agregarVillano(new Meybel());
        recorrido.add(p5);

        Parada p6 = new Parada("EDIFICIO 2 - PISO 3",
                "Roger y Johan cuidan la escalera. Desde arriba se escucha un zumbido cada vez mas fuerte.",
                "Roger se sienta en el piso. \"El zumbido es el Nucleo. Cuanto mas tiempo pasa, mas fuerte se hace.\"");
        p6.agregarVillano(new Roger());
        p6.agregarVillano(new Johan());
        recorrido.add(p6);

        Parada p7 = new Parada("EDIFICIO 2 - PISO 4",
                "Solo queda un guardia antes del auditorio: Juan Ubaldo.\n"
                + "Detras de el, la puerta del auditorio brilla violeta.",
                "Juan Ubaldo se corre de la puerta.\n"
                + "\"Tene cuidado. Cuando la profe esta en peligro... usa el Nucleo en ella misma.\"");
        p7.agregarVillano(new JuanUbaldo());
        recorrido.add(p7);

        Parada p8 = new Parada("EDIFICIO 2 - PISO 5 - AUDITORIO",
                "Las luces se apagan. Solo se ve el Nucleo Cuantico brillando en el escenario.\n"
                + "La profe Diana esta sentada en el escritorio, corrigiendo examenes con una birome violeta.\n"
                + "Sin levantar la vista dice: \"Llegas tarde.\"",
                "El Nucleo Cuantico se rompe en mil pedazos.");
        p8.agregarVillano(new ProfeDiana());
        recorrido.add(p8);
    }

    private void terminarPartida(boolean gano) {
        if (gano) {
            consola.titulo("GANASTE! " + heroe.getNombre().toUpperCase() + " SALVO LA UPA");
            System.out.println("La onda violeta se apaga y todos en el campus se despiertan.");
            System.out.println("La profe Diana mira los pedazos del Nucleo y dice:");
            System.out.println("\"Esto no termina aca... y no te olvides que el lunes hay parcial.\"");
            System.out.println();
            System.out.println("Al otro dia la UPA abre como siempre. Nadie se acuerda de nada...");
            System.out.println("menos vos, que todavia sentis los poderes en las manos.");
        } else if (!heroe.estaVivo()) {
            consola.titulo("PERDISTE");
            System.out.println(heroe.getNombre() + " cae al piso. La luz violeta lo envuelve...");
            System.out.println("Ahora vos tambien obedeces a la profe Diana.");
        } else {
            consola.titulo("HUISTE");
            System.out.println("Saliste de la UPA corriendo. Quedaron " + (recorrido.size() + 1) + " lugares sin liberar.");
        }

        int puntaje = calcularPuntaje(gano);
        System.out.println("\nVillanos vencidos: " + villanosVencidos + " | Nivel: " + heroe.getNivel()
                + " | Monedas: " + heroe.getMonedas());
        System.out.println("PUNTAJE FINAL: " + puntaje);

        RegistroPuntaje anterior = ranking.buscarMejorDe(jugador);
        RegistroPuntaje nuevo = new RegistroPuntaje(jugador, heroe.getNombre(), puntaje, gano);
        ranking.agregar(nuevo);
        if (anterior != null && puntaje > anterior.getPuntaje()) {
            System.out.println("Nuevo record! (tu mejor puntaje era " + anterior.getPuntaje() + ")");
        }
        System.out.println("Quedaste en el puesto " + ranking.getPuesto(nuevo) + " del ranking\n");
        ranking.mostrar();
        consola.pausa();
    }

    // puntaje = villanos*100 + nivel*50 + vida + monedas (+1000 si gano)
    private int calcularPuntaje(boolean gano) {
        int puntaje = villanosVencidos * 100 + heroe.getNivel() * 50 + heroe.getVida() + heroe.getMonedas();
        if (gano) {
            puntaje = puntaje + 1000;
        }
        return puntaje;
    }

    private void mostrarAyuda() {
        consola.titulo("COMO SE JUEGA");
        System.out.println("- Elegis un heroe y vas recorriendo la UPA piso por piso, peleando por turnos.");
        System.out.println("- En tu turno podes atacar, usar tu poder (gasta energia), usar un item,");
        System.out.println("  defenderte (recibis la mitad) o ver tu mochila.");
        System.out.println("- Recuperas " + Heroe.ENERGIA_POR_TURNO + " de energia por ronda.");
        System.out.println("- Los golpes pueden ser CRITICOS (doble) o los pueden esquivar.");
        System.out.println("- Cada villano tiene una habilidad especial, lee bien lo que pasa!");
        System.out.println("- Cuando ganas te dan experiencia (subis de nivel) y monedas para la cantina.");
        System.out.println("- Para usar un item escribi el nombre (ej: empanada).");
        System.out.println("- Al final tu puntaje se guarda en el ranking.");
        consola.pausa();
    }
}
