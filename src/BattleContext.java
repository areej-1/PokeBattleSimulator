public class BattleContext {
    private Pokemon user;
    private Pokemon target;
    private Move move; //move used by the user pokemon
    private double damage;
    private int turnNumber; //turn number (counting how many turns a pokemon has been in battle for; note that it is reset for the specific pokemon if the pokemon is called back, fainted, etc. also, there is a turn number for each pokemon, not just one for the entire battle). to be implemented later (helps with abilities like Huge Power and Intimidate, and moves like Outrage and Thrash, which use the turn number to determine when to stop, or in the case of, say, Huge Power, its only done once (when the pokemon is initially sent out), then the pokemon's attack is set back to normal afterwards (after fainting, call back, etc))
    private double multiplier;
    private Battle currentBattle;
    private boolean wasHit = false; //boolean variable that stores whether or not the user pokemon was hit by a move in the current turn. false by default.
    private boolean wasSwapped = false; //boolean variable that stores whether or not the user pokemon was swapped out in the current turn. false by default.
    //variable for consecutive turns protect (used in the move class for the protect move)
    private int consecutiveTurnsProtect = 0;
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
    public BattleContext(Pokemon user, double damage, int turnNumber, double multiplier) {
        this.user = user;
        this.damage = damage;
        this.turnNumber = turnNumber;
        this.multiplier = multiplier;
    }
    public BattleContext(Pokemon user, Pokemon target, double damage, int turnNumber, double multiplier) {
        this.user = user;
        this.target = target;
        this.damage = damage;
        this.turnNumber = turnNumber;
        this.multiplier = multiplier;
    }
    public BattleContext(Pokemon user, Move move, double damage, int turnNumber, double multiplier) {
        this.user = user;
        this.move = move;
        this.damage = damage;
        this.turnNumber = turnNumber;
        this.multiplier = multiplier;
    }
    public BattleContext(Pokemon user, Pokemon target, Move move, double damage, int turnNumber, double multiplier) {
        this.user = user;
        this.target = target;
        this.move = move;
        this.damage = damage;
        this.turnNumber = turnNumber;
        this.multiplier = multiplier;
    }
    public BattleContext(Battle battle){
        this.currentBattle = battle;
    }
    public BattleContext(Battle battle, Pokemon user){
        this.currentBattle = battle;
        this.user = user;
    }
    public BattleContext(Battle battle, Pokemon user, Pokemon target){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
    }
    public BattleContext(Battle battle, Pokemon user, Move move){
        this.currentBattle = battle;
        this.user = user;
        this.move = move;
    }
    public BattleContext(Battle battle, Pokemon user, Pokemon target, Move move){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
        this.move = move;
    }
    public BattleContext(Battle battle, Pokemon user, double damage){
        this.currentBattle = battle;
        this.user = user;
        this.damage = damage;
    }
    public BattleContext(Battle battle, Pokemon user, Pokemon target, double damage){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
        this.damage = damage;
    }
    public BattleContext(Battle battle, Pokemon user, Move move, double damage){
        this.currentBattle = battle;
        this.user = user;
        this.move = move;
        this.damage = damage;
    }
    public BattleContext(Battle battle, Pokemon user, Pokemon target, Move move, double damage){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
        this.move = move;
        this.damage = damage;
    }
    public BattleContext(Battle battle, Pokemon user, int turnNumber){
        this.currentBattle = battle;
        this.user = user;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Battle battle, Pokemon user, Pokemon target, int turnNumber){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Battle battle, Pokemon user, Move move, int turnNumber){
        this.currentBattle = battle;
        this.user = user;
        this.move = move;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Battle battle, Pokemon user, Pokemon target, Move move, int turnNumber){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
        this.move = move;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Battle battle, Pokemon user, double damage, int turnNumber){
        this.currentBattle = battle;
        this.user = user;
        this.damage = damage;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Battle battle, Pokemon user, Pokemon target, double damage, int turnNumber){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
        this.damage = damage;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Battle battle, Pokemon user, Move move, double damage, int turnNumber){
        this.currentBattle = battle;
        this.user = user;
        this.move = move;
        this.damage = damage;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Battle battle, Pokemon user, Pokemon target, Move move, double damage, int turnNumber){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
        this.move = move;
        this.damage = damage;
        this.turnNumber = turnNumber;
    }
    public BattleContext(Battle battle, Pokemon user, double damage, int turnNumber, double multiplier){
        this.currentBattle = battle;
        this.user = user;
        this.damage = damage;
        this.turnNumber = turnNumber;
        this.multiplier = multiplier;
    }
    public BattleContext(Pokemon user, Pokemon target, Battle battle){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
    }
    public BattleContext(Pokemon user, Move move, Battle battle){
        this.currentBattle = battle;
        this.user = user;
        this.move = move;
    }
    public BattleContext(Pokemon user, Pokemon target, Move move, Battle battle){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
        this.move = move;
    }
    public BattleContext(Pokemon user, double damage, Battle battle){
        this.currentBattle = battle;
        this.user = user;
        this.damage = damage;
    }
    public BattleContext(Pokemon user, Pokemon target, double damage, Battle battle){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
        this.damage = damage;
    }
    public BattleContext(Pokemon user, Move move, double damage, Battle battle){
        this.currentBattle = battle;
        this.user = user;
        this.move = move;
        this.damage = damage;
    }
    public BattleContext(Pokemon user, Pokemon target, Move move, double damage, Battle battle){
        this.currentBattle = battle;
        this.user = user;
        this.target = target;
        this.move = move;
        this.damage = damage;
    }
    public BattleContext(Pokemon user, int turnNumber, Battle battle){
        this.currentBattle = battle;
        this.user = user;
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
        //check if the move is not protect 
        if (!move.getName().equals("Protect")) {
            consecutiveTurnsProtect = 0;
        }
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
    public void incrementTurnNumber() {
        turnNumber++;
    }
    public double getMultiplier() {
        return multiplier;
    }
    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
    public Battle getCurrentBattle() { //returns the current battle ()
        return currentBattle;
    }
    public void setCurrentBattle(Battle currentBattle) {
        this.currentBattle = currentBattle;
    }
    public boolean getWasHit() {
        return wasHit;
    }
    public void setWasHit(boolean wasHit) {
        this.wasHit = wasHit;
    }
    public boolean getWasSwapped() {
        return wasSwapped;
    }
    public void setWasSwapped(boolean wasSwapped) {
        this.wasSwapped = wasSwapped;
    }
    public int getConsecutiveProtect() {
        return consecutiveTurnsProtect;
    }
    public void setConsecutiveProtect(int consecutiveTurnsProtect) {
        this.consecutiveTurnsProtect = consecutiveTurnsProtect;
    }

}
