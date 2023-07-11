public interface Ability {
    void applyEffect(String event, BattleContext context);
}
class Blaze implements Ability {  //Blaze: "Powers up Fire-type moves when the Pokémon's HP is low."
    @Override
    public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain FIRE and check if the user's HP is less than or equal to 33% of its max HP. if so, multiply the damage by 1.5
		if ("damage calculation".equals(event) && context.getMove().getType().contains(Pokemon.PokemonType.FIRE) && context.getUser().healthVal() <= context.getUser().getMaxHP() / 3) {
			context.setDamage(context.getDamage() * 1.5);
		}
    }
}
/*class HugePower implements Ability { //Huge Power: "Doubles the Pokémon's Attack stat."
    @Override
    public void applyEffect(String event, BattleContext context) { //check if the event is "stats modification". if so, multiply the user's attack by 2
        if ("stats modification".equals(event)) {
            context.getUser().setAttack(context.getUser().getAttack() * 2);
        }
    }
}*/ //implemented when i add the necessary code (for changing the stats only once (when the pokemon is  sent out), then setting back to normal afterwards(after fainting, call back, etc))
// Intimidate: "Lowers the foe's Attack stat."
/*class Intimidate implements Ability {
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "stats modification". if so, divide the target's attack by 2
		if ("stats modification".equals(event)) {
			context.getTarget().setAttack(context.getTarget().getAttack() / 2);
		}
	}
}*/ //same thing as for HugePower: implemented when i add the necessary code (for changing the stats only once (when the pokemon is  sent out), then setting back to normal afterwards(after fainting, call back, etc))
class Torrent implements Ability{ //Torrent: "Powers up Water-type moves when the Pokémon's HP is low."
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain WATER and check if the user's HP is less than or equal to 33% of its max HP. if so, multiply the damage by 1.5
		if ("damage calculation".equals(event) && context.getMove().getType().contains(Pokemon.PokemonType.WATER) && context.getUser().healthVal() <= context.getUser().getMaxHP() / 3) {
			context.setDamage(context.getDamage() * 1.5);
		}
	}
}
// Thick Fat: "Ups resistance to Fire- and Ice-type moves."
class ThickFat implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain FIRE or ICE. if so, divide the damage by 2
		if ("damage calculation".equals(event) && (context.getMove().getType().contains(Pokemon.PokemonType.FIRE) || context.getMove().getType().contains(Pokemon.PokemonType.ICE))) {
			context.setDamage(context.getDamage() / 2);
		}
	}
}
// Adaptability: "Powers up moves of the same type."
class Adaptability implements Ability{
	@Override
	public void applyEffect(String event, BattleContext context) { //check if the event is "damage calculation" and if the types of the move contain the same type as the user. if so, multiply the damage by 2
		if ("damage calculation".equals(event) && context.getMove().getType().contains(context.getUser().getType())) {
			context.setDamage(context.getDamage() * 2);
		}
	}
}
