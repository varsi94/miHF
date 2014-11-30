package accentprediction;

import java.io.Serializable;

public class Word implements Serializable, Comparable<Word> {
    private static final long serialVersionUID = 1L;
    private String s;
    private int count;

    public Word(String s) {
	this.s = s;
	count = 1;
    }

    @Override
    public int compareTo(Word o) {
	return s.compareTo(o.s);
    }
    
    @Override
    public boolean equals(Object obj) {
	return s.equals(obj);
    }

    /**
     * @return the count
     */
    public int getCount() {
	return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
	this.count = count;
    }
    
    @Override
    public String toString() {
	return s;
    }
}
