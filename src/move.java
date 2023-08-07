import java.util.ArrayList;
import java.util.Scanner;

public class Move {
	private ArrayList<Pokemon.PokemonType> type = new ArrayList<Pokemon.PokemonType>();
	private String name;
	private int damage;
	public Move(String n, int damage, Pokemon.PokemonType ... t) {
		for (Pokemon.PokemonType type : t) {
			this.type.add(type);
		}
		name = n;
		this.damage = damage;
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
	//method to inflict any status effects that the move may have
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		System.out.println("Overwrite this method in the subclasses");
	}

	
}
class Surf extends Move {
	public Surf() {
		super("Surf", 90, Pokemon.PokemonType.WATER);
	}
	//overwrite the inflictStatus method to inflict the status effect of surf
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//do nothing, since surf does not inflict a status effect
	}
}
class Thunderbolt extends Move {
	public Thunderbolt() {
		super("Thunderbolt", 90, Pokemon.PokemonType.ELECTRIC);
	}
	//overwrite the inflictStatus method to inflict the status effect of thunderbolt
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//10% chance of paralyzing the opponent
		if (Math.random() < 0.1) {
			StatusCondition.paralyzed(targetPokemon);
		}
	}
}
class Flamethrower extends Move {
	public Flamethrower() {
		super("Flamethrower", 90,  Pokemon.PokemonType.FIRE);
	}
	//overwrite the inflictStatus method to inflict the status effect of flamethrower
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//10% chance of burning the opponent
		if (Math.random() < 0.1) {
			StatusCondition.burn(targetPokemon);
		}
	}

}
class IceBeam extends Move {
	public IceBeam() {
		super("Ice Beam", 90, Pokemon.PokemonType.ICE);
	}
	//overwrite the inflictStatus method to inflict the status effect of ice beam
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//10% chance of freezing the opponent
		if (Math.random() < 0.1) {
			StatusCondition.frozen(targetPokemon);
		}
	}
}
class Earthquake extends Move {
	public Earthquake() {
		super("Earthquake", 100, Pokemon.PokemonType.GROUND);
	}
	//overwrite the inflictStatus method to inflict the status effect of earthquake
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//do nothing, since earthquake does not inflict a status effect
	}
}
class ThunderPunch extends Move{
	public ThunderPunch() {
		super("Thunder Punch", 75, Pokemon.PokemonType.ELECTRIC);
	}
	//overwrite the inflictStatus method to inflict the status effect of thunder punch
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//10% chance of paralyzing the opponent, as long as the opponent is not a electric type
		if (Math.random() < 0.1 && !targetPokemon.getType().contains(Pokemon.PokemonType.ELECTRIC)) {
			StatusCondition.paralyzed(targetPokemon);
		}
	}
}

class FlareBlitz extends Move{
	public FlareBlitz(){
		super("Flare Blitz", 120, Pokemon.PokemonType.FIRE);
	}
	//overwrite the inflictStatus method to inflict the status effect of flare blitz
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//10% chance of burning the opponent, as long as the opponent is not a fire type. also, the user of the move takes 1/3 of the damage dealt as recoil damage. if the user is frozen, flare blitz will thaw the user out, then deal recoil damage
		if (Math.random() < 0.1 && !targetPokemon.getType().contains(Pokemon.PokemonType.FIRE)) {
			StatusCondition.burn(targetPokemon);
		}
		if (userPokemon.getStatusCondition().isFrozen()) {
			userPokemon.getStatusCondition().setFrozen(false);

		}
		userPokemon.doDamage((int)(targetMove.getDamage()/3));
		System.out.print(userPokemon.getName() + " was hurt by recoil!");
	}
}

