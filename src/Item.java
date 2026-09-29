/**
 * Objeto del inventario del héroe.
 * tipo puede ser: "CURACION" (recupera vida), "ATAQUE" (sube el ataque)
 * o "ENERGIA" (recarga energía para el superpoder).
 */
public class Item {

    private String nombre;
    private String tipo;
    private int poder;

    public Item(String nombre, String tipo, int poder) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.poder = poder;
    }

    // ----- Getters y setters -----

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getPoder() { return poder; }
    public void setPoder(int poder) { this.poder = poder; }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ", poder " + poder + ")";
    }
}
