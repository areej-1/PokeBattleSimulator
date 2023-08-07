import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Battle {
	private Trainer trainer1;
	private Trainer trainer2;
	BattleContext bc_trainer1;
	BattleContext bc_trainer2;
	private final String[] options = {"heal", "attack", "swap"};
	public Battle(Trainer t1, Trainer t2) {
		trainer1 = t1;
		trainer2 = t2;
		bc_trainer1 = new BattleContext(trainer1.party()[0], trainer2.party()[0]);
		bc_trainer2 = new BattleContext(trainer2.party()[0], trainer1.party()[0]);

	}

	private int swapCurrentPokemon(Scanner scanner, Trainer t, int curr) { //swapCurrentPokemon method. takes in scanner, trainer, and current pokemon, and swaps the current pokemon to the one the trainer chooses
		if (t.party()[curr].healthVal() == 0) {
	        while (true) {
	            System.out.println("Which Pokemon would you like to send out next?");
	            System.out.println(Arrays.toString(t.notFainted()));
	            String input = scanner.nextLine();
	            int index = 0;
	            for (Pokemon p : t.party()) {
	                if (p.getName().equals(input) && p.healthVal() > 0) {
	                    return index;
	                }
	                index++;
	            }
	            System.out.println("That Pokemon isn't in your party or is already fainted, " + t.getName() + "!");
	        }
	    }
		while (true) {
            System.out.println("Which Pokemon would you like to send out next?");
            ArrayList<String> exc = new ArrayList<>(Arrays.asList(t.notFainted()));
            exc.remove(curr);
            String[] array = new String[exc.size()];
            array = exc.toArray(array);
            System.out.println(Arrays.toString(array));
            
            
            String input = scanner.nextLine();
            int index = 0;
            for (Pokemon p : t.party()) {
                if (p.getName().equals(input) && p.healthVal() > 0) {
                    return index;
                }
                index++;
            }
            System.out.println("That Pokemon isn't in your party or is already fainted, " + t.getName() + "!");
        }
		
	}
	private void heal(String input, int currentP, Trainer t) { //heal method. takes in input, current pokemon, and trainer, and heals the pokemon depending on the item (input)
		switch(input) {
			case "Potion":
				try {
					t.party()[currentP].heal(20);
					t.bagg().removeItem("Potion", 1);
				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			case "Super Potion":
				try {
					t.party()[currentP].heal(50);
					t.bagg().removeItem("Super Potion", 1);
				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			case "Hyper Potion":
				try {
					t.party()[currentP].heal(200);
					t.bagg().removeItem("Hyper Potion", 1);
				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			case "Max Potion":
				try {
					t.party()[currentP].heal(t.party()[currentP].getMaxHP());
					t.bagg().removeItem("Max Potion", 1);
				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			case "Revive":
				try {
					t.party()[currentP].heal(t.party()[currentP].getMaxHP());
					t.bagg().removeItem("Revive", 1);
				} catch(IllegalArgumentException i) {
					System.out.println("You don't have that item!");
				}
				break;
			default:
				throw new IllegalArgumentException("Sorry-that item doesn't exist!");
		}

		System.out.println(t.party()[currentP].getName() + " is now at " + t.party()[currentP].healthVal() + " HP.");
		

		
	}
	private void attack(int p, Trainer curr, int opP, Trainer other, Scanner scanner) { //attack method. takes in the current pokemon, current trainer, opponent pokemon, opponent trainer, and scanner, and attacks the opponent pokemon
		Move targetMove;
		//if curr == trainer1, then bc = bc_trainer1, else bc = bc_trainer2
		BattleContext bc = (curr == trainer1) ? bc_trainer1 : bc_trainer2;
		while (true) {
			System.out.println("What move should " + curr.party()[p].getName() + " use?");
			System.out.println(curr.party()[p].getMoves());
			String n = scanner.nextLine();
			targetMove = Arrays.stream(curr.party()[p].moves())
	                .filter(move -> n.equals(move.getName()))
	                .findFirst()
	                .orElse(null);

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
		//check if the current pokemon has a ability (not null), then use the applyEffect method with the parameters "damage calculation" and the battle context (bc)
		if (curr.party()[p].getAbility() != null) {
			curr.party()[p].getAbility().applyEffect("damage calculation", bc);
		}
		other.party()[opP].doDamage(bc.getDamage(), targetMove); 
	}
	public String battle(Scanner scanner) { 
	    System.out.println("This is a battle between " + trainer1.getName() + " and " + trainer2.getName() + ". Battle begin!");
	    System.out.println(trainer1.getName() + " sent out " + trainer1.party()[0].getName());
	    System.out.println(trainer2.getName() + " sent out " + trainer2.party()[0].getName());
	    int currentP1 = 0, currentP2 = 0;
	    String input;
		
	    boolean initiative = trainer1.party()[currentP1].getSpeed() > trainer2.party()[currentP2].getSpeed(); //
	    boolean trainer1Turn = trainer1.party()[currentP1].getSpeed() == trainer2.party()[currentP2].getSpeed() ? initiative : trainer1.party()[currentP1].getSpeed() > trainer2.party()[currentP2].getSpeed();
	    while (!trainer1.partyFainted() && !trainer2.partyFainted()) {
	        Trainer currentTrainer = trainer1Turn ? trainer1 : trainer2;
	        Trainer otherTrainer = trainer1Turn ? trainer2 : trainer1;
	        int currentP = trainer1Turn ? currentP1 : currentP2;
	        int otherP = trainer1Turn ? currentP2 : currentP1;
			//if current trainer is trainer1, get the ability of trainer1s current pokemon, and use applyEffect method with parameters "stats modification" and bc_trainer1. if its trainer2, get ability of trainer2s current pokemon, then use applyEffect with parameters "stats modification" and bc_trainer2
			/*
			if (currentTrainer == trainer1) {
	        	if (currentTrainer.party()[currentP].getAbility() != null) {
	        		currentTrainer.party()[currentP].getAbility().applyEffect("stats modification", bc_trainer1);
	        	}
	        } else {
	        	if (currentTrainer.party()[currentP].getAbility() != null) {
	        		currentTrainer.party()[currentP].getAbility().applyEffect("stats modification", bc_trainer2);
	        	}
	        }*/ //(commented out until i figure out how to only make it work once, when the pokemon is sent out. not every turn)
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
						currentP2 = swapCurrentPokemon(scanner, otherTrainer, otherP); //swap the current pokemon for trainer2
						bc_trainer1.setTarget(otherTrainer.party()[currentP2]); //set the target pokemon for trainer1's battle context to the pokemon that trainer2 swapped in
						bc_trainer2.setUser(otherTrainer.party()[currentP2]); //set the user pokemon for trainer2's battle context to the pokemon that trainer2 swapped in
					} else { //if its trainer2's turn
						currentP1 = swapCurrentPokemon(scanner, otherTrainer, otherP); //swap the current pokemon for trainer1
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
	        			        	heal("Revive", i, currentTrainer);
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
	        				heal(input, currentP, currentTrainer);
	        				break;
	        			} catch (IllegalArgumentException iae) {
	        				System.out.println("That item doesn't exist.");
	        			}
	            	}
	            }
	        } else if (input.equals("swap")) {
	            if (trainer1Turn) {
	                currentP1 = swapCurrentPokemon(scanner, currentTrainer, currentP);
					bc_trainer1.setUser(currentTrainer.party()[currentP1]); //set the user of the battle context for trainer1 to the new pokemon
					bc_trainer2.setTarget(currentTrainer.party()[currentP1]); //set the target of the battle context for trainer2 to the new pokemon
	            } else {
	                currentP2 = swapCurrentPokemon(scanner, currentTrainer, currentP);
					bc_trainer1.setTarget(currentTrainer.party()[currentP2]); //set the target of the battle context for trainer1 to the new pokemon
					bc_trainer2.setUser(currentTrainer.party()[currentP2]); //set the user of the battle context for trainer2 to the new pokemon
	            }
	        }
	        
	        // Flip the trainer1Turn flag at the end of each turn
	        trainer1Turn = !trainer1Turn;

	        if (trainer1.party()[currentP1].getSpeed() == trainer2.party()[currentP2].getSpeed()) {
	            initiative = !initiative; // Flip the initiative when levels are the same
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