
import java.util.Scanner;
public class tester {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Ability torrent = new Torrent(); 
		Ability blaze = new Blaze();
		Ability rivalry = new Rivalry();
		Ability intimidate = new Intimidate();
		//constructor for pokemon is (name, type, level, nickname(applicable only for partnerpokemon class instances), hp, attack, ability, gender(true: female, false: male), scanner)
		PartnerPokemon m = new PartnerPokemon("Empoleon", "Water/Steel", 67, "Emperor", 214, 137, 150, 173, 144, 132, torrent, false, scanner);
		Trainer h = new Trainer("Hikari", 8, m, "Mizuko", "064120", 229230); //constructor for trainer is (name, number of badges, partnerpokemon, nickname, id, money)
		Pokemon b = new Pokemon("Mamoswine", "Ice/Ground", 59, "Twin Tusk", 217, 190, 131, 100, 98, 128, null, true, scanner);
		Pokemon c = new Pokemon("Garchomp", "Dragon/Ground", 54, "Mach", 199, 179, 138, 101, 119, 129, null, false, scanner);
		Pokemon k = new Pokemon("Lucario", "Fighting/Steel", 58, "Aura", 344, 202, 130, 211, 130, 141, null, false,scanner);
		Pokemon f = new Pokemon("Staraptor", "Normal/Flying", 64, "Predator", 204, 191, 126, 83, 85, 159, intimidate, true, scanner);
		Pokemon s = new Pokemon("Luxray", "Electric", 67, "Gleam Eyes", 198, 195, 120, 179, 124, 137, rivalry, true, scanner);
		h.setParty(b, c, s, k, f);
		h.setBag("Potion", "Potion", "Potion", "Super Potion", "Max Potion", "Max Potion", "Max Potion", "Revive"); //set bag
		h.swapPositions(m, b);
		PartnerPokemon n = new PartnerPokemon("Infernape", "Fire/Fighting", 61, "Flame", 186, 160, 109, 130, 111, 175, blaze, false, scanner);
		Trainer a = new Trainer("Arkn", 8, n, "Nape", "064121", 100000);
		Pokemon ga = new Pokemon("Garchomp", "Dragon/Ground", 55, "Mach", 196, 162, 128, 111, 106, 152, null, true, scanner);
		Pokemon luc = new Pokemon("Lucario", "Fighting/Steel", 50, "Aura", 142, 133, 92, 127, 93, 108, null, false, scanner);
		Pokemon st = new Pokemon("Staraptor", "Normal/Flying", 56, "Predator", 169, 154, 109, 81, 87, 147, intimidate, false, scanner);
		Pokemon lux = new Pokemon("Luxray", "Electric", 56, "Gleam Eyes", 167, 152, 113, 133, 110, 160, intimidate, false, scanner);
		Pokemon ma = new Pokemon("Piloswine", "Ice/Ground", 55, "Twin Tusk", 187, 141, 103, 95, 84, 104, null, false, scanner);
		a.setParty(st, lux, ga, ma, luc);
		a.swapPositions(n, luc); 
		a.setBag("Potion", "Potion", "Potion", "Super Potion", "Max Potion", "Max Potion", "Max Potion", "Revive"); //set bag
		/*for (int i = 0; i <h.party().length; i++) {
			h.party()[i].setMoves(scanner); //set moves
		}
		for (int i = 0; i <a.party().length; i++) {
			a.party()[i].setMoves(scanner); //set moves
		}*/
		h.party()[0].setMoves(scanner);
		a.party()[0].setMoves(scanner);
		Battle bte = new Battle(h, a); //constructor for battle is (trainer1, trainer2)
		System.out.println(bte.battle(scanner)); //battle
		scanner.close();
	}
}
