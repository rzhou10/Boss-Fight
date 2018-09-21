import java.util.*;
import java.io.*;
import java.text.*;

public class PlayerClass{
    public static Map<String, Integer> equipment;
    public static int playerHealth;

    public PlayerClass(Map<String, Integer> equipment, int playerHealth){
        this.equipment = equipment;
        this.playerHealth = playerHealth;
    }
}