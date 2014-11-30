package accentprediction;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class WordList extends TreeSet<Word> {
    private static final long serialVersionUID = -5718540546244604226L;
    
    public static WordList loadWordListFromWiki(File f) throws ParserConfigurationException, SAXException, IOException {
	WordList result = new WordList();
	SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
	XmlParseHandler handler = new XmlParseHandler();
	parser.parse(f, handler);
	handler.closeFile();
	return result;
    }
}
