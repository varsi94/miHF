package accentprediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class TrigramDB extends TreeMap<Trigram, Integer> {
    private static final long serialVersionUID = -5611134591244079350L;
    
    public void increase(Trigram t, int addNum) {
	int num = get(t);
	put(t, addNum + num);
    }

    public static TrigramDB loadFromFile(File file) {
	BufferedReader reader = null;
	TrigramDB db = new TrigramDB();
	try {
	    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	    String currLine;
	    while ((currLine = reader.readLine()) != null) {
		String[] data = currLine.split(":");
		String trigram = data[0];
		int num = Integer.parseInt(data[1]);
		Trigram t = Trigram.parseString(trigram);
		db.put(t, num);
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
