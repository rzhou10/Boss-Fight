import java.util.*;

class Main{
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
            Game g = new Game();
            g.beginGame();
        }
        
        System.out.println("That must have been exhausting!");
        System.out.println("We hope you had a nice time!");
        readChoice.close();
    }
}