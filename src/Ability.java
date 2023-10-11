public interface Ability {
    void applyEffect(String event, BattleContext context);
}
class Blaze implements Ability {  //Blaze: "Powers up Fire-type moves when the Pokémon's HP is low."
    @Override
    public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain FIRE and check if the user's HP is less than or equal to 33% of its max HP. if so, multiply the damage by 1.5
		if ("damage calculation".equals(event) && context.getMove().getType().contains(Pokemon.PokemonType.FIRE) && context.getUser().healthVal() <= context.getUser().getMaxHP() / 3) {
			System.out.println(context.getUser()+ "'s Blaze activated!");
			context.setDamage(context.getDamage() * 1.5);
		}
    }
}
class HugePower implements Ability { //Huge Power: "Doubles the Pokémon's Attack stat."
    @Override
    public void applyEffect(String event, BattleContext context) { //check if the event is "stats modification". if so, multiply the user's attack by 2
        if ("stats modification".equals(event)) {
			System.out.println(context.getUser()+ "'s Huge Power activated!");
            context.getUser().setAttack(context.getUser().getAttack() * 2);
        }
    }
}
// Intimidate: "Lowers the foe's Attack stat."
class Intimidate implements Ability {
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "stats modification". if so, divide the target's attack by 2
		if ("stats modification".equals(event)) {
			System.out.println(context.getUser()+ "'s Intimidate activated!");
			context.getTarget().setAttack(context.getTarget().getAttack() / 2);
		}
	}
}
class Torrent implements Ability{ //Torrent: "Powers up Water-type moves when the Pokémon's HP is low."
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain WATER and check if the user's HP is less than or equal to 33% of its max HP. if so, multiply the damage by 1.5
		if ("damage calculation".equals(event) && context.getMove().getType().contains(Pokemon.PokemonType.WATER) && context.getUser().healthVal() <= context.getUser().getMaxHP() / 3) {
			System.out.println(context.getUser()+ "'s Torrent activated!");
			context.setDamage(context.getDamage() * 1.5);
		}
	}
}
// Thick Fat: "Ups resistance to Fire- and Ice-type moves."
class ThickFat implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain FIRE or ICE. if so, divide the damage by 2
		if ("damage calculation".equals(event) && (context.getMove().getType().contains(Pokemon.PokemonType.FIRE) || context.getMove().getType().contains(Pokemon.PokemonType.ICE))) {
			System.out.println(context.getUser()+ "'s Thick Fat activated!");
			context.setDamage(context.getDamage() / 2);
		}
	}
}
// Adaptability: "Powers up moves of the same type."
class Adaptability implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain the same type as the user. if so, multiply the damage by 2
		if ("damage calculation".equals(event) && context.getMove().checkType(context.getUser())) {
			System.out.println(context.getUser()+ "'s Adaptability activated!");
			context.setDamage(context.getDamage() * 2);
		}
	}
}
//Levitate: "By floating in the air, the Pokémon receives full immunity to all Ground-type moves."
class Levitate implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain GROUND. if so, set the damage to 0
		if ("damage calculation".equals(event) && context.getMove().getType().contains(Pokemon.PokemonType.GROUND)) {
			System.out.println(context.getUser()+ "'s Levitate activated!");
			context.setDamage(0);
		}
	}
}
//Water Absorb: "Restores HP if hit by a Water-type move, instead of taking damage."
class WaterAbsorb implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain WATER. if so, set the damage to 0 and heal the user by the damage that would have been dealt
		if ("damage calculation".equals(event) && context.getMove().getType().contains(Pokemon.PokemonType.WATER)) {
			System.out.println(context.getUser()+ "'s Water Absorb activated!");
			context.getUser().heal((int)context.getDamage());
			context.setDamage(0);
		}
	}
}
//Volt Absorb: "Restores HP if hit by an Electric-type move, instead of taking damage."
class VoltAbsorb implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain ELECTRIC. if so, set the damage to 0 and heal the user by the damage that would have been dealt
		if ("damage calculation".equals(event) && context.getMove().getType().contains(Pokemon.PokemonType.ELECTRIC)) {
			System.out.println(context.getUser()+ "'s Volt Absorb activated!");
			context.getUser().heal((int)context.getDamage());
			context.setDamage(0);
		}
	}
}
//Swarm: "Powers up Bug-type moves when the Pokémon's HP is low."
class Swarm implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain BUG and check if the user's HP is less than or equal to 33% of its max HP. if so, multiply the damage by 1.5
		if ("damage calculation".equals(event) && context.getMove().getType().contains(Pokemon.PokemonType.BUG) && context.getUser().healthVal() <= context.getUser().getMaxHP() / 3) {
			System.out.println(context.getUser()+ "'s Swarm activated!");
			context.setDamage(context.getDamage() * 1.5);
		}
	}
}
//Motor Drive: "The Pokémon takes no damage from Electric-type moves. Instead, it Speed stat is boosted."
class MotorDrive implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain ELECTRIC. if so, set the damage to 0 and multiply the user's speed by 1.5
		if ("damage calculation".equals(event) && context.getMove().getType().contains(Pokemon.PokemonType.ELECTRIC)) {
			System.out.println(context.getUser()+ "'s Motor Drive activated!");
			context.getUser().setSpeed((int)(context.getUser().getSpeed() * 1.5));
			context.setDamage(0);
		}
	}
}
//Filter: "Reduces the power of supereffective attacks that hit the Pokémon."
class Filter implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the damage multiplier is greater than 1. if so, divide the damage by 2
		if ("damage calculation".equals(event) && context.getMultiplier() > 1) {
			System.out.println(context.getUser()+ "'s Filter activated!");
			context.setDamage(context.getDamage() / 2);
		}
	}
}
//solid rock: "Reduces the power of supereffective attacks that hit the Pokémon."
class SolidRock implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the damage multiplier is greater than 1. if so, divide the damage by 2
		if ("damage calculation".equals(event) && context.getMultiplier() > 1) {
			System.out.println(context.getUser()+ "'s Solid Rock activated!");
			context.setDamage(context.getDamage() / 2);
		}
	}
}
//justified: "Raises Attack when hit by a Dark-type move."
class Justified implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the move type contains DARK. if so, multiply the user's attack by 1.5
		if ("damage calculation".equals(event) && context.getMove().getType().contains(Pokemon.PokemonType.DARK)) {
			System.out.println(context.getUser()+ "'s Justified activated!");
			context.getUser().setAttack((int)(context.getUser().getAttack() * 1.5));
		}
	}
}
//Rattled: "The Pokémon gets scared when hit by a Dark-, Ghost-, or Bug-type move or if intimidated, which boosts its Speed stat."
class Rattled implements Ability{
	//Note that to check if a Pokemon is intimidated, first grab its status condition, then run the isIntimidated() method on it (e.g. context.getUser().getStatusCondition().isIntimidated())
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the move type contains DARK, GHOST, or BUG or if the user is intimidated. if so, multiply the user's speed by 1.5
		if ("damage calculation".equals(event) && (context.getMove().getType().contains(Pokemon.PokemonType.DARK) || context.getMove().getType().contains(Pokemon.PokemonType.GHOST) || context.getMove().getType().contains(Pokemon.PokemonType.BUG) /*|| context.getUser().getStatusCondition().isIntimidated()*/)) {
			System.out.println(context.getUser()+ "'s Rattled activated!");
			context.getUser().setSpeed((int)(context.getUser().getSpeed() * 1.5));
		}
	}
}
//Poison Heal: "Restores HP if the Pokémon is poisoned, instead of losing HP."
//Note that to check if a Pokemon is poisoned, first grab its status condition, then run the isPoisoned() method on it (e.g. context.getUser().getStatus().isPoisoned())
class PoisonHeal implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" or "status damage" and if the user is poisoned. if so, heal the user by 1/8 of its max HP
		if (("damage calculation".equals(event) || "status damage".equals(event)) && context.getUser().getStatusCondition().isPoisoned()) {
			System.out.println(context.getUser()+ "'s Poison Heal activated!");
			context.getUser().heal(context.getUser().getMaxHP() / 8);
		}
	}
}
//Heatproof: "Powers down Fire-type moves."
class Heatproof implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" or "status damage" and if the move type contains FIRE. if so, divide the damage by 2
		if (("damage calculation".equals(event) || "status damage".equals(event)) && context.getMove().getType().contains(Pokemon.PokemonType.FIRE)) {
			System.out.println(context.getUser()+ "'s Heatproof activated!");
			context.setDamage(context.getDamage() / 2);
		}
	}
}

