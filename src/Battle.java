import java.util.Arrays;
import java.util.Scanner;
public class Battle {
	private static Trainer trainer1;
	private static Trainer trainer2;
	private static BattleContext bc_trainer1;
	private static BattleContext bc_trainer2;
	private static Trainer currentTrainer;
	private int currentP;
	private int otherP;
	private static int currentP1;
	private static int currentP2;
	private static boolean trainer1Turn;
	private double turnSkipMoveDamage_trainer1;
	private double turnSkipMoveDamage_trainer2; //stores the damage of any moves that skip a turn (meaning they skip a turn, then do damage the next turn). ie. fly, dig, dive, etc.
	private final String[] options = {"heal", "attack", "swap"};
	public Battle(Trainer t1, Trainer t2) {
		trainer1 = t1;
		trainer2 = t2;
		bc_trainer1 = new BattleContext(trainer1.party()[0], trainer2.party()[0], this);
		bc_trainer2 = new BattleContext(trainer2.party()[0], trainer1.party()[0], this);

	}
	//method to select a pokemon, and returns the index of the pokemon in the party. to be used for healing and reviving. takes in user input for the name of a pokemon, and returns the index of the pokemon in the party
	private int selectPokemon(Trainer t, Scanner scanner){
		System.out.println("Which pokemon would you like to use it on?");
		String input = scanner.nextLine();
		input = inputHandler(input, scanner);
		while (true){
			if (input.equals("invalid")) {
				System.out.println("Invalid input! Please try again.");
				input = scanner.nextLine();
				input = inputHandler(input, scanner);
			} else {
				break;
			}
		}
		for (int i = 0; i < t.party().length; i++){
			if (t.party()[i].getName().equals(input)){
				return i;
			}
		}
		System.out.println("You don't have a Pokemon with that name! Please try again.");
		return selectPokemon(t, scanner);
	}
	private String inputHandler(String input, Scanner scanner){
		return Matcher.isMatch(input, scanner);
	}

