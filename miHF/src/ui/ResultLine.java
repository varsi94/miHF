package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultLine extends JPanel {
    private static final long serialVersionUID = -9102144215757148912L;
    private JLabel captionLabel, valueLabel;
    
    public ResultLine() {
	super();
	initComponents();
    }
    
    public ResultLine(String caption, String value) {
	super();
	initComponents();
	setCaption(caption);
	setValue(value);
    }

    private void initComponents() {
	setLayout(new BorderLayout());
	captionLabel = new JLabel();
	valueLabel = new JLabel();
	valueLabel.setPreferredSize(new Dimension(100, 0));
	add(captionLabel, BorderLayout.CENTER);
	add(valueLabel, BorderLayout.EAST);
    }
    
    public String getCaption() {
	return captionLabel.getText();
    }
    
    public void setCaption(String caption) {
	captionLabel.setText(caption);
    }
    
    public String getValue() {
	return valueLabel.getText();
    }
    
    public void setValue(String value) {
	valueLabel.setText(value);
    }
}
