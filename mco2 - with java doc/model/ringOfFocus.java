package model;
/**
 * ringOfFocus.java
 *
 * Represents a magical item that enhances the user's energy regeneration,
 * symbolized by a simple enchanted ring.
 */
public class ringOfFocus extends MagicItem {
    
    /**
     * Constructs a new Ring of Focus.
     * 
     * This item adds +2 to energy points (EP), providing a minor but steady
     * energy regeneration boost. It has no effect on health points (HP).
     * 
     */
    public ringOfFocus(){
        super("Ring Of Focus",  "A plain ring that helps you concentrate, boosting your energy regeneration.",  2, 0);
    }
}