//Magic Guard: "The Pokémon only takes damage from attacks."
class MagicGuard implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "status damage." if so, set the damage to 0 
		if ("status damage".equals(event)) {
			System.out.println(context.getUser()+ "'s Magic Guard activated!");
			context.setDamage(0);
		}
	}
}

//Defeatist: "Halves the Pokémon's Attack and Special Attack stats when its HP becomes half or less."
class Defeatist implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the user's HP is less than or equal to 50% of its max HP. if so, divide the user's attack and special attack by 2
		if ("damage calculation".equals(event) && context.getUser().healthVal() <= context.getUser().getMaxHP() / 2) { 
			System.out.println(context.getUser()+ "'s Defeatist activated!"); 
			context.getUser().setAttack(context.getUser().getAttack() / 2); 
			context.getUser().setSpAtk(context.getUser().getSpAtk() / 2);
		}
	}
}
class Rivalry implements Ability{ //Rivalry: "Becomes competitive and deals more damage to Pokemon of the same gender, but deals less to Pokemon of the opposite gender."
	@Override
	public void applyEffect(String event, BattleContext context){
		if ("damage calculation".equals(event)) {
			if (context.getUser().getGender() == context.getTarget().getGender()) {
				System.out.println("Rivalry activated!");
				context.setDamage(context.getDamage() * 1.25);
			}
			else if (context.getUser().getGender() != context.getTarget().getGender()) {
				System.out.println(context.getUser()+ "'s Rivalry activated!");
				context.setDamage(context.getDamage() * 0.75);
			}
		}
	}
}

