package accentprediction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParseHandler extends DefaultHandler {
    private BufferedWriter writer;
    private WordList wordList;
    private boolean text = false;
    
    public XmlParseHandler() {
	File f = new File("output.txt");
	try {
	    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	
	wordList = new WordList();
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
	    String word = m.group(1);
	    Word w = new Word(word);
	    if (!wordList.contains(w)) {
		wordList.add(w);
	    } else {
		Word curr = wordList.floor(w);
		curr.setCount(curr.getCount() + 1);
	    }
	}
    }
    
    public void closeFile() {
	System.out.println("XmlParseHandler.closeFile()");
	try {
	    for (Word word : wordList) {
		writer.write(word + "\n");
	    }
	    writer.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
