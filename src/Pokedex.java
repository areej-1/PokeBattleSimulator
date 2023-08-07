import java.util.ArrayList;
import java.util.Scanner;

public class Pokedex {
	private static ArrayList<Pokemon> allPokemon;
	private static Pokedex pokedex;
	
	private Pokedex() {
		allPokemon = new ArrayList<Pokemon>();
	}
	public String toString() {
		String res = "";
		for (Pokemon p : allPokemon) {
			res = res + p + "\n\n";
		}
		return res;
	}
	public void add(Pokemon ... all) {
		for (Pokemon p : all) {
			allPokemon.add(p);
		}
	}
	public void add(Pokemon p) {
		allPokemon.add(p);
	}
	public static Pokedex getPokedex(){
		if (pokedex == null) {
			pokedex = new Pokedex();
		}
		return pokedex;
	}
	public String PokedexEntries() {
		return pokedex.toString();
	}
	
}
