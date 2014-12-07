package ui;

import java.sql.SQLException;

import accentprediction.AccentPrediction;

public class Application {

    public static void main(String[] args) throws ClassNotFoundException,
	    SQLException {
	MainFrame mf = new MainFrame();
	mf.setHandler(new AccentPrediction());
	mf.setVisible(true);
    }

}
