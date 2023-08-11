import java.util.Scanner;
public class tester {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Ability torrent = new Torrent(); 
		Ability blaze = new Blaze();
		//constructor for pokemon is (name, type, level, nickname(applicable only for partnerpokemon class instances), hp, attack, ability, gender(true: female, false: male), scanner)
		PartnerPokemon m = new PartnerPokemon("Empoleon", "Water/Steel", 67, "Emperor", 372, 159, 162, 204, 186, 132, torrent, false, scanner);
		Trainer h = new Trainer("Hikari", 8, m, "Mizuko", "064120", 229230); //constructor for trainer is (name, number of badges, partnerpokemon, nickname, id, money)
		Pokemon b = new Pokemon("Mamoswine", "Ice/Ground", 58, "Twin Tusk", 424, 238, 148, 130, 112, 128, null, true, scanner);
		Pokemon c = new Pokemon("Garchomp", "Dragon/Ground", 50, "Mach", 420, 238, 175, 148, 157, 129, null, false, scanner);
		Pokemon k = new Pokemon("Lucario", "Fighting/Steel", 57, "Aura", 344, 202, 130, 211, 130, 141, null, false,scanner);
		Pokemon f = new Pokemon("Staraptor", "Normal/Flying", 63, "Predator", 374, 220, 130, 94, 112, 159, null, true, scanner);
		Pokemon s = new Pokemon("Luxray", "Electric", 66, "Gleam Eyes", 364, 220, 146, 175, 146, 137, null, true, scanner);
		h.setParty(b, c, s, k, f);
		h.swapPositions(m, c); 
		h.setBag("Potion", "Potion", "Potion", "Super Potion", "Max Potion", "Max Potion", "Max Potion", "Revive"); //set bag
		
		PartnerPokemon n = new PartnerPokemon("Infernape", "Fire/Fighting", 61, "Flame", 186, 160, 109, 130, 111, 175, blaze, false, scanner);
		Trainer a = new Trainer("Arkn", 8, n, "Nape", "064121", 100000);
		Pokemon ga = new Pokemon("Garchomp", "Dragon/Ground", 54, "Mach", 192, 158, 125, 109, 104, 149, null, true, scanner);
		Pokemon luc = new Pokemon("Lucario", "Fighting/Steel", 49, "Aura", 139, 129, 90, 125, 91, 105, null, false, scanner);
		Pokemon st = new Pokemon("Staraptor", "Normal/Flying", 55, "Predator", 166, 151, 107, 80, 86, 145, null, false, scanner);
		Pokemon lux = new Pokemon("Luxray", "Electric", 56, "Gleam Eyes", 167, 152, 113, 133, 110, 110, null, false, scanner);
		Pokemon ma = new Pokemon("Piloswine", "Ice/Ground", 54, "Twin Tusk", 183, 138, 101, 93, 83, 102, null, false, scanner);
		a.setParty(st, lux, ga, ma, luc);
		a.setBag("Potion", "Potion", "Potion", "Super Potion", "Max Potion", "Max Potion", "Max Potion", "Revive"); //set bag
		for (int i = 0; i <h.party().length; i++) {
			h.party()[i].setMoves(scanner); //set moves
		}
		for (int i = 0; i <a.party().length; i++) {
			a.party()[i].setMoves(scanner); //set moves
		}
		Battle bte = new Battle(h, a); //constructor for battle is (trainer1, trainer2)
		System.out.println(bte.battle(scanner)); //battle
		scanner.close();
		System.out.println(h.party()[0].healthVal());
	}
}
