import java.util.Scanner;
public class tester {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Ability torrent = new Torrent();
		Ability blaze = new Blaze();
		PartnerPokemon m = new PartnerPokemon("Empoleon", "Water/Steel", 67, "Emperor", 372, 132, torrent, false, scanner);
		Trainer h = new Trainer("Hikari", 8, m, "Mizuko", "064120", 229230);
		Pokemon b = new Pokemon("Mamoswine", "Ice/Ground", 58, "Twin Tusk", 424, 128, null, true, scanner);
		Pokemon c = new Pokemon("Garchomp", "Dragon/Ground", 50, "Mach", 420, 129, null, false, scanner);
		Pokemon k = new Pokemon("Lucario", "Fighting/Steel", 57, "Aura", 344, 141, null, false,scanner);
		Pokemon f = new Pokemon("Staraptor", "Normal/Flying", 63, "Predator", 374, 159, null, true, scanner);
		Pokemon s = new Pokemon("Luxray", "Electric", 66, "Gleam Eyes", 364, 137, null, true, scanner);
		h.setParty(b, c, s, k, f);
		h.setBag("Potion", "Potion", "Potion", "Super Potion", "Max Potion", "Max Potion", "Max Potion", "Revive");
		
		PartnerPokemon n = new PartnerPokemon("Infernape", "Fire/Fighting", 63, "Flame", 372, 120, blaze, false, scanner);
		Trainer a = new Trainer("Arkn", 8, n, "Nape", "064121", 100000);
		Pokemon ga = new Pokemon("Garchomp", "Dragon/Ground", 54, "Mach", 420, 95, null, true, scanner);
		Pokemon luc = new Pokemon("Lucario", "Fighting/Steel", 43, "Aura", 344, 65, null, false, scanner);
		Pokemon st = new Pokemon("Staraptor", "Normal/Flying", 60, "Predator", 374, 110, null, false, scanner);
		Pokemon lux = new Pokemon("Luxray", "Electric", 61, "Gleam Eyes", 364, 95, null, false, scanner);
		Pokemon ma = new Pokemon("Mamoswine", "Ice/Ground", 52, "Twin Tusk", 424, 75, null, false, scanner);
		a.setParty(st, lux, ga, ma, luc);
		a.setBag("Potion", "Potion", "Potion", "Super Potion", "Max Potion", "Max Potion", "Max Potion", "Revive");
		for (int i = 0; i <h.party().length; i++) {
			h.party()[i].setMoves(scanner);
		}
		for (int i = 0; i <a.party().length; i++) {
			a.party()[i].setMoves(scanner);
		}
		Battle bte = new Battle(h, a);
		System.out.println(bte.battle(scanner));
		scanner.close();
	}
}
