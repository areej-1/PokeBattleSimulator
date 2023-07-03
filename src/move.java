import java.util.ArrayList;
import java.util.Scanner;

public class Move {
	private ArrayList<Pokemon.PokemonType> type = new ArrayList<Pokemon.PokemonType>();
	private String name;
	private int damage;
	public Move(String n, Pokemon.PokemonType ... t) {
		for (Pokemon.PokemonType type : t) {
			this.type.add(type);
		}
		name = n;
		setDamage();
	}
	public ArrayList<Pokemon.PokemonType> getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    Move m = (Move) obj;
	    // Compare fields
	    if (!name.equals(m.name)) {
	        return false;
	    }
	    return true;
	}
	@Override
	public String toString() {
		return name;
	}
	public void setType(Pokemon.PokemonType nt, Scanner scanner) {
		int index = 0;
		if (type.size() > 1) {
			System.out.println("Which type would you like to change? (1 or 2)");
			index = scanner.nextInt();
		}
		type.set(index, nt);
	}
	//damage is set to random number between 1 and 250, inclusive
	private void setDamage(){
		damage = (int)(Math.random() * 250 + 1);
	}
	public int getDamage(){
		return damage;
	}
	public static double damageToInflict(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		int level = userPokemon.getLevel();
		int attack = userPokemon.getAttack();
		int defense = targetPokemon.getDefense();
		int targets = 1, PB = 1, Weather = 1, GlaiveRush = 1, critical = 1;
		//generate a random integer between 85 and 100, inclusive, then divide by 100. any decimals are rounded down to the nearest integer. defined as random
		int random = (int)(Math.random() * 16 + 85);
		//STAB is the same-type attack bonus. This is equal to 1.5 if the move's type matches any of the user's types, 2 if the user of the move additionally has Adaptability as a ability, and 1 otherwise or if the attacker and/or used move is typeless.
		double STAB = userPokemon.getSTAB(targetMove);
		double type = targetPokemon.findMultiplier(targetMove);
		double burn = 1, other = 1, ZMove = 1, TeraShield = 1;

		return ((2*level/5 + 2) * targetMove.damage * attack/defense/50 + 2)*targets*PB*Weather*GlaiveRush*critical*random*STAB*type*burn*other*ZMove*TeraShield;
	}
}
