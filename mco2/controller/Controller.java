/**
 * Controller.java
 *
 * Handles user input and game state management for the application.
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Character;
import model.Ability;
import model.CharacterType;
import model.Player;
import model.Race;
import model.model;
import model.MagicItem;
import view.MainFrame;
import view.UserMenu;
import view.Interface;
import java.util.HashSet;
import java.util.Set;

/**
 * Controller class implementing ActionListener and DocumentListener to handle UI events.
 */
public class Controller implements ActionListener, DocumentListener{
    private MainFrame gui;
    private model model;
    private UserMenu usMenu;
    private Interface intrfc;
    private int editingIndex = -1;

                                    //manage
    private enum InputState { NONE, AWAITING_PLAYER, AWAITING_MODE,  AWAITING_DETAIL, AWAITING_DETAIL_SELECTION , AWAITING_CHARNAME, 
        AWAITING_CHARRACE, AWAITING_CHARTYPE, AWAITING_ABILITY1, AWAITING_ABILITY2, AWAITING_ABILITY3, AWAITING_ABILITY1GNOME,
    AWAITING_ABILITY2GNOME, AWAITING_ABILITY3GNOME,AWAITING_ABILITY4GNOME,AWAITING_CHARTYPEGNOME, 
    AWAITING_EDIT_SELECTION, AWAITING_EDIT_ABILITY1, AWAITING_EDIT_ABILITY2, AWAITING_EDIT_ABILITY3, AWAITING_EDIT_ABILITY1_GNOME,
  AWAITING_EDIT_ABILITY2_GNOME, AWAITING_EDIT_ABILITY3_GNOME, AWAITING_EDIT_ABILITY4_GNOME, AWAITING_EDIT_CHARTYPE_GNOME, 
AWAITING_DELETE_INDEX, 

//fight
AWAITING_P1_CHARACTER_SELECTION, AWAITING_P2_CHARACTER_SELECTION, BATTLE_START,
// New fight states
AWAITING_P1_ABILITY_SELECTION, AWAITING_P2_ABILITY_SELECTION, SHOW_ROUND_RESULT, SHOW_BATTLE_RESULT
}

    private InputState inputState = InputState.NONE;

    private int fightRound = 1;
    private boolean c1AmuVitTrue, c2AmuVitTrue, c1RingFocusTrue, c2RingFocusTrue, c1OrbResTrue, c2OrbResTrue, c1AnPowTrue, c2AnPowTrue;
    private int c1maxCount, c2maxCount;
    private Ability p1SelectedAbility, p2SelectedAbility;

    /**
     * Builds a prompt string listing the player's magic items.
     * @param p the Player whose magic items are listed
     * @return a formatted prompt string
     */
    private String getMagicItemsPrompt(Player player) {
        StringBuilder sb = new StringBuilder();
        java.util.List<MagicItem> items = player.getAllMagicItems();
        int itemNum = 1;
        for (int i = 0; i < items.size(); i++) {
            MagicItem mi = items.get(i);
            if (mi != null) {
                sb.append(itemNum).append(". ").append(mi.getName()).append(": ").append(mi.getDescription()).append("\n");
                itemNum++;
            }
        }
        return sb.toString();
    }


    private boolean awaitingMagicItemP1 = false;
    private boolean awaitingMagicItemP2 = false;
    private Set<Character> amuletApplied = new HashSet<>();
    private boolean c1DefAegis = false;
    private boolean c2DefAegis = false;

    /**
     * Checks if the player has any magic items they can use.
     * @param p the Player to check for usable magic items
     * @return true if usable magic items exist, false otherwise
     */
    private boolean hasUsableMagicItems(Player player) {
        for (MagicItem mi : player.getAllMagicItems()) {
            if (mi != null) return true;
        }
        return false;
    }

    /**
     * Constructs a Controller with the given MainFrame.
     * @param frame the MainFrame instance for the UI
     */
    public Controller( model model, MainFrame gui){
        this.gui = gui;
        this.model = model;
        this.usMenu = gui.getUserMenu();
        this.intrfc = gui.getInterface();

        updateView();
        intrfc.setActionListener(this);
        intrfc.setDocumentListener(this);


        usMenu.setActionListener(this);
        usMenu.setDocumentListener(this);

    }

