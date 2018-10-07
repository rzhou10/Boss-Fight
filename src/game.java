import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.text.*;

public class Game{
    int taserDamage = 5;
    int macheteDamage = 9;
    int sprayDamage = 7;
    int chainsawDamage = 12;

    //list of weapons for each boss
    String[] weaponList = {"Whip", "Sword", "Crowbar", "Gun", "Acid", "Magic Staff", "Atomic Bomb"};

    //list of damage for each boss
    int[] damageList = {7, 12, 14, 19, 23, 27, 38};

    //boundaries for health, will be randomly generated for each boss
    int lowerBound = 51;
    int upperBound = 59;

    //leave game after defeat
    boolean leaveGame = false;

    //keeps track of which boss you're at
    int bossCounter = 0;

    //keeps track of how much to increment new health by
    int maxHealth = 50;

    //# of potions
    int potionCount = 4;

    //usage for each weapon
    int useTaser = 0;
    int useMachete = 0;
    int useSpray = 0;
    int useChainsaw = 0;

    int weaponIndex = 0;
    int currentEnemyDamage = damageList[weaponIndex];

    int currentDamage = 0;

    public void beginGame(){
        System.out.println("You have some crappy weapons in stock: A taser that does 5 damage, a machete that does 9 damage, ");
        System.out.println("pepper spray that does 7 damage, and a chainsaw that does 12 damage.");
        System.out.println("Don't worry, you have the potential to critically hit the enemy for 2x damage. Be on the lookout.");
        System.out.println("Your max health is at 50 right now.");
        System.out.println("Weapons can only be used thrice in each fight. Choose wisely.");
        System.out.println("You only have four potions to replenish your health. They can only be taken in between the fights");
        System.out.println("In any case, let's begin!");
        System.out.println();

        while (bossCounter < 7){

            if (leaveGame){
                System.out.println("Come back next time!");
                return;
            }
            else{
                fighting();
            }
        }
    }