class InnerFocus implements Ability{ //Inner Focus: "The Pokémon is protected from flinching."
	@Override
	public void applyEffect(String event, BattleContext context){
		if ("flinch".equals(event)) {
			System.out.println(context.getTarget().getName() + " won't flinch because of its Inner Focus!");
			context.getUser().getStatusCondition().setFlinched(false);
		}
	}
}
/*class SandViel implements Ability{ //Sand Veil: "Boosts the Pokémon's evasion in a sandstorm."
	@Override
	public void applyEffect(String event, BattleContext context){
		if ("evasion".equals(event) && context.getWeather().equals("Sandstorm")) {
			System.out.println(context.getUser()+ "'s Sand Veil activated!");
			context.setEvasion(context.getEvasion() * 1.25);
		}
	}
}

		
class Oblivious implements Ability{ //Oblivious: "The Pokémon is oblivious, and that keeps it from being infatuated or falling for taunts."
	@Override
	public void applyEffect(String event, BattleContext context){
		if ("infatuation".equals(event) || "taunt".equals(event)) {
			System.out.println(context.getUser()+ "'s Oblivious activated!");
			context.setInfatuation(false);
			context.setTaunt(false);
		}
	}
}

class SnowCloak implements Ability{ //Snow Cloak: "Raises evasion in a hailstorm."
	@Override
	public void applyEffect(String event, BattleContext context){
		if ("evasion".equals(event) && context.getWeather().equals("Hail")) {
			System.out.println(context.getUser()+ "'s Snow Cloak activated!");
			context.setEvasion(context.getEvasion() * 1.25);
		}
	}
}*/

class Steadfast implements Ability{ //Steadfast: "Raises Speed each time the Pokémon flinches."
	@Override
	public void applyEffect(String event, BattleContext context){
		if ("flinch".equals(event)) {
			System.out.println(context.getUser()+ "'s Steadfast activated!");
			//increases speed stat by one stage whenever the user flinches
			context.getUser().setSpeed(context.getUser().getSpeed() + 1);
		}
	}
}



