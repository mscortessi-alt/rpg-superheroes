import java.util.ArrayList;

// Un lugar del recorrido (un piso, el patio, el auditorio) con sus villanos
// historia: lo que se cuenta al llegar
// final: lo que pasa cuando los vences
public class Parada {

    private String lugar;
    private String historia;
    private String textoFinal;
    private ArrayList<Villano> villanos;

    public Parada(String lugar, String historia, String textoFinal) {
        this.lugar = lugar;
        this.historia = historia;
        this.textoFinal = textoFinal;
        villanos = new ArrayList<Villano>();
    }

    public void agregarVillano(Villano villano) {
        villanos.add(villano);
    }

    public String getLugar() { return lugar; }
    public String getHistoria() { return historia; }
    public String getTextoFinal() { return textoFinal; }
    public ArrayList<Villano> getVillanos() { return villanos; }
}
