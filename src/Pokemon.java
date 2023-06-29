
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
public class Pokemon {
	public enum PokemonType {
	    NORMAL,
	    FIRE,
	    WATER,
	    ELECTRIC,
	    GRASS,
	    ICE,
	    FIGHTING,
	    POISON,
	    GROUND,
	    FLYING,
	    PSYCHIC,
	    BUG,
	    ROCK,
	    GHOST,
	    DRAGON,
	    DARK,
	    STEEL,
	    FAIRY;
	}
	
	private String name;
	private List<PokemonType> type;
	private int level;
	private String desc;
	private String extendedDesc;
	private String evolution;
	private String evolveFrom;
	private final int maxChar = 119;
	private move[] moves = new move[4];
	private int health;
	private static HashMap<PokemonType, List<PokemonType>> typeWeaknesses = new HashMap<>();
	private static HashMap<PokemonType, List<PokemonType>> typeResistances = new HashMap<>();
	private static HashMap<PokemonType, List<PokemonType>> typeNoEffects = new HashMap<>();
	private int[] stats = new int[6]; //order is HP, Attack, Defense, SP. Attack, SP. Defense, and Speed. 
	private Ability ability;
	private boolean gender; //true -> female, false -> male
	
	Set<PokemonType> weakness = new HashSet<PokemonType>();
	Set<PokemonType> resistance = new HashSet<PokemonType>();
	Set<PokemonType> noEffect = new HashSet<PokemonType>();
	