    public void fighting(){
        //all weapons
        Map<String, Integer> weaponMap = new HashMap<String, Integer>();
        weaponMap.put("Taser", taserDamage);
        weaponMap.put("Machete", macheteDamage);
        weaponMap.put("Pepper Spray", sprayDamage);
        weaponMap.put("Chainsaw", chainsawDamage);

        //boss stats
        int randHealth = ThreadLocalRandom.current().nextInt(lowerBound, upperBound);
        Boss currentBoss = new Boss(weaponList[weaponIndex], randHealth, currentEnemyDamage);

        System.out.println("The boss is wielding a(n) " + weaponList[weaponIndex] + " which does " + Integer.toString(damageList[weaponIndex]) + ". Current health pool is at " + Integer.toString(randHealth));
        System.out.println();

        PlayerClass player = new PlayerClass(weaponMap, maxHealth);
        Scanner input = new Scanner(System.in);

        while (true){
        //chance of critical hit
         int critChancePlayer = ThreadLocalRandom.current().nextInt(0, 100);
         int critChanceEnemy = ThreadLocalRandom.current().nextInt(0, 100);

         //chance of actually hitting
         int hitChancePlayer = ThreadLocalRandom.current().nextInt(0, 100);
         int hitChanceEnemy = ThreadLocalRandom.current().nextInt(0, 100);

         System.out.println("Player health: " + player.playerHealth);
         System.out.println("Boss " + (bossCounter + 1) + " health: " + currentBoss.bossHealth);

         //loss
        if (player.playerHealth <= 0){
            System.out.println("Sorry, you lost! Come back later.");

            leaveGame = true;
            break;
        }
        //win
         else if (currentBoss.bossHealth <= 0){
            System.out.println("Congrats, would you like to take a potion? [yes/no]");
            String potionChoice = input.nextLine();

            if (potionChoice.equals("yes")){
                if (potionCount == 0){
                    System.out.println("Sorry, you don't have any left.");
                    maxHealth += 10;
                    bossCounter++;
                    weaponIndex++;
                    taserDamage += 7;
                    macheteDamage += 7;
                    sprayDamage += 7;
                    chainsawDamage += 7;

                    System.out.println("Current health is at " + player.playerHealth);
                    useTaser = 0;
                    useMachete = 0;
                    useSpray = 0;
                    useChainsaw = 0;
                    lowerBound += 7;
                    upperBound += 7;

                    break;
                }
                else{
                    maxHealth += 10;
                    if (maxHealth - player.playerHealth < 70){
                        player.playerHealth = maxHealth;

                        System.out.println("Current health is at " + player.playerHealth);
                    }
        
                    else{
                        player.playerHealth += 70;

                        System.out.println("Current health is at " + player.playerHealth);
                    }
    
                    potionCount--;
    
                    System.out.println("You have " + potionCount + " left");
                    bossCounter++;
        
                    weaponIndex++;
                    taserDamage += 7;
                    macheteDamage += 7;
                    sprayDamage += 7;
                    chainsawDamage += 7;
                    useTaser = 0;
                    useMachete = 0;
                    useSpray = 0;
                    useChainsaw = 0;
                    lowerBound += 7;
                    upperBound += 7;

                    break;
                }
            }
            else{
                maxHealth += 10;
                bossCounter++;
                weaponIndex++;

                taserDamage += 7;
                macheteDamage += 7;
                sprayDamage += 7;
                chainsawDamage += 7;
                useTaser = 0;
                useMachete = 0;
                useSpray = 0;
                useChainsaw = 0;
                lowerBound += 7;
                upperBound += 7;

                break;
            }
         }
         //continue fighing
         else{
            System.out.println("Which weapon would you like to pick?");
            String weaponChoice = input.nextLine();
            
            if (!weaponMap.containsKey(weaponChoice)){
                System.out.println("No weapon " + weaponChoice + " exists.");
            }
            else{
                if (weaponChoice.equals("Taser")){
                    if (useTaser == 3){
                        System.out.println("Already used 3 times.");
                    }
                    else{
                        useTaser++;
                        currentDamage = weaponMap.get("Taser");
                        System.out.println("Used " + useTaser);
                    }
                }
                else if (weaponChoice.equals("Machete")){
                    if (useMachete == 3){
                        System.out.println("Already used 3 times.");
                    }
                    else{
                        useMachete++;
                        currentDamage = weaponMap.get("Machete");
                        System.out.println("Used " + Integer.toString(useMachete));
                    }
                }
                else if (weaponChoice.equals("Pepper Spray")){
                    if (useSpray == 3){
                        System.out.println("Already used 3 times.");
                    }
                    else{
                        useSpray++;
                        currentDamage = weaponMap.get("Pepper Spray");
                        System.out.println("Used " + Integer.toString(useSpray));
                    }
        
                }
                else if (weaponChoice.equals("Chainsaw")){
                    if (useChainsaw == 3){
                        System.out.println("Already used 3 times.");
                    }
                    else{
                        useChainsaw++;
                        currentDamage = weaponMap.get("Chainsaw");
                        System.out.println("Used " + Integer.toString(useChainsaw));
                    }
                }
                
                if (hitChancePlayer > 30){
                    if (critChancePlayer > 55){
                        currentBoss.bossHealth -= 2*currentDamage;
                        System.out.println("Critical hit! Enemy has taken " + (2*currentDamage));
                        System.out.println();
                    }
                    else{
                        currentBoss.bossHealth -= currentDamage;
                        System.out.println("Enemy has taken " + Integer.toString(currentDamage));
                        System.out.println();
                    }
                }
                else{
                    System.out.println("You missed!");
                    System.out.println();
                }

                if (hitChanceEnemy > 30){
                    if (critChanceEnemy > 55){
                        player.playerHealth -= 2*currentEnemyDamage;
                        System.out.println("Critical hit! You have taken " + Integer.toString(2*currentEnemyDamage));
                        System.out.println();
            
                    }
                    else{
                        player.playerHealth -= currentEnemyDamage;
                        System.out.println("You have taken " + Integer.toString(currentEnemyDamage));
                        System.out.println();
                    }
                 }
                 else{
                    System.out.println("Enemy missed!");
                    System.out.println();
                }
            }
         }
        }
    }
}