class CloseCombat extends Move{
	public CloseCombat(){
		super("Close Combat", 120, Pokemon.PokemonType.FIGHTING);
	}
	//overwrite the inflictStatus method to inflict the status effect of close combat
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//owers the user's Defense stat and Special Defense stat by one stage each.
		userPokemon.setDefense(userPokemon.getDefense() - 1);
		userPokemon.setSpDef(userPokemon.getSpDef() - 1);
	}
}
class IceFang extends Move{
	public IceFang(){
		super("Ice Fang", 65, Pokemon.PokemonType.ICE);
	}
	//overwrite the inflictStatus method to inflict the status effect of ice fang
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//10% chance of freezing the opponent, as long as the opponent is not a ice type. also, 10% chance of flinching the opponent
		if (Math.random() < 0.1 && !targetPokemon.getType().contains(Pokemon.PokemonType.ICE)) {
			StatusCondition.frozen(targetPokemon);
		}
		/*if (Math.random() < 0.1) {
			StatusCondition.flinch(targetPokemon);
		}*/ //this is commented out because flinching is not implemented yet
	}
}

class StoneEdge extends Move{
	public StoneEdge(){
		super("Stone Edge", 100, Pokemon.PokemonType.ROCK);
	}
	//overwrite the inflictStatus method to inflict the status effect of stone edge
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//do nothing, since stone edge does not inflict a status effect
	}
}

class IceShard extends Move{
	public IceShard(){
		super("Ice Shard", 40, Pokemon.PokemonType.ICE);
	}
	//overwrite the inflictStatus method to inflict the status effect of ice shard
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//ice shard has a priority of +1, so is used before all moves that do not have increased priority.
		//to be implemented. wip

	}
}

class AuraSphere extends Move{
	public AuraSphere(){
		super("Aura Sphere", 80, Pokemon.PokemonType.FIGHTING);
	}
	//overwrite the inflictStatus method to inflict the status effect of aura sphere
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//do nothing, since aura sphere does not inflict a status effect

		//Aura Sphere inflicts damage and bypasses accuracy checks to always hit, unless the target is in the semi-invulnerable turn of a move such as Dig or Fly.
		//wip
	}
}

class FlashCannon extends Move{
	public FlashCannon(){
		super("Flash Cannon", 80, Pokemon.PokemonType.STEEL);
	}
	//overwrite the inflictStatus method to inflict the status effect of flash cannon
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//10% chance of lowering the opponent's Special Defense stat by one stage
		if (Math.random() < 0.1) {
			targetPokemon.setSpDef(targetPokemon.getSpDef() - 1);
		}
	}
}

class DarkPulse extends Move{
	public DarkPulse(){
		super("Dark Pulse", 80, Pokemon.PokemonType.DARK);
	}
	//overwrite the inflictStatus method to inflict the status effect of dark pulse
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//20% chance of flinching the opponent
		/*if (Math.random() < 0.2) {
			StatusCondition.flinch(targetPokemon);
		}*/ //this is commented out because flinching is not implemented yet
	}
}

class ThunderFang extends Move{
	public ThunderFang(){
		super("Thunder Fang", 65, Pokemon.PokemonType.ELECTRIC);
	}
	//overwrite the inflictStatus method to inflict the status effect of thunder fang
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//10% chance of paralyzing the opponent, as long as the opponent is not a electric type. also, 10% chance of flinching the opponent
		if (Math.random() < 0.1 && !targetPokemon.getType().contains(Pokemon.PokemonType.ELECTRIC)) {
			StatusCondition.paralyzed(targetPokemon);
		}
		/*if (Math.random() < 0.1) {
			StatusCondition.flinch(targetPokemon);
		}*/ //this is commented out because flinching is not implemented yet
	}
}

class Crunch extends Move{
	public Crunch(){
		super("Crunch", 80, Pokemon.PokemonType.DARK);
	}
	//overwrite the inflictStatus method to inflict the status effect of crunch
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//20% chance of lowering the opponent's Defense stat by one stage
		if (Math.random() < 0.2) {
			targetPokemon.setDefense(targetPokemon.getDefense() - 1);
		}
	}
}

class IronTail extends Move{
	public IronTail(){
		super("Iron Tail", 100, Pokemon.PokemonType.STEEL);
	}
	//overwrite the inflictStatus method to inflict the status effect of iron tail
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//30% chance of lowering the opponent's Defense stat by one stage
		if (Math.random() < 0.3) {
			targetPokemon.setDefense(targetPokemon.getDefense() - 1);
		}
	}
}

