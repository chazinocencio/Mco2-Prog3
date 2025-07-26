package model;

/**
 * potionOfMinorHealing.java
 *
 * A specific MagicItem representing a simple restorative potion
 * that heals a small amount of the user’s health.
 */
public class potionOfMinorHealing extends MagicItem {
    
    /**
     * Constructs a new Potion of Minor Healing.
     *
     * This potion consumes no effect points (EP) but restores 40 health points (HP),
     * representing a straightforward healing draught for minor injuries.
     * 
     */
    public potionOfMinorHealing(){
        super("Potion of Minor Healing", "A basic potion that restores a small amount of health",  0, -40);

    }
}
