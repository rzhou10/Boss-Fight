import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game{
    private int taserDamage = 5;
    private int macheteDamage = 9;
    private int sprayDamage = 7;
    private int chainsawDamage = 12;

    //list of weapons for each boss
    private String[] weaponList = {"Whip", "Sword", "Crowbar", "Gun", "Acid", "Magic Staff", "Atomic Bomb"};

    //list of damage for each boss
    private int[] damageList = {7, 12, 14, 19, 23, 27, 38};

    //boundaries for health, will be randomly generated for each boss
    private int lowerBound = 51;
    private int upperBound = 59;

    //leave game after defeat
    private boolean leaveGame = false;

    //keeps track of which boss you're at
    private int bossCounter = 0;

    //keeps track of how much to increment new health by
    private int maxHealth = 50;

    //# of potions
    private int potionCount = 4;

    //usage for each weapon
    private int useTaser = 0;
    private int useMachete = 0;
    private int useSpray = 0;
    private int useChainsaw = 0;

    private int weaponIndex = 0;
    private int currentEnemyDamage = damageList[weaponIndex];

    private int currentDamage = 0;

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

            if (!leaveGame){
                fighting();
            }
            else{
                return;
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

        System.out.println("The boss is wielding a(n) " + weaponList[weaponIndex] + " which does " + damageList[weaponIndex] + ". Current health pool is at " + randHealth);
        System.out.println();

        PlayerClass player = new PlayerClass(weaponMap, maxHealth);
        Scanner input = new Scanner(System.in);

        limit: while (true){
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
                input.close();
                break;
            }
            //win
            else if (currentBoss.bossHealth <= 0){
                System.out.println("Congrats, would you like to take a potion? [yes/no]");
                String potionChoice = input.nextLine();

                if (potionChoice.equals("yes")){
                    if (potionCount == 0){
                        System.out.println("Sorry, you don't have any left.");
                        updateStats();

                        break;
                    }
                    else{

                        //take potion if player health is really low, otherwise no
                        maxHealth += 10;
                        if (maxHealth - player.playerHealth < 70){
                            player.playerHealth = maxHealth;
                        }
                        else{
                            player.playerHealth += 70;
                        }

                        potionCount--;

                        System.out.println("You have " + potionCount + " left");
                        updateStats();

                        break;
                    }
                }
                else{
                    updateStats();

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
                            continue limit;
                        }
                        else{
                            useTaser++;
                            currentDamage = weaponMap.get("Taser");
                            System.out.println("Used " + useTaser + " times.");
                        }
                    }
                    else if (weaponChoice.equals("Machete")){
                        if (useMachete == 3){
                            System.out.println("Already used 3 times.");
                            continue limit;
                        }
                        else{
                            useMachete++;
                            currentDamage = weaponMap.get("Machete");
                            System.out.println("Used " + useMachete + " times.");
                        }
                    }
                    else if (weaponChoice.equals("Pepper Spray")){
                        if (useSpray == 3){
                            System.out.println("Already used 3 times.");
                            continue limit;
                        }
                        else{
                            useSpray++;
                            currentDamage = weaponMap.get("Pepper Spray");
                            System.out.println("Used " + useSpray + " times.");
                        }
            
                    }
                    else if (weaponChoice.equals("Chainsaw")){
                        if (useChainsaw == 3){
                            System.out.println("Already used 3 times.");
                            continue limit;
                        }
                        else{
                            useChainsaw++;
                            currentDamage = weaponMap.get("Chainsaw");
                            System.out.println("Used " + useChainsaw + " times.");
                        }
                    }
                    
                    if (hitChancePlayer > 30){
                        if (critChancePlayer > 55){
                            currentBoss.bossHealth -= 2*currentDamage;
                            System.out.println("Critical hit! Enemy has taken " + (2 * currentDamage));
                            System.out.println();
                        }
                        else{
                            currentBoss.bossHealth -= currentDamage;
                            System.out.println("Enemy has taken " + currentDamage);
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
                            System.out.println("Critical hit! You have taken " + (2 * currentEnemyDamage));
                            System.out.println();
                
                        }
                        else{
                            player.playerHealth -= currentEnemyDamage;
                            System.out.println("You have taken " + currentEnemyDamage);
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

    public void updateStats(){
        maxHealth += 10;
        bossCounter++;
        weaponIndex++;
        taserDamage += 7;
        macheteDamage += 7;
        sprayDamage += 7;
        chainsawDamage += 7;

        System.out.println("Current health is at " + maxHealth);
        useTaser = 0;
        useMachete = 0;
        useSpray = 0;
        useChainsaw = 0;
        lowerBound += 7;
        upperBound += 7;
    }
    
}