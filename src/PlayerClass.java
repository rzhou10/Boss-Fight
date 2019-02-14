import java.util.*;

public class PlayerClass{
    public Map<String, Integer> equipment;
    public int playerHealth;

    public PlayerClass(Map<String, Integer> equipment, int playerHealth){
        this.equipment = equipment;
        this.playerHealth = playerHealth;
    }
}