	static {
		addToMap(typeWeaknesses, PokemonType.NORMAL, PokemonType.FIGHTING);
	    addToMap(typeWeaknesses, PokemonType.FIRE, PokemonType.WATER, PokemonType.GROUND, PokemonType.ROCK);
	    addToMap(typeWeaknesses, PokemonType.WATER, PokemonType.ELECTRIC, PokemonType.GRASS);
	    addToMap(typeWeaknesses, PokemonType.ELECTRIC, PokemonType.GROUND);
	    addToMap(typeWeaknesses, PokemonType.GRASS, PokemonType.FIRE, PokemonType.ICE, PokemonType.POISON, PokemonType.FLYING, PokemonType.BUG);
	    addToMap(typeWeaknesses, PokemonType.ICE, PokemonType.FIRE, PokemonType.FIGHTING, PokemonType.ROCK, PokemonType.STEEL);
	    addToMap(typeWeaknesses, PokemonType.FIGHTING, PokemonType.FLYING, PokemonType.PSYCHIC, PokemonType.FAIRY);
	    addToMap(typeWeaknesses, PokemonType.POISON, PokemonType.PSYCHIC, PokemonType.GROUND);
	    addToMap(typeWeaknesses, PokemonType.GROUND, PokemonType.WATER, PokemonType.GRASS, PokemonType.ICE);
	    addToMap(typeWeaknesses, PokemonType.FLYING, PokemonType.ELECTRIC, PokemonType.ROCK, PokemonType.ICE);
	    addToMap(typeWeaknesses, PokemonType.PSYCHIC, PokemonType.BUG, PokemonType.GHOST, PokemonType.DARK);
	    addToMap(typeWeaknesses, PokemonType.BUG, PokemonType.FIRE, PokemonType.FLYING, PokemonType.ROCK);
	    addToMap(typeWeaknesses, PokemonType.ROCK, PokemonType.WATER, PokemonType.GRASS, PokemonType.FIGHTING, PokemonType.GROUND, PokemonType.STEEL);
	    addToMap(typeWeaknesses, PokemonType.GHOST, PokemonType.GHOST, PokemonType.DARK);
	    addToMap(typeWeaknesses, PokemonType.DRAGON, PokemonType.DRAGON, PokemonType.FAIRY, PokemonType.ICE);
	    addToMap(typeWeaknesses, PokemonType.DARK, PokemonType.FIGHTING, PokemonType.BUG, PokemonType.FAIRY);
	    addToMap(typeWeaknesses, PokemonType.STEEL, PokemonType.FIRE, PokemonType.FIGHTING, PokemonType.GROUND, PokemonType.ELECTRIC);
	    addToMap(typeWeaknesses, PokemonType.FAIRY, PokemonType.POISON, PokemonType.STEEL);
	    
	    addToMap(typeResistances, PokemonType.NORMAL);
	    addToMap(typeResistances, PokemonType.FIRE, PokemonType.FIRE, PokemonType.GRASS, PokemonType.ICE, PokemonType.BUG, PokemonType.STEEL, PokemonType.FAIRY);
	    addToMap(typeResistances, PokemonType.WATER, PokemonType.FIRE, PokemonType.WATER, PokemonType.ICE, PokemonType.STEEL);
	    addToMap(typeResistances, PokemonType.ELECTRIC, PokemonType.ELECTRIC, PokemonType.FLYING, PokemonType.STEEL);
	    addToMap(typeResistances, PokemonType.GRASS, PokemonType.WATER, PokemonType.ELECTRIC, PokemonType.GRASS, PokemonType.GROUND);
	    addToMap(typeResistances, PokemonType.ICE, PokemonType.ICE);
	    addToMap(typeResistances, PokemonType.FIGHTING, PokemonType.BUG, PokemonType.ROCK, PokemonType.DARK);
	    addToMap(typeResistances, PokemonType.POISON, PokemonType.GRASS, PokemonType.FIGHTING, PokemonType.POISON, PokemonType.BUG, PokemonType.FAIRY);
	    addToMap(typeResistances, PokemonType.GROUND, PokemonType.POISON, PokemonType.ROCK);
	    addToMap(typeResistances, PokemonType.FLYING, PokemonType.GRASS, PokemonType.FIGHTING, PokemonType.BUG);
	    addToMap(typeResistances, PokemonType.PSYCHIC, PokemonType.FIGHTING, PokemonType.PSYCHIC);
	    addToMap(typeResistances, PokemonType.BUG, PokemonType.GRASS, PokemonType.FIGHTING, PokemonType.GROUND);
	    addToMap(typeResistances, PokemonType.ROCK, PokemonType.NORMAL, PokemonType.FIRE, PokemonType.POISON, PokemonType.FLYING);
	    addToMap(typeResistances, PokemonType.GHOST, PokemonType.POISON, PokemonType.BUG);
	    addToMap(typeResistances, PokemonType.DRAGON, PokemonType.FIRE, PokemonType.WATER, PokemonType.ELECTRIC, PokemonType.GRASS);
	    addToMap(typeResistances, PokemonType.DARK, PokemonType.GHOST, PokemonType.DARK);
	    addToMap(typeResistances, PokemonType.STEEL, PokemonType.NORMAL, PokemonType.GRASS, PokemonType.ICE, PokemonType.FLYING, PokemonType.PSYCHIC, PokemonType.BUG, PokemonType.ROCK, PokemonType.DRAGON, PokemonType.STEEL, PokemonType.FAIRY);
	    addToMap(typeResistances, PokemonType.FAIRY, PokemonType.FIGHTING, PokemonType.BUG, PokemonType.DARK);
	    
	    addToMap(typeNoEffects, PokemonType.NORMAL, PokemonType.GHOST);
	    addToMap(typeNoEffects, PokemonType.FIRE);
	    addToMap(typeNoEffects, PokemonType.WATER);
	    addToMap(typeNoEffects, PokemonType.ELECTRIC);
	    addToMap(typeNoEffects, PokemonType.GRASS);
	    addToMap(typeNoEffects, PokemonType.ICE);
	    addToMap(typeNoEffects, PokemonType.FIGHTING);
	    addToMap(typeNoEffects, PokemonType.POISON);
	    addToMap(typeNoEffects, PokemonType.GROUND, PokemonType.ELECTRIC);
	    addToMap(typeNoEffects, PokemonType.FLYING, PokemonType.GROUND);
	    addToMap(typeNoEffects, PokemonType.PSYCHIC);
	    addToMap(typeNoEffects, PokemonType.BUG);
	    addToMap(typeNoEffects, PokemonType.ROCK);
	    addToMap(typeNoEffects, PokemonType.GHOST, PokemonType.NORMAL, PokemonType.FIGHTING);
	    addToMap(typeNoEffects, PokemonType.DRAGON);
	    addToMap(typeNoEffects, PokemonType.DARK, PokemonType.PSYCHIC);
	    addToMap(typeNoEffects, PokemonType.STEEL, PokemonType.POISON);
	    addToMap(typeNoEffects, PokemonType.FAIRY, PokemonType.DRAGON);
	}
	
