import java.util.ArrayList;
import java.util.Scanner;

public class Move {
	private ArrayList<Pokemon.PokemonType> type = new ArrayList<Pokemon.PokemonType>();
	private String name;
	private int damage;
	private double criticalPercentage = 0.0416666666666667; //default is 1/24
	private int baseAccuracy;
	private boolean usesAccuracy = true;
	private boolean isTwoTurn = false;
	public Move(String n, int damage, int baseAccuracy, boolean usesAccuracy, boolean isTwoTurn, Pokemon.PokemonType ... t) {
		for (Pokemon.PokemonType type : t) {
			this.type.add(type);
		}
		name = n;
		this.damage = damage;
		this.usesAccuracy = usesAccuracy;
		this.baseAccuracy = baseAccuracy;
		this.isTwoTurn = isTwoTurn;
	}
	public ArrayList<Pokemon.PokemonType> getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String failMessage(){ //returns a message to be printed if the move misses (overwritten in some subclasses)
		return "The move missed!";
	}
	public String successMessage(){ //returns a message to be printed if the move hits (overwritten in some subclasses)
		return "The move hit!";
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
	public void setDamage(int damage){ //for moves like avalanche, where the damage is variable
		this.damage = damage;
	}
	public void setCriticalPercentage(double critical) {
		this.criticalPercentage = critical;
	}
	private double getCritical(){
		//there is a 1/24 chance of a critical hit- (can be increased by certain things)
		if (Math.random() < criticalPercentage) {
			return 1.5;
		}
		else {
			return 1.0;
		}
	}
	//method to determine the move accuracy
	public double accuracy(Pokemon userPokemon, Pokemon targetPokemon){
		double stagemultiplier = Pokemon.getValue(userPokemon.getAccuracyStage()-targetPokemon.getEvasionStage());
		return baseAccuracy*stagemultiplier;
	}
	public double damageToInflict(Pokemon userPokemon, Pokemon targetPokemon) { //returns a -1.0 if the move misses, to be handled in the battle class
		int level = userPokemon.getLevel();
		int attack = userPokemon.getAttack();
		int defense = targetPokemon.getDefense();
		int power = this.getDamage();
		double targets = 1;
		double weather = 1;
		double badge = 1;
		double critical = getCritical();
		//random value between 0.85 and 1.00, inclusive
		double random = Math.random() * (1.00 - 0.85) + 0.85;
		//STAB is the same-type attack bonus. This is equal to 1.5 if the move's type matches any of the user's types, 2 if the user of the move additionally has Adaptability as a ability, and 1 otherwise or if the attacker and/or used move is typeless.
		double STAB = userPokemon.getSTAB(this);
		double type = targetPokemon.findMultiplier(this);
		double burn = 1;
		double other = 1;
		//calculate whether or not the move hits
		if (usesAccuracy) {
			if (Math.random() > accuracy(userPokemon, targetPokemon)) {
				System.out.println("The move missed!");
				return -1.0;
			}
		}
		if (critical == 1.5) {
			System.out.println("A critical hit!");
		}
		return ((((((((2*level)/5)+2)*power*attack)/defense)/50)+2)*targets*weather*badge*critical*random*STAB*type*burn*other);
	}
	//method to inflict any status effects that the move may have
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		System.out.println("Overwrite this method in the subclasses");
	}

