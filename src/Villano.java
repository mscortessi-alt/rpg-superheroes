/**
 * Enemigo al que se enfrenta el héroe. Al ser derrotado deja un ítem de recompensa.
 */
public class Villano extends Personaje {

    private String fraseAmenaza;
    private Item recompensa;

    public Villano(String nombre, int vidaMaxima, int ataque, int defensa,
                   String fraseAmenaza, Item recompensa) {
        super(nombre, vidaMaxima, ataque, defensa);
        this.fraseAmenaza = fraseAmenaza;
        this.recompensa = recompensa;
    }

    // ----- Getters y setters -----

    public String getFraseAmenaza() { return fraseAmenaza; }
    public void setFraseAmenaza(String fraseAmenaza) { this.fraseAmenaza = fraseAmenaza; }

    public Item getRecompensa() { return recompensa; }
    public void setRecompensa(Item recompensa) { this.recompensa = recompensa; }
}
