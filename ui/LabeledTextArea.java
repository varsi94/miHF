package ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class LabeledTextArea extends JPanel {
    private static final long serialVersionUID = -7707315313201947271L;
    private JLabel label;
    private JTextArea textArea;

    public LabeledTextArea(String label) {
	super();
	initComponents(label);
    }

    private void initComponents(String labelString) {
	label = new JLabel(labelString);

	textArea = new JTextArea();
	textArea.setBorder(new LineBorder(Color.BLACK));
	textArea.setLineWrap(true);
	JScrollPane scrollPane = new JScrollPane(textArea,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	setLayout(new BorderLayout());
	add(label, BorderLayout.NORTH);
	add(scrollPane, BorderLayout.CENTER);
    }

    public void setText(String text) {
	textArea.setText(text);
    }

    public String getText() {
	return textArea.getText();
    }
    
    @Override
    public void setEnabled(boolean enabled) {
	textArea.setEnabled(enabled);
    }
    
    @Override
    public boolean isEnabled() {
	return textArea.isEnabled();
    }
}