	//method to check if a given pokemon's type(s) matches the move's type(s)
	public boolean checkType(Pokemon pokemon) {
		for (Pokemon.PokemonType type : pokemon.getType()) {
			if (this.type.contains(type)) {
				return true;
			}
		}
		return false;
	}
	public boolean isTwoTurn() {
		return isTwoTurn;
	}


	
}
class Surf extends Move {
	public Surf() {
		super("Surf", 90, 90, true, false, Pokemon.PokemonType.WATER);
	}
	//overwrite the inflictStatus method to inflict the status effect of surf
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//do nothing, since surf does not inflict a status effect
	}
}
class Thunderbolt extends Move {
	public Thunderbolt() {
		super("Thunderbolt", 90, 100, true, false, Pokemon.PokemonType.ELECTRIC);
	}
	//overwrite the inflictStatus method to inflict the status effect of thunderbolt
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//10% chance of paralyzing the opponent
		if (Math.random() < 0.1) {
			StatusCondition.paralyzed(targetPokemon);
		}
	}
}
class Flamethrower extends Move {
	public Flamethrower() {
		super("Flamethrower", 90, 100, true, false, Pokemon.PokemonType.FIRE);
	}
	//overwrite the inflictStatus method to inflict the status effect of flamethrower
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//10% chance of burning the opponent
		if (Math.random() < 0.1) {
			StatusCondition.burn(targetPokemon);
		}
	}

}
class IceBeam extends Move {
	public IceBeam() {
		super("Ice Beam", 90, 100, true, false, Pokemon.PokemonType.ICE);
	}
	//overwrite the inflictStatus method to inflict the status effect of ice beam
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//10% chance of freezing the opponent
		if (Math.random() < 0.1) {
			StatusCondition.frozen(targetPokemon);
		}
	}
}
class Earthquake extends Move {
	public Earthquake() {
		super("Earthquake", 100, 100, true, false, Pokemon.PokemonType.GROUND);
	}
	//overwrite the inflictStatus method to inflict the status effect of earthquake
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//do nothing, since earthquake does not inflict a status effect
	}
}
class ThunderPunch extends Move{
	public ThunderPunch() {
		super("Thunder Punch", 75, 100, true, false, Pokemon.PokemonType.ELECTRIC);
	}
	//overwrite the inflictStatus method to inflict the status effect of thunder punch
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//10% chance of paralyzing the opponent, as long as the opponent is not a electric type
		if (Math.random() < 0.1 && !targetPokemon.getType().contains(Pokemon.PokemonType.ELECTRIC)) {
			StatusCondition.paralyzed(targetPokemon);
		}
	}
}

class FlareBlitz extends Move{
	public FlareBlitz(){
		super("Flare Blitz", 120, 100, true, false, Pokemon.PokemonType.FIRE);
	}
	//overwrite the inflictStatus method to inflict the status effect of flare blitz
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//10% chance of burning the opponent, as long as the opponent is not a fire type. also, the user of the move takes 1/3 of the damage dealt as recoil damage. if the user is frozen, flare blitz will thaw the user out, then deal recoil damage
		if (Math.random() < 0.1 && !targetPokemon.getType().contains(Pokemon.PokemonType.FIRE)) {
			StatusCondition.burn(targetPokemon);
		}
		if (userPokemon.getStatusCondition().isFrozen()) {
			userPokemon.getStatusCondition().setFrozen(false);

		}
		userPokemon.doDamage((int)(getDamage()/3));
		System.out.print(userPokemon.getName() + " was hurt by recoil!");
	}
}

class CloseCombat extends Move{
	public CloseCombat(){
		super("Close Combat", 120, 100, true, false, Pokemon.PokemonType.FIGHTING);
	}
	//overwrite the inflictStatus method to inflict the status effect of close combat
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon,BattleContext bc) {
		//lowers the user's Defense stat and Special Defense stat by one stage each.
		userPokemon.setDefense(userPokemon.getDefense() - 1);
		userPokemon.setSpDef(userPokemon.getSpDef() - 1);
	}
}
class IceFang extends Move{
	public IceFang(){
		super("Ice Fang", 65, 95, true, false, Pokemon.PokemonType.ICE);
	}
	//overwrite the inflictStatus method to inflict the status effect of ice fang
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//10% chance of freezing the opponent, as long as the opponent is not a ice type. also, 10% chance of flinching the opponent
		if (Math.random() < 0.1 && !targetPokemon.getType().contains(Pokemon.PokemonType.ICE)) {
			StatusCondition.frozen(targetPokemon);
		}
		if (Math.random() < 0.1) {
			targetPokemon.setCanMove(false);

		}
	}
}

class StoneEdge extends Move{
	public StoneEdge(){
		super("Stone Edge", 100, 80, true, false, Pokemon.PokemonType.ROCK);
	}
	//overwrite the inflictStatus method to inflict the status effect of stone edge
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon,BattleContext bc) {
		//do nothing, since stone edge does not inflict a status effect
	}
}

class IceShard extends Move{
	public IceShard(){
		super("Ice Shard", 40, 100, true, false, Pokemon.PokemonType.ICE);
	}
	//overwrite the inflictStatus method to inflict the status effect of ice shard
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon,BattleContext bc) {
		//ice shard has a priority of +1, so is used before all moves that do not have increased priority.
		//to be implemented. wip

	}
}

class AuraSphere extends Move{
	public AuraSphere(){
		super("Aura Sphere", 80, 0, false, false, Pokemon.PokemonType.FIGHTING);
	}
	//overwrite the inflictStatus method to inflict the status effect of aura sphere
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//Aura Sphere inflicts damage and bypasses accuracy checks to always hit, unless the target is in the semi-invulnerable turn of a move such as Dig or Fly.
		//wip
	}
}

