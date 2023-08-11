import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ValidMove {
        private static String validateMove(String input, Scanner scanner) {
            String line = "";
            try {
                ProcessBuilder pb = new ProcessBuilder("/usr/local/bin/node", "/Users/areej/PokeBattleSimulator/pokemon_move_identifier.js", input);
                Process p = pb.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                while ((line = reader.readLine()) != null) {
                    break;
                }

                int exitCode = p.waitFor();
                if (exitCode != 0) {
                    System.out.println(errorReader.readLine());
                    System.out.print("Error occurred while calling the script-please try again in a moment.");
                    System.out.println(exitCode);
                    return ("error");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return line;
            
        
    }
    public static String isValid(String input, Scanner scanner){
        String valid = validateMove(input, scanner);
        while (valid.contains("invalid")){
            valid = validateMove(input, scanner); // keep trying until it works
        }
        return valid;
    }
}
