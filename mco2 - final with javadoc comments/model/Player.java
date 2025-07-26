    /*
    * Player.java
    * Represents a player with a username and roster of characters.
    */

    package model;

    
    import java.util.Scanner;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.Random;

    /**
     * Models a game player who can manage multiple characters.
     */
    public class Player {
        /** The username of the player. */
        private String userName;
        /** Array of characters owned by the player. */
        private Character[] characters;
        private int characterCount;
        private int winCount;
        private List<MagicItem> magicItems;
        private CharacterType currentAbilityType;
        private Ability[] selectedAbilities = new Ability[5]; // 3 + defend + recharge
        private int selectedCount = 0;

        private CharacterType gnomeBaseType;
        private Ability[] gnomeSelectedAbilities;
        private int gnomeSelectCount = 0;
        private Ability[] gnomeSourceAbilities;
        
        

        /**
         * Constructs a Player with the given name.
         *
         * @param name the name of the player
         */
        public Player(String name){
            userName = name;
            characters = new Character[6];
            characterCount = 0;
            winCount = 0;
            this.magicItems = new ArrayList<>();
        }

        /**
         * getSelectedAbilities
         * @return Ability[]
         */
        public Ability[] getSelectedAbilities(){
            return selectedAbilities;
        }
        
        /**
         * Gets the player's name.
         * @return the player's name
         */
        public String getName(){
            return userName;
        }

        /**
         * getCharacters
         * @return Character[]
         */
        public Character[] getCharacters(){
            return characters;
        }

         /**
         * Gets the player's currentabilitytype.
         * @return the player's currentabilitytype
         */
        public CharacterType getCurrentAbilityType(){
            return currentAbilityType;
        }


        /**
         * setName
         * @param name The new name of player
         * 
         */
        public void setName(String name) {
            userName = name;
        }

         /**
         * Sets the player's currentabilitytype to the specified value.
         * @param c the c value of type CharacterType
         */
        public void setCurrentAbilityType(CharacterType c){
            currentAbilityType = c;
        }

         /**
         * Returns the number of characters this player has.
         *
         * @return the characterCount
         */
        public int getCharacterCount() {
            return characterCount;
        }

        /**
         * Adds a magicitem to the player's inventory.
         * @param item the item value of type MagicItem
         */
        public void addMagicItem(MagicItem item) {
            magicItems.add(item);
        }
        
        /**
         * Gets the player's magicitem.
         * @param index the index value of type int
         * @return the player's magicitem
         */
        public MagicItem getMagicItem(int index) {
            return magicItems.get(index);
        }
        
         /**
         * Gets the player's allmagicitems.
         * @return the player's allmagicitems
         */
        public List<MagicItem> getAllMagicItems() {
            return magicItems;
        }

       /**
         * Gets the player's wincount.
         * @return the player's wincount
         */
        public int getWinCount() {
            return winCount;
        }

        /**
         * Gets Win Coun Add.
         */
        public void winCountAdd() {
            winCount += 1;
        }

        /**
         * getCharacterByName.
         * @param name The Name of the Character
         * @return Character
         */
        public Character getCharacterByName(String name) {
            for (Character c : characters) {
                if (c != null && c.getCharacterName().equalsIgnoreCase(name)) {
                    return c;
                }
            }
            return null;
        }

        /**
         * magicItemReward.
         * @return MagicItem
         */
        public MagicItem magicItemReward(){
            if(winCount % 3 == 0){
                dropChance r = dropChance.random();
                switch (r) {
                case COMMON:     
                    Random randomCom = new Random();

                    int countCom = randomCom.nextInt(3) + 1;

                    if(countCom == 1){
                    return  new potionOfMinorHealing();
                    }
                    else if(countCom == 2){
                        return new scrollOfMinorEnergy();
                    }
                    else if (countCom == 3){
                        return new defendersAegis();
                    }
                case UNCOMMON:
                    Random randomUn = new Random();

                    int countUn = randomUn.nextInt(2) + 1;

                    if(countUn == 1){
                        return new amuletOfVitality();
                    }
                    else if(countUn == 2){
                        return new ringOfFocus();
                    }
                case RARE: 
                    Random randomR = new Random();

                    int countR = randomR.nextInt(2) + 1;

                    if(countR == 1){
                    return new orbOfResilience();
                    }
                    else if(countR == 2){
                        return new ancientTomeOfPower();
                    }

                default:
                    throw new IllegalStateException("Unknown rarity: " + r);
                }
            }
            else {
                return null;
            }
        }
    
       /**
         * Creates a new character and adds it to this player.
         *
         * @param name character name
         * @param type character type
         * @param abilities array of abilities
         * @param race character race
         */
        public void createCharacter(String name, CharacterType type, Ability[] abilities, Race race) {
                characters[characterCount] = new Character(name, type, abilities, race);
                characterCount++;
                System.out.println("Character created successfully.");
            
        }

        /**
         * Creates a new character and returns a status message.
         *
         * @param name      the name of the character
         * @param type      the CharacterType enum specifying class and base stats
         * @param abilities array of abilities to assign to the character
         * @param race      the character's race
         * @return Status message after character creation.
         */
        public String createCharacterAndReturnStatus(String name, CharacterType type, Ability[] abilities, Race race) {
            characters[characterCount] = new Character(name, type, abilities, race);
            characterCount++;
            return "Character \"" + name + "\" created successfully as a " + race + " " + type + ".";
        }

        /**
         * Edits an existing character's abilities at the specified index.
         *
         * @param index        zero-based position of the character in the roster
         * @param newAbilities array of new abilities to assign
         */
        public void editCharacter(int index, Ability[] newAbilities){
            if (index >= 0 && index < characterCount) {
                characters[index].setAbilities(newAbilities);
                System.out.println("Abilities updated for " + characters[index].getCharacterName() + ".");
            } else {
                System.out.println("Invalid index.");
            }

        }

        /**
         * Deletes a character from the roster at the specified index, shifting subsequent entries down.
         *
         * @param index zero-based position of the character to remove
         */
        public void deleteCharacter (int index){
            if (index >= 0 && index < characterCount) {
                for (int i = index; i < characterCount - 1; i++) {
                    characters[i] = characters[i + 1];
                }
                characters[characterCount-1] = null;
                characterCount--;
                System.out.println("Character deleted.");
            } else {
                System.out.println("Invalid character index.");
            }
        }
        
        /**
         * Prompts the user to choose an ability for the given character, validating EP cost.
         *
         * @param sc Scanner for reading user input
         * @param c  the Character whose abilities are being selected
         * @return the chosen Ability
         */
        public Ability chooseAbility(Scanner sc, Character c) {
            Ability[] abilities = c.getAbility();
            Ability chosen = null;
            boolean magic = false;
            while (chosen == null) {
                System.out.println("\n" + c.getCharacterName() + " - Choose an ability:");
                for (int i = 0; i < abilities.length; i++) {
                    int epLabel;
                    if (abilities[i].getEpCost() < 0) {
                        epLabel = 0;
                    } else {
                        epLabel = abilities[i].getEpCost();
                    }
                    System.out.println((i + 1) + ". " + abilities[i].getAbilityName() + " (" + epLabel + " EP): " + abilities[i].getDesc());
                }

                for (MagicItem I : magicItems) {
                    if ( I != null) {
                        magic = true;
                    }
                }

                if (magic) {
                    System.out.println("6. Magic Items"); 
                }
        
                System.out.print("Enter move number: ");
                int choice = sc.nextInt() - 1;
        
                if(choice == 5){
                    List<Integer> validItemIndexes = new ArrayList<>();
                    int itemNum = 1;

                    for (int i = 0; i < magicItems.size(); i++) {
                        MagicItem mi = magicItems.get(i);
                        if (mi != null) {
                            System.out.printf("%2d. %s: %s%n",
                                    itemNum,
                                    mi.getName(),
                                    mi.getDescription());
                            validItemIndexes.add(i); // Save the actual index
                            itemNum++;
                        }
                    }

                    System.out.print("Enter item number: ");
                    int magicChoice = sc.nextInt() - 1;

                    if (magicChoice < 0 || magicChoice >= validItemIndexes.size()) {
                        System.out.println("Invalid item choice!");
                    }
                    else{
                        int actualIndex = validItemIndexes.get(magicChoice);
                        MagicItem selectedItem = magicItems.get(actualIndex);

                        magicItems.set(actualIndex, null);

                        Ability magicItemAb = new Ability(
                            selectedItem.getName(),
                            selectedItem.getDescription(),
                            0,
                            selectedItem.getHp()
                        );
                        
                        return magicItemAb;
                    }

                
                }
                else if (choice < 0 || choice >= abilities.length) {
                    System.out.println("Invalid choice. Try again.");
                } else if (c.getEp() < abilities[choice].getEpCost()) {
                    System.out.println("Not enough EP for that move!");
                } else {
                    chosen = abilities[choice];
                }
            }
        
            return chosen;
        }

        /**
         * Allows the player to select three unique abilities for a new character based on its type,
         * then adds default Defend and Recharge abilities.
         *
         * @param sc   Scanner for reading user input
         * @param type the CharacterType from which ability pool is drawn
         * @return array of 5 Abilities (3 selected + Defend + Recharge)
         */
        public Ability[] selectThreeAbilities(Scanner sc, CharacterType type){
            Ability[] all = type.getAbilities();
            Ability [] selected = new Ability [5];
            int count = 0;

            selected [3] = new Ability("Defend", "Take Half Damage", 5,0);
            selected [4] = new Ability("Recharge","Gain 5 EP", -5, 0);

            while (count < 3){
                System.out.println("Select ability #" + (count + 1) + " for " + type + ":\n");
                for (int i = 0; i < all.length; i++) {
                    int epLabel;
                    if (all[i].getEpCost() < 0) {
                    epLabel = 0;
                } else {
                    epLabel = all[i].getEpCost();
                }
                    System.out.println((i + 1) + ". " + all[i].getAbilityName() + " (" +
                            epLabel + " EP): " + all[i].getDesc());
            }
            System.out.print("\nEnter choice: ");
            int choice = sc.nextInt() - 1;

            if (choice < 0 || choice >= all.length) {
                System.out.println("\nInvalid choice. Try again. \n");
            } else if (selected[0] == all[choice] || selected[1] == all[choice] || selected[2] == all[choice]) {
                System.out.println("\nYou've already selected that ability. Choose another.\n");
            } else {
                selected[count] = all[choice];
                System.out.println("\nYou Have chosen " + selected[count] + "\n");
                count++;
            }
        }

            return selected;
        }

        /**
         * Selects Three Abilities for GUI use.
         * @param type the CharacterType 
         */
       public void selectThreeAbilitiesGUI(CharacterType type) {
            this.currentAbilityType = type;
            selectedAbilities = new Ability[5];
            this.selectedAbilities[3] = new Ability("Defend", "Take Half Damage", 5, 0);
            this.selectedAbilities[4] = new Ability("Recharge", "Gain 5 EP", -5, 0);
            selectedCount = 0;
            System.out.println("working");
        }
        
        /**
         * selectAbilityByIndex.
         * @param index Index of the Ability[]
         * @return String
         */
        public String selectAbilityByIndex(int index) {
            Ability[] temp = currentAbilityType.getAbilities();

            if (index < 0 || index >= temp.length) {
                return "Invalid choice. Try again.";
            }

            // Check if already selected
            for (int i = 0; i < selectedCount; i++) {
                if (selectedAbilities[i] == temp[index]) {
                    return "You've already selected that ability. Choose another.";
                }
            }

            selectedAbilities[selectedCount] = temp[index];
            selectedCount++;
            return "You have chosen " + selectedAbilities[selectedCount - 1].getAbilityName() + ".";
        }

        /**
         * Get Ability List For GUI use.
         * @param type CharacterType
         * @return String
         */
        public String getAbilityListForGUI(CharacterType type) {
            Ability[] all = type.getAbilities();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < all.length; i++) {
                int index = (i + 1);
                int epLabel = Math.max(0, all[i].getEpCost());
                sb.append(index)
                .append(". ")
                .append(all[i].getAbilityName())
                .append(" (")
                .append(epLabel)
                .append(" EP): ")
                .append(all[i].getDesc())
                .append("\n");
            }
            return sb.toString();
        }

        /**
         * gnomeAbilities.
         * @param sc the scaner for terminal base game
         * @param type the CharacterType of the given class for bonus skill
         * @return Ability[]
         */
        public Ability[] gnomeAbilities (Scanner sc, CharacterType type){
            Ability[] classAbilities = type.getAbilities();
        Ability[] selected = new Ability[6];
        int count = 0;
        Ability[] source = null;

        selected[4] = new Ability("Defend", "Take Half Damage", 5, 0);
        selected[5] = new Ability("Recharge", "Gain 5 EP", -5, 0);

        while (count < 4) {
            if (count < 3) {
                source = classAbilities;
                System.out.println("Select ability #" + (count + 1) + " for " + type + ":\n");
            } else {
                CharacterType selectedCharType = null;
                do {
                    System.out.println("Choose class for 4th ability (1. Mage, 2. Rogue, 3. Warrior):");
                    int opt = sc.nextInt();
                    if (opt == 1) selectedCharType = CharacterType.Mage;
                    else if (opt == 2) selectedCharType = CharacterType.Rogue;
                    else if (opt == 3) selectedCharType = CharacterType.Warrior;
                } while (selectedCharType == null);
                source = selectedCharType.getAbilities();
                System.out.println("Select your bonus ability from " + selectedCharType + ":");
            }

            // Print available abilities
            for (int i = 0; i < source.length; i++) {
                int epLabel;
                if (source[i].getEpCost() < 0) {
                    epLabel = 0;
                } else {
                    epLabel = source[i].getEpCost();
                }
                System.out.println((i + 1) + ". " + source[i].getAbilityName() + " (" + epLabel + " EP): " + source[i].getDesc());
            }

            // Get user choice
            System.out.print("Enter choice: ");
            int choice = sc.nextInt() - 1;

            if (choice < 0 || choice >= source.length) {
                System.out.println("Invalid choice. Try again.\n");
                continue;
            }

            // Check for duplicates
            boolean duplicate = false;
            for (int j = 0; j < count; j++) {
                if (selected[j] == source[choice]) {
                    duplicate = true;
                    break;
                }
            }

            if (duplicate) {
                System.out.println("You've already selected that ability. Choose another.\n");
            } else {
                selected[count] = source[choice];
                System.out.println("You have chosen " + selected[count].getAbilityName() + "\n");
                count++;
            }
        }

        return selected;
        }

        /**
         * Builds a String representation of this player's characters.
         *
         * @param mode 1 for simple view, 2 for detailed view
         * @return A multi‐line String listing each character.
         */
        public String getCharactersAsString(int mode) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < characterCount; i++) {
                Character c = characters[i];
                sb.append("[").append(i + 1).append("] ")
                .append(c.getCharacterName());
                if (mode == 2) {
                    // Detailed info
                    sb.append(" (Race: ").append(c.getRace())
                    .append(", Type: ").append(c.getType())
                    .append(")\n  HP: ").append(c.getHp())
                    .append(" | EP: ").append(c.getEp())
                    .append("\n  Abilities:\n");
                    // List abilities, skipping nulls
                    for (Ability a : c.getAbility()) {
                        if (a == null) continue;
                        int epCost = Math.max(0, a.getEpCost());
                        sb.append("    - ")
                        .append(a.getAbilityName())
                        .append(" (").append(epCost).append(" EP): ")
                        .append(a.getDesc())
                        .append("\n");
                    }
                } else {
                    sb.append("\n");
                }
            }
            return sb.toString();
        }

        /**
         * ViewCharacters.
         * @param mode View mode
         */
    public void viewCharacters(int mode) {
        System.out.print(getCharactersAsString(mode));
    }

    /**
     * start Gnome Ability Selection.
     * @param type CharacterType
     */
    public void startGnomeAbilitySelection(CharacterType type) {
        this.gnomeBaseType = type;
        this.gnomeSelectedAbilities = new Ability[6];
        this.gnomeSelectedAbilities[4] = new Ability("Defend", "Take Half Damage", 5, 0);
        this.gnomeSelectedAbilities[5] = new Ability("Recharge", "Gain 5 EP", -5, 0);
        this.gnomeSelectCount = 0;
        this.gnomeSourceAbilities = type.getAbilities(); // Start with base abilities
    }

    /**
     * select Gnome Ability By Index.
     * @param index Index of Ability[]
     * @return String
     */
    public String selectGnomeAbilityByIndex(int index) {
        if (gnomeSourceAbilities == null || index < 0 || index >= gnomeSourceAbilities.length) {
            return "Invalid choice. Try again.";
        }

        Ability chosen = gnomeSourceAbilities[index];

        // Check for duplicates
        for (int i = 0; i < gnomeSelectCount; i++) {
            if (gnomeSelectedAbilities[i] == chosen) {
                return "You've already selected that ability. Choose another.";
            }
        }

        gnomeSelectedAbilities[gnomeSelectCount] = chosen;
        gnomeSelectCount++;

        return "You have chosen " + chosen.getAbilityName();
    }

    /**
     * Get Ability List For GUI Gnome.
     * @param selectedCharType CharacterType fo the selected Character
     * @return String
     */
    public String getAbilityListForGUIGnome(CharacterType selectedCharType) {

        Ability[] gnomeAbilities = selectedCharType.getAbilities();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < gnomeAbilities.length; i++) {
            int index = (i + 1);
            int epLabel = Math.max(0, gnomeAbilities[i].getEpCost());
            sb.append(index)
            .append(". ")
            .append(gnomeAbilities[i].getAbilityName())
            .append(" (")
            .append(epLabel)
            .append(" EP): ")
            .append(gnomeAbilities[i].getDesc())
            .append("\n");
        }

        return sb.toString();
    }

    /**
     * Checks if Gnome Selection is Done.
     * @return boolean
     */
    public boolean isGnomeSelectionDone() {
        return gnomeSelectCount >= 4;
    }

    /**
     * Get Final Gnome Abilities.
     * @return Ability[]
     */
    public Ability[] getFinalGnomeAbilities() {
        return gnomeSelectedAbilities;
    }

    /**
     * Set Gnome's Fourth Ability Class.
     * @param bonusClass The CharacterType of the bonus class of characters
     */
    public void setGnomeFourthAbilityClass(CharacterType bonusClass) {
        this.gnomeSourceAbilities = bonusClass.getAbilities();
    }

    /**
     * Deletes Character For GUI.
     * @param index Index of characters[]
     */
    public void deleteCharacterGUI(int index) {
    if (index >= 0 && index < characterCount) {
        for (int i = index; i < characterCount - 1; i++) {
            characters[i] = characters[i + 1];
        }
        characters[characterCount - 1] = null;
        characterCount--;
    }
}

    /**
     * Remove Magic Item By Visible Index.
     * @param visibleIndex Magic Item index
     * @return MagicItem
     */
    public MagicItem removeMagicItemByVisibleIndex(int visibleIndex) {
        int itemNum = 0;
        for (int i = 0; i < magicItems.size(); i++) {
            if (magicItems.get(i) != null) {
                if (itemNum == visibleIndex) {
                    MagicItem removed = magicItems.get(i);
                    magicItems.set(i, null);
                    return removed;
                }
                itemNum++;
            }
        }
        return null;
    }

    /**
     * Gets Magic Items List For GUI.
     * @return String
     */
    public String getMagicItemsListForGUI() {
        StringBuilder sb = new StringBuilder();
        int itemNum = 1;
        for (int i = 0; i < magicItems.size(); i++) {
            MagicItem mi = magicItems.get(i);
            if (mi != null) {
                sb.append(itemNum).append(". ").append(mi.getName()).append(": ").append(mi.getDescription()).append("\n");
                itemNum++;
            }
        }
        return sb.toString();
    }
}
