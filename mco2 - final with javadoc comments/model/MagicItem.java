
package model;
/**
 * MagicItem.java
 *
 * Represents an abstract magical item with a name, description, and associated
 * effect points (EP) and health points (HP) values. Subclasses should define
 * specific magical behaviors or effects.
 */
public abstract class MagicItem{
    /** The name of the magic item. */
    protected String name;
    /** A brief description of the magic item’s effects. */
    protected String description;
    /** The effect points (EP) restored or consumed by this item. */
    protected int ep;
    /** The health points (HP) restored or consumed by this item. */
    protected int hp;
    
    /**
     * Constructs a new MagicItem with the specified attributes.
     *
     * @param name the name of the magic item
     * @param description a brief description of the item's magical properties
     * @param ep the effect points that this item grants or uses
     * @param hp the health points that this item grants or uses
     */
    public MagicItem(String name,  String description, int ep, int hp){
        this.name = name;
        
        this.description = description;
        this.ep = ep;
        this.hp = hp;
    }

    /**
     * Gets the name of this magic item.
     *
     * @return the magic item's name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the effect points (EP) value of this magic item.
     *
     * @return the EP value associated with this item
     */
    public int getEp(){
        return ep;
    }

    /**
     * Gets the health points (HP) value of this magic item.
     *
     * @return the HP value associated with this item
     */
    public int getHp(){
        return hp;
    }

    /**
     * Gets the description of this magic item.
     *
     * @return a brief description of the item's magical effects
     */
    public String getDescription(){
        return description;
    }

}
