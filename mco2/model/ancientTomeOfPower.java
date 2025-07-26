package model;
/**
 * ancientTomeOfPower.java
 *
 * A specific MagicItem that provides a continual boost of magical energy
 * through the knowledge contained within its pages.
 */
public class ancientTomeOfPower extends MagicItem {
    /**
     * Constructs a new Ancient Tome of Power.
     *
     * This tome grants 5 effect points (EP) each use without altering HP,
     * symbolizing a steady flow of arcane energy to the reader.
     * 
     */
    public ancientTomeOfPower(){
        super("Ancient Tome Of Power",  "A worn book filled with forgotten wisdom that grants a small, continuous surge of power.",  5, 0);
    }
}
