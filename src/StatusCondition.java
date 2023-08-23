public class StatusCondition {
    //class to hold the status condition of a pokemon (just the basic ones for now (only the conditions from the tcg), will add more later)
    private boolean burned;
    private boolean paralyzed;
    private boolean poisoned;
    private boolean asleep;
    private boolean frozen;
    private static BattleContext context;

    public StatusCondition(Pokemon pokemon) {
        burned = false;
        paralyzed = false;
        poisoned = false;
        asleep = false;
        frozen = false;
        context = new BattleContext(pokemon);
    }

    //getters and setters for the status conditions
    public boolean isBurned() {
        return burned;
    }
    public void setBurned(boolean burned) {
        this.burned = burned;
        if (burned) {
            System.out.println(context.getUser().getName() + " was burned!");
        }
    }
    public boolean isParalyzed() {
        return paralyzed;
    }
    public void setParalyzed(boolean paralyzed) {
        this.paralyzed = paralyzed;
        if (paralyzed) {
            System.out.println(context.getUser().getName() + " was paralyzed!");
        }
    }
    public boolean isPoisoned() {
        return poisoned;
    }
    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
        if (poisoned) {
            System.out.println(context.getUser().getName() + " was poisoned!");
        }
    }
    public boolean isAsleep() {
        return asleep;
    }
    public void setAsleep(boolean asleep) {
        this.asleep = asleep;
        if (asleep) {
            System.out.println(context.getUser().getName() + " fell asleep!");
        }
    }
    public boolean isFrozen() {
        return frozen;
    }
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
        if (!frozen) {
            System.out.println(context.getUser().getName() + " thawed out!");
        }
        else {
            System.out.println(context.getUser().getName() + " was frozen solid!");
        }
    }

    //methods to inflict the effect of a status condition
    public static void burn(Pokemon pokemon) {
        //burned pokemon take damage equal to 1/16 of their max HP at the end of each turn
        context.setDamage(pokemon.healthVal() - pokemon.getMaxHP() / 16);
        if (!pokemon.getType().contains(Pokemon.PokemonType.FIRE)) {
            pokemon.getAbility().applyEffect("status damage",  context);
            if (context.getDamage() > 0){
                System.out.println(pokemon.getName() + " is hurt by its burn!");
                pokemon.doDamage((int) context.getDamage());
            }
            else {
                System.out.println(pokemon.getName() + " wasn't hurt by its burn!"); //this is if the pokemon has any ability that prevents it from taking damage from its burn 
            }
        }
        System.out.println(pokemon.getName() + " wasn't hurt by its burn!"); //if the pokemon is a fire type, it doesn't take damage from its burn
    }
    public static void poisoned(Pokemon pokemon) {
        //poisoned pokemon take damage equal to 1/8 of their max HP at the end of each turn
        //first, make sure the pokemon doesn't have the poison heal ability (make sure the ability is not of type PoisonHeal), or isn't a poison type or steel type
        if (pokemon.getAbility() instanceof PoisonHeal && !pokemon.getType().contains(Pokemon.PokemonType.POISON) && !pokemon.getType().contains(Pokemon.PokemonType.STEEL)) {
            System.out.println(pokemon.getName() + " is hurt by its poison!");
            pokemon.doDamage(pokemon.healthVal() - pokemon.getMaxHP() / 8);
        }
        //if the pokemon's ability is of type PoisonHeal, heal the pokemon instead of hurting it
        else if (pokemon.getAbility() instanceof PoisonHeal) {
            System.out.println(pokemon.getName() + " is healed by its poison!");
            pokemon.getAbility().applyEffect("status damage", new BattleContext(pokemon, pokemon.getMaxHP() / 8));
        }
    }
    public static void paralyzed(Pokemon pokemon) {
        //paralyzed pokemon have a 25% chance of not being able to move each turn
        if (Math.random() < 0.25) {
            System.out.println(pokemon.getName() + " is paralyzed! It can't move!");
            pokemon.setCanMove(false);
        }
        //also, paralyzed pokemon have their speed stat halved
        pokemon.setSpeed(pokemon.getSpeed() / 2);
    }
    public static void asleep(Pokemon pokemon) {
        //asleep pokemon have a 50% chance of waking up each turn
        if (Math.random() < 0.5) {
            System.out.println(pokemon.getName() + " woke up!");
            pokemon.setStatus(null);
        }
        //also, asleep pokemon can't move
        System.out.println(pokemon.getName() + " is asleep! It can't move!");
        pokemon.setCanMove(false);
    }
    public static void frozen(Pokemon pokemon) {
        //frozen pokemon have a 20% chance of thawing out each turn
        if (Math.random() < 0.2) {
            System.out.println(pokemon.getName() + " thawed out!");
            pokemon.setStatus(null);
        }
        //also, frozen pokemon can't move
        System.out.println(pokemon.getName() + " is frozen solid! It can't move!");
        pokemon.setCanMove(false);
    }
    public void applyEffect(Pokemon pokemon){
        //this method applies the effect of the status condition to the pokemon
        if (pokemon.getStatusCondition() != null) {
            if (isBurned()) {
                burn(pokemon);
            }
            if (isPoisoned()) {
                poisoned(pokemon);
            }
            if (isParalyzed()) {
                paralyzed(pokemon);
            }
            if (isAsleep()) {
                asleep(pokemon);
            }
            if (isFrozen()) {
                frozen(pokemon);
            }
        }
    }



   
}
