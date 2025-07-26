package model;

/**
 * model.java
 *
 * Manages the core game state, including players, temporary character creation data,
 * and current battle participants. Provides methods to configure fighters and retrieve
 * or reset battle and character creation state.
 */
public class model {
    /** The first player's roster container. */
    private Player p1;
    /** The second player's roster container. */
    private Player p2;
    /** A temporary player used during character creation. */
    private Player temPlayer;
    /** The name selected for the new character being created. */
    private String tempCharName;
    /** The race selected for the new character being created. */
    private Race tempCharRace;
    /** The type selected for the new character being created. */
    private CharacterType tempCharType;
    /** The Gnome‐specific type selected for the new character being created. */
    private CharacterType tempCharTypeGnome;
    /** The character currently being edited. */
    private Character editingCharacter;
    /** The first combatant in the current battle. */   
    private Character fighter1;
    /** The second combatant in the current battle. */
    private Character fighter2;
    
    /** Accumulates the log of events during a battle. */
    private String battleLog = "";

    
    
    /**
     * Constructs a new model with default players prepopulated.
     * <p>
     * Initializes p1, p2, and temPlayer with placeholder names.
     * </p>
     */
    public model(){
        p1 = new Player("chaz");
        p2 = new Player("lulu");
        temPlayer = new Player("temp");
    }

    /**
     * Sets the first fighter for the upcoming battle.
     *
     * @param c the Character to assign as the first combatant
     */
    public void setFighter1(Character c) {
    this.fighter1 = c;
    }

    /**
     * Sets the second fighter for the upcoming battle.
     *
     * @param c the Character to assign as the second combatant
     */
    public void setFighter2(Character c) {
        this.fighter2 = c;
    }

    /**
     * Gets the first fighter for the battle.
     *
     * @return the Character assigned as fighter1
     * @throws IllegalStateException if fighter1 has not been set
     */
    public Character getFighter1() {
        if (fighter1 == null) throw new IllegalStateException("Fighter1 is not set");
        return fighter1;
    }

    /**
     * Gets the second fighter for the battle.
     *
     * @return the Character assigned as fighter2
     * @throws IllegalStateException if fighter2 has not been set
     */
    public Character getFighter2() {
        if (fighter2 == null) throw new IllegalStateException("Fighter2 is not set");
        return fighter2;
    }

    /**
     * Resets the battle state by clearing both fighters and the battle log.
     */
    public void resetBattleState() {
        fighter1 = null;
        fighter2 = null;
        battleLog = "";
    }

    /**
     * Selects the first fighter from player one's roster by index.
     *
     * @param idx the zero-based index of the character in p1's roster
     */
    public void setFighter1ByIndex(int idx) {
        if (p1 != null && idx >= 0 && idx < p1.getCharacterCount()) {
            fighter1 = p1.getCharacters()[idx];
        }
    }
    /**
     * Selects the second fighter from player two's roster by index.
     *
     * @param idx the zero-based index of the character in p2's roster
     */
    public void setFighter2ByIndex(int idx) {
        if (p2 != null && idx >= 0 && idx < p2.getCharacterCount()) {
            fighter2 = p2.getCharacters()[idx];
        }
    }

    /**
     * Selects the first fighter from player one's roster by character name.
     *
     * @param name the name of the character to find in p1's roster
     */
    public void setFighter1ByName(String name) {
        if (p1 != null) {
            fighter1 = p1.getCharacterByName(name);
        }
    }

    /**
     * Selects the second fighter from player two's roster by character name.
     *
     * @param name the name of the character to find in p2's roster
     */
    public void setFighter2ByName(String name) {
        if (p2 != null) {
            fighter2 = p2.getCharacterByName(name);
        }
    }

    /**
     * Gets the first Player instance.
     *
     * @return the Player object representing player one
     */
    public Player getp1(){
        return p1;
    }

    /**
     * Gets the second Player instance.
     *
     * @return the Player object representing player two
     */
    public Player getp2(){
        return p2;
    }

    /**
     * Gets the temporary Player used during character creation.
     *
     * @return the temporary Player object
     */
    public Player getTemPlayer(){
        return temPlayer;
    }
    
    /**
     * Sets the first Player instance.
     *
     * @param player the Player to assign to p1
     */
    public void setp1(Player player){
        p1 = player;
    }

    /**
     * Sets the second Player instance.
     *
     * @param player the Player to assign to p2
     */
    public void setp2(Player player){
        p2 = player;
    }

    /**
     * Sets the temporary Player for character creation.
     *
     * @param player the Player to assign to temPlayer
     */
    public void setTemPlayer(Player player){
        temPlayer = player;
    }

    /**
     * Sets the name for the new character being created.
     *
     * @param name the chosen character name
     */
    public void setTempCharName(String name){
        tempCharName = name;
    }
    /**
     * Gets the name chosen for the new character.
     *
     * @return the temporary character's name
     */
    public String getTempCharName() {
        return tempCharName;
    }

    /**
     * Sets the race for the new character being created.
     *
     * @param race the Race selected for the new character
     */
     public void setTempCharRace(Race race){
        tempCharRace = race;
    }

    /**
     * Gets the race chosen for the new character.
     *
     * @return the temporary character's Race
     */
    public Race getTempCharRace() {
        return tempCharRace;
    }

    /**
     * Sets the type for the new character being created.
     *
     * @param type the CharacterType selected for the new character
     */
     public void setTempCharType(CharacterType type){
        tempCharType = type;
    }
    /**
     * Sets the Gnome‐specific type for the new character being created.
     *
     * @param type the CharacterType selected for the Gnome variant
     */
    public void setTempCharTypeGnome(CharacterType type){
        tempCharTypeGnome = type;
    }

    /**
     * Gets the Gnome‐specific type chosen for the new character.
     *
     * @return the temporary character's Gnome CharacterType
     */
    public CharacterType getTempCharTypeGnome() {
        return tempCharTypeGnome;
    }
    
    /**
     * Gets the type chosen for the new character.
     *
     * @return the temporary character's CharacterType
     */
    public CharacterType getTempCharType() {
        return tempCharType;
    }

    /**
     * Gets the type chosen for the new character.
     *
     * @return the temporary character's CharacterType
     */
    public void setEditingCharacter(Character c) {
        this.editingCharacter = c;
    }

     /**
     * Gets the Character instance currently being edited.
     *
     * @return the Character under editing
     */
    public Character getEditingCharacter() {
        return editingCharacter;
    }

}
