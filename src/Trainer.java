//import java.util.Arrays;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.text.NumberFormat;
//import java.util.Locale;

public class Trainer {
	private Pokemon[] party = new Pokemon[6];
	private String name;
	private Pokedex pokedex = Pokedex.getPokedex();
	private int numBadges;
	private final PartnerPokemon partner;
	private String trainer_id;
	private int pokedollars;
	private bag myBag = new bag();
	
	public Trainer(String n, int badges, PartnerPokemon p, String nickname, String ID, int money) {
		name = n;
		numBadges = badges;
		partner = p;
		partner.setNickname(nickname);
		party[0] = partner;
		trainer_id = ID;
		setMoney(money);
	}
	private String format(int n) {
		NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        return numberFormat.format(n);
	}
	public String toString() {
		return "Trainer: " + name + "\nID: " + trainer_id + "\n円: " + format(pokedollars) + "\n\nTrainer " + name + " has " + numBadges + " badges. " + name + "'s partner Pokemon is " + partner.getNickname() + "(" + partner.getName() + "). " + name + "'s entire team is " + getPartyPokemon() + ".";
	}
	public void setParty(Pokemon... poke) {
		if (poke.length > 5) {
			throw new ArrayIndexOutOfBoundsException("Cannot have more than 6 Pokemon in your party at time, " + name + "!");
		}
		else {
			int index = 1;
			for (Pokemon p : poke) {
				party[index] = p;
				index++;
			}
		}
	}
	public String setBadge(int n) {
		if (n > 8 || n < 0) {
			return numberOfBadges(n);
		}
		numBadges = n;
		return numberOfBadges(numBadges);
		
		
	}
	public Pokemon getPartner() {
		return partner;
	}
	private String numberOfBadges(int n) {
		if (n > 8 || n < 0) {
			throw new ArithmeticException("Invalid amount of badges.");
		}
		if (n == 8) {
			return "Congratulations! You can now challenge the Elite Four and the Champion!";
		}
		
		return "You still have " + (8-n) + " badges to go until you can compete in the Pokemon League.";
		
	}
	public int getBadges() {
		return numBadges;
	}
	public String myPokedex() {
		return pokedex.PokedexEntries();
	}
	public String getPartyPokemon() {
	    StringBuilder result = new StringBuilder();

	    // Iterate through the array
	    for (int i = 0; i < party.length; i++) {
	        // If the value is not null
	        if (party[i] != null) {
	            // Check if the Pokemon is a PartnerPokemon
	            if (party[i] instanceof PartnerPokemon) {
	                // Append the nickname to the StringBuilder
	                result.append(((PartnerPokemon) party[i]).getNickname());
	            } else {
	                // Append the Pokemon's name to the StringBuilder
	                result.append(party[i].getName());
	            }
	            // Check if it's not the last non-null value
	            if (i < party.length - 1 && party[i + 1] != null) {
	                result.append(", ");
	            }
	        }
	    }

	    // Print the result
	    return result.toString();
	}
	public void swapPokemon(Pokemon p, String name) {
		for (int i = 0; i < party.length; i++) {
			if (party[i].getName().equals(name)) {
				party[i] = p;
				break;
			}
		}
	}
	public int getMoney() {
		return pokedollars;
	}
	public void setMoney(int pokedollars) {
		this.pokedollars = pokedollars;
	}
	public void addMoney(int pokedollars) {
		this.pokedollars += pokedollars;
	}
	public void withdraw(int pokedollars) {
		this.pokedollars-= pokedollars;
	}
	public String partnerInfo() {
		return partner.toString();
	}
	public String getName() {
		return name;
	}
	public String getID() {
		return trainer_id;
	}
	public Pokemon[] party() {
		return party;
	}
	public boolean partyFainted() {
		int i = 0;
		for (Pokemon p : party) {
			if (p.healthVal() == 0) {
				i++;
			}
		}
		return i == 6;
	}
	public String[] fainted() {
		int i = 0;
		for (Pokemon p : party) {
			if (p.healthVal()==0) {
				i++;
			}
		}
		String[] pp = new String[i];
		i = 0;
		for (Pokemon p : party) {
			if (p.healthVal() == 0) {
				pp[i] = p.getName();
				i++;
			}
		}
		
		return pp;
	}
	public String[] notFainted() {
		int i = 0;
		for (Pokemon p : party) {
			if (p.healthVal()!=0) {
				i++;
			}
		}
		String[] pp = new String[i];
		i = 0;
		for (Pokemon p : party) {
			if (p.healthVal() != 0) {
				pp[i] = p.getName();
				i++;
			}
		}
		
		return pp;
	}
	public ArrayList<String> items(){
		return myBag.insideBag();
	}
	public bag bagg() {
		return myBag;
	}
	public void setBag(String ... n) {
		for (String o : n) {
			if (Collections.frequency(items(), o)==0) {
				myBag.addItem(o, Collections.frequency(Arrays.asList(n), o));
			}
		}
	}
	public String[] p(int current) {
		String[] s = notFainted();
		String[] y = new String[s.length-1];
		int index = 0, index1 = 0;
		for (String str : s) {
			if (index!=current) {
				y[index1] = str;
				index1++;
			}
			index++;
		}
		return y;
		
	}
}

class GymLeader extends Trainer{
	private String speciality;
	private String gymLocation;
	private String badgeName;
	private static int gymID = 0;
	public GymLeader(String n, PartnerPokemon p, String nickname, String special, String location, int money, String badge) {
		super(n, 0, p, nickname, Integer.toString(gymID), money);
		gymID++;
		speciality = special;
		gymLocation = location;
		badgeName = badge;
	}
	public String setBadge(int n) {
		throw new UnsupportedOperationException("A Gym Leader can't have badges!");
	}
	public String toString() {
		return this.getName() + " is a specialist in " + speciality + "-type Pokemon and is the Gym Leader at the " + gymLocation + " Gym. Challengers, after winning, may obtain the " + badgeName + " Badge as a symbol of their victory.";
	}
}

class Champion extends Trainer{
	private String region;
	private boolean gender;
	public Champion(String n, PartnerPokemon p, String nickname, String ID, int money, String re) {
		super(n, 8, p, nickname, ID, money);
		region = re;
		gender = gender();
	}
	private boolean gender() {
		String n = getName().replaceAll(" ", "_");
		if (WebScraper.getGender(n).equals("Male")) {
			return false;
		}
		return true;
	}
	@Override
	public String setBadge(int n) {
		throw new UnsupportedOperationException("You're already the Champion of "+region+"!");
	}
	@Override
	public String toString() {
		if (gender) {
			return getName() + " is the Pokemon League Champion of " + region +". Her partner Pokemon is " + getPartner().getName()+".";
		}
		return getName() + " is the Pokemon League Champion of " + region +". His partner Pokemon is " + getPartner().getName()+".";
	}
}