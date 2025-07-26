package model;

/**
 * amuletOfVitality.java
 *
 * A specific MagicItem that enhances the wearer’s vitality by
 * reducing their HP cost while providing other magical benefits.
 */
public class amuletOfVitality extends MagicItem {
    
    /**
     * Constructs a new Amulet of Vitality.
     * 
     * This amulet grants no additional EP but decreases HP by 20,
     * representing a trade‑off between magical strength and vitality.
     * 
     */
    public amuletOfVitality(){
        super("Amulet Of Vitality",  "An enchanted amulet that subtly strengthens your life force.", 0,-20);
    }
}