	public Pokemon(String n, String t, int lvl, String d, int he, int speed, Ability myAbility, boolean mof, Scanner scanner) {
        name = n;
        
        type = new ArrayList<>();
        String[] types = t.split("/");
        for (String typeStr : types) {
            type.add(PokemonType.valueOf(typeStr.toUpperCase()));
        }
        
        level = lvl;
        desc = d;
        try {
            extendedDesc = WebScraper.readDescription(name.toLowerCase());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //evolves(scanner);
        //evolveFrom(scanner);
        health = he;
        stats[0] = he;
        stats[5] = speed;
        for (int i = 0; i < getType().size(); i++) {
			weakness.addAll(typeWeaknesses.get(this.getType().get(i)));
			resistance.addAll(typeResistances.get(this.getType().get(i)));
			noEffect.addAll(typeNoEffects.get(this.getType().get(i)));
		}
        ability = myAbility;
        gender = mof;
    }

	private static void addToMap(HashMap<PokemonType, List<PokemonType>> map, PokemonType key, PokemonType... values) {
	    map.put(key, new ArrayList<>(Arrays.asList(values)));
	}
	
	public void setMoves(Scanner scanner) {
	    String move;
	    PokemonType type;
	    move m;
	    for (int i = 0; i <= 3; i++) {
	        System.out.println("Please enter in move number " + (i + 1) + " of " + getName());
	        move = scanner.nextLine();
	        System.out.println("Please enter in the type of that move");
	        while (true){
	            try {
	                type = PokemonType.valueOf(scanner.nextLine().toUpperCase());
	                break;
	            } catch (IllegalArgumentException iae) {
	                System.out.println("Not a valid type! Please try again");
	            }

	        }
	        m = new move(type, move);
	        moves[i] = m;
	    }
	}
	public void changeMove(Scanner scanner, move newMove) {
		System.out.println("Which move would you like to replace? Choose from the following:");
		System.out.println(Arrays.toString(moves));
		String n = scanner.nextLine();
		move cMove = new move(null, n);
		boolean contains = Arrays.stream(moves).anyMatch(cMove::equals);
		if (!contains) {
			System.out.println("This Pokemon doesn't know that move.");
		}
		else {
			moves[Arrays.asList(moves).indexOf(cMove)] = newMove;
			System.out.println("Congratulations! " + name + " has learned the move " + newMove+"!");
		}
		
	}
	public void levelUp() {
		level++;
	}
	public String format(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            sb.append(input.charAt(i));
            if ((i + 1) % maxChar == 0) {
                sb.append("\n");
            }
        }

        return sb.toString();
	}
	public String getMoves() {
		String s = "";
		for (int i = 0; i < 3; i++) {
			s += moves[i].getName() + ", ";
		}
		s+= moves[3].getName();
		return s;
	}
	public move[] moveset() {
		return moves;
	}
	/*public String toString() {
		if (evolution.equals("Doesn't evolve into any Pokemon") && evolveFrom.equals("Doesn't evolve from any Pokemon")) {
			return name + " - A " + type + " Type\nThe " + desc + " Pokemon\n" + evolveFrom + ", " + evolution + "\n\n" + extendedDesc+"\n\n"; 
		}
		else if(evolution.equals("Doesn't evolve into any Pokemon")) {
			return name + " - A " + type + " Type\nThe " + desc + " Pokemon\n" + "Evolves from: " + evolveFrom + ", " + evolution + "\n\n" + extendedDesc+"\n\n"; 
		}
		else if (evolveFrom.equals("Doesn't evolve from any Pokemon")) {
			return name + " - A " + type + " Type\nThe " + desc + " Pokemon\n" + evolveFrom + ", evolves into: " + evolution + "\n\n" + extendedDesc+"\n\n";
		}
		return name + " - A " + type + " Type\nThe " + desc + " Pokemon\n" + "Evolves from: " + evolveFrom + ", evolves into: " + evolution + "\n\n" + extendedDesc+"\n\n";
	}*/
	public String getName() {
		return name;
	}
	public int getLevel() {
		return level;
	}
	public move[] moves() {
		return moves;
	}
	public List<PokemonType> getType() {
		return type;
	}
	public String getClassification() {
		return desc;
	}
	public String previousEvolution() {
		return evolveFrom;
	}
	public String nextEvolution() {
		return evolution;
	}
	public String getDescription() {
		return extendedDesc;
	}
	public int healthVal() {
		return health;
	}
	public void doDamage(double damage, move m) {
	    boolean isWeak = false;
	    boolean isResistant = false;

	    for (PokemonType type : this.type) {
	        if (typeWeaknesses.get(type).contains(m.getType())) {
	            isWeak = true;
	        } else if (typeResistances.get(type).contains(m.getType())) {
	            isResistant = true;
	        }
	    }

	    if (isWeak && !isResistant) {
	        damage *= 2;
	        System.out.println("Super effective!");
	    } else if (!isWeak && isResistant) {
	        damage /= 2;
	        System.out.println("Not very effective.");
	    } else if (noEffect.contains(m.getType())) {
	        damage = 0;
	        System.out.println("It had no effect...");
	    }

	    if (health - damage < 0) {
	        health = 0;
	    } else {
	        health -= damage;
	    }
	}


