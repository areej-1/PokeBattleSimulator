public class Ability {
	private String name;
	public Ability(String n) {
		name = n;
	}
	public String getName() {
		return name;
	}
}
class Torrent extends Ability{
	public Torrent() {
		super("Torrent");
	}
	public double applyEffect(Pokemon user, Move move, double baseDamage) {
		if (move.getType().contains(Pokemon.PokemonType.WATER) && user.healthVal() <= user.maxHP() * 0.33) {
			System.out.println("torrent used");
            return baseDamage * 1.5;
        }
        return baseDamage;
	}
}
class Blaze extends Ability{
	public Blaze() {
		super("Blaze");
	}
	public double applyEffect(Pokemon user, Move move, double baseDamage) {
		if (move.getType().contains(Pokemon.PokemonType.FIRE) && user.healthVal() <= user.maxHP() * 0.33) {
			System.out.println("blaze used");
            return baseDamage * 1.5;
        }
        return baseDamage;
	}
}

//class for the ability rivalry, which is described as The Pokémon's competitive spirit makes it deal more damage to Pokémon of the same gender, but less damage to Pokémon of the opposite gender.	
//the class, other than its constructor, has a function, called applyEffect, which takes the user Pokemon, target Pokemon, and the base damage of the move as parameters. It returns the base damage of the move, after applying the effect of the ability.
class Rivalry extends Ability{
	public Rivalry() {
		super("Rivalry");
	}
	public double applyEffect(Pokemon user, double baseDamage, Pokemon target) {
		if (user.getGender() == target.getGender()) {
			System.out.println("rivalry used");
			return baseDamage * 1.25;
		} else if (user.getGender() != target.getGender()) {
			return baseDamage * 0.75;
		}
		return baseDamage;
	}
}
//class for the ability Huge Power, which is described as "Doubles the Pokémon's (user's) Attack stat."
//to apply the effect of the ability, a function called applyEffect is used, which takes the user Pokemon (of the ability), and doubles its attack stat. it is a void method.
class HugePower extends Ability{
	public HugePower() {
		super("Huge Power");
	}
	public void applyEffect(Pokemon user) {
		System.out.println("huge power used");
		user.setAttack(user.getAttack() * 2);
	}
}

//class for the ability Thick Fat, which is described as "The Pokémon is protected by a layer of thick fat, which halves the damage taken from Fire- and Ice-type moves."
//to apply the effect of the ability, a function called applyEffect is used, which takes the user Pokemon (of the ability), and halves the damage taken from fire and ice type moves. i
class ThickFat extends Ability{
	public ThickFat() {
		super("Thick Fat");
	}
	public double applyEffect(Pokemon user, double baseDamage, Move move) {
		System.out.println("thick fat used");
		if (move.getType().contains(Pokemon.PokemonType.FIRE) || move.getType().contains(Pokemon.PokemonType.ICE)) {
			baseDamage = baseDamage / 2;
		}
		return baseDamage;

	}
}

//class for the ability Adaptability, which is described as "Powers up moves of the same type as the Pokémon."
//the effects of adaptability are applied in the Pokemon class, when the method getSTAB is called. the method getSTAB checks whether or not a Pokemon has the ability adaptability, and if it does, it sets the STAB of the Pokemon to 2 (as long as the move is of the same type as the Pokemon).
class Adaptability extends Ability{
	public Adaptability() {
		super("Adaptability");
	}

}
