// PROFE DIANA - la villana principal, la que esta detras de todo
// Pelea en 2 fases.
// Fase 1:
//   Control de Clase: 35% de bloquearte el poder por 2 turnos
// Fase 2 (cuando llega a la mitad de vida usa el Nucleo Cuantico):
//   Recuperatorio: si le queda menos del 30% se cura, una sola vez
//   Nota Final: avisa un turno antes y despues pega muy fuerte (conviene defenderse)
public class ProfeDiana extends Villano {

    private boolean faseDos;
    private boolean recuperatorioUsado;
    private boolean cargandoNotaFinal;
    private int turnosParaNotaFinal;

    public ProfeDiana() {
        super("Profe Diana", "Asi que vos sos el que anda liberando mi universidad... Sentate. La clase recien empieza.",
                270, 25, 11, 12, 8, 0, 0);
        faseDos = false;
        recuperatorioUsado = false;
        cargandoNotaFinal = false;
        turnosParaNotaFinal = 1;
    }

    public void actuar(Heroe heroe, Combate combate) {
        if (!faseDos && getVida() <= getVidaMaxima() / 2) {
            activarNucleo();
            return; // activar el nucleo ocupa su turno
        }
        if (!faseDos) {
            faseUno(heroe);
        } else {
            faseDos(heroe);
        }
    }

    private void faseUno(Heroe heroe) {
        if (Dado.chance(35)) {
            System.out.println("CONTROL DE CLASE: \"Nada de poderes en mi clase.\"");
            System.out.println("   Tu poder queda bloqueado por 2 turnos");
            heroe.bloquearPoder(2);
        }
        atacarNormal(heroe);
    }

    private void faseDos(Heroe heroe) {
        // recuperatorio
        if (!recuperatorioUsado && getVida() < getVidaMaxima() * 30 / 100) {
            recuperatorioUsado = true;
            System.out.println("RECUPERATORIO! \"Todos merecen una segunda oportunidad... sobre todo yo.\"");
            curar(getVidaMaxima() * 30 / 100);
            return;
        }
        // nota final: un turno avisa y el siguiente pega
        if (cargandoNotaFinal) {
            cargandoNotaFinal = false;
            turnosParaNotaFinal = 3;
            System.out.println("NOTA FINAL!! La profe Diana descarga todo el poder del Nucleo");
            golpear(heroe, 2.5, false);
            return;
        }
        if (turnosParaNotaFinal == 0) {
            cargandoNotaFinal = true;
            System.out.println("La profe Diana empieza a escribir tu NOTA FINAL en el pizarron...");
            System.out.println("   CUIDADO! El proximo turno viene un ataque muy fuerte (te conviene defenderte)");
            return;
        }
        turnosParaNotaFinal--;
        atacarNormal(heroe);
    }

    private void activarNucleo() {
        faseDos = true;
        System.out.println();
        System.out.println("   La profe Diana saca el Nucleo Cuantico de su cartera...");
        System.out.println("   \"Pense que no iba a necesitar esto con un alumno... pero sos mas molesto de lo que crei.\"");
        System.out.println("   El Nucleo brilla y las luces del auditorio explotan una por una.");
        System.out.println();
        setAtaque(getAtaque() + 4);
        System.out.println("   FASE 2: la profe Diana absorbe el poder del Nucleo (+4 ataque)");
    }

    public boolean isFaseDos() {
        return faseDos;
    }
}
