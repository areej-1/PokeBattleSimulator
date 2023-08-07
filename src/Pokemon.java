
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.lang.reflect.*;
public class Pokemon {
	public enum PokemonType { // 18 types (represented by enum constants)
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
	private Move[] moves = new Move[4];
	private int health;
	private static HashMap<PokemonType, List<PokemonType>> typeWeaknesses = new HashMap<>();
	private static HashMap<PokemonType, List<PokemonType>> typeResistances = new HashMap<>();
	private static HashMap<PokemonType, List<PokemonType>> typeNoEffects = new HashMap<>();
	private int[] stats = new int[6]; //order is HP, Attack, Defense, SP. Attack, SP. Defense, and Speed. 
	private int[] originalStats = new int[6]; //order is HP, Attack, Defense, SP. Attack, SP. Defense, and Speed. Used for resetting stats after battle/when withdrawn.
	private Ability ability;
	private boolean gender; //true -> female, false -> male
	private StatusCondition status;
	private boolean canMove;
	
	Set<PokemonType> weakness = new HashSet<PokemonType>(); //specific to each Pokemon, taken from the static HashMaps below
	Set<PokemonType> resistance = new HashSet<PokemonType>();
	Set<PokemonType> noEffect = new HashSet<PokemonType>();
	
	static { //static initializer for typeWeaknesses, typeResistances, and typeNoEffects (all HashMaps)
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
	
	public Pokemon(String n, String t, int lvl, String d, int he, int attack, int defense, int spAtk, int spDef, int speed, Ability myAbility, boolean mof, Scanner scanner) { 
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
		originalStats[0] = he;
		stats[1] = attack;
		originalStats[1] = attack;
		stats[2] = defense;
		originalStats[2] = defense;
		stats[3] = spAtk;
		originalStats[3] = spAtk;
		stats[4] = spDef;
		originalStats[4] = spDef;
        stats[5] = speed;
		originalStats[5] = speed;

        for (int i = 0; i < getType().size(); i++) {
			weakness.addAll(typeWeaknesses.get(this.getType().get(i)));
			resistance.addAll(typeResistances.get(this.getType().get(i)));
			noEffect.addAll(typeNoEffects.get(this.getType().get(i)));
		}
        ability = myAbility;
        gender = mof;
    }

	private static void addToMap(HashMap<PokemonType, List<PokemonType>> map, PokemonType key, PokemonType... values) { //adds a key and values to a map (used for type weaknesses, resistances, and no effects)
	    map.put(key, new ArrayList<>(Arrays.asList(values)));
	}
	
	public void setMoves(Scanner scanner) { 
		for (int i = 0; i < 4; i++) {
			System.out.println("Please enter in the name of the " + ordinal(i + 1) + " move of " + getName());
			
			while (true) {
				String move = scanner.nextLine();
				String className = ValidMove.isValid(move, scanner); //checks if the move is valid

				if (!className.equals("Invalid input.")) {
					try {
						Class<?> moveClass = Class.forName(className);  
						Move m = (Move) moveClass.getDeclaredConstructor().newInstance(); 
						moves[i] = m;
						break;  // exit the loop when the move is successfully set
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Not a valid move! Please try again");
				}
			}
		}
	}

	private String ordinal(int i) {
		switch (i) {
			case 1: return "1st";
			case 2: return "2nd";
			case 3: return "3rd";
			default: return i + "th";
		}
	}

	public void changeMove(Scanner scanner) {
    System.out.println("Which move would you like to replace? Choose from the following:");
    System.out.println(Arrays.toString(moves));

    Move[] cMoveWrapper = new Move[1];
    String move, className;

    while (true) {
        move = scanner.nextLine();
        className = ValidMove.isValid(move, scanner);

        while (className.equals("Invalid input.")) {
            System.out.println("Not a valid move! Please try again");
            move = scanner.nextLine();
            className = ValidMove.isValid(move, scanner);
        }

        try {
            Class<?> moveClass = Class.forName(className);
            cMoveWrapper[0] = (Move) moveClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return;  // exit method if there's an error
        }
        boolean contains = Arrays.stream(moves).anyMatch(moveObj -> moveObj.equals(cMoveWrapper[0]));
        if (!contains) {
            break;
        } else {
            System.out.println("This Pokemon doesn't know that move. Please try again.");
        }
    }

    System.out.println("Which move would you like " + getName() + " to learn?");
    String newMoveName = scanner.nextLine();

    Move nMove = null;
    String classN = ValidMove.isValid(newMoveName, scanner);

    while (classN.equals("Invalid input.")) {
        System.out.println("Not a valid move! Please try again");
        newMoveName = scanner.nextLine();
        classN = ValidMove.isValid(newMoveName, scanner);
    }

    try {
        Class<?> moveClass = Class.forName(classN);
        nMove = (Move) moveClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
        e.printStackTrace();
        return;
    }

    int index = -1; 
    for (int i = 0; i < moves.length; i++) { 
        if (moves[i].equals(cMoveWrapper[0])) { 
            index = i; 
            break; 
        }
    }

    if(index != -1) {
        moves[index] = nMove;
    }
    System.out.println("Congratulations! " + getName() + " has learned the move " + newMoveName + "!");
}


	public void levelUp() { //levels up the Pokemon by 1
		level++;
	}
	public String format(String input) { //formats the input string so that it is easier to read in the console (used in the toString method for descriptions/pokedex entries)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            sb.append(input.charAt(i));
            if ((i + 1) % maxChar == 0) {
                sb.append("\n");
            }
        }

        return sb.toString();
	}
	public String getMoves() { //returns the moveset of the Pokemon, but in a String format (for the toString method). formatting looks like this: 'move1, move2, move3, move4'
		String s = "";
		for (int i = 0; i < 3; i++) {
			s += moves[i].getName() + ", ";
		}
		s+= moves[3].getName();
		return s;
	}
	public Move[] moveset() { //returns the moveset of the Pokemon
		return moves;
	}

	public String toString(){
		return name;
	}
	public String getName() { //returns the name of the Pokemon
		return name;
	}
	public int getLevel() { //returns the level of the Pokemon
		return level;
	}
	public Move[] moves() { //returns the moves of the Pokemon
		return moves;
	}
	public List<PokemonType> getType() { //returns the type(s) of the Pokemon
		return type;
	}
	public String getClassification() { //returns the classification of the Pokemon
		return desc;
	}
	public String previousEvolution() { //returns the previous evolution of the Pokemon
		return evolveFrom;
	}
	public String nextEvolution() { //returns the next evolution of the Pokemon
		return evolution;
	}
	public String getDescription() { //returns the extended description of the Pokemon
		return extendedDesc;
	}
	public int healthVal() { //returns the current health of the Pokemon
		return health;
	}
	public void doDamage(double damage, Move m) {  //deals damage to the current Pokemon
		double multiplier = findMultiplier(m);
	    damage = damage*multiplier;
		//if multiplier is 2, print 'Super effective!'
		//if multiplier is 0.5, print 'Not very effective.'
		//if multiplier is 0, print 'It had no effect...'
		//if multiplier is 1, print 'It was effective.'
		if (multiplier == 2) {
			System.out.println("Super effective!");
		}
		else if (multiplier == 0.5) {
			System.out.println("Not very effective.");
		}
		else if (multiplier == 0) {
			System.out.println("It had no effect...");
		}
		else if (multiplier == 1) {
			System.out.println("It was effective.");
		}

	    if (health - damage < 0) {
	        health = 0;
	    } else {
	        health -= damage;
	    }
	}
	//another doDamage method, but takes a number of damage instead (used for status effects)
	public void doDamage(int damage) {
		if (health - damage < 0) {
	        health = 0;
	    } else {
	        health -= damage;
	    }
	}
	public void heal(int healAmount) { //heals the Pokemon by healAmount, but cannot exceed the max HP
		if (health+healAmount > stats[0]) {
			health = stats[0];
		}
		else {
			health+=healAmount;
		}
	}
	public int getMaxHP() { //the max HP of the Pokemon
		return stats[0];
	}
	public Set<Pokemon.PokemonType> getWeakness() { //returns the weaknesses of the Pokemon
		return weakness;
	}
	public Set<Pokemon.PokemonType> getResistance() { //returns the resistances of the Pokemon
		return resistance;
	}
	public Set<Pokemon.PokemonType> getNoEffect() { //returns the types that have no effect on the Pokemon (can be empty)
		return noEffect;
	}
	public int getAttack() { //returns the attack stat of the Pokemon
		return stats[1];
	}
	public int getDefense() { //returns the defense stat of the Pokemon
		return stats[2];
	}
	public int getSpAtk() { //returns the special attack stat of the Pokemon
		return stats[3];
	}
	public int getSpDef() { //returns the special defense stat of the Pokemon
		return stats[4];
	}
	public int getSpeed() { //returns the speed stat of the Pokemon
		return stats[5];
	}
	public Ability getAbility() { //returns the ability of the Pokemon
		if (ability == null) {
			return null;
		}
		return ability;
	}

	public boolean getGender() { //returns the gender of the pokemon: true -> female, false -> male
		return gender;
	}
	//finds the multiplier needed when a move of a specific type is used. can be 0.25, 0.5, 1, 2, or 4. will be 0 if the move has no effect. by default, multiplier is 1.
	//for the findMultiplier method to find the multiplier, the type, or types if applicable, of the move are checked against the type, or types, of the current pokemon object.
	public double findMultiplier(Move m){
		double multiplier = 1;
		//check if moveType is effective against the current pokemon object's type(s) and change multiplier accordingly.
		for (PokemonType pt : m.getType()){
			if (getWeakness().contains(pt)) {
				multiplier*=2;
			}
			if (getResistance().contains(pt)) {
				multiplier*=0.5;
			}
			if (getNoEffect().contains(pt)) {
				multiplier = 0;
				break;
			}
		}
		return multiplier;
	}
	public void setAttack(int a) { //sets the attack stat of the Pokemon
		stats[1] = a;
	}
	public void setDefense(int d) { //sets the defense stat of the Pokemon
		stats[2] = d;
	}
	public void setSpAtk(int sa) { //sets the special attack stat of the Pokemon
		stats[3] = sa;
	}
	public void setSpDef(int sd) { //sets the special defense stat of the Pokemon
		stats[4] = sd;
	}
	public void setSpeed(int s) { //sets the speed stat of the Pokemon
		stats[5] = s;
	}
	//returns the STAB value of the Pokemon, depending on the type of move/ability the Pokemon has
	public double getSTAB(Move m) {
		if (m.getType().contains(type.get(0))) {
			return 1.5;
		}
		//if the Pokemon has two types, it will return 1.5 if the move is of either type, but before checking the second type, it will check whether or not the move has 2 types
		else if (type.size() == 2 && m.getType().contains(type.get(1))) {
			return 1.5;
		}
		else {
			return 1.0;
		}
	}

	public StatusCondition getStatusCondition() { //returns the status condition of the Pokemon
		return status;
	}
	public void setStatus(StatusCondition s) { //sets the status condition of the Pokemon
		status = s;
	}
	public void removeStatus() { //removes the status condition of the Pokemon
		status = null;
	}
	public void setCanMove(boolean b){
		canMove = b;
	}
	public boolean getCanMove(){
		return canMove;
	}
	//method to reset all values of the pokemon that may have been impacted by a battle, along with stats that may have been lowered or raised
	public void reset() {
		health = stats[0];
		status = null;
		canMove = true;
		for (int i = 1; i < stats.length; i++) {
			stats[i] = originalStats[i];
		}
	}
}

class PartnerPokemon extends Pokemon{ //this class is for the Pokemon that the player has as a partner
	private String nickname;
	public PartnerPokemon(String n, String t, int lvl, String d, int health, int attack, int defense, int spAtk, int spDef, int speed, Ability ability, boolean mof, Scanner scanner) {
		super(n, t, lvl, d, health, attack, defense, spAtk, spDef, speed, ability, mof, scanner);
	}
	public void setNickname(String nn) { //sets the nickname of the Pokemon
		nickname = nn;
	}
	public String getNickname() { //returns the nickname of the Pokemon
		return nickname;
	}
}