//Protect - to be implemented. wip- bc of the fact that its completely different, since it blocks damage 

class BraveBird extends Move{
	public BraveBird(){
		super("Brave Bird", 120, Pokemon.PokemonType.FLYING);
	}
	//overwrite the inflictStatus method to inflict the status effect of brave bird
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//Brave Bird inflicts damage, and the user receives recoil damage equal to ⅓ of the damage done to the target. If the user inflicts no damage (such as if Disguise takes the damage), they do not take recoil damage.
		//wip for the case where the user inflicts no damage, but it works otherwise (normally, i mean)

		userPokemon.doDamage(targetMove.getDamage() / 3);

	}

}


class AerialAce extends Move{
	public AerialAce(){
		super("Aerial Ace", 60, Pokemon.PokemonType.FLYING);
	}
	//overwrite the inflictStatus method to inflict the status effect of aerial ace
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//Aerial Ace inflicts damage and ignores changes to the Accuracy and Evasion stats.
		//wip
	}
}

class UTurn extends Move{
	public UTurn(){
		super("U-Turn", 70, Pokemon.PokemonType.BUG);
	}
	//overwrite the inflictStatus method to inflict the status effect of u-turn
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//U-turn inflicts damage and then switches the user out for another Pokémon in the party. 
	}
}

class DragonClaw extends Move{
	public DragonClaw(){
		super("Dragon Claw", 80, Pokemon.PokemonType.DRAGON);
	}
	//overwrite the inflictStatus method to inflict the status effect of dragon claw
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//do nothing, since dragon claw does not inflict a status effect
	}
}

class SwordsDance extends Move{
	public SwordsDance(){
		super("Swords Dance", 0, Pokemon.PokemonType.NORMAL);
	}
	//overwrite the inflictStatus method to inflict the status effect of swords dance
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//Raises the user's Attack stat by two stages.
		userPokemon.setAttack(userPokemon.getAttack() + 2);
	}
}

class Waterfall extends Move{
	public Waterfall(){
		super("Waterfall", 80, Pokemon.PokemonType.WATER);
	}
	//overwrite the inflictStatus method to inflict the status effect of waterfall
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//20% chance of flinching the opponent
		/*if (Math.random() < 0.2) {
			StatusCondition.flinch(targetPokemon);
		}*/ //this is commented out because flinching is not implemented yet
	}
}

class Avalanche extends Move{
	public Avalanche(){
		super("Avalanche", 60, Pokemon.PokemonType.ICE);
	}
	//overwrite the inflictStatus method to inflict the status effect of avalanche
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//Avalanche inflicts damage, and has a base power of 120 if the user was hit by the target in the same turn. its also a decreased priority move (priority -4)
		//wip
	}
}

class VoltSwitch extends Move{
	public VoltSwitch(){
		super("Volt Switch", 70, Pokemon.PokemonType.ELECTRIC);
	}
	//overwrite the inflictStatus method to inflict the status effect of volt switch
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//Volt Switch inflicts damage, then switches the user out. 
		//wip
	}
}

//Fly here, work in progress, there's a lot of stuff to do here!

class Roost extends Move{
	public Roost(){
		super("Roost", 0, Pokemon.PokemonType.FLYING);
	}
	//overwrite the inflictStatus method to inflict the status effect of roost
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//The user recovers ½ of its maximum HP, rounded half up. 
		userPokemon.heal(userPokemon.healthVal() + (userPokemon.getMaxHP() / 2));

		//The user loses its Flying type classification until the end of the turn.
		//wip
	}
}

class ExtremeSpeed extends Move{
	public ExtremeSpeed(){
		super("Extreme Speed", 80, Pokemon.PokemonType.NORMAL);
	}
	//overwrite the inflictStatus method to inflict the status effect of extreme speed
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, Move targetMove) {
		//Extreme Speed inflicts damage and has no secondary effect. 
		//it does have priority +2 though, so that's something. to be implemented
	}
}