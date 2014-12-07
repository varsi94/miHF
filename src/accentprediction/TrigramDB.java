package accentprediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrigramDB extends TreeMap<TrigramWord, Integer> {
    private static final long serialVersionUID = -5611134591244079350L;
    
    private Integer increase(TrigramWord t, int addNum) {
	int num = get(t);
	super.put(t, addNum + num);
	return addNum + num;
    }
    
//    public static TrigramDB loadWordListFromWiki(File f) throws ParserConfigurationException, SAXException, IOException {
//	TrigramDB result = new TrigramDB();
//	SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
//	FileInputStream is = new FileInputStream(f);
//	XmlParseHandler handler = new XmlParseHandler(is, result);
//	parser.parse(is, handler);
//	return result;
//    }
    
    @Override
    public Integer put(TrigramWord key, Integer value) {
	if (!containsKey(key)) {
	    return super.put(key, value);
	} else {
	    return increase(key, value);
	}
    }
    
    public static TrigramDB loadFromSerialized(File f) {
	TrigramDB result = null;
	try {
	    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
	    result = (TrigramDB) ois.readObject();
	    ois.close();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return result;
    }
    
    public static TrigramDB loadFromAranyEmber(File f) {
	BufferedReader br;
	TrigramDB db = new TrigramDB();
	try {
	    br = new BufferedReader(new InputStreamReader(new FileInputStream(f), Charset.forName("UTF-8")));
	    String line;
	    while ((line = br.readLine()) != null) {
		Pattern pattern = Pattern.compile("([a-zA-ZéáűőúöüóíÉŰÁŐÚÖÜÓÍ]+)");
		Matcher m = pattern.matcher(line);
		String word1 = null;
		while (m.find()) {
		    String word = m.group(1).toLowerCase();
		    if (word1 == null) {
			word1 = word;
			continue;
		    }
		    
		    TrigramWord trigram = new TrigramWord(word1, word);
		    if (db.containsKey(trigram)) {
			db.increase(trigram, 1);
		    } else {
			db.put(trigram, 1);
		    }
		    word1 = word;
		}
	    }
	    br.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return db;
    }

    public static TrigramDB loadFromFile(File file) {
	BufferedReader reader = null;
	TrigramDB db = new TrigramDB();
	try {
	    FileInputStream fis = new FileInputStream(file);
	    reader = new BufferedReader(new InputStreamReader(fis));
	    int last = 0;
	    String currLine;
	    while ((currLine = reader.readLine()) != null) {
		String[] data = currLine.split(" ");
		int num = Integer.parseInt(data[2]);
		TrigramWord trigram = new TrigramWord(data[0], data[1]);
		db.put(trigram, num);
		FileChannel channel = fis.getChannel();
		int curr = (int) ((double)channel.position() / channel.size() * 100);
		if (curr > last) {
		    System.out.println(curr);
		    last = curr;
		}
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (reader != null) {
		try {
		    reader.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	return db;
    }
}
