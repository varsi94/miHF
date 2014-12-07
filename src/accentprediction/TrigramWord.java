package accentprediction;

import java.io.Serializable;

public class TrigramWord implements Comparable<TrigramWord>, Serializable {
    private static final long serialVersionUID = -2639569145235708568L;
    public static final int TRIGRAM_NUM = 2;
    private String[] words;
    
    public TrigramWord(String word1, String word2) {
	words = new String[] {word1, word2};
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
	    sb.append(word + " ");
	}
        return sb.toString().trim();
    }
    
    @Override
    public int compareTo(TrigramWord o) {
	return this.toString().compareTo(o.toString());  
    }
    
    public String getWord(int index) {
	return words[index];
    }
    
    @Override
    public boolean equals(Object obj) {
	TrigramWord other = (TrigramWord) obj;
	return toString().equals(other.toString());
    }
    
    @Override
    public int hashCode() {
	return words[0].hashCode() ^ words[1].hashCode();
    }
}
