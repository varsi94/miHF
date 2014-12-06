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
    private StringBuilder sb;

    public XmlParseHandler(FileInputStream is) {
	wordList = new WordList();
	this.is = is;
	currPercentage = 0;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
	    Attributes attributes) throws SAXException {
	super.startElement(uri, localName, qName, attributes);
	
	// itt egy komment
	if (qName.equalsIgnoreCase("text")) {
	    text = true;
	    sb = new StringBuilder();
	}
    }

    @Override
    public void endElement(String uri, String localName, String qName)
	    throws SAXException {
	super.endElement(uri, localName, qName);
	if (qName.equalsIgnoreCase("text") && text) {
	    text = false;
	    String s = sb.toString();
	    s = s.replaceAll("<(.*)>", "");
	    s = s.replaceAll("http(s|)://([^\\s]+)", "");
	    s = s.replaceAll("(\\[|\\{)([\\s]*)(\\[|\\{)(.*?)(\\]|\\})([\\s]*)(\\]|\\})", " ");
	    Pattern p = Pattern.compile("([\\s]*)([a-zA-ZéáűőúöüóíÉÁŰŐÚÖÜÓÍ]+)([\\s]*)");
	    Matcher m = p.matcher(s);
	    while(m.find()) {
		String word = m.group(2).toLowerCase();
		if (word.length() < 2) continue;
		if (wordList.keySet().contains(word)) {
		    wordList.increase(word);
		} else {
		    wordList.put(word, 1);
		}
	    }
	    //System.out.println(s);
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

    @Override
    public void characters(char[] ch, int start, int length)
	    throws SAXException {
	super.characters(ch, start, length);
	if (text) {
	    sb.append(new String(ch, start, length));
	}
    }

    public WordList closeFile() {
	return wordList;
    }
}
