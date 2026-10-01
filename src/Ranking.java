import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Ranking de puntajes, se guarda en el archivo ranking.txt
// asi no se borra cuando cerras el juego
public class Ranking {

    private ArrayList<RegistroPuntaje> registros;

    public Ranking() {
        registros = new ArrayList<RegistroPuntaje>();
        cargar();
    }

    // lee el archivo linea por linea
    public void cargar() {
        registros.clear();
        File archivo = new File("ranking.txt");
        if (!archivo.exists()) {
            return; // todavia nadie jugo
        }
        try {
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                if (!linea.equals("")) {
                    try {
                        registros.add(RegistroPuntaje.desdeLinea(linea));
                    } catch (AccionInvalidaException e) {
                        System.out.println("  [!] Se salteo una linea rota del ranking");
                    }
                }
            }
            lector.close();
        } catch (FileNotFoundException e) {
            System.out.println("  [!] No se pudo abrir el ranking");
        }
    }

    public void agregar(RegistroPuntaje registro) {
        registros.add(registro);
        ordenarPorPuntaje();
        guardar();
    }

    // escribe todo el ranking en el archivo
    private void guardar() {
        try {
            FileWriter escritor = new FileWriter("ranking.txt");
            for (int i = 0; i < registros.size(); i++) {
                escritor.write(registros.get(i).aLinea() + "\n");
            }
            escritor.close();
        } catch (IOException e) {
            System.out.println("  [!] No se pudo guardar el ranking");
        }
    }

    // ORDENAMIENTO POR SELECCION: busca el mas alto y lo pone primero, despues el segundo, etc
    public void ordenarPorPuntaje() {
        for (int i = 0; i < registros.size() - 1; i++) {
            int mayor = i;
            for (int j = i + 1; j < registros.size(); j++) {
                if (registros.get(j).getPuntaje() > registros.get(mayor).getPuntaje()) {
                    mayor = j;
                }
            }
            RegistroPuntaje aux = registros.get(i);
            registros.set(i, registros.get(mayor));
            registros.set(mayor, aux);
        }
    }

    // BUSQUEDA LINEAL: el mejor puntaje de un jugador
    // como esta ordenado, el primero que encuentra es el mejor
    public RegistroPuntaje buscarMejorDe(String jugador) {
        for (int i = 0; i < registros.size(); i++) {
            if (registros.get(i).getJugador().equalsIgnoreCase(jugador)) {
                return registros.get(i);
            }
        }
        return null;
    }

    public int getPuesto(RegistroPuntaje registro) {
        return registros.indexOf(registro) + 1;
    }

    public void mostrar() {
        System.out.println("RANKING - TOP 5");
        if (registros.size() == 0) {
            System.out.println("   Todavia no jugo nadie");
            return;
        }
        int cantidad = registros.size();
        if (cantidad > 5) {
            cantidad = 5;
        }
        for (int i = 0; i < cantidad; i++) {
            RegistroPuntaje r = registros.get(i);
            String texto = "   " + (i + 1) + ". " + r.getJugador() + " (" + r.getHeroe() + ") - " + r.getPuntaje() + " pts";
            if (r.isGano()) {
                texto = texto + " - salvo la UPA";
            }
            System.out.println(texto);
        }
    }
}
