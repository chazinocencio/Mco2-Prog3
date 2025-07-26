package model;
public abstract class MagicItem{
    protected String name;
    protected String description;
    protected int ep;
    protected int hp;
    

    public MagicItem(String name,  String description, int ep, int hp){
        this.name = name;
        
        this.description = description;
        this.ep = ep;
        this.hp = hp;
    }

    public String getName(){
        return name;
    }

    public int getEp(){
        return ep;
    }
    public int getHp(){
        return hp;
    }
    public String getDescription(){
        return description;
    }

}
