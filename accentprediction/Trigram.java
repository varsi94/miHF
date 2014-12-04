package accentprediction;


public class Trigram implements Comparable<Trigram> {
    private char[] characters;

    public Trigram() {
	characters = new char[3];
    }
    
    public static class TrigramWrongLengthException extends RuntimeException {
	private static final long serialVersionUID = 4611732857794610973L;
    }
    
    public boolean match(String s) {
	if (s.length() != 3) {
	    throw new TrigramWrongLengthException();
	}
	
	for (int i = 0; i < 3; i++) {
	    if (s.charAt(i) != characters[i]) {
		return false;
	    }
	}
	return true;
    }
    
    public static Trigram parseString(String s) {
	if (s.length() != 3) {
	    throw new TrigramWrongLengthException();
	}
	Trigram t = new Trigram();
	t.characters[0] = s.charAt(0);
	t.characters[1] = s.charAt(1);
	t.characters[2] = s.charAt(2);
	
	return t;
    }
    
    public String getCharacters() {
        return new String(characters);
    }
    
    @Override
    public boolean equals(Object obj) {
	Trigram another = (Trigram)obj;
	for (int i = 0; i < characters.length; i++) {
	    if (characters[i] != another.characters[i]) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public int compareTo(Trigram o) {
	String str = new String(characters);
	String s2 = new String(o.characters);
	return str.compareTo(s2);
    }
}
