package accentprediction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Magic happens.
 * 
 * @author Varsi
 *
 */
public class AccentPrediction implements AccentPredictionHandler {
    private Statistics statistics;
    private List<String> words;
    private WordList wordList;
    private TrigramDB trigramDB;
    public static final char[] accents = { 'á', 'í', 'é', 'ó', 'ö', 'ő', 'ú',
	    'ü', 'ű', 'Á', 'Í', 'É', 'Ó', 'Ö', 'Ő', 'Ú', 'Ü', 'Ű' };

    private String getTrigram(String word, int index) {
	if (index < 0) {
	    String s = word.substring(0, index + 3);
	    while (s.length() < 3) {
		s = " " + s;
	    }
	    return s;
	} else if (index >= word.length() - 2) {
	    String s = word.substring(index, word.length());
	    while (s.length() < 3) {
		s = s + " ";
	    }

	    return s;
	} else {
	    return word.substring(index, index + 3);
	}
    }

    public AccentPrediction() {

	/*
	 * words = new ArrayList<String>(); BufferedWriter br = null; try { br =
	 * new BufferedWriter(new OutputStreamWriter( new
	 * FileOutputStream("output.txt"))); wordList = WordList
	 * .loadWordListFromWiki(new File("D:\\szavak.xml")); for (String word :
	 * wordList.keySet()) { int num = wordList.get(word); br.write(word +
	 * " " + num + "\n"); } } catch (ParserConfigurationException e) {
	 * e.printStackTrace(); } catch (SAXException e) { e.printStackTrace();
	 * } catch (IOException e) { e.printStackTrace(); } finally { if (br !=
	 * null) { try { br.close(); } catch (IOException e) { } } }
	 */

	/*
	 * wordList = WordList.loadFromDictionary(new File("output.txt"));
	 * trigramDB = TrigramDB.loadFromFile(new File("output_trigram.txt"));
	 * words = new ArrayList<String>();
	 * 
	 * trigramDB = new TrigramDB(); for (String word : wordList.keySet()) {
	 * if (wordList.get(word) > 1) { for (int i = -2; i < word.length();
	 * i++) { String s = getTrigram(word, i); Trigram t =
	 * Trigram.parseString(s); if (trigramDB.containsKey(t)) {
	 * trigramDB.increase(t, wordList.get(word)); } else { trigramDB.put(t,
	 * wordList.get(word)); } } } }
	 * 
	 * PrintWriter bw = null; try { bw = new PrintWriter(new
	 * File("output_trigram.txt")); for (Trigram t : trigramDB.keySet()) {
	 * bw.println(t.getCharacters() + ":" + trigramDB.get(t)); } } catch
	 * (FileNotFoundException e) { e.printStackTrace(); } finally { if (bw
	 * != null) { bw.close(); } }
	 */
	
	
    }

    private boolean isAccentsWord(String s) {
	s = s.toLowerCase();
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
	String[] wordList = s.split("[\\s+]");
	List<String> list = new ArrayList<String>();
	for (int i = 0; i < wordList.length; i++) {
	    if (!wordList.equals("")) {
		list.add(convertStringToWord(wordList[i]));
	    }
	}

	return list;
    }

    private List<String> getWordVariants(String s) {
	s = s.toLowerCase();
	List<String> result = new ArrayList<String>();
	result.add(s);
	String vowels = "aieouAIEOU";
	String vowels2 = "áíéóúÁÍÉÓÚ";
	for (int i = 0; i < s.length(); i++) {
	    int size = result.size();
	    for (int j = 0; j < size; j++) {
		int index = -1;
		if ((index = vowels.indexOf(s.charAt(i))) != -1) {
		    char[] currArr = result.get(j).toCharArray();
		    currArr[i] = vowels2.charAt(index);
		    result.add(new String(currArr));
		    if (s.charAt(i) == 'o') {
			currArr[i] = 'ö';
			result.add(new String(currArr));
			currArr[i] = 'ő';
			result.add(new String(currArr));
		    } else if (s.charAt(i) == 'u') {
			currArr[i] = 'ü';
			result.add(new String(currArr));
			currArr[i] = 'ű';
			result.add(new String(currArr));
		    } else if (s.charAt(i) == 'U') {
			currArr[i] = 'Ü';
			result.add(new String(currArr));
			currArr[i] = 'Ű';
			result.add(new String(currArr));
		    } else if (s.charAt(i) == 'O') {
			currArr[i] = 'Ö';
			result.add(new String(currArr));
			currArr[i] = 'Ő';
			result.add(new String(currArr));
		    }
		}
	    }
	}
	return result;
    }

    @Override
    public String getAccentsString(String in) {
	List<String> wordList = convertStringToWords(in);
	int goodAccentsWordsCount = 0;
	int goodNoAccentsWordsCount = 0;

	/**
	 * Magic happens.
	 */
	for (int i = 0; i < wordList.size(); i++) {
	    String word = wordList.get(i);
	    String res = word;
	    List<String> variations = getWordVariants(word);
	    int maxFrequency = 0;
	    for (String variation : variations) {
		if (this.wordList.containsKey(variation)) {
		    int curr = 1;
		    for (int j = -2; j < variation.length(); j++) {
			Trigram t = Trigram
				.parseString(getTrigram(variation, j));
			curr *= trigramDB.get(t);
		    }

		    if (curr > maxFrequency) {
			maxFrequency = curr;
			res = variation;
		    }
		}
	    }
	    wordList.set(i, res);
	}

	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < wordList.size(); i++) {
	    if (words.get(i).equalsIgnoreCase(wordList.get(i))) {
		if (isAccentsWord(wordList.get(i))) {
		    goodAccentsWordsCount++;
		    System.out.println(wordList.get(i));
		} else {
		    goodNoAccentsWordsCount++;
		}
	    }
	    sb.append(wordList.get(i));
	    sb.append(" ");
	}
	statistics.setGoodAccentsWords(goodAccentsWordsCount);
	statistics.setGoodNoAccentsWords(goodNoAccentsWordsCount);
	return sb.toString();
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

	statistics = new Statistics(accentsWordsCount, noAccentsWordsCount, 0,
		0);
	char[] replace = { 'a', 'i', 'e', 'o', 'o', 'o', 'u', 'u', 'u', 'A',
		'I', 'E', 'O', 'O', 'O', 'U', 'U', 'U' };
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