    public void updateView(){
        System.err.println("hello");
    }

   
    @Override
     /**
     * Invoked when an action occurs (e.g., button press in the UI).
     * @param e the ActionEvent triggered by the UI
     */
    public void actionPerformed (ActionEvent e){
        switch (e.getActionCommand()) {
            case "Start":
                String Username1 = usMenu.getUsername1();
                String Username2 = usMenu.getUsername2();
                model.getp1().setName(Username1);
                model.getp2().setName(Username2);
                intrfc.setUsername1(model.getp1().getName());
                intrfc.setUsername2(model.getp2().getName());

                intrfc.setGameAreaText("Which action do you want to do? (click Button below)");

                gui.showCard("INTERFACE");

                
                break;
            case "Manage":
                intrfc.setGameAreaText("Which player is in control? (1, 2):");
                intrfc.showManageControls();
                inputState = InputState.AWAITING_PLAYER;
                intrfc.clearUserAnswer();
                break;
        
            case "Fight":
                gui.getInterface();
                intrfc.showManageControls();
                if (model.getp1().getCharacterCount() == 0 || model.getp2().getCharacterCount() ==0 ){
                    intrfc.setGameAreaText("Both players must have at least 1 character to fight!");
                }
                else{
                    intrfc.setGameAreaText(model.getp1().getName() + ", choose your character by number:\n"
                        + model.getp1().getCharactersAsString(2));
                    inputState = InputState.AWAITING_P1_CHARACTER_SELECTION;
                    intrfc.clearUserAnswer();
                }

                break;
            case "Hallff":
                gui.getInterface();
                // Display player rankings by win count
                StringBuilder ranking = new StringBuilder();
                ranking.append("--- PLAYER RANKINGS ---\n");
                Player p1 = model.getp1();
                Player p2 = model.getp2();
                if (p1.getWinCount() > p2.getWinCount()) {
                    ranking.append("1. " + p1.getName() + " - " + p1.getWinCount() + " wins\n");
                    ranking.append("2. " + p2.getName() + " - " + p2.getWinCount() + " wins\n");
                } else if (p2.getWinCount() > p1.getWinCount()) {
                    ranking.append("1. " + p2.getName() + " - " + p2.getWinCount() + " wins\n");
                    ranking.append("2. " + p1.getName() + " - " + p1.getWinCount() + " wins\n");
                } else {
                    ranking.append("1. " + p1.getName() + " - " + p1.getWinCount() + " wins\n");
                    ranking.append("1. " + p2.getName() + " - " + p2.getWinCount() + " wins\n");
                }
                // Display character rankings by name only
                ranking.append("\n--- CHARACTER RANKINGS ---\n");
                java.util.List<Character> allChars = new java.util.ArrayList<>();
                for (Character c : p1.getCharacters()) {
                    if (c != null) allChars.add(c);
                }
                for (Character c : p2.getCharacters()) {
                    if (c != null) allChars.add(c);
                }
                // Sort characters by winCount descending, then by name
                allChars.sort((a, b) -> {
                    int cmp = Integer.compare(b.getWinCount(), a.getWinCount());
                    if (cmp != 0) return cmp;
                    return a.getCharacterName().compareToIgnoreCase(b.getCharacterName());
                });
                int rank = 1;
                for (Character c : allChars) {
                    String charLine = rank + ". " + c.getCharacterName() + " (" + c.getType() + ", " + c.getRace() + ") - " + c.getWinCount() + " wins";
                    ranking.append(charLine + "\n");
                    rank++;
                }
                ranking.append("\nPress Confirm to return to menu.");
                intrfc.setGameAreaText(ranking.toString());
                intrfc.hideManageControls();
                inputState = InputState.SHOW_ROUND_RESULT; // Use a state to catch Confirm
                break;
            case "End":
                gui.getInterface();
                System.err.println("end");
                intrfc.hideManageControls();
                System.exit(0);
                inputState = InputState.NONE;
                break;
            case "Confirm":
                gui.getInterface();
                System.err.println("yay");
                String ans = intrfc.getUserAnswer().trim();
                if (inputState == InputState.SHOW_ROUND_RESULT) {
                    intrfc.setGameAreaText("Which action do you want to do? (click Button below)");
                    inputState = InputState.NONE;
                    intrfc.showManageControls();
                    intrfc.clearUserAnswer();
                }

                else if (inputState == InputState.AWAITING_PLAYER) {
                    if ("1".equals(ans)) {
                        model.setTemPlayer(model.getp1());
                        intrfc.appendGameAreaText("\nPlayer " + model.getTemPlayer().getName() + " is now in control.");
                        
                        intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                        inputState = InputState.AWAITING_MODE;
                        intrfc.clearUserAnswer();
                        
                    } else if("2".equals(ans)){
                        model.setTemPlayer(model.getp2());
                        intrfc.appendGameAreaText("\nPlayer " + model.getTemPlayer().getName() + " is now in control.");
                        
                        intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                        inputState = InputState.AWAITING_MODE;
                        intrfc.clearUserAnswer();
                    }
                    else {
                        intrfc.appendGameAreaText("\nInvalid input! Please enter 1 or 2.");
                        intrfc.clearUserAnswer();
                    }
                } else if (inputState == InputState.AWAITING_MODE) {
                    
                    switch (ans.toLowerCase()) {
                        case "1":
                            intrfc.setGameAreaText("You chose VIEW mode.");
                            if(model.getTemPlayer().getCharacterCount() == 0){
                                    intrfc.appendGameAreaText("\nNo characters created yet.");

                                    intrfc.clearUserAnswer();
                                    intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                                    inputState = InputState.AWAITING_MODE;
                            }
                            else{
                                intrfc.setGameAreaText("Choose a mode (1. simple, 2. detailed):");
                                intrfc.clearUserAnswer();
                                inputState = InputState.AWAITING_DETAIL;
                            }
                
                            break;
                        case "2":
                            intrfc.setGameAreaText("You chose CREATE mode.");
                            if(model.getTemPlayer().getCharacterCount() < 6){
                                intrfc.appendGameAreaText("\nName Your Character");
                                inputState = InputState.AWAITING_CHARNAME;
                                intrfc.clearUserAnswer();
                            }
                            else{
                                intrfc.appendGameAreaText("\nMaximum of 6 characters reached.");
                            }
                            break;
                        case "3":
                            intrfc.setGameAreaText("\nYou chose EDIT mode.");
                            if(model.getTemPlayer().getCharacterCount() == 0){
                                    intrfc.appendGameAreaText("\nNo characters created yet.");

                                    intrfc.clearUserAnswer();
                                    intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                                    inputState = InputState.AWAITING_MODE;
                                    intrfc.clearUserAnswer();
                            }
                            else{
                                intrfc.appendGameAreaText("\nWhich Hero do you want to edit? (Enter Number):");
                                intrfc.appendGameAreaText("\n" + model.getTemPlayer().getCharactersAsString(1));
                                inputState = InputState.AWAITING_EDIT_SELECTION;
                                intrfc.clearUserAnswer();
                            }
                            break;
                        case "4":
                            intrfc.appendGameAreaText("\nYou chose DELETE mode.");
                            if (model.getTemPlayer().getCharacterCount() == 0) {
                                intrfc.appendGameAreaText("\nYou don't have any heroes to delete!");
                                intrfc.clearUserAnswer();
                                intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                                inputState = InputState.AWAITING_MODE;
                            } else {
                                String charList = model.getTemPlayer().getCharactersAsString(1); 
                                intrfc.setGameAreaText("\nWhich hero do you want to delete? (Enter number):\n" + charList);
                                intrfc.clearUserAnswer();
                                inputState = InputState.AWAITING_DELETE_INDEX;
                            }
                            break;
                        case "5":
                            intrfc.setGameAreaText("Exiting manage menu.");
                            intrfc.setGameAreaText("Which player is in control? (1, 2):");
                            inputState = InputState.AWAITING_PLAYER;
                            intrfc.clearUserAnswer();
                            break;
                        default:
                            intrfc.appendGameAreaText("\nInvalid mode! Please enter (1. view, 2. create, 3. edit, 4. delete, 5. exit).");
                            // stay in AWAITING_MODE so they can retry
                            intrfc.clearUserAnswer();
                            break;  
                    }
                } 
                else if (inputState == InputState.AWAITING_DETAIL) {
                    
                    switch (ans.toLowerCase()) {
                        case "1":
                            intrfc.setGameAreaText("Showing SIMPLE view of characters...");
                            String simpleList = model.getTemPlayer().getCharactersAsString(1);
                            intrfc.appendGameAreaText("\n"+ simpleList);
                            
                            intrfc.clearUserAnswer();
                            intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                            inputState = InputState.AWAITING_MODE;
                            
                            break;
                       case "2":
                            intrfc.setGameAreaText("Showing DETAILED view of all characters...\n");
                            String detailedList = model.getTemPlayer().getCharactersAsString(2); // mode 2 = DETAILED
                            intrfc.appendGameAreaText(detailedList);
                            
                            intrfc.clearUserAnswer();
                            intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                            inputState = InputState.AWAITING_MODE;
                            break;
                        default:
                            intrfc.clearUserAnswer();
                            intrfc.appendGameAreaText("\nInvalid, CHoose Between (1. simple, 2. detailed):");
                        break;
                    }
                }

                else if (inputState == InputState.AWAITING_CHARNAME) {
                   String charName = ans; 
                    if (charName.isEmpty()) {
                        intrfc.appendGameAreaText("\nName cannot be empty. Try again:");
                        intrfc.clearUserAnswer();
                        return;
                    }
                    intrfc.appendGameAreaText("\nWelcom Hero " + charName + "!");
                    model.setTempCharName(charName);

                    intrfc.appendGameAreaText("\nChoose a Race (1. Human, 2.Elf, 3. Dwarf, 4. Gnome):");
                    inputState = InputState.AWAITING_CHARRACE;
                    intrfc.clearUserAnswer();
                }
                else if (inputState == InputState.AWAITING_CHARRACE) {
                   Race selectedRace;
                    switch (ans.toLowerCase()) {
                        case "1":
                            selectedRace = Race.Human;
                            break;
                        case "2":
                            selectedRace = Race.Elf;
                            break;
                        case "3":
                            selectedRace = Race.Dwarf;
                            break;
                        case "4":
                            selectedRace = Race.Gnome;
                            break;
                        default:
                            intrfc.appendGameAreaText("\nInvalid race! Please enter (1. Human, 2.Elf, 3. Dwarf, 4. Gnome):");
                            intrfc.clearUserAnswer();
                            return;
                    }

                model.setTempCharRace(selectedRace); // store race temp
                intrfc.setGameAreaText("Choose a Character Type (1. Mage, 2. Warrior, 3. Rogue):");
                inputState = InputState.AWAITING_CHARTYPE;
                intrfc.clearUserAnswer();
                return;
                }
                else if (inputState == InputState.AWAITING_CHARTYPE) {
                    CharacterType selectedType;
                    switch (ans.toLowerCase()) {
                        case "2":
                            selectedType = CharacterType.Warrior;
                            break;
                        case "1":
                            selectedType = CharacterType.Mage;
                            break;
                        case "3":
                            selectedType = CharacterType.Rogue;
                            break;
                        default:
                            intrfc.appendGameAreaText("\nInvalid character type! Please enter (1. Mage, 2. Warrior, 3. Rogue):");
                            intrfc.clearUserAnswer();
                            return;
                    }
                    
                    model.setTempCharType(selectedType);

                    if(model.getTempCharRace() != Race.Gnome){
                        model.getTemPlayer().selectThreeAbilitiesGUI(selectedType);
                        String options = model.getTemPlayer().getAbilityListForGUI(selectedType);
                        intrfc.setGameAreaText("Select ability #1:\n" + options);
                        
                        inputState = InputState.AWAITING_ABILITY1;
                        intrfc.clearUserAnswer();
                    }
                    else{
                        model.getTemPlayer().startGnomeAbilitySelection(selectedType); 
                        String options = model.getTemPlayer().getAbilityListForGUIGnome(selectedType);
                        intrfc.setGameAreaText("Select ability #1:\n" + options);
                        
                        inputState = InputState.AWAITING_ABILITY1GNOME;
                        intrfc.clearUserAnswer();
                    }
                    
                }
                else if (inputState == InputState.AWAITING_ABILITY1) {
                    int index = 0;
                    switch (ans.toLowerCase()) {
                        case "1":
                            index = 0;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));
                            break;
                        case "2":
                            index = 1;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));
                            break;
                        case "3":
                            index = 2;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));    
                            break;
                        case "4":
                            index = 3;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));  
                            break;
                        case "5":
                            index = 4;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));  
                            break;
                    
                        default:
                            intrfc.setGameAreaText("Invalid!");
                            String options = model.getTemPlayer().getAbilityListForGUI(model.getTempCharType());
                            intrfc.appendGameAreaText("\nSelect ability #1:\n" + options);
                            inputState = InputState.AWAITING_ABILITY1;
                            break;
                    }

                    String options = model.getTemPlayer().getAbilityListForGUI(model.getTempCharType());
                    intrfc.appendGameAreaText("\nSelect ability #2:\n" + options);
                    inputState = InputState.AWAITING_ABILITY2;
                    intrfc.clearUserAnswer();
                }
                else if (inputState == InputState.AWAITING_ABILITY2) {
                    int index = 0;
                    switch (ans.toLowerCase()) {
                        case "1":
                            index = 0;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));
                            break;
                        case "2":
                            index = 1;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));
                            break;
                        case "3":
                            index = 2;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));    
                            break;
                        case "4":
                            index = 3;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));  
                            break;
                        case "5":
                            index = 4;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));  
                            break;
                    
                        default:
                            intrfc.setGameAreaText("Invalid!");
                            String options = model.getTemPlayer().getAbilityListForGUI(model.getTempCharType());
                            intrfc.appendGameAreaText("\nSelect ability #2:\n" + options);
                            inputState = InputState.AWAITING_ABILITY2;
                            break;
                    }

                    String options = model.getTemPlayer().getAbilityListForGUI(model.getTempCharType());
                    intrfc.appendGameAreaText("\nSelect ability #3:\n" + options);
                    inputState = InputState.AWAITING_ABILITY3;
                    intrfc.clearUserAnswer();
                }
                else if (inputState == InputState.AWAITING_ABILITY3) {
                    int index = 0;
                    switch (ans.toLowerCase()) {
                        case "1":
                            index = 0;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));
                            break;
                        case "2":
                            index = 1;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));
                            break;
                        case "3":
                            index = 2;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));    
                            break;
                        case "4":
                            index = 3;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));  
                            break;
                        case "5":
                            index = 4;
                            intrfc.setGameAreaText(model.getTemPlayer().selectAbilityByIndex(index));  
                            break;
                    
                        default:
                            intrfc.setGameAreaText("Invalid!");
                            String options = model.getTemPlayer().getAbilityListForGUI(model.getTempCharType());
                            intrfc.appendGameAreaText("\nSelect ability #3:\n" + options);
                            inputState = InputState.AWAITING_ABILITY3;
                            break;
                    }
                    Ability[] finalAbilities = model.getTemPlayer().getSelectedAbilities();
                    intrfc.setGameAreaText("\n" + model.getTemPlayer().createCharacterAndReturnStatus(model.getTempCharName(), 
                    model.getTempCharType(), finalAbilities, model.getTempCharRace()));
                    intrfc.clearUserAnswer();
                    intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                    inputState = InputState.AWAITING_MODE;
                }

                else if (inputState == InputState.AWAITING_ABILITY1GNOME) {
                    int index = 0;
                    switch (ans.toLowerCase()) {
                        case "1":
                            index = 0;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));
                            break;
                        case "2":
                            index = 1;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));
                            break;
                        case "3":
                            index = 2;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));    
                            break;
                        case "4":
                            index = 3;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));  
                            break;
                        case "5":
                            index = 4;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));  
                            break;
                    
                        default:
                            intrfc.setGameAreaText("Invalid!");
                            String options = model.getTemPlayer().getAbilityListForGUIGnome(model.getTempCharType());
                            intrfc.setGameAreaText("Select ability #1:\n" + options);
                            inputState = InputState.AWAITING_ABILITY1;
                            break;
                    }

                    String options = model.getTemPlayer().getAbilityListForGUIGnome(model.getTempCharType());
                    intrfc.appendGameAreaText("\nSelect ability #2:\n" + options);
                    inputState = InputState.AWAITING_ABILITY2GNOME;
                    intrfc.clearUserAnswer();
                }
                else if (inputState == InputState.AWAITING_ABILITY2GNOME) {
                    int index = 0;
                    switch (ans.toLowerCase()) {
                        case "1":
                            index = 0;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));
                            break;
                        case "2":
                            index = 1;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));
                            break;
                        case "3":
                            index = 2;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));    
                            break;
                        case "4":
                            index = 3;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));  
                            break;
                        case "5":
                            index = 4;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));  
                            break;
                    
                        default:
                            intrfc.setGameAreaText("Invalid!");
                            String options = model.getTemPlayer().getAbilityListForGUIGnome(model.getTempCharType());
                            intrfc.setGameAreaText("Select ability #2:\n" + options);
                            inputState = InputState.AWAITING_ABILITY2;
                            break;
                    }

                    String options = model.getTemPlayer().getAbilityListForGUIGnome(model.getTempCharType());
                    intrfc.appendGameAreaText("\nSelect ability #3:\n" + options);
                    inputState = InputState.AWAITING_ABILITY3GNOME;
                    intrfc.clearUserAnswer();
                }
                else if (inputState == InputState.AWAITING_ABILITY3GNOME) {
                    int index = 0;
                    switch (ans.toLowerCase()) {
                        case "1":
                            index = 0;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));
                            break;
                        case "2":
                            index = 1;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));
                            break;
                        case "3":
                            index = 2;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));    
                            break;
                        case "4":
                            index = 3;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));  
                            break;
                        case "5":
                            index = 4;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));  
                            break;
                    
                        default:
                            intrfc.setGameAreaText("Invalid!");
                            String options = model.getTemPlayer().getAbilityListForGUIGnome(model.getTempCharType());
                            intrfc.setGameAreaText("Select ability #3:\n" + options);
                            inputState = InputState.AWAITING_ABILITY3;
                            break;
                    }

                    intrfc.setGameAreaText("Choose a Character Type (1. Mage, 2. Warrior, 3. Rogue):");
                    inputState = InputState.AWAITING_CHARTYPEGNOME;
                    intrfc.clearUserAnswer();
                }
                else if (inputState == InputState.AWAITING_CHARTYPEGNOME) {
                    CharacterType selectedType;
                    switch (ans.toLowerCase()) {
                        case "2":
                            selectedType = CharacterType.Warrior;
                            break;
                        case "1":
                            selectedType = CharacterType.Mage;
                            break;
                        case "3":
                            selectedType = CharacterType.Rogue;
                            break;
                        default:
                            intrfc.appendGameAreaText("\nInvalid character type! Please enter (1. Mage, 2. Warrior, 3. Rogue):");
                            intrfc.clearUserAnswer();
                            return;
                    }
                    
                    model.setTempCharTypeGnome(selectedType);

                    model.getTemPlayer().setGnomeFourthAbilityClass(selectedType);

                    String options = model.getTemPlayer().getAbilityListForGUIGnome(selectedType);
                    intrfc.setGameAreaText("Select ability #4:\n" + options);
                    
                    inputState = InputState.AWAITING_ABILITY4GNOME;
                    intrfc.clearUserAnswer();

                    
                }
                else if (inputState == InputState.AWAITING_ABILITY4GNOME) {
                    int index = 0;
                    switch (ans.toLowerCase()) {
                        case "1":
                            index = 0;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));
                            break;
                        case "2":
                            index = 1;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));
                            break;
                        case "3":
                            index = 2;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));    
                            break;
                        case "4":
                            index = 3;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));  
                            break;
                        case "5":
                            index = 4;
                            intrfc.setGameAreaText(model.getTemPlayer().selectGnomeAbilityByIndex(index));  
                            break;
                    
                        default:
                            intrfc.setGameAreaText("Invalid!");
                            String options = model.getTemPlayer().getAbilityListForGUIGnome(model.getTempCharTypeGnome());
                            intrfc.setGameAreaText("Select ability #4:\n" + options);
                            inputState = InputState.AWAITING_EDIT_ABILITY4_GNOME;
                            break;
                    }

                    Ability[] finalAbilities = model.getTemPlayer().getFinalGnomeAbilities();
                   CharacterType originalType = model.getTempCharType();  

                    intrfc.setGameAreaText(
                        model.getTemPlayer().createCharacterAndReturnStatus(
                            model.getTempCharName(),
                            originalType, 
                            finalAbilities,
                            model.getTempCharRace()
                        )
                    );
                    
                    intrfc.clearUserAnswer();
                    intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                    inputState = InputState.AWAITING_MODE;
                }

                else if (inputState == InputState.AWAITING_EDIT_SELECTION) {
                    int idx = -1;  // Set default invalid index

                    switch (ans) {
                        case "1": idx = 0; break;
                        case "2": idx = 1; break;
                        case "3": idx = 2; break;
                        case "4": idx = 3; break;
                        case "5": idx = 4; break;
                        case "6": idx = 5; break;
                        default:
                            idx = -1;
                    }

                    if (idx == -1) {
                        intrfc.appendGameAreaText("\nInvalid input! Please enter 1" 
                            + model.getTemPlayer().getCharacterCount() + ":");
                        intrfc.clearUserAnswer();
                    } else if (idx >= model.getTemPlayer().getCharacterCount()) {
                        intrfc.appendGameAreaText("\nNo character at that index! Choose 1" 
                            + model.getTemPlayer().getCharacterCount() + ":");
                        intrfc.clearUserAnswer();
                    } else {
                        
                        editingIndex = idx;
                        Character toEdit = model.getTemPlayer().getCharacters()[editingIndex];
                        intrfc.appendGameAreaText("\nEditing:\n");

                        if (toEdit.getRace() == Race.Gnome) {
                            model.getTemPlayer().startGnomeAbilitySelection(toEdit.getType());
                            intrfc.appendGameAreaText("Select ability #1:\n" +
                                model.getTemPlayer().getAbilityListForGUIGnome(toEdit.getType()));
                            inputState = InputState.AWAITING_EDIT_ABILITY1_GNOME;
                        } else {
                            model.getTemPlayer().selectThreeAbilitiesGUI(toEdit.getType());
                            intrfc.appendGameAreaText("Select ability #1:\n" +
                                model.getTemPlayer().getAbilityListForGUI(toEdit.getType()));
                            inputState = InputState.AWAITING_EDIT_ABILITY1;
                        }

                        intrfc.clearUserAnswer();
                    }
                }
                else if (inputState == InputState.AWAITING_EDIT_ABILITY1) {
                    int abilityIdx = -1;
                    switch (ans) {
                        case "1": abilityIdx = 0; break;
                        case "2": abilityIdx = 1; break;
                        case "3": abilityIdx = 2; break;
                        case "4": abilityIdx = 3; break;
                        case "5": abilityIdx = 4; break;
                        default: break;
                    }

                    if (abilityIdx == -1) {
                        intrfc.appendGameAreaText("\nInvalid! Enter 1 - 5:");
                        intrfc.clearUserAnswer();
                        inputState = InputState.AWAITING_EDIT_ABILITY1;
                    } else {
                        intrfc.appendGameAreaText("\n" + model.getTemPlayer().selectAbilityByIndex(abilityIdx));
                        intrfc.appendGameAreaText("\nSelect ability #2:\n" +
                            model.getTemPlayer().getAbilityListForGUI(
                                model.getTemPlayer().getCurrentAbilityType()));
                        inputState = InputState.AWAITING_EDIT_ABILITY2;
                        intrfc.clearUserAnswer();
                    }
                }
                else if (inputState == InputState.AWAITING_EDIT_ABILITY2) {
                    int abilityIdx = -1;
                    switch (ans) {
                        case "1": abilityIdx = 0; break;
                        case "2": abilityIdx = 1; break;
                        case "3": abilityIdx = 2; break;
                        case "4": abilityIdx = 3; break;
                        case "5": abilityIdx = 4; break;
                        default: break;
                    }

                    if (abilityIdx == -1) {
                        intrfc.appendGameAreaText("\nInvalid! Enter 1 - 5:");
                        intrfc.clearUserAnswer();
                        inputState = InputState.AWAITING_EDIT_ABILITY2;
                    } else {
                        intrfc.appendGameAreaText("\n" + model.getTemPlayer().selectAbilityByIndex(abilityIdx));
                        intrfc.appendGameAreaText("\nSelect ability #3:\n" +
                            model.getTemPlayer().getAbilityListForGUI(
                                model.getTemPlayer().getCurrentAbilityType()));
                        inputState = InputState.AWAITING_EDIT_ABILITY3;
                        intrfc.clearUserAnswer();
                    }
                }
                else if (inputState == InputState.AWAITING_EDIT_ABILITY3) {
                    int abilityIdx = -1;
                    switch (ans) {
                        case "1": abilityIdx = 0; break;
                        case "2": abilityIdx = 1; break;
                        case "3": abilityIdx = 2; break;
                        case "4": abilityIdx = 3; break;
                        case "5": abilityIdx = 4; break;
                        default: break;
                    }

                    if (abilityIdx == -1) {
                        intrfc.appendGameAreaText("\nInvalid! Enter 1 - 5:");
                        intrfc.clearUserAnswer();
                        inputState = InputState.AWAITING_EDIT_ABILITY3;
                    } else {
                        intrfc.appendGameAreaText("\n" + model.getTemPlayer().selectAbilityByIndex(abilityIdx));

                        
                        Ability[] newAbilities = model.getTemPlayer().getSelectedAbilities();
                        model.getTemPlayer().editCharacter(editingIndex, newAbilities);

                        intrfc.appendGameAreaText("\nAbilities updated for " +
                            model.getTemPlayer().getCharacters()[editingIndex].getCharacterName() + "!");

                        
                        intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                        inputState = InputState.AWAITING_MODE;
                        intrfc.clearUserAnswer();
                    }
                }

                else if (inputState == InputState.AWAITING_EDIT_ABILITY1_GNOME) {

                    int idx = -1;
                    switch (ans) {
                        case "1": idx = 0; break;
                        case "2": idx = 1; break;
                        case "3": idx = 2; break;
                        case "4": idx = 3; break;
                        default: break;
                    }

                    if (idx == -1) {
                        intrfc.appendGameAreaText("\nInvalid! Enter 1 - 4:");
                        intrfc.clearUserAnswer();
                        inputState = InputState.AWAITING_EDIT_ABILITY1_GNOME;
                    } else {
                        intrfc.appendGameAreaText("\n" + model.getTemPlayer().selectGnomeAbilityByIndex(idx));

                        String options = model.getTemPlayer().getAbilityListForGUIGnome(model.getTempCharType());
                        intrfc.appendGameAreaText("\nSelect ability #2:\n" + options);
                        inputState = InputState.AWAITING_EDIT_ABILITY2_GNOME;
                        intrfc.clearUserAnswer();
                    }
                }
                else if (inputState == InputState.AWAITING_EDIT_ABILITY2_GNOME) {
                    
                    int idx = -1;
                    switch (ans) {
                        case "1": idx = 0; break;
                        case "2": idx = 1; break;
                        case "3": idx = 2; break;
                        case "4": idx = 3; break;
                        default: break;
                    }

                    if (idx == -1) {
                        intrfc.appendGameAreaText("\nInvalid! Enter 1 - 4:");
                        intrfc.clearUserAnswer();
                        inputState = InputState.AWAITING_EDIT_ABILITY2_GNOME;
                    } else {
                        intrfc.appendGameAreaText("\n" + model.getTemPlayer().selectGnomeAbilityByIndex(idx));

                        String options = model.getTemPlayer().getAbilityListForGUIGnome(model.getTempCharType());
                        intrfc.appendGameAreaText("\nSelect ability #3:\n" + options);

                        inputState = InputState.AWAITING_EDIT_ABILITY3_GNOME;
                        intrfc.clearUserAnswer();
                    }
                }
                else if (inputState == InputState.AWAITING_EDIT_ABILITY3_GNOME) {

                int index = -1;

                switch (ans) {
                    case "1": index = 0; break;
                    case "2": index = 1; break;
                    case "3": index = 2; break;
                    case "4": index = 3; break;
                    default: break;
                }

                if (index == -1) {
                    intrfc.appendGameAreaText("\nInvalid! Enter 1 - 4:");
                    intrfc.clearUserAnswer();
                    inputState = InputState.AWAITING_EDIT_ABILITY3_GNOME; 
                } else {
                    intrfc.appendGameAreaText("\n" + model.getTemPlayer().selectGnomeAbilityByIndex(index));
                    intrfc.appendGameAreaText("\nChoose a Character Type for 4th ability (1. Mage, 2. Warrior, 3. Rogue):");
                    inputState = InputState.AWAITING_EDIT_CHARTYPE_GNOME;
                    intrfc.clearUserAnswer();
                }
            }
                else if (inputState == InputState.AWAITING_EDIT_CHARTYPE_GNOME) {

                    CharacterType selectedType = null;

                    switch (ans) {
                        case "1": selectedType = CharacterType.Mage; break;
                        case "2": selectedType = CharacterType.Warrior; break;
                        case "3": selectedType = CharacterType.Rogue; break;
                        default: break;
                    }

                    if (selectedType == null) {
                        intrfc.appendGameAreaText("\nInvalid character type! Enter 1 (Mage), 2 (Warrior), or 3 (Rogue):");
                        intrfc.clearUserAnswer();
                        inputState = InputState.AWAITING_EDIT_CHARTYPE_GNOME;  
                    } else {
                        model.setTempCharTypeGnome(selectedType);
                        model.getTemPlayer().setGnomeFourthAbilityClass(selectedType);

                        String options = model.getTemPlayer().getAbilityListForGUIGnome(selectedType);
                        intrfc.appendGameAreaText("Select ability #4:\n" + options);

                        inputState = InputState.AWAITING_EDIT_ABILITY4_GNOME;
                        intrfc.clearUserAnswer();
                    }
                }
                else if (inputState == InputState.AWAITING_EDIT_ABILITY4_GNOME) {
                    int index = -1;

                    switch (ans) {
                        case "1": index = 0; break;
                        case "2": index = 1; break;
                        case "3": index = 2; break;
                        case "4": index = 3; break;
                        case "5": index = 4; break;
                        default: index = -1; break;
                    }

                    if (index == -1) {
                        intrfc.appendGameAreaText("\nInvalid! Enter 1 - 5:");
                        String options = model.getTemPlayer().getAbilityListForGUIGnome(model.getTempCharTypeGnome());
                        intrfc.appendGameAreaText("\nSelect ability #4:\n" + options);
                        intrfc.clearUserAnswer();
                        inputState = InputState.AWAITING_EDIT_ABILITY4_GNOME; 
                    } else {
                        intrfc.appendGameAreaText("\n" + model.getTemPlayer().selectGnomeAbilityByIndex(index));

                        Ability[] newAbilities = model.getTemPlayer().getFinalGnomeAbilities();
                        model.getTemPlayer().editCharacter(editingIndex, newAbilities);

                        intrfc.appendGameAreaText("\nGnome abilities updated for " +
                            model.getTemPlayer().getCharacters()[editingIndex].getCharacterName() + "!");

                        intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                        inputState = InputState.AWAITING_MODE;
                        intrfc.clearUserAnswer();
                    }
                }
                
                else if (inputState == InputState.AWAITING_DELETE_INDEX) {
                    int idx = -1;

                    switch (ans) {
                        case "1": idx = 0; break;
                        case "2": idx = 1; break;
                        case "3": idx = 2; break;
                        case "4": idx = 3; break;
                        case "5": idx = 4; break;
                        case "6": idx = 5; break;
                        default: idx = -1; break;
                    }

                    if (idx == -1) {
                        intrfc.appendGameAreaText("\nInvalid input! Please enter a number from 1 to " 
                            + model.getTemPlayer().getCharacterCount() + ":");
                        intrfc.clearUserAnswer();
                        inputState = InputState.AWAITING_DELETE_INDEX;
                    } 
                    else if (idx >= model.getTemPlayer().getCharacterCount()) {
                        intrfc.appendGameAreaText("\nNo character at that index! Please choose from 1 to " 
                            + model.getTemPlayer().getCharacterCount() + ":");
                        intrfc.clearUserAnswer();
                        inputState = InputState.AWAITING_DELETE_INDEX;
                    } 
                    else {
                        model.getTemPlayer().deleteCharacter(idx);
                        intrfc.setGameAreaText("Character deleted successfully.");

                        intrfc.clearUserAnswer();
                        intrfc.appendGameAreaText("\nChoose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                        inputState = InputState.AWAITING_MODE;
                    }
                }
                
                
                else if(inputState == InputState.AWAITING_P1_CHARACTER_SELECTION) {
                    try {
                        int idx = Integer.parseInt(ans) - 1;
                        if (idx < 0 || idx >= model.getp1().getCharacterCount()) {
                            intrfc.appendGameAreaText("\nInvalid index! Try again:\n" + model.getp1().getCharactersAsString(2));
                            intrfc.clearUserAnswer();
                            return;
                        }
                        model.setFighter1(model.getp1().getCharacters()[idx]);
                        intrfc.appendGameAreaText("\n"+ model.getp1().getName() +"selected: " + model.getFighter1().getCharacterName());
                        intrfc.appendGameAreaText("\n\nSelect " + model.getp2().getName() + " character:\n" + model.getp2().getCharactersAsString(2));
                        inputState = InputState.AWAITING_P2_CHARACTER_SELECTION;
                        intrfc.clearUserAnswer();
                    } catch (NumberFormatException ex) {
                        intrfc.appendGameAreaText("\nInvalid input! Please enter a number:\n" + model.getp1().getCharactersAsString(2));
                        intrfc.clearUserAnswer();
                    }
                }
                else if (inputState == InputState.AWAITING_P2_CHARACTER_SELECTION) {
                try {
                    int idx = Integer.parseInt(ans) - 1;
                    if (idx < 0 || idx >= model.getp2().getCharacterCount()) {
                        intrfc.appendGameAreaText("\nInvalid index! Try again:\n" + model.getp2().getCharactersAsString(2));
                        intrfc.clearUserAnswer();
                        return;
                    }
                    model.setFighter2(model.getp2().getCharacters()[idx]);
                    intrfc.appendGameAreaText("\n" + model.getp2().getName()  + " selected: " + model.getFighter2().getCharacterName());
                    intrfc.appendGameAreaText("\n\nLet the battle begin!");

                    // Initialize fight state 
                    model.getFighter1().reset();
                    model.getFighter2().reset();
                    fightRound = 1;
                    c1AmuVitTrue = c2AmuVitTrue = c1RingFocusTrue = c2RingFocusTrue = false;
                    c1OrbResTrue = c2OrbResTrue = c1AnPowTrue = c2AnPowTrue = false;
                    c1maxCount = c2maxCount = 0;
                    p1SelectedAbility = null;
                    p2SelectedAbility = null;

                    intrfc.setGameAreaText("--- BATTLE START ---\n" + model.getFighter1().getCharacterName() + " VS " + model.getFighter2().getCharacterName());
                    intrfc.appendGameAreaText("\n\n--- ROUND " + fightRound + " ---");
                    intrfc.appendGameAreaText("\n" + model.getp1().getName() + " - " + model.getFighter1().getCharacterName() + " | HP: " + model.getFighter1().getHp() + " | EP: " + model.getFighter1().getEp());
                    intrfc.appendGameAreaText("\n" + model.getp2().getName() + " - " + model.getFighter2().getCharacterName() + " | HP: " + model.getFighter2().getHp() + " | EP: " + model.getFighter2().getEp());
                    String abilityPromptP1 = "\n\n" + model.getp1().getName() + ", choose your ability by number:\n" + model.getFighter1().getAbilitiesAsString();
                    if (hasUsableMagicItems(model.getp1())) {
                        abilityPromptP1 += (model.getFighter1().getAbilities().length + 1) + ". Magic Items\n";
                    }
                    intrfc.appendGameAreaText(abilityPromptP1);
                    inputState = InputState.AWAITING_P1_ABILITY_SELECTION;
                    intrfc.clearUserAnswer();
                } catch (NumberFormatException ex) {
                    intrfc.appendGameAreaText("\nInvalid input! Please enter a number:\n" + model.getp2().getCharactersAsString(2));
                    intrfc.clearUserAnswer();
                }
            }
            // FIGHTP1
            else if (inputState == InputState.AWAITING_P1_ABILITY_SELECTION) {
                int numAbilities = model.getFighter1().getAbilities().length;
                boolean hasMagic = hasUsableMagicItems(model.getp1());
                int magicOptionNum = numAbilities + 1;
                int idx = -1;
                try { idx = Integer.parseInt(ans) - 1; } catch (Exception ex) { idx = -1; }
                if (hasMagic && (idx == numAbilities)) {
                    // Magic Items selected
                    String prompt = "Select a Magic Item to use:\n" + getMagicItemsPrompt(model.getp1());
                    intrfc.setGameAreaText(prompt);
                    awaitingMagicItemP1 = true;
                    inputState = InputState.AWAITING_P1_ABILITY_SELECTION;
                    intrfc.clearUserAnswer();
                    return;
                }
                if (awaitingMagicItemP1) {
                    // Handle magic 
                    MagicItem selectedItem = model.getp1().removeMagicItemByVisibleIndex(idx);
                    if (selectedItem != null) {
                        p1SelectedAbility = new Ability(selectedItem.getName(), selectedItem.getDescription(), 0, selectedItem.getHp());
                        intrfc.appendGameAreaText("\n" + model.getp1().getName() + " used magic item: " + selectedItem.getName());
                        awaitingMagicItemP1 = false;
                        
                        String abilityPromptP2 = "\n" + model.getp2().getName() + ", choose your ability by number:\n" + model.getFighter2().getAbilitiesAsString();
                        if (hasUsableMagicItems(model.getp2())) {
                            abilityPromptP2 += (model.getFighter2().getAbilities().length + 1) + ". Magic Items\n";
                        }
                        intrfc.appendGameAreaText(abilityPromptP2);
                        inputState = InputState.AWAITING_P2_ABILITY_SELECTION;
                        intrfc.clearUserAnswer();
                        return;
                    } else {
                        intrfc.appendGameAreaText("\nInvalid magic item selection. Try again:\n" + getMagicItemsPrompt(model.getp1()));
                        intrfc.clearUserAnswer();
                        return;
                    }
                }
                if (idx < 0 || idx >= numAbilities) {
                    intrfc.appendGameAreaText("\nInvalid ability! Try again:\n" + model.getFighter1().getAbilitiesAsString() + (hasMagic ? magicOptionNum + ". Magic Items\n" : ""));
                    intrfc.clearUserAnswer();
                    return;
                }
                p1SelectedAbility = model.getFighter1().getAbilities()[idx];
                intrfc.appendGameAreaText("\n" + model.getp1().getName() + " selected: " + p1SelectedAbility.getAbilityName());
                String abilityPromptP2 = "\n" + model.getp2().getName() + ", choose your ability by number:\n" + model.getFighter2().getAbilitiesAsString();
                if (hasUsableMagicItems(model.getp2())) {
                    abilityPromptP2 += (model.getFighter2().getAbilities().length + 1) + ". Magic Items\n";
                }
                intrfc.appendGameAreaText(abilityPromptP2);
                inputState = InputState.AWAITING_P2_ABILITY_SELECTION;
                intrfc.clearUserAnswer();
            }
            // FIGHTP2
            else if (inputState == InputState.AWAITING_P2_ABILITY_SELECTION) {
                int numAbilities = model.getFighter2().getAbilities().length;
                boolean hasMagic = hasUsableMagicItems(model.getp2());
                int magicOptionNum = numAbilities + 1;
                int idx = -1;
                try { idx = Integer.parseInt(ans) - 1; } catch (Exception ex) { idx = -1; }
                if (hasMagic && (idx == numAbilities)) {
                    // Magic Items
                    String prompt = "Select a Magic Item to use:\n" + getMagicItemsPrompt(model.getp2());
                    intrfc.setGameAreaText(prompt);
                    awaitingMagicItemP2 = true;
                    inputState = InputState.AWAITING_P2_ABILITY_SELECTION;
                    intrfc.clearUserAnswer();
                    return;
                }
                if (awaitingMagicItemP2) {
                    // Handle magic 
                    MagicItem selectedItem = model.getp2().removeMagicItemByVisibleIndex(idx);
                    if (selectedItem != null) {
                        p2SelectedAbility = new Ability(selectedItem.getName(), selectedItem.getDescription(), 0, selectedItem.getHp());
                        intrfc.appendGameAreaText("\n" + model.getp2().getName() + " used magic item: " + selectedItem.getName());
                        awaitingMagicItemP2 = false;
                        
                        resolveFightRound();
                        return;
                    } else {
                        intrfc.appendGameAreaText("\nInvalid magic item selection. Try again:\n" + getMagicItemsPrompt(model.getp2()));
                        intrfc.clearUserAnswer();
                        return;
                    }
                }
                if (idx < 0 || idx >= numAbilities) {
                    intrfc.appendGameAreaText("\nInvalid ability! Try again:\n" + model.getFighter2().getAbilitiesAsString() + (hasMagic ? magicOptionNum + ". Magic Items\n" : ""));
                    intrfc.clearUserAnswer();
                    return;
                }
                p2SelectedAbility = model.getFighter2().getAbilities()[idx];
                intrfc.appendGameAreaText("\n" + model.getp2().getName() + " selected: " + p2SelectedAbility.getAbilityName());
                
                resolveFightRound();
            }
                else if (inputState == InputState.BATTLE_START) {

                    model.getFighter1().reset();
                    model.getFighter2().reset();
                    intrfc.setGameAreaText(model.getFighter1().getCharacterName() + " VS " + model.getFighter2().getCharacterName());

                    inputState = InputState.AWAITING_MODE;
                    intrfc.appendGameAreaText("\nBattle complete. Choose a mode (1. view, 2. create, 3. edit, 4. delete, 5. exit):");
                    intrfc.clearUserAnswer();
                }
                else {
                    
                    intrfc.hideManageControls();
                    inputState = InputState.NONE;
                }
                    break;
            default:
                gui.getInterface();
                System.err.println("Nah");
                intrfc.hideManageControls();
                break;
        }
    }
    @Override
    /**
     * Called when text is inserted into the document.
     * @param e the DocumentEvent representing the insert
     */
    public void insertUpdate(DocumentEvent e){

    }

    @Override
    /**
     * Called when text is removed from the document.
     * @param e the DocumentEvent representing the remove
     */
    public void removeUpdate(DocumentEvent e){

    }

    @Override
    /**
     * Called when attributes change in the document.
     * @param e the DocumentEvent representing the change
     */
    public void changedUpdate(DocumentEvent e){

    }

