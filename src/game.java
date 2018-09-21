import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.text.*;

public class game{
    static int taserDamage = 5;
    static int macheteDamage = 9;
    static int sprayDamage = 7;
    static int chainsawDamage = 12;

    //list of weapons for each boss
    static String[] weaponList = {"Whip", "Sword", "Crowbar", "Gun", "Acid", "Magic Staff", "Atomic Bomb"};

    //list of damage for each boss
    static int[] damageList = {7, 10, 12, 16, 19, 24, 33};

    //boundaries for health, will be randomly generated for each boss
    static int lowerBound = 51;
    static int upperBound = 59;

    //will you have to leave after defeat
    static boolean leaveGame = false;

    //keeps track of which boss you're at
    static int bossCounter = 0;

    //keeps track of how much to increment new health by
    static int maxHealth = 50;

    //# of potions
    static int potionCount = 4;

    //usage for each weapon
    static int useTaser = 0;
    static int useMachete = 0;
    static int useSpray = 0;
    static int useChainsaw = 0;

    static int weaponIndex = 0;
    static int currentEnemyDamage = damageList[weaponIndex];

    static int currentDamage = 0;

    public static void beginGame(){
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

    public static void fighting(){
        
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
        Scanner whichWeapon = new Scanner(System.in);

        while (true){
        //chance of critical hit
         int critChancePlayer = ThreadLocalRandom.current().nextInt(0, 100);
         int critChanceEnemy = ThreadLocalRandom.current().nextInt(0, 100);

         //chance of actually hitting
         int hitChancePlayer = ThreadLocalRandom.current().nextInt(0, 100);
         int hitChanceEnemy = ThreadLocalRandom.current().nextInt(0, 100);

         System.out.println("Player health: " + player.playerHealth);
         System.out.println("Boss " + (bossCounter + 1) + " health: " + currentBoss.bossHealth);

        //win
         if (currentBoss.bossHealth <= 0){
            Scanner takePotion = new Scanner(System.in);
            System.out.println("Congrats, would you like to take a potion? [yes/no]");
            String potionChoice = takePotion.nextLine();

            if (potionChoice.equals("yes")){

                if (potionCount == 0){

                    System.out.println("Sorry, you don't have any left.");
                    maxHealth += 10;
                    bossCounter++;
                    weaponIndex++;
                    weaponMap.put("Taser", taserDamage + 7);
                    weaponMap.put("Machete", macheteDamage + 7);
                    weaponMap.put("Pepper Spray", sprayDamage + 7);
                    weaponMap.put("Chainsaw", chainsawDamage + 7);

                    System.out.println("Current health is at " + player.playerHealth);

        
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
                    weaponMap.put("Taser", taserDamage + 7);
                    weaponMap.put("Machete", macheteDamage + 7);
                    weaponMap.put("Pepper Spray", sprayDamage + 7);
                    weaponMap.put("Chainsaw", chainsawDamage + 7);
                }
            }
            else{
                maxHealth += 10;
                bossCounter++;
                weaponIndex++;

                weaponMap.put("Taser", taserDamage + 7);
                weaponMap.put("Machete", macheteDamage + 7);
                weaponMap.put("Pepper Spray", sprayDamage + 7);
                weaponMap.put("Chainsaw", chainsawDamage + 7);

            }

            takePotion.close();
         }
         //loss
         else if (PlayerClass.playerHealth <= 0){
             System.out.println("Sorry, you lost! Come back later.");

             leaveGame = true;
         }
         //continue fighing
         else{
            System.out.println("Which weapon would you like to pick?");
            String weaponChoice = whichWeapon.nextLine();
            
            if (weaponMap.containsKey(weaponChoice)){
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

            whichWeapon.close();
         }
         if (hitChanceEnemy > 30){
            if (critChanceEnemy > 55){
                currentBoss.bossHealth -= 2*currentEnemyDamage;
                System.out.println("Critical hit! You have taken " + Integer.toString(2*currentEnemyDamage));
                System.out.println();
    
            }
            else{
                currentBoss.bossHealth -= currentEnemyDamage;
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

    public static void main(String[] args){
        System.out.println("This is a game where you fight 7 bosses one after another, using nothing but the weapons that you have on you.");
        System.out.println("Progress will not be saved, you must fight them all in one shot.");
        System.out.println("Don't worry, your health and damage will increase after each fight.");

        Scanner readChoice = new Scanner(System.in);
        System.out.println("Are you up for the challenge? Enter no to leave, anything else to play");
        String playerChoice = readChoice.next();

        if (playerChoice.equals("no")){
            System.out.println("Oh, well, come back when you feel you are ready.");
            System.exit(0);
        }
        else{
            beginGame();
        }

        readChoice.close();
    }
}