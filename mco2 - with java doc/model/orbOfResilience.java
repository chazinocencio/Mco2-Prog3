/**
 * orbOfResilience.java
 *
 * Represents a magical orb that grants its bearer a constant, minor protective aura,
 * slightly bolstering their defenses at the cost of a small amount of health.
 */

package model;

/**
 * orbOfResilience extends MagicItem to provide a continuous resilience effect.
 */
public class orbOfResilience extends MagicItem {
    
    /**
     * Constructs a new Orb of Resilience.
     * 
     * This orb does not alter effect points (EP) but reduces HP by 5,
     * symbolizing the constant effort required to maintain its protective aura.
     * 
     */
    public orbOfResilience(){
        super("Orb Of Resilience", "A small, smooth orb that provides a constant minor protective aura.",  0, -5);
    }
}
