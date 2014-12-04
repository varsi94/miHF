package ui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import accentprediction.Statistics;

public class ResultsPanel extends JPanel {
    private static final long serialVersionUID = 3390947755451194180L;
    private ResultLine accentsWordRL, noAccentsWordRL, goodAccentsWordRL,
	    goodNoAccentsWordRL, recoginitionRL, wrongRecognitionRL;
    private Statistics statistics;

    public ResultsPanel() {
	super();
	initComponents();
    }

    private void initComponents() {
	setLayout(new GridLayout(7, 1));

	accentsWordRL = new ResultLine("Ékezetes szavak az alap szövegben:", "");
	noAccentsWordRL = new ResultLine(
		"Nem ékezetes szavak az alap szövegben:", "");
	goodAccentsWordRL = new ResultLine("Jól felismert ékezetes szavak:", "");
	goodNoAccentsWordRL = new ResultLine(
		"Jól meghagyott nem ékezetes szavak:", "");
	recoginitionRL = new ResultLine("Találati arány:", "");
	wrongRecognitionRL = new ResultLine("Hiba arány:", "");

	add(accentsWordRL);
	add(noAccentsWordRL);
	add(goodAccentsWordRL);
	add(goodNoAccentsWordRL);
	add(recoginitionRL);
	add(wrongRecognitionRL);
	statistics = null;
    }

    public Statistics getStatistics() {
	return statistics;
    }

    public void setStatistics(Statistics statistics) {
	this.statistics = statistics;
	accentsWordRL.setValue(statistics.getAccentsWords() + "");
	noAccentsWordRL.setValue(statistics.getNoAccentsWords() + "");
	goodAccentsWordRL.setValue(statistics.getGoodAccentsWords() + "");
	goodNoAccentsWordRL.setValue(statistics.getGoodNoAccentsWords() + "");
	recoginitionRL.setValue(statistics.getRecognitionPercent() + " %");
	wrongRecognitionRL.setValue(statistics.getWrongRecoginitionPercent() + " %");
    }
}
