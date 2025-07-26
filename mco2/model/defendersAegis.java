package model;
/**
 * defendersAegis.java
 *
 * A specific MagicItem that grants the bearer a temporary protective barrier
 * capable of absorbing incoming damage.
 */
public class defendersAegis extends MagicItem {
    /**
     * Constructs a new Defender’s Aegis.
     * 
     * This aegis provides a transient shield with no direct EP or HP cost,
     * representing a brief, magical barrier around the user.
     * 
     */
    public defendersAegis(){
        super("Defender's Aegis",  "A small, temporary barrier that absorbs damage.",  0, 0);

    }
}