private void resolveFightRound() {
    // +5

    model.getFighter1().rechargeHpMax(model.getFighter1().getRechargeHPMax());
    model.getFighter2().rechargeHpMax(model.getFighter2().getRechargeHPMax());
    
    
    
    StringBuilder roundResult = new StringBuilder();
    roundResult.append("\n--- EXECUTING MOVES ---\n");
    roundResult.append(model.getFighter1().getCharacterName() + " | HP: " + model.getFighter1().getHp() + " | EP: " + model.getFighter1().getEp() + "\n");
    roundResult.append(model.getFighter2().getCharacterName() + " | HP: " + model.getFighter2().getHp() + " | EP: " + model.getFighter2().getEp() + "\n");
    intrfc.setGameAreaText(roundResult.toString());

    Ability a1 = p1SelectedAbility;
    Ability a2 = p2SelectedAbility;
    boolean c1Defending = a1.getAbilityName().equals("Defend");
    boolean c2Defending = a2.getAbilityName().equals("Defend");
    boolean c1FullDefend = a1.getAbilityName().equals("Ironclad Defense");
    boolean c2FullDefend = a2.getAbilityName().equals("Ironclad Defense");

    //common
    boolean c1PotionHealing = a1.getAbilityName().equals("Potion of Minor Healing");
    boolean c2PotionHealing = a2.getAbilityName().equals("Potion of Minor Healing");
    boolean c1ScrollEnergy = a1.getAbilityName().equals("Scroll Of Minor Energy");
    boolean c2ScrollEnergy = a2.getAbilityName().equals("Scroll Of Minor Energy");
    boolean c1DefAe = a1.getAbilityName().equals("Defender's Aegis");
    boolean c2DefAe = a2.getAbilityName().equals("Defender's Aegis");

    //Uncommon
    boolean c1AmuVit = a1.getAbilityName().equals("Amulet Of Vitality");
    boolean c2AmuVit = a2.getAbilityName().equals("Amulet Of Vitality");

    boolean c1RingFocus = a1.getAbilityName().equals("Ring Of Focus");
    boolean c2RingFocus = a2.getAbilityName().equals("Ring Of Focus");

    //rare
    boolean c1OrbRes = a1.getAbilityName().equals("Orb Of Resilience");
    boolean c2OrbRes = a2.getAbilityName().equals("Orb Of Resilience");

    boolean c1AnPow = a1.getAbilityName().equals("Ancient Tome Of Power");
    boolean c2AnPow = a2.getAbilityName().equals("Ancient Tome Of Power");


    // Defender's Aegis flags
    boolean skipC1Damage = c1DefAe;
    boolean skipC2Damage = c2DefAe;

    if(c1AmuVit || c1AmuVitTrue){
        c1AmuVitTrue = true;
    }
    if(c2AmuVit || c2AmuVitTrue){
        c2AmuVitTrue = true;
    }

    if(c1RingFocus || c1RingFocusTrue){
        c1RingFocusTrue = true;
    }
    if(c2RingFocus || c2RingFocusTrue){
        c2RingFocusTrue = true;
    }

    if(c1OrbRes || c1OrbResTrue){
        c1OrbResTrue = true;
    }
    if(c2OrbRes || c2OrbResTrue){
        c2OrbResTrue = true;
    }

    if(c1AnPow || c1AnPowTrue){
        c1AnPowTrue = true;
    }
    if(c2AnPow || c2AnPowTrue){
        c2AnPowTrue = true;
    }

    // c1 move
    if (c1Defending) {
        if (model.getFighter1().getEp() >= a1.getEpCost()) {
            model.getFighter1().consumeEp(a1);
        } else {
            roundResult.append(model.getFighter1().getCharacterName() + " didn't have enough EP to use " + a1.getAbilityName() + "!\n");
        }
    } else if (c1DefAe) {
        roundResult.append(model.getFighter1().getCharacterName() + " used Defender's Aegis! Negates all incoming damage this round.\n");
    } else if (a1.getAbilityName().equals("Ring of Focus")) {
        model.getFighter1().recharge(2);
        roundResult.append(model.getFighter1().getCharacterName() + " used Ring of Focus! +2 EP.\n");
    } else if (a1.getAbilityName().equals("Orb of Resilience")) {
        model.getFighter1().restoreHP(5);
        roundResult.append(model.getFighter1().getCharacterName() + " used Orb of Resilience! +5 HP.\n");
    } else if (a1.getAbilityName().equals("Ancient Tome of Power")) {
        model.getFighter1().recharge(5);
        roundResult.append(model.getFighter1().getCharacterName() + " used Ancient Tome of Power! +5 EP.\n");
    } else if (a1.getAbilityName().equals("Amulet of Vitality")) {
        model.getFighter1().addMaxHP(20);
        roundResult.append(model.getFighter1().getCharacterName() + " used Amulet of Vitality! Max HP increased by 20.\n");
    } else {
        if (model.getFighter1().getEp() >= a1.getEpCost()) {
            model.getFighter1().consumeEp(a1);
            if (a1.getAbilityName().equals("Lesser Heal")) {
                model.getFighter1().takeDamage(a1);
            } else if (a1.getAbilityName().equals("Smoke Bomb")) {
                if (new java.util.Random().nextBoolean()) {
                    if (!(c2FullDefend || c2Defending || c2DefAe)) {
                        model.getFighter2().applyIncoming(a1, c2Defending);
                    }
                } else {
                    roundResult.append("Dodged!\n");
                }
            } else if (c1PotionHealing) {
                model.getFighter1().restoreHP(40);
                roundResult.append(model.getFighter1().getCharacterName() + " used Potion of Minor Healing! +40 HP.\n");
            } else if (c1ScrollEnergy) {
                model.getFighter1().recharge(20);
                roundResult.append(model.getFighter1().getCharacterName() + " used Scroll Of Minor Energy! +20 EP.\n");
            } else {
                if (!(c2FullDefend || c2DefAe)) {
                    model.getFighter2().applyIncoming(a1, c2Defending);
                }
            }
        } else {
            roundResult.append(model.getFighter1().getCharacterName() + " didn't have enough EP to use " + a1.getAbilityName() + "!\n");
        }
    }
    // c2 move
    if (c2Defending) {
        if (model.getFighter2().getEp() >= a2.getEpCost()) {
            model.getFighter2().consumeEp(a2);
        } else {
            roundResult.append(model.getFighter2().getCharacterName() + " didn't have enough EP to use " + a2.getAbilityName() + "!\n");
        }
    } else if (c2DefAe) {
        roundResult.append(model.getFighter2().getCharacterName() + " used Defender's Aegis! Negates all incoming damage this round.\n");
    } else if (a2.getAbilityName().equals("Ring of Focus")) {
        model.getFighter2().recharge(2);
        roundResult.append(model.getFighter2().getCharacterName() + " used Ring of Focus! +2 EP.\n");
    } else if (a2.getAbilityName().equals("Orb of Resilience")) {
        model.getFighter2().restoreHP(5);
        roundResult.append(model.getFighter2().getCharacterName() + " used Orb of Resilience! +5 HP.\n");
    } else if (a2.getAbilityName().equals("Ancient Tome of Power")) {
        model.getFighter2().recharge(5);
        roundResult.append(model.getFighter2().getCharacterName() + " used Ancient Tome of Power! +5 EP.\n");
    } else if (a2.getAbilityName().equals("Amulet of Vitality")) {
        model.getFighter2().addMaxHP(20);
        roundResult.append(model.getFighter2().getCharacterName() + " used Amulet of Vitality! Max HP increased by 20.\n");
    } else {
        if (model.getFighter2().getEp() >= a2.getEpCost()) {
            model.getFighter2().consumeEp(a2);
            if (a2.getAbilityName().equals("Lesser Heal")) {
                model.getFighter2().takeDamage(a2);
            } else if (a2.getAbilityName().equals("Smoke Bomb")) {
                if (new java.util.Random().nextBoolean()) {
                    if (!(c1FullDefend || c1Defending || c1DefAe)) {
                        model.getFighter1().applyIncoming(a2, c1Defending);
                    }
                } else {
                    roundResult.append("Dodged!\n");
                }
            } else if (c2PotionHealing) {
                model.getFighter2().restoreHP(40);
                roundResult.append(model.getFighter2().getCharacterName() + " used Potion of Minor Healing! +40 HP.\n");
            } else if (c2ScrollEnergy) {
                model.getFighter2().recharge(20);
                roundResult.append(model.getFighter2().getCharacterName() + " used Scroll Of Minor Energy! +20 EP.\n");
            } else {
                if (!(c1FullDefend || c1DefAe)) {
                    model.getFighter1().applyIncoming(a2, c1Defending);
                }
            }
        } else {
            roundResult.append(model.getFighter2().getCharacterName() + " didn't have enough EP to use " + a2.getAbilityName() + "!\n");
        }
    }

    roundResult.append("\n--- ROUND RESULT ---\n");
    roundResult.append(model.getFighter1().getCharacterName() + " | HP: " + model.getFighter1().getHp() + " | EP: " + model.getFighter1().getEp() + "\n");
    roundResult.append(model.getFighter2().getCharacterName() + " | HP: " + model.getFighter2().getHp() + " | EP: " + model.getFighter2().getEp() + "\n");

    // Check for end of battle
    if (!model.getFighter1().getAlive() || !model.getFighter2().getAlive()) {
        roundResult.append("\n--- BATTLE END ---\n");
        // Remove Amulet of Vitality bonus at end of battle
        if (amuletApplied.contains(model.getFighter1())) {
            model.getFighter1().addMaxHP(-20);
            amuletApplied.remove(model.getFighter1());
        }
        if (amuletApplied.contains(model.getFighter2())) {
            model.getFighter2().addMaxHP(-20);
            amuletApplied.remove(model.getFighter2());
        }
        if (model.getFighter1().getAlive() && !model.getFighter2().getAlive()) {
            roundResult.append(model.getp1().getName() + "'s " + model.getFighter1().getCharacterName() + " wins!\n");
            model.getp1().winCountAdd();
            model.getFighter1().addWin();
            MagicItem reward = model.getp1().magicItemReward();
            if (reward != null) {
                model.getp1().addMagicItem(reward);
                roundResult.append("Magic Item Reward: " + reward.getName() + "\n");
            }
        } else if (model.getFighter2().getAlive() && !model.getFighter1().getAlive()) {
            roundResult.append(model.getp2().getName() + "'s " + model.getFighter2().getCharacterName() + " wins!\n");
            model.getp2().winCountAdd();
            model.getFighter2().addWin();
            MagicItem reward = model.getp2().magicItemReward();
            if (reward != null) {
                model.getp2().addMagicItem(reward);
                roundResult.append("Magic Item Reward: " + reward.getName() + "\n");
            }
        } else if (!model.getFighter1().getAlive() && !model.getFighter2().getAlive()) {
            roundResult.append("TIE\n");
        }
        intrfc.appendGameAreaText(roundResult.toString());
        inputState = InputState.SHOW_BATTLE_RESULT;
        intrfc.appendGameAreaText("\nPress Confirm to return to menu.");
        intrfc.clearUserAnswer();
        // Reset fighters for next battle
        model.getFighter1().reset();
        model.getFighter2().reset();
    } else {
        //start offight
        fightRound++;
        model.getFighter1().recharge(model.getFighter1().getRechargeAmount());
        model.getFighter2().recharge(model.getFighter2().getRechargeAmount());

        if (c1AmuVitTrue) {
            model.getFighter1().addMaxHP(20);
        }
        if (c2AmuVitTrue){
            model.getFighter2().addMaxHP(20);
        }
        // Ring of Focus: +2 EP
        if (c1RingFocusTrue) {
            model.getFighter1().recharge(2);
        }
        if (c2RingFocusTrue) {
            model.getFighter2().recharge(2);
        }
        // Orb of Resilience: +5 HP
        if (c1OrbResTrue) {
            model.getFighter1().restoreHP(5);
        }
        if (c2OrbResTrue) {
            model.getFighter2().restoreHP(5);
        }
        // Ancient Tome of Power: +5 EP
        if (c1AnPowTrue) {
            model.getFighter1().recharge(5);
        }
        if (c2AnPowTrue) {
            model.getFighter2().recharge(5);
        }
        intrfc.appendGameAreaText(roundResult.toString());
        intrfc.appendGameAreaText("\n--- ROUND " + fightRound + " ---");
        intrfc.appendGameAreaText("\n" + model.getp1().getName() + " - " + model.getFighter1().getCharacterName() + " | HP: " + model.getFighter1().getHp() + " | EP: " + model.getFighter1().getEp());
        intrfc.appendGameAreaText("\n" + model.getp2().getName() + " - " + model.getFighter2().getCharacterName() + " | HP: " + model.getFighter2().getHp() + " | EP: " + model.getFighter2().getEp());
        String abilityPromptP1 = "\n\n" + model.getp1().getName() + ", choose your ability by number:\n" + model.getFighter1().getAbilitiesAsString();
        if (hasUsableMagicItems(model.getp1())) {
            abilityPromptP1 += (model.getFighter1().getAbilities().length + 1) + ". Magic Items\n";
        }
        intrfc.appendGameAreaText(abilityPromptP1);
        inputState = InputState.AWAITING_P1_ABILITY_SELECTION;
        intrfc.clearUserAnswer();
    }
}

}

