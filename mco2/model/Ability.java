/*
 * Ability.java
 * Defines an ability with name, description, EP cost, and damage value.
 */
package model;


/**
 * Represents a skill or action a character can perform, costing EP and dealing damage.
 */
public class Ability {
    /** The name of the ability. */
    private String name;
    /** A brief description of the ability's effect. */
    private String description;
    /** EP cost required to use the ability. */
    private int epCost;
    /** Damage dealt by the ability. */
    private int damage;
    
     /**
     * Constructs an Ability with specified parameters.
     *
     * @param name        the name of the ability
     * @param description a brief description of its effect
     * @param epCost      how much EP is consumed when used
     * @param damage      how much damage it inflicts
     */
    public Ability(String name, String description, int epCost, int damage){
        this.name = name;
        this.description = description;
        this.epCost = epCost;
        this.damage = damage;
    }

    /**
     * Gets the ability's name.
     *
     * @return the ability name
     */
    public String getAbilityName(){
        return name;
    }

    /**
     * Gets the description of the ability.
     *
     * @return the description text
     */
    public String getDesc(){
        return description;
    }

    /**
     * Gets the EP cost to use this ability.
     *
     * @return the EP cost
     */
    public int getEpCost (){
        return epCost;
    }

    /**
     * Gets the damage amount of this ability.
     *
     * @return the damage value
     */
    public int getDamage(){
        return damage;
    }

    

    /**
     * Returns a formatted string representing the ability.
     *
     * @return a string in the form "name (EP cost EP, damage DMG): description"
     */
    @Override
    public String toString() {
        return name + " (" + epCost + " EP, " + damage + " DMG): " + description;
    }

}
