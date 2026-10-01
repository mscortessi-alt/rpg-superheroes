// Un item que se puede usar en la pelea o comprar en la tienda
// tipos: CURACION (da vida), ENERGIA (da energia), ATAQUE y DEFENSA (suben para siempre)
public class Item {

    public static final String CURACION = "CURACION";
    public static final String ENERGIA = "ENERGIA";
    public static final String ATAQUE = "ATAQUE";
    public static final String DEFENSA = "DEFENSA";

    private String nombre;
    private String tipo;
    private int poder;
    private int precio;

    public Item(String nombre, String tipo, int poder, int precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.poder = poder;
        this.precio = precio;
    }

    // la tienda vende una copia, no su propio item
    public Item copiar() {
        return new Item(nombre, tipo, poder, precio);
    }

    // compara el nombre sin importar mayusculas
    public boolean seLlama(String texto) {
        return nombre.equalsIgnoreCase(texto.trim());
    }

    public String getEfecto() {
        if (tipo.equals(CURACION)) {
            return "+" + poder + " vida";
        } else if (tipo.equals(ENERGIA)) {
            return "+" + poder + " energia";
        } else if (tipo.equals(ATAQUE)) {
            return "+" + poder + " ataque";
        } else {
            return "+" + poder + " defensa";
        }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getPoder() { return poder; }
    public void setPoder(int poder) { this.poder = poder; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }

    public String toString() {
        return nombre + " (" + getEfecto() + ")";
    }
}
