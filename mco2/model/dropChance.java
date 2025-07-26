/**
 * dropChance.java
 *
 * Represents the rarity levels of item drops and provides a method
 * to select a random rarity according to defined probability weights.
 */
package model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Enum dropChance defines possible drop rarities and includes
 * a utility for randomly selecting one based on configured chances.
 */
public enum dropChance {
    /** Common rarity with the highest drop probability. */
    COMMON,
    /** Uncommon rarity with moderate drop probability. */
    UNCOMMON,
    /** Rare rarity with the lowest drop probability. */
    RARE;
   
    /**
     * Randomly selects a dropChance value based on weighted probabilities:
     * 
     *   COMMON: 60% chance
     *   UNCOMMON: 35% chance
     *   RARE: 5% chance
     * 
     *
     * @return a {@code dropChance} enum value chosen according to its drop weight
     */
    public static dropChance random() {
        
        int roll = ThreadLocalRandom.current().nextInt(100);
        
        if (roll < 60) {
            
            return COMMON;
        } else if (roll < 60 + 35) {
            
            return UNCOMMON;
        } else {
            
            return RARE;
        }
    }
}
