package accentprediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class WordList extends TreeMap<String, Integer> {
    private static final long serialVersionUID = -5718540546244604226L;
    
    public static WordList loadWordListFromWiki(File f) throws ParserConfigurationException, SAXException, IOException {
	SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
	FileInputStream is = new FileInputStream(f);
	XmlParseHandler handler = new XmlParseHandler(is);
	parser.parse(is, handler);
	return handler.closeFile();
    }

    public void increase(String word) {
	int frequency = get(word);
	put(word, frequency+1);
    }
    
    public static WordList loadFromDictionary(File f) {
	WordList wordList = new WordList();
	BufferedReader br = null;
	try {
	    FileInputStream fis = new FileInputStream(f);
	    br = new BufferedReader(new InputStreamReader(fis));
	    String currLine;
	    int currProgress = 0;
	    while ((currLine = br.readLine()) != null) {
		String[] data = currLine.split(" ");
		String word = data[0];
		int num = Integer.parseInt(data[1]);
		FileChannel channel = fis.getChannel();
		if (num > 1) {
		    wordList.put(word, num);
		}
		int progress = (int) (100.0f * channel.position() / channel.size());
		if (progress > currProgress) {
		    System.out.println("Progress: " + progress + " %");
		    currProgress = progress;
		}
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	return wordList;
    }
}