	public void heal(int healAmount) {
		if (health+healAmount > stats[0]) {
			health = stats[0];
		}
		else {
			health+=healAmount;
		}
	}
	public int maxHP() {
		return stats[0];
	}
	public Set<Pokemon.PokemonType> getWEAKNESS() {
		return weakness;
	}
	public Set<Pokemon.PokemonType> getRESISTANCE() {
		return resistance;
	}
	public Set<Pokemon.PokemonType> getNOEFFECT() {
		return noEffect;
	}
	public int getSpeed() {
		return stats[5];
	}

	public Ability getAbility() {
		return ability;
	}

	public boolean getGender() {
		return gender;
	}

}

class PartnerPokemon extends Pokemon{
	private String nickname;
	public PartnerPokemon(String n, String t, int lvl, String d, int health, int speed, Ability ability, boolean mof, Scanner scanner) {
		super(n, t, lvl, d, health, speed, ability,mof, scanner);
	}
	public void setNickname(String nn) {
		nickname = nn;
	}
	public String getNickname() {
		return nickname;
	}
	@Override
	public String toString() {
		if (nextEvolution().equals("Doesn't evolve into any Pokemon") && previousEvolution().equals("Doesn't evolve from any Pokemon")) {
			return nickname + "(" + getName() + ") - A " + getType() + " Type\nThe " + getClassification() + " Pokemon\n" + previousEvolution() + ", " + nextEvolution() + "\n\n" + getDescription()+"\n\n"; 
		}
		else if(nextEvolution().equals("Doesn't evolve into any Pokemon")) {
			return nickname + "(" + getName() + ") - A " + getType() + " Type\nThe " + getClassification() + " Pokemon\n" + "Evolves from: " + previousEvolution() + ", " + nextEvolution() + "\n\n" + getDescription()+"\n\n"; 
		}
		else if (previousEvolution().equals("Doesn't evolve from any Pokemon")) {
			return nickname + "(" + getName() + ") - A " + getType() + " Type\nThe " + getClassification() + " Pokemon\n" + previousEvolution() + ", evolves into: " + nextEvolution() + "\n\n" + getDescription()+"\n\n";
		}
		return nickname + "(" + getName() + ") - A " + getType() + " Type\nThe " + getClassification() + " Pokemon\n" + "Evolves from: " + previousEvolution() + ", evolves into: " + nextEvolution() + "\n\n" + getDescription()+"\n\n";
	}
}
