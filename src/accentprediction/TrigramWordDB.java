package accentprediction;

public class TrigramWordDB implements Comparable<TrigramWordDB> {
    private String[] words;
    
    public TrigramWordDB(String word1, String word2, String word3) {
	words = new String[] {word1, word2, word3};
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
    public int compareTo(TrigramWordDB o) {
	return this.toString().compareTo(o.toString());  
    }
    
    public String getWord(int index) {
	return words[index];
    }

}
