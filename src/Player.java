import java.util.*;

public class Player{
    public Map<String, Integer> equipment;
    public int playerHealth;

    public Player(Map<String, Integer> equipment, int playerHealth){
        this.equipment = equipment;
        this.playerHealth = playerHealth;
    }
}