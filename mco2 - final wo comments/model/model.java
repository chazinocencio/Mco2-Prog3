package model;

public class model {

    private Player p1;
    private Player p2;
    private Player temPlayer;
    private String tempCharName;
    private Race tempCharRace;
    private CharacterType tempCharType;
    private CharacterType tempCharTypeGnome;
    private Character editingCharacter;

    private Character fighter1;
    private Character fighter2;
    private String battleLog = "";


    public void setFighter1(Character c) {
    this.fighter1 = c;
    }

    public void setFighter2(Character c) {
        this.fighter2 = c;
    }

    /**
     * Returns the first fighter for the battle. Throws if not set.
     */
    public Character getFighter1() {
        if (fighter1 == null) throw new IllegalStateException("Fighter1 is not set");
        return fighter1;
    }

    /**
     * Returns the second fighter for the battle. Throws if not set.
     */
    public Character getFighter2() {
        if (fighter2 == null) throw new IllegalStateException("Fighter2 is not set");
        return fighter2;
    }

    /**
     * Resets the battle state (fighters, round log, etc.)
     */
    public void resetBattleState() {
        fighter1 = null;
        fighter2 = null;
        battleLog = "";
    }

    // Optionally, allow setting fighters by index or name (for future flexibility)
    public void setFighter1ByIndex(int idx) {
        if (p1 != null && idx >= 0 && idx < p1.getCharacterCount()) {
            fighter1 = p1.getCharacters()[idx];
        }
    }
    public void setFighter2ByIndex(int idx) {
        if (p2 != null && idx >= 0 && idx < p2.getCharacterCount()) {
            fighter2 = p2.getCharacters()[idx];
        }
    }
    public void setFighter1ByName(String name) {
        if (p1 != null) {
            fighter1 = p1.getCharacterByName(name);
        }
    }
    public void setFighter2ByName(String name) {
        if (p2 != null) {
            fighter2 = p2.getCharacterByName(name);
        }
    }


    public model(){
        p1 = new Player("chaz");
        p2 = new Player("lulu");
        temPlayer = new Player("temp");
    }

    public Player getp1(){
        return p1;
    }

    public Player getp2(){
        return p2;
    }
    public Player getTemPlayer(){
        return temPlayer;
    }
    

    public void setp1(Player player){
        p1 = player;
    }
    public void setp2(Player player){
        p2 = player;
    }

    public void setTemPlayer(Player player){
        temPlayer = player;
    }

    public void setTempCharName(String name){
        tempCharName = name;
    }
    public String getTempCharName() {
        return tempCharName;
    }

     public void setTempCharRace(Race race){
        tempCharRace = race;
    }

    public Race getTempCharRace() {
        return tempCharRace;
    }

     public void setTempCharType(CharacterType type){
        tempCharType = type;
    }
    public void setTempCharTypeGnome(CharacterType type){
        tempCharTypeGnome = type;
    }
    public CharacterType getTempCharTypeGnome() {
        return tempCharTypeGnome;
    }
    
    public CharacterType getTempCharType() {
        return tempCharType;
    }

    public void setEditingCharacter(Character c) {
        this.editingCharacter = c;
    }

    public Character getEditingCharacter() {
        return editingCharacter;
    }

}
