/*
 * Character.java
 * Defines a game character with health, energy, type, and abilities.
 */
package model;



public class Character {
    /** Character's name. */
    private String name;
    /** Current energy points (EP). */
    private int ep;
    /** Current health points (HP). */
    private int hp;
    /** Flag indicating whether the character is alive. */  
    private boolean isAlive;
    /** Abilities available to the character. */
    private Ability[] ability;
    /** The character's type, providing base stats and ability pool. */
    private CharacterType type;
    private Race race;
    private int maxEp;
    private int maxHp;
    private int rechargeAmount;
    private int rechargeAmountHP;
    private int winCount = 0;
    


    /**
     * Constructs a Character with specified name, type, and abilities. Initializes HP and EP to defaults.
     *
     * @param name     the name of the character
     * @param type     the CharacterType determining base stats and ability list
     * @param ability  array of Ability objects to assign
     */
    public Character (String name, CharacterType type,Ability[] ability,Race race){
        this.name = name;
        this.type = type;
        this.ability = ability;
        this.race = race;
        ep = 50;
        hp = 100;
        maxEp = 50;
        maxHp = 100;
        rechargeAmount = 5;
        rechargeAmountHP = 0;
        isAlive = true;

        if (race == Race.Human){
            ep += 5;
            hp += 15;
            maxEp += 5;
            maxHp += 15;
        }
        else if (race == Race.Dwarf){
            hp += 30;
            maxHp += 30;
        }
        else if (race == Race.Elf){
            ep += 15;
            maxEp += 15;
        }
            
        isAlive = true;
    }

    /**
     * Gets the character's name.
     *
     * @return the name
     */
    public String getCharacterName(){
        return name;
    }

    /**
     * Gets the character's type.
     *
     * @return the CharacterType
     */
    public CharacterType getType(){
        return type;
    }

    /**
     * Gets the abilities array assigned to the character.
     *
     * @return the ability array
     */
    public Ability[] getAbility(){
        return ability;
    }

    /**
     * Gets the character's abilities.
     * @return the Ability[] result representing the abilities
     */
    public Ability[] getAbilities() {
        return getAbility();
    }

