import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Matcher {
    private static String matchMoveOrPokemon(String input, Scanner scanner){
        String line = "";
        try {
            ProcessBuilder pb = new ProcessBuilder("/usr/local/bin/node", "/Users/areej/PokeBattleSimulator/pokemon_and_move_matcher.js", input);
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = reader.readLine()) != null){
                break;
            }
            
            int exitCode = p.waitFor();
            if (exitCode != 0){
                System.out.print("Error occurred while calling the script-please try again in a moment.");
                System.out.println(exitCode);
                return ("error");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return line;
    }

    public static String isMatch(String input, Scanner scanner){ 
        String valid = matchMoveOrPokemon(input, scanner);
        while (valid.contains("invalid")){
            valid = matchMoveOrPokemon(input, scanner);
        }
        return valid;
    }
}