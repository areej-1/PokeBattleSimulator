public class BattleContext {
    private Pokemon user;
    private Pokemon target;
    private Move move;
    private double damage;
    private int turnNumber;
    //constructors for all possible combinations of parameters (doesn't have to be all of the parameters; can be less than five)
    public BattleContext(Pokemon user, Pokemon target, Move move, double damage) {
        this.user = user;
        this.target = target;
        this.move = move;
        this.damage = damage;
    }
    public BattleContext(Pokemon user, Pokemon target, Move move) {
        this.user = user;
        this.target = target;
        this.move = move;
    }
    public BattleContext(Pokemon user, Pokemon target, double damage) {
        this.user = user;
        this.target = target;
        this.damage = damage;
    }
    public BattleContext(Pokemon user, Pokemon target) {
        this.user = user;
        this.target = target;
    }
    public BattleContext(Pokemon user, Move move, double damage) {
        this.user = user;
        this.move = move;
        this.damage = damage;
    }
    public BattleContext(Pokemon user, Move move) {
        this.user = user;
        this.move = move;
    }
    public BattleContext(Pokemon user, double damage) {
        this.user = user;
        this.damage = damage;
    }
    public BattleContext(Pokemon user) {
        this.user = user;
    }
    public BattleContext(Pokemon user, int turnNumber) {
        this.user = user;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Pokemon user, Pokemon target, int turnNumber) {
        this.user = user;
        this.target = target;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Pokemon user, Move move, int turnNumber) {
        this.user = user;
        this.move = move;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Pokemon user, Pokemon target, Move move, int turnNumber) {
        this.user = user;
        this.target = target;
        this.move = move;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Pokemon user, double damage, int turnNumber) {
        this.user = user;
        this.damage = damage;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Pokemon user, Pokemon target, double damage, int turnNumber) {
        this.user = user;
        this.target = target;
        this.damage = damage;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Pokemon user, Move move, double damage, int turnNumber) {
        this.user = user;
        this.move = move;
        this.damage = damage;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Pokemon user, Pokemon target, Move move, double damage, int turnNumber) {
        this.user = user;
        this.target = target;
        this.move = move;
        this.damage = damage;
        this.turnNumber = turnNumber;
    }
    //getters and setters for parameters to be used in the ability classes
    public Pokemon getUser() {
        return user;
    }
    public void setUser(Pokemon user) {
        this.user = user;
    }
    public Pokemon getTarget() {
        return target;
    }
    public void setTarget(Pokemon target) {
        this.target = target;
    }
    public Move getMove() {
        return move;
    }
    public void setMove(Move move) {
        this.move = move;
    }
    public double getDamage() {
        return damage;
    }
    public void setDamage(double damage) {
        this.damage = damage;
    }
    public int getTurnNumber() {
        return turnNumber;
    }
    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

}