class FlashCannon extends Move{
	public FlashCannon(){
		super("Flash Cannon", 80, 100, true, false, Pokemon.PokemonType.STEEL);
	}
	//overwrite the inflictStatus method to inflict the status effect of flash cannon
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//10% chance of lowering the opponent's Special Defense stat by one stage
		if (Math.random() < 0.1) {
			targetPokemon.setSpDef(targetPokemon.getSpDef() - 1);
		}
	}
}

class DarkPulse extends Move{
	public DarkPulse(){
		super("Dark Pulse", 80, 100, true, false, Pokemon.PokemonType.DARK);
	}
	//overwrite the inflictStatus method to inflict the status effect of dark pulse
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon,BattleContext bc) {
		//20% chance of flinching the opponent
		if (Math.random() < 0.2) {
			targetPokemon.setCanMove(false);
		}
	}
}

class ThunderFang extends Move{
	public ThunderFang(){
		super("Thunder Fang", 65,95, true, false, Pokemon.PokemonType.ELECTRIC);
	}
	//overwrite the inflictStatus method to inflict the status effect of thunder fang
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//10% chance of paralyzing the opponent, as long as the opponent is not a electric type. also, 10% chance of flinching the opponent
		if (Math.random() < 0.1 && !targetPokemon.getType().contains(Pokemon.PokemonType.ELECTRIC)) {
			StatusCondition.paralyzed(targetPokemon);
		}
		if (Math.random() < 0.1) {
			targetPokemon.setCanMove(false);
		}
	}
}

class Crunch extends Move{
	public Crunch(){
		super("Crunch", 80, 100, true, false, Pokemon.PokemonType.DARK);
	}
	//overwrite the inflictStatus method to inflict the status effect of crunch
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//20% chance of lowering the opponent's Defense stat by one stage
		if (Math.random() < 0.2) {
			targetPokemon.setDefense(targetPokemon.getDefense() - 1);
		}
	}
}

class IronTail extends Move{
	public IronTail(){
		super("Iron Tail", 100, 75, true, false, Pokemon.PokemonType.STEEL);
	}
	//overwrite the inflictStatus method to inflict the status effect of iron tail
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//30% chance of lowering the opponent's Defense stat by one stage
		if (Math.random() < 0.3) {
			targetPokemon.setDefense(targetPokemon.getDefense() - 1);
		}
	}
}

class BraveBird extends Move{
	public BraveBird(){
		super("Brave Bird", 120, 100, true, false, Pokemon.PokemonType.FLYING);
	}
	//overwrite the inflictStatus method to inflict the status effect of brave bird
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//Brave Bird inflicts damage, and the user receives recoil damage equal to ⅓ of the damage done to the target. If the user inflicts no damage (such as if Disguise takes the damage), they do not take recoil damage.
		//wip for the case where the user inflicts no damage, but it works otherwise (normally, i mean)

		userPokemon.doDamage(getDamage() / 3);

	}

}


class AerialAce extends Move{
	public AerialAce(){
		super("Aerial Ace", 60, 0, false, false, Pokemon.PokemonType.FLYING);
	}
	//overwrite the inflictStatus method to inflict the status effect of aerial ace
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//Aerial Ace inflicts damage and ignores changes to the Accuracy and Evasion stats.
	}
}

class UTurn extends Move{
	public UTurn(){
		super("U-Turn", 70, 100, true, false, Pokemon.PokemonType.BUG);
	}
	//overwrite the inflictStatus method to inflict the status effect of u-turn
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//U-turn inflicts damage and then switches the user out for another Pokémon in the party. 
		Scanner scanner = new Scanner(System.in);
		bc.getCurrentBattle().swapPokemon(scanner);

	}
}

class DragonClaw extends Move{
	public DragonClaw(){
		super("Dragon Claw", 80, 100, true, false, Pokemon.PokemonType.DRAGON);
	}
	//overwrite the inflictStatus method to inflict the status effect of dragon claw
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//do nothing, since dragon claw does not inflict a status effect
	}
}

class SwordsDance extends Move{
	public SwordsDance(){
		super("Swords Dance", 0, 0, false, false, Pokemon.PokemonType.NORMAL);
	}
	//overwrite the inflictStatus method to inflict the status effect of swords dance
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//Raises the user's Attack stat by two stages.
		userPokemon.setAttack(userPokemon.getAttack() + 2);
	}
}

class Waterfall extends Move{
	public Waterfall(){
		super("Waterfall", 80, 100, true, false, Pokemon.PokemonType.WATER);
	}
	//overwrite the inflictStatus method to inflict the status effect of waterfall
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon,BattleContext bc) {
		//20% chance of flinching the opponent
		if (Math.random() < 0.2) {
			targetPokemon.setCanMove(false);
			System.out.println(targetPokemon.getName() + " flinched and cannot move!");
		}
	}
}

