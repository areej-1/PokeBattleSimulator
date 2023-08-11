import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Battle {
	private static Trainer trainer1;
	private static Trainer trainer2;
	private static BattleContext bc_trainer1;
	private static BattleContext bc_trainer2;
	private static Trainer currentTrainer;
	private static int currentP;
	private static boolean trainer1Turn;
	private final String[] options = {"heal", "attack", "swap"};
	public Battle(Trainer t1, Trainer t2) {
		trainer1 = t1;
		trainer2 = t2;
		bc_trainer1 = new BattleContext(trainer1.party()[0], trainer2.party()[0]);
		bc_trainer2 = new BattleContext(trainer2.party()[0], trainer1.party()[0]);

	}
	private String inputHandler(String input, Scanner scanner){
		return Matcher.isMatch(input, scanner);
	}

	private void heal(String input, int currentP, Trainer t, Scanner scanner) { //heal method. takes in input, current pokemon, and trainer, and heals the pokemon depending on the item (input)
		input = inputHandler(input, scanner);
		switch(input) {
			case "Potion": //heals pokemon by 20 HP (as long as it's not fainted)
				try {
					t.bagg().removeItem("Potion", 1);
					t.party()[currentP].heal(20);

				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			case "Super Potion": //heals pokemon by 50 HP (as long as it's not fainted)
				try {
					t.bagg().removeItem("Super Potion", 1);
					t.party()[currentP].heal(50);
				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			case "Hyper Potion": //heals pokemon by 200 HP (as long as it's not fainted)
				try {
					t.bagg().removeItem("Hyper Potion", 1);
					t.party()[currentP].heal(200);
				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			case "Max Potion": //heals pokemon to full health (as long as it's not fainted)
				try {
					t.bagg().removeItem("Max Potion", 1);
					t.party()[currentP].heal(t.party()[currentP].getMaxHP());
				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			case "Revive": //heals pokemon to half health, when fainted
				try {
					t.bagg().removeItem("Revive", 1);
					t.party()[currentP].heal(t.party()[currentP].getMaxHP()/2);
				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			case "Max Revive": //heals pokemon to full health, when fainted
				try {
					t.bagg().removeItem("Max Revive", 1);
					t.party()[currentP].heal(t.party()[currentP].getMaxHP());
				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			case "Full Restore": //heals pokemon to full health and removes status conditions (as long as it's not fainted)
				try {
					t.bagg().removeItem("Full Restore", 1);
					t.party()[currentP].heal(t.party()[currentP].getMaxHP());
				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			default:
				throw new IllegalArgumentException("Sorry-that item doesn't exist!");
		}

		System.out.println(t.party()[currentP].getName() + " is now at " + t.party()[currentP].healthVal() + " HP.");
		

		
	}
	//new implementation of attack method? tbd lol- if added, need to update the battle method, and add any methods that are needed (ie. pokemon class --> attack method)
	/*private void attack(int currentP, Trainer t, Pokemon opponentP, Trainer opponentT, Scanner scanner) {
		System.out.println(t.getName() + " used " + t.party()[currentP].getMove() + "!");
		int damage = t.party()[currentP].attack(opponentP);
		System.out.println(t.party()[currentP].getName() + " dealt " + damage + " damage to " + opponentP.getName() + "!");
		if (opponentP.healthVal() == 0) {
			System.out.println(opponentP.getName() + " fainted!");
			if (opponentT.party().length == 1) {
				System.out.println(opponentT.getName() + " is out of usable Pokemon!");
				System.out.println(t.getName() + " wins!");
				System.exit(0);
			}
			else {
				int nextP = nextPokemon(opponentT, 0, scanner);
				opponentT.party()[nextP].heal(opponentT.party()[nextP].getMaxHP());
				System.out.println(opponentT.getName() + " sent out " + opponentT.party()[nextP].getName() + "!");
				bc_trainer1 = new BattleContext(trainer1.party()[0], trainer2.party()[0]);
				bc_trainer2 = new BattleContext(trainer2.party()[0], trainer1.party()[0]);
			}
		}
	}*/
	private void attack(int p, Trainer curr, int opP, Trainer other, Scanner scanner) { //attack method. takes in the current pokemon, current trainer, opponent pokemon, opponent trainer, and scanner, and attacks the opponent pokemon
		Move targetMove;
		//if curr == trainer1, then bc = bc_trainer1, else bc = bc_trainer2
		BattleContext bc = (curr == trainer1) ? bc_trainer1 : bc_trainer2;
		while (true) {
			System.out.println("What move should " + curr.party()[p].getName() + " use?");
			System.out.println(curr.party()[p].getMoves());
			String n = scanner.nextLine();
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
		System.out.println(curr.party()[p].getName() + " used " + targetMove.getName() + "!");
		double damage = Move.damageToInflict(curr.party()[p], other.party()[opP], targetMove);
		bc.setDamage(damage); //set the damage in the battle context to the damage calculated
		bc.setMove(targetMove); //set the move in the battle context to the move used
		bc.setMultiplier(bc.getTarget().findMultiplier(targetMove)); //set the multiplier in the battle context to the multiplier calculated
		//check if the current pokemon has a ability (not null), then use the applyEffect method with the parameters "damage calculation" and the battle context (bc)
		if (curr.party()[p].getAbility() != null) {
			curr.party()[p].getAbility().applyEffect("damage calculation", bc);
		}
		other.party()[opP].doDamage(bc.getDamage(), targetMove); 
	}
	public static void swapPokemon(Scanner scanner){ //swapping pokemon method to be used in other classes
		int nextP = 0; //placeholder value
		boolean isDone = false;
		while (true) {
			System.out.println("Which Pokemon should " + currentTrainer.getName() + " switch to?");
			System.out.println(Arrays.toString(currentTrainer.party()));
			String n = scanner.nextLine();
			int index = 0;
			for (Pokemon p : currentTrainer.party()) {
				if (p.getName().equals(n) && p.healthVal() > 0) {
					nextP = index;
					isDone = true;
					break;
				}
				index++;
				if (isDone){
					break;
				}
			}
			if (isDone){
				break;
			}
			System.out.println("That Pokemon isn't in your party or is already fainted, " + currentTrainer.getName() + "!");
		}

		System.out.println(currentTrainer.getName() + " sent out " + currentTrainer.party()[nextP].getName() + "!");

		if (trainer1Turn){
			bc_trainer1.setUser(trainer1.party()[nextP]);
			bc_trainer2.setTarget(trainer1.party()[nextP]);
		}
		else {
			bc_trainer2.setUser(trainer2.party()[nextP]);
			bc_trainer1.setTarget(trainer2.party()[nextP]);
		}
		currentP = nextP;
	}
	private int switchPokemon(Trainer t, int currentP, Scanner scanner) { //switchPokemon method. takes in the trainer, current pokemon, and scanner, and switches the current pokemon to another pokemon in the party
		int nextP = nextPokemon(t, currentP, scanner);
		System.out.println(t.getName() + " withdrew " + t.party()[currentP].getName() + "!");
		System.out.println(t.getName() + " sent out " + t.party()[nextP].getName() + "!");
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
	    System.out.println("This is a battle between " + trainer1.getName() + " and " + trainer2.getName() + ". Battle begin!");
	    System.out.println(trainer1.getName() + " sent out " + trainer1.party()[0].getName());
	    System.out.println(trainer2.getName() + " sent out " + trainer2.party()[0].getName());
	    int currentP1 = 0, currentP2 = 0;
	    String input;
		//set the turnNumber in the battle contexts to 0
	    bc_trainer1.setTurnNumber(0);
		bc_trainer2.setTurnNumber(0);
	    boolean initiative = trainer1.party()[currentP1].getSpeed() > trainer2.party()[currentP2].getSpeed(); //
	    trainer1Turn = trainer1.party()[currentP1].getSpeed() == trainer2.party()[currentP2].getSpeed() ? initiative : trainer1.party()[currentP1].getSpeed() > trainer2.party()[currentP2].getSpeed();
	    while (!trainer1.partyFainted() && !trainer2.partyFainted()) {
	        currentTrainer = trainer1Turn ? trainer1 : trainer2;
	        Trainer otherTrainer = trainer1Turn ? trainer2 : trainer1;
	        int currentP = trainer1Turn ? currentP1 : currentP2;
	        int otherP = trainer1Turn ? currentP2 : currentP1;
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
			System.out.println("Trainer 1 current pokemon's turn number: "+ bc_trainer1.getTurnNumber());
			System.out.println("Trainer 2 current pokemon's turn number: "+ bc_trainer2.getTurnNumber());

	        System.out.println("What would you like to do, " + currentTrainer.getName() + "?");
	        System.out.println(Arrays.toString(options));
	        input = scanner.nextLine();
	        if (input.equals("attack")) { 
	            attack(currentP, currentTrainer, otherP, otherTrainer, scanner);
	            if (otherTrainer.partyFainted()) {
	            	System.out.println(otherTrainer.party()[otherP].getName() + " has fainted");
	                break;
	            } else if (otherTrainer.party()[otherP].healthVal() == 0) { //if the opponent pokemon has fainted, ask the opponent which pokemon they want to swap with
	            	System.out.println(otherTrainer.party()[otherP].getName() + " has fainted");
					//check which trainer's turn it is, then set the user pokemon (for the opposing/other trainer's battle context) and target pokemon (for the current trainer's battle context) in the battle context accordingly, and swap the current pokemon for the trainer whose turn it is
					
					if (trainer1Turn) { //if its trainer1's turn
						currentP2 = switchPokemon(otherTrainer, otherP, scanner); //swap the current pokemon for trainer2
						bc_trainer2.setTurnNumber(0); //reset the turn number for trainer2's battle context (since the pokemon is switched)
						bc_trainer1.setTarget(otherTrainer.party()[currentP2]); //set the target pokemon for trainer1's battle context to the pokemon that trainer2 swapped in
						bc_trainer2.setUser(otherTrainer.party()[currentP2]); //set the user pokemon for trainer2's battle context to the pokemon that trainer2 swapped in
					} else { //if its trainer2's turn
						currentP1 = switchPokemon(otherTrainer, otherP, scanner); //swap the current pokemon for trainer1
						bc_trainer1.setTurnNumber(0); //reset the turn number for trainer1's battle context (since the pokemon is switched)
						bc_trainer2.setTarget(otherTrainer.party()[currentP1]); //set the target pokemon for trainer2's battle context to the pokemon that trainer1 swapped in
						bc_trainer1.setUser(otherTrainer.party()[currentP1]); //set the user pokemon for trainer1's battle context to the pokemon that trainer1 swapped in
					}	
	            }
				else { //if the pokemon has not fainted, print out the pokemon's health
	            	System.out.println(otherTrainer.party()[otherP].getName() + " has " + otherTrainer.party()[otherP].healthVal() + " HP remaining");
				}	
	        } else if (input.equals("heal")) { //if the user inputs heal, ask them which item they want to use
	            System.out.println("Which item would you like to use?\n" + currentTrainer.items());		
	            input = scanner.nextLine();
	            if (input.equals("Revive")) {
	        		System.out.println("Which Pokemon would you like to revive?\n" + Arrays.toString(currentTrainer.fainted()));
	        		input = scanner.nextLine();
	        		for (int i = 0; i < currentTrainer.party().length; i++) {
	        			    // Checking if the Pokémon's name matches the input and if it has fainted.
	        			    if (currentTrainer.party()[i].getName().equals(input) && currentTrainer.party()[i].healthVal() == 0) {
	        			        // Now also checking if the Trainer has a Revive in their inventory.
	        			        if (currentTrainer.bagg().contains("Revive")) {
	        			            // If all conditions are met, then we can use a Revive.
	        			        	System.out.println("Revive was used on " + input + "!");
	        			        	heal("Revive", i, currentTrainer, scanner);
	        			        } else {
	        			            System.out.println("You have no Revives in your inventory.");
	        			        }
	        			    } else if (currentTrainer.party()[i].getName().equals(input) && currentTrainer.party()[i].healthVal() != 0) {
	        			        System.out.println("You cannot use a Revive on a Pokemon that hasn't fainted.");
	        			    }
	        			}
	        		}

	            else {
	            	while (true) {
	            		try {
	        				heal(input, currentP, currentTrainer, scanner);
	        				break;
	        			} catch (IllegalArgumentException iae) {
	        				System.out.println("That item doesn't exist.");
	        			}
	            	}
	            }
	        } else if (input.equals("swap")) {
	            if (trainer1Turn) {
	                currentP1 = switchPokemon(currentTrainer, currentP, scanner);
					bc_trainer1.setTurnNumber(0); //reset the turn number for trainer1's battle context (since the pokemon is switched)
					bc_trainer1.setUser(currentTrainer.party()[currentP1]); //set the user of the battle context for trainer1 to the new pokemon
					bc_trainer2.setTarget(currentTrainer.party()[currentP1]); //set the target of the battle context for trainer2 to the new pokemon
	            } else {
	                currentP2 = switchPokemon(currentTrainer, currentP, scanner); 
					bc_trainer2.setTurnNumber(0); //reset the turn number for trainer2's battle context (since the pokemon is switched)
					bc_trainer1.setTarget(currentTrainer.party()[currentP2]); //set the target of the battle context for trainer1 to the new pokemon
					bc_trainer2.setUser(currentTrainer.party()[currentP2]); //set the user of the battle context for trainer2 to the new pokemon
	            }
	        }
	        
	        // Flip the trainer1Turn flag at the end of each turn
	        trainer1Turn = !trainer1Turn;

	        if (trainer1.party()[currentP1].getSpeed() == trainer2.party()[currentP2].getSpeed()) {
	            initiative = !initiative; // Flip the initiative when levels are the same
	        }
			//check if the pokemon was swapped (if the turn number is 0), and if it was, then leave the number as is. otherwise, increment by 1 (since it is the next turn)
			if (bc_trainer1.getTurnNumber() != 0) {
				bc_trainer1.incrementTurnNumber();
			}
			if (bc_trainer2.getTurnNumber() != 0) {
				bc_trainer2.incrementTurnNumber();
			}

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