    /**
     * Returns a numbered string of the character's abilities for GUI display.
     * Each line: 1. AbilityName (EPCost EP): Description
     */
    public String getAbilitiesAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ability.length; i++) {
            Ability a = ability[i];
            sb.append((i + 1) + ". " + a.getAbilityName() + " (" + a.getEpCost() + " EP): " + a.getDesc() + "\n");
        }
        return sb.toString();
    }
     /**
     * Checks if the character is alive.
     *
     * @return true if alive, false otherwise
     */
    public boolean getAlive(){
        return isAlive;
    }

    /**
     * Gets the current energy points.
     *
     * @return the ep value
     */
    public int getEp(){
        return ep;
    }

    /**
     * Gets the current health points.
     *
     * @return the hp value
     */
    public int getHp(){
        return hp;
    }

    /**
     * Gets the character's race.
     * @return the Race result representing the race
     */
    public Race getRace(){
        return race;
    }

    /**
     * Gets the character's maxep.
     * @return the int result representing the maxep
     */
    public int getMaxEp(){
        return maxEp;
    }

    
    /**
     * Gets the character's maxhp.
     * @return the int result representing the maxhp
     */
    public int getMaxHp(){
        return maxHp;
    }

     /**
      * Gets the character's rechargeamount.
      * @return the int result representing the rechargeamount
      */
     public int getRechargeAmount(){
        return rechargeAmount;
    }

    /**
      * Gets the character's rechargehpmax.
      * @return the int result representing the rechargehpmax
      */
     public int getRechargeHPMax(){
        return rechargeAmountHP;
    }

    /**
     * Gets the character's wincount.
     * @return the int result representing the wincount
     */
    public int getWinCount() {
        return winCount;
    }

    /**
     * Adds a win to the character's inventory.
     */
    public void addWin() {
        winCount++;
    }

     /**
     * Sets the character's maxhp to the provided value.
     * @param amount the amount value of type int
     */
    public void setMaxHP(int amount){
        maxHp = amount;
    }

    /**
     * Sets the character's maxep to the provided value.
     * @param amount the amount value of type int
     */
    public void setMaxEP(int amount){
        maxEp = amount;
    }

    /**
     * Sets a new abilities array if valid (length 3), otherwise prints error.
     *
     * @param newAbilities array of Ability objects to set
     */
    public void setAbilities(Ability[] newAbilities){
       // if(newAbilities != null && newAbilities.length == 3)
            ability = newAbilities;
       // else 
        //    System.out.println("ERROR");
    }

    /**
     * Applies an incoming ability effect: either defend or take full damage.
     *
     * @param ability    the Ability being used against this character
     * @param isDefending true if defending, false to take full damage
     */
    public void applyIncoming(Ability ability, boolean isDefending) {
            if (isDefending) {
                defend(ability);
            } else {
                takeDamage(ability);
            }
        }

    /**
    * Reduces HP by ability damage; if HP &lt;= 0, sets alive flag to false.
    *
    * @param ability    the Ability being used against this character
    */
    public void takeDamage (Ability ability){
        hp -= ability.getDamage();
        if (hp <= 0){
             isAlive = false;
             hp = 0;
        }
        if (hp > maxHp){
            hp = maxHp;
        }
    }

    /**
     * Consumes EP based on ability cost if available.
     *
     * @param ability the Ability whose EP cost is deducted
     */
    public void consumeEp (Ability ability){
        int cost = ability.getEpCost();
        if (ep - cost >= 0) 
            ep -= cost;
        if (ep > maxEp) 
            ep = maxEp;
        if (ep < 0)
            ep = 0;
        
    }

     /**
     * Defends against an ability, costing 5 EP, taking half damage.
     *
     * @param ability the Ability being defended against
     */
    public void defend (Ability ability){
        if (ep - 5 >= 0){
            ep -= 5;
            hp -= (ability.getDamage()/2);
            if (hp <= 0) 
                isAlive = false;
        }
         
    }

    /**
     * RestoreHP.
     * @param amount the amount value of type int
     */
    public void restoreHP(int amount) {
       hp += amount; 
       if (hp > maxHp){
            hp = maxHp;
        }
    }

    /**
     * RechargeEP.
     * @param amount the amount value of type int
     */
    public void rechargeEP(int amount) {
       ep += amount; 
    }

    /**
     * Adds a maxhp to the character's inventory.
     * @param amount the amount value of type int
     */
    public void addMaxHP(int amount) {
       maxHp += amount; 
    }

    /**
     * Recharges EP by 5, capping at maximum EP (50).
     */
    public void recharge(int amount){ 
        
        ep += amount;
        if (ep > maxEp) 
            ep = maxEp;
        if (ep < 0)
            ep = 0;
    } 

    /**
      * rechargeHpMax.
      * @param amount the amount value of type int
      */
     public void rechargeHpMax(int amount){ //added
        hp += amount;
        if (hp > maxHp) 
            hp = maxHp;
    } 

    /**
     * Resets HP, EP, and alive status to default values.
     */
    public void reset() {
        hp = maxHp;
        ep = maxEp;
        isAlive = true;
    }

    /**
     * Returns a formatted string of the character's stats and abilities.
     *
     * @return string representation
     */
    
    @Override
    public String toString() {
        String info = "Name: " + name + "\nRace: " + race + "\nType: " + type + "\nHP: " + hp + "\nEP: " + ep + "\nAbilities:\n";
        for (Ability a : ability) {
            String epLabel;
            if (a.getEpCost() < 0) {
                epLabel = 0 + " ";
            } else {
                epLabel = a.getEpCost()+ " ";
            }
            info += "- " + a.getAbilityName() + " (" + epLabel + "EP): " + a.getDesc() + "\n";
        }
        return info;
    }

    /**
     * Gets the character's ability.
     * @param index the index value of type int
     * @return the Ability result representing the ability
     */
    public Ability getAbility(int index) {
    if (index >= 0 && index < ability.length) {
        return ability[index];
    }
    return null;
    }

    /**
     * Gets the character's randomusableability.
     * @return the Ability result representing the randomusableability
     */
    public Ability getRandomUsableAbility() {
        for (Ability a : ability) {
            if (ep >= a.getEpCost()) return a;
        }
        return null;
    }

    /**
     * Fight.
     * @param target the target value of type Character
     * @param hasAmulet the hasAmulet value of type boolean
     * @param targetHasRing the targetHasRing value of type boolean
     * @return the String result representing the fight
     */
    public String fight(Character target, boolean hasAmulet, boolean targetHasRing) {
        StringBuilder result = new StringBuilder();
        
        boolean actionDone = false;
        int index = 0;

        
        while (index < ability.length && !actionDone) {
            Ability a = ability[index];

            if (ep >= a.getEpCost()) {
                
                if (targetHasRing) {
                    target.defend(a);
                    result.append(name).append(" uses ").append(a.getAbilityName())
                        .append(" on ").append(target.getCharacterName())
                        .append(" (Defended, half damage)").append("\n");
                } else {
                    target.takeDamage(a);
                    result.append(name).append(" uses ").append(a.getAbilityName())
                        .append(" on ").append(target.getCharacterName())
                        .append(" (Full damage)").append("\n");
                }

                consumeEp(a);
                actionDone = true;
            }

            index++;
        }

        
        if (!actionDone) {
            recharge(rechargeAmount);
            rechargeHpMax(rechargeAmountHP);
            result.append(name).append(" has no energy and recharges.\n");
        }

        if (hasAmulet) {
            rechargeHpMax(5);
            result.append(name).append(" is healed by Amulet of Vitality (+5 HP).\n");
        }

        return result.toString();
    }
}
