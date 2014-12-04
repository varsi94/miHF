package accentprediction;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParseHandler extends DefaultHandler {
    private WordList wordList;
    private boolean text = false;
    private FileInputStream is;
    private int currPercentage;
    
    public XmlParseHandler(FileInputStream is) {
	wordList = new WordList();
	this.is = is;
	currPercentage = 0;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equalsIgnoreCase("text")) {
            text = true;
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equalsIgnoreCase("text") && text) {
            text = false;
        }
    }
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        Pattern p = Pattern.compile("([a-zA-ZéáűőúöüóíÉÁŰŐÚÖÜÓÍ]+)");
	String s = new String(ch, start, length);
	Matcher m = p.matcher(s);
	while (m.find()) {
	    String word = m.group(1).toLowerCase();
	    if (!wordList.containsKey(word)) {
		wordList.put(word, 1);
	    } else {
		wordList.increase(word);
	    }
	}
	
	
	try {
	    long currByte = is.getChannel().position();
	    long size = is.getChannel().size();
	    int szazalek = (int) ((double)currByte / size * 100);
	    if (szazalek > currPercentage) {
		System.out.println(szazalek + " %");
		currPercentage = szazalek;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public WordList closeFile() {
	return wordList;
    }
}
