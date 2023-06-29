public class move {
	private Pokemon.PokemonType type;
	private String name;
	public move(Pokemon.PokemonType t, String n) {
		type = t;
		name = n;
	}
	public Pokemon.PokemonType getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    move m = (move) obj;
	    // Compare fields
	    if (!name.equals(m.name)) {
	        return false;
	    }
	    return true;
	}
	@Override
	public String toString() {
		return name;
	}
	public void setType(Pokemon.PokemonType nt) {
		type = nt;
	}
}
