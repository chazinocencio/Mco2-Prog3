package model;

/*
 * CharacterType.java
 * Defines available character classes and their default abilities.
 */

/**
 * Enumeration of character classes, each with its own set of abilities.
 */
public enum CharacterType {
    /**
     * Mages command arcane energies, specializing in powerful spells and mystical manipulation.
     */
    Mage, 
    
    /**
     * Rogues are agile and tricky, relying on precision and debilitating opponents.
     */
    Rogue, 
    
    /**
     * Warriors are tough, focusing on direct combat and robust defense.
     */
    Warrior;

    /**
     * Retrieves the default ability set for this character type.
     *
     * @return an array of 5 Ability objects specific to this class
     */
    public Ability[] getAbilities() {
        Ability[] abilities = new Ability[5];

        if (this == Mage) {
            abilities[0] = new Ability("Arcane Bolt", "Launch a basic magical projectile that deals 20 arcane damage to the target.", 5, 20);
            abilities[1] = new Ability("Arcane Blast", "Unleash a burst of fiery energy, dealing 65 arcane damage to the target.", 30, 65);
            abilities[2] = new Ability("Mana Channel", "Draw upon ambient magical energy to restore your own. Restores 15 EP.", -15,0);
            abilities[3] = new Ability("Lesser Heal", "Weave a minor healing spell to mend your wounds. Restores 40 HP.", 15,-40);
            abilities[4] = new Ability("Arcane Shield", "Conjure a protective barrier of mystical energy around yourself. You do not take any damage for the round.", 12, 0);
        } else if (this == Rogue) {
            abilities[0] = new Ability("Shiv", "A quick, precise stab that deals 20 physical damage.", 5, 20);
            abilities[1] = new Ability("Backstab", "Strike a vital point and deal 35 points of physical damage.", 15, 35);
            abilities[2] = new Ability("Focus", "Take a moment to concentrate, restoring your mental energy. Restores 10 EP.", -10, 0);
            abilities[3] = new Ability("Smoke Bomb", "Throw a smoke bomb, making you harder to hit. You have a 50% chance of evading any incoming attacks in the current round.", 15, 0);
            abilities[4] = new Ability("Sneak Attack", "You rely on your agility to evade your opponent, taking no damage from any of their attacks, while you deal 45 physical damage to them.", 25, 45);
        } else if (this == Warrior) {
            abilities[0] = new Ability("Cleave", "A sweeping strike that deals 20 physical damage.", 5, 20);
            abilities[1] = new Ability("Shield Bash", "Slam your shield into the opponent, dealing 35 physical damage.", 15,35);
            abilities[2] = new Ability("Ironclad Defense", "Brace yourself, effectively taking no damage for the current round.", 15, 0);
            abilities[3] = new Ability("Bloodlust", "Tap into your inner fury, restoring a small amount of health. Restores 30 HP.", 12, -30);
            abilities[4] = new Ability("Rallying Cry", "Let out a powerful shout, inspiring yourself and recovering 12 EP", -12, 0);
        }

        return abilities;
    }
    
}