	private void heal(String input, Trainer t, Scanner scanner) { //heal method. takes in input, current pokemon, and trainer, and heals the pokemon depending on the item (input)
		input = inputHandler(input, scanner);
		int currentP;
		while (true){
			if (input.equals("invalid")) {
				System.out.println("Invalid input! Please try again.");
				input = scanner.nextLine();
				input = inputHandler(input, scanner);
			} else {
				break;
			}
		}

		switch(input){
			//compare input with names of items to heal with. first make sure that the item is in the bag, then heal the pokemon
			case "Potion":
				if (t.bagg().contains("Potion")){
					currentP = selectPokemon(t, scanner);
					System.out.println(t.getName() + " used a Potion.");
					if (t.party()[currentP].healthVal() != 0 && t.party()[currentP].healthVal()!= t.party()[currentP].getMaxHP()){
						System.out.println(t.party()[currentP].getName() + "'s HP has been restored.");
					}
					t.party()[currentP].heal(20);
					t.bagg().removeItem("Potion", 1);
				} else {
					System.out.println("You don't have any potions! Please try again.");
					heal(scanner.nextLine(), t, scanner);
				}
				break;
			case "Super Potion":
				currentP = selectPokemon(t, scanner);
				if (t.bagg().contains("Super Potion")){
					System.out.println(t.getName() + " used a Super Potion.");
					if (t.party()[currentP].healthVal() != 0 && t.party()[currentP].healthVal()!= t.party()[currentP].getMaxHP()){
						System.out.println(t.party()[currentP].getName() + "'s HP has been restored.");
					}
					t.party()[currentP].heal(50);
					t.bagg().removeItem("Super Potion", 1);
				} else {
					System.out.println("You don't have any super potions! Please try again.");
					heal(scanner.nextLine(), t, scanner);
				}
				break;
			case "Hyper Potion":
			
				if (t.bagg().contains("Hyper Potion")){
					currentP = selectPokemon(t, scanner);
					System.out.println(t.getName() + " used a Hyper Potion.");
					if (t.party()[currentP].healthVal() != 0 && t.party()[currentP].healthVal()!= t.party()[currentP].getMaxHP()){
						System.out.println(t.party()[currentP].getName() + "'s HP has been restored.");
					}
					t.party()[currentP].heal(200);
					t.bagg().removeItem("Hyper Potion", 1);
				} else {
					System.out.println("You don't have any hyper potions! Please try again.");
					heal(scanner.nextLine(), t, scanner);
				}
				break;
			case "Max Potion":
				if (t.bagg().contains("Max Potion")){
					currentP = selectPokemon(t, scanner);
					System.out.println(t.getName() + " used a Max Potion.");
					if (t.party()[currentP].healthVal() != 0 && t.party()[currentP].healthVal()!= t.party()[currentP].getMaxHP()){
						System.out.println(t.party()[currentP].getName() + "'s HP has been restored.");
					}
					t.party()[currentP].heal(999);
					t.bagg().removeItem("Max Potion", 1);
				} else {
					System.out.println("You don't have any max potions! Please try again.");
					heal(scanner.nextLine(), t, scanner);
				}
				break;
			case "Full Restore":
				if (t.bagg().contains("Full Restore")){
					currentP = selectPokemon(t, scanner);
					System.out.println(t.getName() + " used a Full Restore.");
					if (t.party()[currentP].healthVal() != 0 && t.party()[currentP].healthVal()!= t.party()[currentP].getMaxHP()){
						System.out.println(t.party()[currentP].getName() + "'s HP has been restored.");
					}
					t.party()[currentP].heal(999);
					t.bagg().removeItem("Full Restore", 1);
					//check if the pokemon has any status conditions. if so, remove them
					if (t.party()[currentP].getStatusCondition() != null){
						if (t.party()[currentP].getStatusCondition().isBurned()){
							t.party()[currentP].setStatus(null);
							System.out.println(t.party()[currentP].getName() + " was cured of its burn.");
						}
						if (t.party()[currentP].getStatusCondition().isPoisoned()){
							t.party()[currentP].setStatus(null);
							System.out.println(t.party()[currentP].getName() + " was cured of its poison.");
						}
						if (t.party()[currentP].getStatusCondition().isParalyzed()){
							t.party()[currentP].setStatus(null);
							System.out.println(t.party()[currentP].getName() + " was cured of its paralysis.");
						}
						if (t.party()[currentP].getStatusCondition().isAsleep()){
							t.party()[currentP].setStatus(null);
							System.out.println(t.party()[currentP].getName() + " woke up!");
						}
						if (t.party()[currentP].getStatusCondition().isFrozen()){
							t.party()[currentP].setStatus(null);
							System.out.println(t.party()[currentP].getName() + " was cured of its freeze.");
						}
					}
				} else {
					System.out.println("You don't have any full restores! Please try again.");
					heal(scanner.nextLine(), t, scanner);
				}
				break;
			case "Revive":
				if (t.bagg().contains("Revive")){
					currentP = selectPokemon(t, scanner);
					System.out.println(t.getName() + " used a Revive.");
					if (t.party()[currentP].healthVal() == 0){
						System.out.println(t.party()[currentP].getName() + " was revived!");
					}
					t.party()[currentP].revive("Revive");
					t.bagg().removeItem("Revive", 1);
				} else {
					System.out.println("You don't have any revives! Please try again.");
					heal(scanner.nextLine(), t, scanner);
				}
				break;
			case "Max Revive":
				if (t.bagg().contains("Max Revive")){
					currentP = selectPokemon(t, scanner);
					System.out.println(t.getName() + " used a Max Revive.");
					if (t.party()[currentP].healthVal() == 0){
						System.out.println(t.party()[currentP].getName() + " was revived!");
					}
					t.party()[currentP].revive("Max Revive");
					t.bagg().removeItem("Max Revive", 1);
				} else {
					System.out.println("You don't have any max revives! Please try again.");
					heal(scanner.nextLine(), t, scanner);
				}
				break;
			case "Full Heal":
				if (t.bagg().contains("Full Heal")){
					currentP = selectPokemon(t, scanner);
					//check if the pokemon has any status conditions. if so, remove them. if not, just print that the item had no effect
					if (t.party()[currentP].getStatusCondition() != null){
						System.out.println(t.getName() + " used a Full Heal.");
						if (t.party()[currentP].getStatusCondition().isBurned()){
							t.party()[currentP].setStatus(null);
							System.out.println(t.party()[currentP].getName() + " was cured of its burn.");
						}
						if (t.party()[currentP].getStatusCondition().isPoisoned()){
							t.party()[currentP].setStatus(null);
							System.out.println(t.party()[currentP].getName() + " was cured of its poison.");
						}
						if (t.party()[currentP].getStatusCondition().isParalyzed()){
							t.party()[currentP].setStatus(null);
							System.out.println(t.party()[currentP].getName() + " was cured of its paralysis.");
						}
						if (t.party()[currentP].getStatusCondition().isAsleep()){
							t.party()[currentP].setStatus(null);
							System.out.println(t.party()[currentP].getName() + " woke up!");
						}
						if (t.party()[currentP].getStatusCondition().isFrozen()){
							t.party()[currentP].setStatus(null);
							System.out.println(t.party()[currentP].getName() + " was cured of its freeze.");
						}
						System.out.println(t.party()[currentP].getName() + " was cured of its status condition."); //in the future, replace 'status condition' with the actual status condition's name. its better
						} else {
						System.out.println(t.getName() + " used a Full Heal.");
						System.out.println("But it had no effect!");
					}
				} else {
					System.out.println("You don't have any full heals! Please try again.");
					heal(scanner.nextLine(), t, scanner);
				}
				break;
		}
		
	}

