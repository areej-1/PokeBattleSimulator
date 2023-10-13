public class StatusCondition {
    private boolean burned;
    private boolean paralyzed;
    private boolean poisoned;
    private boolean asleep;
    private boolean frozen;
    private boolean flinched;

    private BattleContext context;
    private Pokemon myPokemon;

    public StatusCondition(Pokemon pokemon) {
        burned = false;
        paralyzed = false;
        poisoned = false;
        asleep = false;
        frozen = false;
        flinched = false;
        context = new BattleContext(pokemon);
        myPokemon = pokemon;
    }

    // Getter and Setter methods
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
        if (frozen) {
            System.out.println(context.getUser().getName() + " was frozen solid!");
        }
    }

    public boolean isFlinched() {
        return flinched;
    }

    public void setFlinched(boolean flinched) {
        this.flinched = flinched;
    }

    // Methods to apply the effects of each status condition
    public void applyEffect(Pokemon pokemon) {
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
        if (isFlinched()) {
            flinch(pokemon);
        }
    }

    // ... (Remaining methods for applying the effects remain the same as previous example) ...

    public static void flinch(Pokemon pokemon) {
        System.out.println(pokemon.getName() + " flinched! It can't move!");
        pokemon.setCanMove(false);
    }

    public static void burn(Pokemon pokemon) {
        // Burn reduces HP by 1/16 of max HP
        System.out.println(pokemon.getName() + " is hurt by its burn!");
        pokemon.doDamage(pokemon.getMaxHP() / 16);
    }

    public static void poisoned(Pokemon pokemon) {
        // Poison reduces HP by 1/8 of max HP
        System.out.println(pokemon.getName() + " is hurt by its poison!");
        pokemon.doDamage(pokemon.getMaxHP() / 8);
    }

    public static void paralyzed(Pokemon pokemon) {
        // Paralysis has a 25% chance of immobilizing the Pokémon
        if (Math.random() < 0.25) {
            System.out.println(pokemon.getName() + " is fully paralyzed and cannot move!");
            pokemon.setCanMove(false);
        }
    }

    public static void asleep(Pokemon pokemon) {
        // Sleep immobilizes the Pokémon but has a 50% chance of waking up each turn
        if (Math.random() < 0.5) {
            System.out.println(pokemon.getName() + " woke up!");
            pokemon.removeStatus(); 
        } else {
            System.out.println(pokemon.getName() + " is fast asleep!");
            pokemon.setCanMove(false);
        }
    }

    public static void frozen(Pokemon pokemon) {
        // Being frozen immobilizes the Pokémon but has a 20% chance of thawing each turn
        if (Math.random() < 0.2) {
            System.out.println(pokemon.getName() + " thawed out!");
            pokemon.removeStatus();
        } else {
            System.out.println(pokemon.getName() + " is frozen solid!");
            pokemon.setCanMove(false);
        }
    }


}
