package model;

import java.util.concurrent.ThreadLocalRandom;

public enum dropChance {
    COMMON,
    UNCOMMON,
    RARE;
   
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