class Avalanche extends Move{
	public Avalanche(){
		super("Avalanche", 60, 100, true, false, Pokemon.PokemonType.ICE);
	}
	//overwrite the inflictStatus method to inflict the status effect of avalanche
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon,BattleContext bc) {
		//Avalanche inflicts damage, and has a base power of 120 if the user was hit by the target in the same turn. its also a decreased priority move (priority -4)
		if (bc.getWasHit()){
			setDamage(120);
		}
	}
}

class VoltSwitch extends Move{
	public VoltSwitch(){
		super("Volt Switch", 70, 100, true, false, Pokemon.PokemonType.ELECTRIC);
	}
	//overwrite the inflictStatus method to inflict the status effect of volt switch
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//Volt Switch inflicts damage, then switches the user out. 
		Scanner scanner = new Scanner(System.in);
		bc.getCurrentBattle().swapPokemon(scanner);
	}
}



class Roost extends Move{
	public Roost(){
		super("Roost", 0, 0, false, false, Pokemon.PokemonType.FLYING);
	}
	//overwrite the inflictStatus method to inflict the status effect of roost
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon,BattleContext bc) {
		//The user recovers ½ of its maximum HP, rounded half up. 
		userPokemon.heal(userPokemon.healthVal() + (userPokemon.getMaxHP() / 2));

		//The user loses its Flying type until the end of the turn.
		userPokemon.removeType(Pokemon.PokemonType.FLYING);
	}
}

class ExtremeSpeed extends Move{
	public ExtremeSpeed(){
		super("Extreme Speed", 80, 100, true, false, Pokemon.PokemonType.NORMAL);
	}
	//overwrite the inflictStatus method to inflict the status effect of extreme speed
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//Extreme Speed inflicts damage and has no secondary effect. 
		//it does have priority +2 though, so that's something. to be implemented
	}
}

class Protect extends Move{
	public Protect(){
		super("Protect", 0, 0, false, false, Pokemon.PokemonType.NORMAL);
	}
	//overwrite the inflictStatus method to inflict the status effect of protect
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//Protect prevents any moves from hitting the user this turn. 
		//Protect has an increased priority of +4, so it is used before all moves that do not have increased priority.
		bc.getUser().setCanRecieveDamage(false);

	}
	public String failMessage() {
		return "But it failed!";
	}
	public String successMessage(){
		return "The Pokemon protected itself!";
	}
}
class Fly extends Move{
	public Fly(){
		super("Fly", 90, 95, true, false, Pokemon.PokemonType.FLYING);
	}
	//overwrite the inflictStatus method to inflict the status effect of fly
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//Fly skips the next turn, becoming a two-turn move, and hits on the second turn.
		userPokemon.setSkipTurn(true);
		userPokemon.setCanRecieveDamage(false);
		
	}


}
class Acrobatics extends Move{
	public Acrobatics(){
		super("Acrobatics", 55, 100, true, false, Pokemon.PokemonType.FLYING);
	}
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc){
		//Acrobatics inflicts damage and has no secondary effect. 
		//However, if the user is not holding an item, Acrobatics's base power doubles from 55 to 110.
		if (userPokemon.getItem() == null) {
			userPokemon.setAttack(userPokemon.getAttack() * 2);
		}
	}
}
class ShadowClaw extends Move{
	public ShadowClaw(){
		super("Shadow Claw", 70, 100, true, false, Pokemon.PokemonType.GHOST);
	}
	//overwrite the inflictStatus method to inflict the status effect of shadow claw
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//Shadow Claw inflicts damage and has an increased critical hit ratio (1⁄8 instead of 1⁄24).
		setCriticalPercentage(0.125);
	}
}
class FlameWheel extends Move{
	public FlameWheel(){
		super("Flame Wheel", 60, 100, true, false, Pokemon.PokemonType.FIRE);
	}
	//overwrite the inflictStatus method to inflict the status effect of flame wheel
	public void inflictStatus(Pokemon userPokemon, Pokemon targetPokemon, BattleContext bc) {
		//10% chance of burning the opponent
		if (Math.random() < 0.1) {
			StatusCondition.burn(targetPokemon);
		}
		//Flame Wheel will thaw out the user if it is frozen too.
		if (userPokemon.getStatusCondition() != null && userPokemon.getStatusCondition().isFrozen()) {
			userPokemon.getStatusCondition().setFrozen(false);
		}
	}
}