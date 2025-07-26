package model;
import java.util.Random;
import java.util.Scanner;

import controller.Controller;
import view.MainFrame;


/**
 * The gameplay class contains the main method and controls the overall game loop.
 * It handles player input for managing characters and starting battles.
 */
public class Battle {
    public static void main(String[] args){

        MainFrame gui = new MainFrame();
        boolean menuOptionChecker = true;
        Scanner sc = new Scanner(System.in);
        String option; 

        System.out.println("WELCOME TO FINAL FANTACY!");
        System.out.println("");

        System.out.print("Please Enter Player 1's Username: ");
        String name1 = sc.next();
        System.out.print("Please Enter Player 2's Username: ");
        String name2 = sc.next();

        Player p1 = new Player(name1);
        Player p2 = new Player(name2);

        // Controller controller1 = new Controller(gui, p1, p2);

        System.out.println("Hello player 1 " + name1 + " and player 2 " + name2 );
        
        do{
            System.out.print("Which action do you want to do? (manage, fight!, halloffame, end): ");
            option = sc.next(); 

            switch (option) {
                case "manage":
                boolean playerOption = true;
                boolean modeFlag = true;
                do{
                    int playerSelected=0;
                    Player p = new Player("temp");
                    do {
                        System.out.print("Which player is in control? (1, 2): ");
                        playerSelected = sc.nextInt();
                        switch(playerSelected){
                            case 1 :
                                p = p1;
                                playerOption = false;
                                break;
                            case 2:
                                p = p2;
                                playerOption = false;
                                break;
                            default:
                            System.out.println("Please Input Again!");
                            break;
                    }
                    } while (playerSelected != 1 && playerSelected != 2);
                    System.out.println("\nPlayer " + p.getName() + " is in control! \n");


                    do {
                        System.out.print("Choose a mode (view, create, edit, delete, exit): ");
                        String mode = sc.next();

                        switch(mode){
                            case "view":
                                if(p.getCharacterCount() == 0){
                                    System.out.println("No characters created yet.");
                                }
                                else{
                                    boolean viewModeChecker = true;
                                    do{
                                        System.out.print("Choose a mode (1. simple 2. detailed): ");
                                        int viewMode = sc.nextInt();

                                        switch (viewMode) {
                                            case 1:
                                                viewModeChecker = false;
                                                p.viewCharacters(viewMode); 
                                                break;
                                            case 2:
                                                viewModeChecker = false;
                                                p.viewCharacters(viewMode); 
                                                break;
                                        
                                            default:
                                                System.out.println("Please type the appropriate responses, try again");
                                                break;
                                        }
                                    }while(viewModeChecker);                                           
                                }

                                break;

                            case "create":
                            if (p.getCharacterCount() < 6){
                                System.out.print("Name Your Character! : ");
                                String name = sc.next();

                                int typeChoice = 0;
                                int raceChoice = 0;
                                CharacterType type = null;
                                Race race = null;
                                do {
                                    System.out.println("Select its Race (1. Human, 2. Dwarf, 3. Elf, 4. Gnome)");
                                    raceChoice = sc.nextInt();
                                if(raceChoice == 1){
                                    race = Race.Human;
                                }
                                else if (raceChoice == 2){
                                    race = Race.Dwarf;
                                }
                                else if (raceChoice == 3){
                                    race = Race.Elf;
                                }
                                else if (raceChoice == 4){
                                    race = Race.Gnome;
                                }
                                } while (raceChoice > 4 || raceChoice < 1);

                                do {
                                    System.out.println("Select its Type (1. Mage, 2. Rogue, 3. Warrior)");
                                    typeChoice = sc.nextInt();
                                if(typeChoice == 1){
                                    type = CharacterType.Mage;
                                }
                                else if (typeChoice == 2){
                                    type = CharacterType.Rogue;
                                }
                                else if (typeChoice == 3){
                                    type = CharacterType.Warrior;
                                }
                                } while (typeChoice > 3 || typeChoice < 1);

                                if(race == Race.Gnome){
                                    p.createCharacter(name, type, p.gnomeAbilities(sc, type),race);
                                }
                                else{
                                    p.createCharacter(name, type, p.selectThreeAbilities(sc, type),race);
                                }
                                
                            } 
                            else{
                                System.out.println("Maximum of 6 characters reached.");
                            }
                                break;

                            case "edit":
                                // Edit existing character's abilities
                                if(p.getCharacterCount() == 0){
                                    System.out.println("You don't have Heros To edit!");
                                }
                                else{
                                    int editIndex = -1;
                                    do {
                                        p.viewCharacters(2);
                                        System.out.print("Which Hero do you wanna edit? (index): ");
                                        editIndex = sc.nextInt(); 
                                    }while (editIndex > p.getCharacterCount() || editIndex < 0);

                                    Character toEdit = p.getCharacters()[editIndex - 1];
                                    System.out.println("You can only edit the abilities of the character!");
                                    p.editCharacter(editIndex - 1, p.selectThreeAbilities(sc, toEdit.getType())); 
                                                                                  
                                }
                                break;

                            case "delete":
                                // Delete a character
                                if(p.getCharacterCount() == 0){
                                    System.out.println("You don't have Heros To delete!");
                                }
                                else{
                                    p.viewCharacters(2);
                                    System.out.print("Which Hero do you wanna delete? (index): ");
                                    int deleteIndex = sc.nextInt();

                                    p.deleteCharacter(deleteIndex - 1);
                                } 
                                break;

                            case "exit":
                                modeFlag = false;
                                System.out.println("Going back to main menu!");
                                break;

                            default:
                                System.out.println("Please type the appropriate responses, try again");
                                break;


                        }

                    }while(modeFlag);
                    

                }while(playerOption);



                break;

                case "fight!":
                // Initiate fight sequence
                 
                if (p1.getCharacterCount() == 0 || p2.getCharacterCount()==0){
                    System.out.println("Both players must have at least 1 character to fight!");
                }
                else{
                    int p1Index = -1;
                    int p2Index = -1;
                    do {
                        System.out.println("\n" + p1.getName() + " choose your character!");
                        p1.viewCharacters(2);
                        System.out.println("Enter Index:");
                        p1Index = sc.nextInt() -1;
                    } while (p1Index > p1.getCharacterCount()-1 || p1Index < 0);
                    Character c1 = p1.getCharacters()[p1Index];

                    do {
                        System.out.println("\n" + p2.getName() + " choose your character!");
                        p2.viewCharacters(2);
                        System.out.println("Enter Index:");
                        p2Index = sc.nextInt() -1;    
                    } while (p2Index > p2.getCharacterCount()-1 || p2Index < 0);
                    

                    Character c2 = p2.getCharacters()[p2Index];

                    c1.reset();
                    c2.reset();

                    System.out.println("Lets Fight!");
                    System.out.println(c1.getCharacterName() + " VS " + c2.getCharacterName());

                    boolean battleOnGoing = true;
                    //magicitems passive
                    boolean c1AmuVitTrue = false;
                    boolean c2AmuVitTrue = false;

                    boolean c1RingFocusTrue = false;
                    boolean c2RingFocusTrue = false;

                    boolean c1OrbResTrue = false;
                    boolean c2OrbResTrue = false;

                    boolean c1AnPowTrue = false;
                    boolean c2AnPowTrue = false;

                    int c1maxCount = 0;
                    int c2maxCount = 0;
  

                    int round = 1;

                    
                    while (battleOnGoing) {
                        System.out.println("\n--- ROUND " + round + " ---");

                        c1.recharge(c1.getRechargeAmount());
                        c2.recharge(c2.getRechargeAmount()); //edited
                        c1.rechargeHpMax(c1.getRechargeHPMax());
                        c2.rechargeHpMax(c2.getRechargeHPMax());

                        if(c1AmuVitTrue){
                            c1.addMaxHP(20);
                            c1maxCount += 1;
                        }
                        if(c2AmuVitTrue){
                            c2.addMaxHP(20);
                            c2maxCount += 1;
                        }

                        if(c1RingFocusTrue){
                            c1.recharge(2);
                        }
                         if(c2RingFocusTrue){
                            c2.recharge(2);
                        }

                        if(c1OrbResTrue){
                            c1.restoreHP(5);
                        }
                        if(c2OrbResTrue){
                            c2.restoreHP(5);
                        }

                        if(c1AnPowTrue){
                            c1.recharge(5);                            
                        }
                         if(c2AnPowTrue){
                            c2.recharge(5);                            
                        }

                        System.out.println(p1.getName() + " - " + c1.getCharacterName() + " | HP: " + c1.getHp() + " | EP: " + c1.getEp());
                        System.out.println(p2.getName() + " - " + c2.getCharacterName() + " | HP: " + c2.getHp() + " | EP: " + c2.getEp());

                        Ability a1 = p1.chooseAbility(sc,c1);
                        Ability a2 = p2.chooseAbility(sc,c2);
                        boolean c1Defending = a1.getAbilityName().equals("Defend");
                        boolean c2Defending = a2.getAbilityName().equals("Defend");
                        boolean c1FullDefend = a1.getAbilityName().equals("Ironclad Defense");
                        boolean c2FullDefend = a2.getAbilityName().equals("Ironclad Defense");
                        // lulu uncommon this
                        boolean c1PotionHealing = a1.getAbilityName().equals("Potion of Minor Healing");
                        boolean c2PotionHealing = a2.getAbilityName().equals("Potion of Minor Healing");

                        boolean c1ScrollEnergy = a1.getAbilityName().equals("Scroll Of Minor Energy");
                        boolean c2ScrollEnergy = a2.getAbilityName().equals("Scroll Of Minor Energy");

                        boolean c1DefAe = a1.getAbilityName().equals("Defender's Aegis");
                        boolean c2DefAe = a2.getAbilityName().equals("Defender's Aegis");
                        //uncomon
                        boolean c1AmuVit = a1.getAbilityName().equals("Amulet Of Vitality");
                        boolean c2AmuVit = a2.getAbilityName().equals("Amulet Of Vitality");

                        boolean c1RingFocus = a1.getAbilityName().equals("Ring Of Focus");
                        boolean c2RingFocus = a2.getAbilityName().equals("Ring Of Focus");

                        //rare
                        boolean c1OrbRes = a1.getAbilityName().equals("Orb Of Resilience");
                        boolean c2OrbRes = a2.getAbilityName().equals("Orb Of Resilience");

                        boolean c1AnPow = a1.getAbilityName().equals("Ancient Tome Of Power");
                        boolean c2AnPow = a2.getAbilityName().equals("Ancient Tome Of Power");

                        System.out.println("\n--- EXECUTING MOVES ---");

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
                            if (c1.getEp() >= a1.getEpCost()) {
                                c1.consumeEp(a1);
                            } else {
                                System.out.println(c1.getCharacterName() + " didn't have enough EP to use " + a1.getAbilityName() + "!");
                            }
                        } else {
                            if (c1.getEp() >= a1.getEpCost()) {
                                c1.consumeEp(a1);
                                if (a1.getAbilityName().equals("Lesser Heal")) {
                                    c1.takeDamage(a1);
                                } 
                                else if (a1.getAbilityName().equals("Smoke Bomb")) {
                                    Random r = new Random();
                                    boolean b = r.nextBoolean();
                                    if (b == true) {
                                        c2.applyIncoming(a1, c2Defending);
                                    } else {
                                        System.out.println("Dodged!");
                                    }
                                }
                                //Magic Item
                                //--------------------------------------------------------------
                                else if(c1PotionHealing){
                                    c1.takeDamage(a1);
                                }
                                else if(c1ScrollEnergy){
                                    c1.recharge(20);
                                }
                                //--------------------------------------------------------------
                                else {
                                    if (c2FullDefend || c2DefAe) { /* no damage */ }
                                    else{
                                        c2.applyIncoming(a1, c2Defending);
                                    }
                                    
                                }
                            } else {
                                System.out.println(c1.getCharacterName() + " didn't have enough EP to use " + a1.getAbilityName() + "!");
                            }
                        }

                        // c2 move
                        if (c2Defending) {
                            if (c2.getEp() >= a2.getEpCost()) {
                                c2.consumeEp(a2);
                            } else {
                                System.out.println(c2.getCharacterName() + " didn't have enough EP to use " + a2.getAbilityName() + "!");
                            }
                        } else {

                            if (c2.getEp() >= a2.getEpCost()) {
                                c2.consumeEp(a2);
                                if (a2.getAbilityName().equals("Lesser Heal")) {
                                    c2.takeDamage(a2);
                                } 
                                else if (a2.getAbilityName().equals("Smoke Bomb")) {
                                    Random r = new Random();
                                    boolean b = r.nextBoolean();
                                    if (b == true) {
                                        c1.applyIncoming(a2, c1Defending);
                                    } else {
                                        System.out.println("Dodged!");
                                    }
                                }
                                //Magic Item
                                //--------------------------------------------------------------
                                else if(c2PotionHealing){
                                    c2.takeDamage(a2);
                                }
                                else if(c2ScrollEnergy){
                                    c2.recharge(20);
                                }
                                //--------------------------------------------------------------
                                else {
                                    if (c1FullDefend || c1DefAe) { /* no damage */ }
                                    else{
                                        c1.applyIncoming(a2, c1Defending);
                                    }
                                    
                                }
                            } else {
                                System.out.println(c2.getCharacterName() + " didn't have enough EP to use " + a2.getAbilityName() + "!");
                            }
                        }

                        System.out.println("\n--- ROUND RESULT ---");

                        System.out.println(c1.getCharacterName() + " | HP: " + c1.getHp() + " | EP: " + c1.getEp());
                        System.out.println(c2.getCharacterName() + " | HP: " + c2.getHp() + " | EP: " + c2.getEp());

                        if (!c1.getAlive() || !c2.getAlive()){
                            System.out.println("\n--- BATTLE END ---");

                            if(c1AmuVitTrue){
                                c1.addMaxHP(-20 * c1maxCount);
                            }

                            if(c2AmuVitTrue){
                                c2.addMaxHP(-20 * c2maxCount);
                            }

                            if (c1.getAlive() && !c2.getAlive()) {
                                System.out.println(p1.getName() + "'s " + c1.getCharacterName() + " wins!");
                                p1.winCountAdd();
                                System.out.println(p1.getWinCount());
                                p1.addMagicItem(p1.magicItemReward());
                                System.out.println(p1.getAllMagicItems());
                                
                            } 
                            else if (c2.getAlive() && !c1.getAlive()) {
                                System.out.println(p2.getName() + "'s " + c2.getCharacterName() + " wins!");
                                p2.winCountAdd();
                                System.out.println(p2.getWinCount());
                                p2.addMagicItem(p2.magicItemReward());
                                System.out.println(p2.getAllMagicItems());
                            }
                            else if (!c1.getAlive() && !c2.getAlive()){
                                System.out.println("TIE");
                            }
                            battleOnGoing = false;
                        }
                        else{
                            round ++;

                        }

                    }

                }
                
                break;

                case "halloffame":
                    int p1Determinatant = p1.getWinCount();
                    int p2Determinatant = p2.getWinCount(); 

                    if(p1Determinatant > p2Determinatant){
                        System.out.println("Hall Of Fame Standings!");
                        System.out.println("[1] " + p1.getName() + " | Wins: " + p1.getWinCount());
                        System.out.println("[2] " + p2.getName() + " | Wins: " + p2.getWinCount());
                    }
                    else if(p1Determinatant < p2Determinatant){
                        System.out.println("Hall Of Fame Standings!");
                        System.out.println("[1] " + p2.getName() + " | Wins: " + p2.getWinCount());
                        System.out.println("[2] " + p1.getName() + " | Wins: " + p1.getWinCount());
                    }
                    else if(p1Determinatant == p2Determinatant && p1Determinatant != 0 && p2Determinatant != 0){
                        System.out.println("Hall Of Fame Standings!");
                        System.out.println(p1.getName() + " and " + p2.getName() + " are Tied!");
                        
                    }
                    else{
                        System.out.println("Hall Of Fame Standings!");
                        System.out.println("Fight To Determine The Rankings!");
                    }

                break;

                case "end":
                    menuOptionChecker = false;
                break;

                default:
                System.out.println("Please type the appropriate responses, try again");
                    break;
            }
        
        }while(menuOptionChecker);

        sc.close();

}
}