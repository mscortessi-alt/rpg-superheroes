// Una fila del ranking
public class RegistroPuntaje {

    private String jugador;
    private String heroe;
    private int puntaje;
    private boolean gano;

    public RegistroPuntaje(String jugador, String heroe, int puntaje, boolean gano) {
        this.jugador = jugador;
        this.heroe = heroe;
        this.puntaje = puntaje;
        this.gano = gano;
    }

    // asi se guarda en el archivo: jugador;heroe;puntaje;gano
    public String aLinea() {
        return jugador + ";" + heroe + ";" + puntaje + ";" + gano;
    }

    // arma un registro a partir de una linea del archivo
    public static RegistroPuntaje desdeLinea(String linea) throws AccionInvalidaException {
        String[] partes = linea.split(";");
        if (partes.length != 4) {
            throw new AccionInvalidaException("Linea mal escrita en el ranking");
        }
        try {
            int puntos = Integer.parseInt(partes[2]);
            boolean gano = partes[3].equals("true");
            return new RegistroPuntaje(partes[0], partes[1], puntos, gano);
        } catch (NumberFormatException e) {
            throw new AccionInvalidaException("Puntaje mal escrito en el ranking");
        }
    }

    public String getJugador() { return jugador; }
    public String getHeroe() { return heroe; }
    public int getPuntaje() { return puntaje; }
    public boolean isGano() { return gano; }
}
