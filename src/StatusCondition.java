public class StatusCondition {
    private boolean burned;
    private boolean paralyzed;
    private boolean poisoned;
    private boolean asleep;
    private boolean frozen;
    private boolean flinched;
    private boolean infatuated;
    private boolean taunted; // not a status condition, but has similar implementation/effects
    private boolean intimidated; // not a status condition, but has similar implementation/effects

    private BattleContext context;

    private int turnCount; // number of turns the status condition has been active (used primarily for taunt)

    public StatusCondition(Pokemon pokemon) {
        burned = false;
        paralyzed = false;
        poisoned = false;
        asleep = false;
        frozen = false;
        flinched = false;
        infatuated = false;
        taunted = false;
        intimidated = false;
        context = new BattleContext(pokemon);
        turnCount = 0;
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

    public boolean isInfatuated() {
        return infatuated;
    }

    public void setInfatuated(boolean infatuated) {
        this.infatuated = infatuated;
    }

    public boolean isTaunted() {
        return taunted;
    }

    public void setTaunted(boolean taunted){

        this.taunted = taunted;
    }

    public boolean isIntimidated() {
        return intimidated;
    }

    public void setIntimidated(boolean intimidated) {
        this.intimidated = intimidated;
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
        if (isInfatuated()) {
            infatuated(pokemon);
        }
        if (isTaunted()) {
            taunted(pokemon);
        }
    }

    // ... (Remaining methods for applying the effects remain the same as previous example) ...

    private void flinch(Pokemon pokemon) {
        System.out.println(pokemon.getName() + " flinched! It can't move!");
        pokemon.setCanMove(false);
    }

    private void burn(Pokemon pokemon) {
        // Burn reduces HP by 1/16 of max HP
        System.out.println(pokemon.getName() + " is hurt by its burn!");
        pokemon.doDamage(pokemon.getMaxHP() / 16);
    }

    private void poisoned(Pokemon pokemon) {
        // Poison reduces HP by 1/8 of max HP
        System.out.println(pokemon.getName() + " is hurt by its poison!");
        pokemon.doDamage(pokemon.getMaxHP() / 8);
    }

    private void paralyzed(Pokemon pokemon) {
        // Paralysis has a 25% chance of immobilizing the Pokémon
        if (Math.random() < 0.25) {
            System.out.println(pokemon.getName() + " is fully paralyzed and cannot move!");
            pokemon.setCanMove(false);
        }
    }

    private void asleep(Pokemon pokemon) {
        // Sleep immobilizes the Pokémon but has a 50% chance of waking up each turn
        if (Math.random() < 0.5) {
            System.out.println(pokemon.getName() + " woke up!");
            pokemon.removeStatus(); 
        } else {
            System.out.println(pokemon.getName() + " is fast asleep!");
            pokemon.setCanMove(false);
        }
    }

    private void frozen(Pokemon pokemon) {
        // Being frozen immobilizes the Pokémon but has a 20% chance of thawing each turn
        if (Math.random() < 0.2) {
            System.out.println(pokemon.getName() + " thawed out!");
            pokemon.removeStatus();
        } else {
            System.out.println(pokemon.getName() + " is frozen solid!");
            pokemon.setCanMove(false);
        }
    }

    private void infatuated(Pokemon pokemon) {
        // Being infatuated has a 50% chance of immobilizing the Pokémon
        if (Math.random() < 0.5) {
            System.out.println(pokemon.getName() + " is immobilized by love!");
            pokemon.setCanMove(false);
        }
    }

    private void taunted(Pokemon pokemon) {
        if (turnCount == 3){
            pokemon.removeStatus();
            System.out.println(pokemon.getName() + " is no longer under the effects of Taunt!");
            turnCount = 0;
        }
        turnCount++;
    }

}
