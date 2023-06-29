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
	public double applyEffect(Pokemon user, move move, double baseDamage) {
		if (move.getType() == Pokemon.PokemonType.WATER && user.healthVal() <= user.maxHP() * 0.33) {
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
	public double applyEffect(Pokemon user, move move, double baseDamage) {
		if (move.getType() == Pokemon.PokemonType.FIRE && user.healthVal() <= user.maxHP() * 0.33) {
			System.out.println("blaze used");
            return baseDamage * 1.5;
        }
        return baseDamage;
	}
}
class Rivalry extends Ability {

    public Rivalry(String name) {
        super(name);
    }

    public double applyEffect(Pokemon user, double baseDamage, Pokemon target) {
        if (user.getGender() == target.getGender()) {
            return baseDamage * 1.25;
        } else {
            return baseDamage * 0.75;
        }
    }
}