	//helper method to choose a move. takes in the current pokemon, current trainer, opponent pokemon, opponent trainer, and scanner, and helps the user choose a move for the current pokemon to use
	private Move chooseMove(int p, Trainer curr, int opP, Trainer other, Scanner scanner){
		Move targetMove;
		//if curr == trainer1, then bc = bc_trainer1, else bc = bc_trainer2
		while (true) {
			System.out.println("What move should " + curr.party()[p].getName() + " use?");
			System.out.println(curr.party()[p].getMoves());
			String n = scanner.nextLine();
			System.out.println(inputHandler(n, scanner));
			if (!inputHandler(n, scanner).contains("invalid")){
				targetMove = Arrays.stream(curr.party()[p].moves())
	                .filter(move -> (inputHandler(n, scanner)).equals(move.getName()))
	                .findFirst()
	                .orElse(null);
			} else {
				System.out.println(curr.party()[p].getName() + " doesn't have that move! Please try again.");
				continue;
			}

	        if (targetMove != null) {
	            break;
	        } else {
	        	System.out.println(curr.party()[p].getName() + " doesn't have that move! Please try again.");
	        }

		}
		//set the move of the respective battle context to the targetMove

		if (curr == trainer1) {
			bc_trainer1.setMove(targetMove);
		} else {
			bc_trainer2.setMove(targetMove);
		}
		return targetMove;
	}

