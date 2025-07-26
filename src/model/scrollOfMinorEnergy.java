package model;

/**
 * scrollOfMinorEnergy.java
 *
 * Represents a magical item that restores a small amount of energy
 * to the user when used.
 */
public class scrollOfMinorEnergy extends MagicItem {
    
    /**
     * Constructs a new Scroll of Minor Energy.
     * 
     * This item provides +20 energy points (EP) and does not affect
     * health points (HP). It symbolizes a basic magical scroll used
     * to recover stamina or magical strength.
     * 
     */
    public scrollOfMinorEnergy(){
        super("Scroll Of Minor Energy", "A simple scroll inscribed with runes that replenish a small amount of energy.",  20, 0);

    }
}
