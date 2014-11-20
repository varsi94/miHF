package accentprediction;

import java.util.ArrayList;
import java.util.List;

/**
 * Magic happens.
 * @author Varsi
 *
 */
public class AccentPrediction implements AccentPredictionHandler {
    private Statistics statistics;
    private List<String> words;
    private char[] accents = {'á', 'í', 'é', 'ó', 'ö', 'ő', 'ú', 'ü', 'ű'};
    
    public AccentPrediction() {
	words = new ArrayList<String>();
    }
    
    private boolean isAccentsWord(String s) {
	for (int i = 0; i < accents.length; i++) {
	    if (s.indexOf(accents[i]) != -1) {
		return true;
	    }
	}
	return false;
    }
    
    private String convertStringToWord(String s) {
	StringBuilder sb = new StringBuilder();
	for (int j = 0; j < s.length(); j++) {
	    if (Character.isLetter(s.charAt(j))) {
		sb.append(s.charAt(j));
	    }
	}
	return sb.toString();
    }
    
    private List<String> convertStringToWords(String s) {
	String[] wordList = s.split(" ");
	List<String> list = new ArrayList<String>();
	for (int i = 0; i < wordList.length; i++) {
	    if (!wordList.equals("")) {
		list.add(convertStringToWord(wordList[i]));
	    }
	}
	
	return list;
    }

    @Override
    public String getAccentsString(String in) {
	List<String> wordList = convertStringToWords(in);
	int goodAccentsWordsCount = 0;
	int goodNoAccentsWordsCount = 0;
	for (int i = 0; i < wordList.size(); i++) {
	    words.add(wordList.get(i));
	    if (words.get(i).equals(wordList.get(i))) {
		if (isAccentsWord(wordList.get(i))) {
		    goodAccentsWordsCount++;
		} else {
		    goodNoAccentsWordsCount++;
		}
	    }
	}
	statistics.setGoodAccentsWords(goodAccentsWordsCount);
	statistics.setGoodNoAccentsWords(goodNoAccentsWordsCount);
	return in;
    }

    @Override
    public String getNoAccentsString(String in) {
	words.clear();
	List<String> wordList = convertStringToWords(in);
	int accentsWordsCount = 0;
	int noAccentsWordsCount = 0;
	
	for (String word : wordList) {
	    words.add(word);
	    if (isAccentsWord(word)) {
		accentsWordsCount++;
	    } else {
		noAccentsWordsCount++;
	    }
	}
	
	statistics = new Statistics(accentsWordsCount, noAccentsWordsCount, 0, 0);
	char[] replace = {'a', 'i', 'e', 'o', 'o', 'o', 'u', 'u', 'u'};
	for (int i = 0; i < accents.length; i++) {
	    in = in.replace(accents[i], replace[i]);
	}
	return in;
    }

    @Override
    public Statistics getStatistics() {
	return statistics;
    }

}