	private void attack(Move targetMove1, Move targetMove2) { //attack method. 
		//compare the priorities of the moves (targetMove1, targetMove2), then use useMove accordingly
		if (targetMove1.getPriority() > targetMove2.getPriority()){
			useMove(trainer1, trainer2, targetMove1, bc_trainer1);
			
			//then use the other move
			useMove(trainer2, trainer1, targetMove2, bc_trainer2);
		}
		else if (targetMove1.getPriority() < targetMove2.getPriority()){
			useMove(trainer2, trainer1, targetMove2, bc_trainer1);
			//then use the other move
			useMove(trainer1, trainer2, targetMove1, bc_trainer2);
		}

		//otherwise, the priorities are equal, so check the speed of the pokemon
		else {
			if (trainer1.party()[currentP1].getSpeed() > trainer2.party()[currentP2].getSpeed()){
				useMove(trainer1, trainer2, targetMove1, bc_trainer1);
				//then use the other move
				useMove(trainer2, trainer1, targetMove2, bc_trainer2);
			}
			else if (trainer1.party()[currentP1].getSpeed() < trainer2.party()[currentP2].getSpeed()){
				useMove(trainer2, trainer1, targetMove2, bc_trainer1);
				//then use the other move
				useMove(trainer1, trainer2, targetMove1, bc_trainer2);
			}
			//otherwise, the speeds are equal, so choose a random pokemon to go first
			else {
				if (Math.random() < 0.5){
					useMove(trainer1, trainer2, targetMove1, bc_trainer1);
					//then use the other move
					useMove(trainer2, trainer1, targetMove2, bc_trainer2);
				}
				else {
					useMove(trainer2, trainer1, targetMove2, bc_trainer1);
					//then use the other move
					useMove(trainer1, trainer2, targetMove1, bc_trainer2);
				}
			}
		}
		
	}
	private void useMove(Trainer curr, Trainer other, Move targetMove, BattleContext bc){
		System.out.println(curr.party()[currentP].getName() + " used " + targetMove.getName());

		targetMove.inflictStatus(curr.party()[currentP], other.party()[otherP], bc); //apply the move's effect to the opponent pokemon
		
		//check if current pokemon is skipping turn.
		if (curr.party()[currentP].getSkipTurn() == true){
			System.out.println(targetMove.successMessage()); //print the success message of the move (ie. "pokemon-name flew up high! (fly)")
			//check if the current trainer is trainer1 or trainer2, then set the turnSkipMoveDamage accordingly, based on values returned by damageToInflict method
			if (curr == trainer1){
				turnSkipMoveDamage_trainer1 = targetMove.damageToInflict(curr.party()[currentP], other.party()[otherP]);
			} else {
				turnSkipMoveDamage_trainer2 = targetMove.damageToInflict(curr.party()[currentP], other.party()[otherP]);
			}
			return;
		}
		double damage = targetMove.damageToInflict(curr.party()[currentP], other.party()[otherP]);
		if (damage == -1.0){
			System.out.println(targetMove.failMessage());
			return;
		}
		else {
			System.out.println(targetMove.successMessage());
		}
		//check if the current pokemon has a ability (not null), then use the applyEffect method with the parameters "damage calculation" and the battle context (bc)
		if (curr.party()[currentP].getAbility() != null) {
			curr.party()[currentP].getAbility().applyEffect("damage calculation", bc);
		}
		if (targetMove.getDamage()!=0){
			other.party()[otherP].doDamage(damage, targetMove); //doDamage takes in the damage and the move used, and applies the damage to the opposing pokemon
		}
		//set wasHit of the opposing trainer's battle context to true
		if (curr == trainer1) {
			bc_trainer2.setWasHit(true);
		} else {
			bc_trainer1.setWasHit(true);
		}

	
	}
	public void swapPokemon(Scanner scanner){ //swapping pokemon method to be used in other classes
		if (trainer1Turn) {
			System.out.println(currentTrainer.party()[currentP]);
			currentP1 = switchPokemon(currentTrainer, currentP, scanner);
			System.out.println(currentTrainer.party()[currentP]);
		} else {
			currentP2 = switchPokemon(currentTrainer, currentP, scanner); 
		}
	}
	private int switchPokemon(Trainer t, int currentP, Scanner scanner) { //switchPokemon method. takes in the trainer, current pokemon, and scanner, and switches the current pokemon to another pokemon in the party
		int nextP = nextPokemon(t, currentP, scanner);
		System.out.println(t.getName() + " withdrew " + t.party()[currentP].getName() + "!");
		System.out.println(t.getName() + " sent out " + t.party()[nextP].getName() + "!");
		//check whether t is trainer1 or trainer2, then set the turn number for the respective battle context to 0, as well as set the target/user pokemon in the battle context accordingly
		if (t == trainer1) {
			bc_trainer1.setTurnNumber(0);
			bc_trainer2.setTarget(t.party()[nextP]);
			bc_trainer1.setUser(t.party()[nextP]);
			bc_trainer1.setWasSwapped(true);
		} else {
			bc_trainer2.setTurnNumber(0);
			bc_trainer1.setTarget(t.party()[nextP]);
			bc_trainer2.setUser(t.party()[nextP]);
			bc_trainer2.setWasSwapped(true);

		}
		this.currentP = nextP;
		return nextP;
	}
	private int nextPokemon(Trainer t, int currentP, Scanner scanner) { //nextPokemon method. takes in the trainer, current pokemon, and scanner, and returns the next pokemon in the party
		while (true) {
			System.out.println("Which Pokemon should " + t.getName() + " switch to?");
			System.out.println(Arrays.toString(t.party()));
			String n = scanner.nextLine();
			n = inputHandler(n, scanner);
			int index = 0;
			for (Pokemon p : t.party()) {
				if (p.getName().equals(n) && p.healthVal() > 0) {
					return index;
				}
				index++;
			}
			System.out.println("That Pokemon isn't in your party or is already fainted, " + t.getName() + "!");
		}
	}
	public String battle(Scanner scanner) { 
		int counterForTurns = 0; //used to count the amount of turns that were in the battle 
	    System.out.println("This is a battle between " + trainer1.getName() + " and " + trainer2.getName() + ". Battle begin!");
	    System.out.println(trainer1.getName() + " sent out " + trainer1.party()[0].getName());
	    System.out.println(trainer2.getName() + " sent out " + trainer2.party()[0].getName());
	    currentP1 = 0;
		currentP2 = 0;
	    String input;
		//set the turnNumber in the battle contexts to 0
	    bc_trainer1.setTurnNumber(0);
		bc_trainer2.setTurnNumber(0);
	    boolean initiative = trainer1.party()[currentP1].getSpeed() > trainer2.party()[currentP2].getSpeed(); //
	    trainer1Turn = trainer1.party()[currentP1].getSpeed() == trainer2.party()[currentP2].getSpeed() ? initiative : trainer1.party()[currentP1].getSpeed() > trainer2.party()[currentP2].getSpeed();
	    Move trainer1_currMove = null;
		Move trainer2_currMove = null;
		while (!trainer1.partyFainted() && !trainer2.partyFainted()) {
	        currentTrainer = trainer1Turn ? trainer1 : trainer2;
	        Trainer otherTrainer = trainer1Turn ? trainer2 : trainer1;
	        currentP = trainer1Turn ? currentP1 : currentP2;
			otherP = trainer1Turn ? currentP2 : currentP1;
			//check if the current trainers current pokemon is skipping a turn; if so, skip
			if (currentTrainer.party()[currentP].getSkipTurn() == true){
				currentTrainer.party()[currentP].setSkipTurn(false);
				trainer1Turn = !trainer1Turn;
				continue;
			}
			//check which trainer's turn it is, then check if the turnNumber = 0, then use the applyEffect method with the parameters "stats modification" and the battle context (bc)
			if (currentTrainer.party()[currentP].getAbility() != null){
				if (trainer1Turn) {
	        		if (bc_trainer1.getTurnNumber() == 0) { //if the turn number is 0, then apply the effect (first turn effect)
	        			trainer1.party()[currentP1].getAbility().applyEffect("stats modification", bc_trainer1);
	        		}
	        	} else {
	        		if (bc_trainer2.getTurnNumber() == 0) { //if the turn number is 0, then apply the effect (first turn effect)
	        			trainer2.party()[currentP2].getAbility().applyEffect("stats modification", bc_trainer2);
	        		}
	     		}
			}
			

			//check which trainer it is, then check if the respective turnskipmovedamage is not 0, then apply the damage to the opposing pokemon
			if (trainer1Turn) {
				if (turnSkipMoveDamage_trainer1 != 0){
					System.out.println(trainer1.party()[currentP1].getName() + " used " + bc_trainer1.getMove()); 
					trainer2.party()[currentP2].doDamage(turnSkipMoveDamage_trainer1, bc_trainer1.getMove());
					turnSkipMoveDamage_trainer1 = 0;
					//print the hp of the opposing pokemon
					System.out.println(trainer2.party()[currentP2].getName() + " has " + trainer2.party()[currentP2].healthVal() + " HP remaining");
				}
			} else {
				if (turnSkipMoveDamage_trainer2 != 0){
					System.out.println(trainer2.party()[currentP2].getName() + " used " + bc_trainer2.getMove());
					trainer1.party()[currentP1].doDamage(turnSkipMoveDamage_trainer2, bc_trainer2.getMove());
					turnSkipMoveDamage_trainer2 = 0;
					//print the hp of the opposing pokemon
					System.out.println(trainer1.party()[currentP1].getName() + " has " + trainer1.party()[currentP1].healthVal() + " HP remaining");
				}
			}
			if (trainer1Turn) {
				if (bc_trainer1.getUser().getStatusCondition() != null) {
					bc_trainer1.getUser().getStatusCondition().applyEffect(currentTrainer.party()[0]);
				}
			} else {
				if (bc_trainer2.getUser().getStatusCondition() != null) {
					bc_trainer2.getUser().getStatusCondition().applyEffect(currentTrainer.party()[0]);
				}
			}
	        System.out.println("What would you like to do, " + currentTrainer.getName() + "?");
	        System.out.println(Arrays.toString(options));
	        input = scanner.nextLine();
	        if (input.equals("attack")) {
				//check if the current pokemon is able to move (i.e. if the current pokemon is not paralyzed, didn't flinch, etc.), then call the attack method with the parameters current pokemon, current trainer, other pokemon, other trainer, and scanner
				if (currentTrainer.party()[currentP].getCanMove()){
					if(trainer1Turn) {
						//set value of trainer1_currMove to the move used by the current pokemon
						trainer1_currMove = chooseMove(currentP1, trainer1, currentP2, trainer2, scanner);
					} else {
						//set value of trainer2_currMove to the move used by the current pokemon
						trainer2_currMove = chooseMove(currentP2, trainer2, currentP1, trainer1, scanner);
					}	
				}
				else {
					System.out.println(currentTrainer.party()[currentP].getName() + " is unable to move!");
				}
	        } else if (input.equals("heal")) { //if the user inputs heal, ask them which item they want to use
	            System.out.println("Which item would you like to use?\n" + currentTrainer.items());		
	            input = scanner.nextLine();
				heal(input, currentTrainer, scanner);
	        } else if (input.equals("swap")) {
	            if (trainer1Turn) {
	                currentP1 = switchPokemon(currentTrainer, currentP, scanner);
		 		} else {
	                currentP2 = switchPokemon(currentTrainer, currentP, scanner); 
				}
	        }
	        // Flip the trainer1Turn flag at the end of each turn
	        trainer1Turn = !trainer1Turn;
			counterForTurns++; //increment the counter for turns

	        if (trainer1.party()[currentP1].getSpeed() == trainer2.party()[currentP2].getSpeed()) {
	            initiative = !initiative; // Flip the initiative when levels are the same
	        }

			//System.out.println("Trainer 1 current pokemon's can recieve damage: "+ trainer1.party()[currentP1].getCanRecieveDamage());
			//System.out.println("Trainer 2 current pokemon's can recieve damage: "+ trainer2.party()[currentP2].getCanRecieveDamage());
			//check if the pokemon was swapped (if the turn number is 0), and if it was, then leave the number as is. otherwise, increment by 1 (since it is the next turn)
			if (counterForTurns%2 == 0) {
				attack(trainer1_currMove, trainer2_currMove);
				//check if either trainers current pokemon fainted. if so, print faint message, then swap pokemon
				if (trainer1.party()[currentP1].healthVal() == 0){
					System.out.println(trainer1.party()[currentP1].getName() + " fainted!");
					currentP1 = switchPokemon(trainer1, currentP1, scanner);
				} else {
					//print the current health of the pokemon
					System.out.println(trainer1.party()[currentP1].getName() + " has " + trainer1.party()[currentP1].healthVal() + " HP remaining");
				}
				if (trainer2.party()[currentP2].healthVal() == 0){
					System.out.println(trainer2.party()[currentP2].getName() + " fainted!");
					currentP2 = switchPokemon(trainer2, currentP2, scanner);
				} else {
					//print the current health of the pokemon
					System.out.println(trainer2.party()[currentP2].getName() + " has " + trainer2.party()[currentP2].healthVal() + " HP remaining");
				}

				if (!bc_trainer1.getWasSwapped()){
					bc_trainer1.incrementTurnNumber();
				}
				if (!bc_trainer2.getWasSwapped()){
					bc_trainer2.incrementTurnNumber();
				}
				//reset values (wasHit, wasSwapped) in the battle contexts
				bc_trainer1.setWasHit(false);
				bc_trainer1.setWasSwapped(false);
				bc_trainer2.setWasHit(false);
				bc_trainer2.setWasSwapped(false);
				//reset the trainer1_currMove and trainer2_currMove to null
				trainer1_currMove = null;
				trainer2_currMove = null;

				//make sure that either pokemon is not skipping a turn. if they're not skipping, then use the endTurnReset method to reset the pokemon's stats
				if (!trainer1.party()[currentP1].getSkipTurn()){
					trainer1.party()[currentP1].endTurnReset();
				}
				if (!trainer2.party()[currentP2].getSkipTurn()){
					trainer2.party()[currentP2].endTurnReset();
				}

			}
			//System.out.println("Trainer 1 current pokemon's can recieve damage: "+ trainer1.party()[currentP1].getCanRecieveDamage());
			//System.out.println("Trainer 2 current pokemon's can recieve damage: "+ trainer2.party()[currentP2].getCanRecieveDamage());
			


	    } 
		// Return the winner of the battle
	    if (trainer1.partyFainted()) { 
	        return trainer2.getName() + " is the victor!";
	    } else if (trainer2.partyFainted()) {
	        return trainer1.getName() + " is the victor!";
	    } else {
	        return "This battle has ended in a draw.";
	    }
	}



}
