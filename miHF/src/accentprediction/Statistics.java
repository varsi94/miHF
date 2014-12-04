package accentprediction;

public class Statistics {
    private int accentsWords, noAccentsWords, goodAccentsWords, goodNoAccentsWords;
 
    public Statistics(int accentsWords, int noAccentsWords,
	    int goodAccentsWords, int goodNoAccentsWords) {
	this.accentsWords = accentsWords;
	this.noAccentsWords = noAccentsWords;
	this.goodAccentsWords = goodAccentsWords;
	this.goodNoAccentsWords = goodNoAccentsWords;
    }

    public int getAccentsWords() {
        return accentsWords;
    }

    public int getNoAccentsWords() {
        return noAccentsWords;
    }

    public int getGoodAccentsWords() {
        return goodAccentsWords;
    }

    public int getGoodNoAccentsWords() {
        return goodNoAccentsWords;
    }

    public int getRecognitionPercent() {
	return (int)((double)goodAccentsWords * 100 / accentsWords);
    }
    
    public int getWrongRecoginitionPercent() {
	return (int) ((double)(noAccentsWords - goodNoAccentsWords) * 100 / noAccentsWords);
    }

    public void setGoodAccentsWords(int goodAccentsWords) {
	this.goodAccentsWords = goodAccentsWords;
    }

    public void setGoodNoAccentsWords(int goodNoAccentsWords) {
	this.goodNoAccentsWords = goodNoAccentsWords;
    }
}
