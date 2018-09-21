import java.util.*;
import java.io.*;
import java.text.*;

public class Boss{
    public static String weapon;
    public static int bossHealth;
    public static int damage;

    public Boss(String weapon, int bossHealth, int damage){
        this.weapon = weapon;
        this.bossHealth = bossHealth;
        this.damage = damage;
    }
}