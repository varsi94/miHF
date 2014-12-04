package ui;

import accentprediction.AccentPrediction;

public class Application {

    public static void main(String[] args) {
	MainFrame mf = new MainFrame();
	mf.setHandler(new AccentPrediction());
	mf.setVisible(true);
    }

}
