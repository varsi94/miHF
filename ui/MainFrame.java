package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import accentprediction.AccentPredictionHandler;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -8808883923263763897L;
    private LabeledTextArea resultTF, inputTF, defaultTF;
    private JButton convertBtn;
    private AccentPredictionHandler handler;

    public MainFrame() {
	super("Mesterséges intelligencia házi feladat");
	initComponents();

	setDefaultCloseOperation(EXIT_ON_CLOSE);
	pack();
    }

    private class ConvertBtnListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
	    inputTF.setText(handler.getNoAccentsString(defaultTF.getText()));
	    resultTF.setText(handler.getAccentsString(inputTF.getText()));
	}

    }

    private void initComponents() {
	GridLayout gridLayout = new GridLayout(1, 3, 10, 10);
	JPanel mainPanel = new JPanel(gridLayout);
	
	defaultTF = new LabeledTextArea("Bemeneti szöveg:");
	defaultTF.setPreferredSize(new Dimension(200, 100));
	mainPanel.add(defaultTF);
	mainPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

	inputTF = new LabeledTextArea("Ékezetek nélkül:");
	inputTF.setPreferredSize(new Dimension(200, 100));
	inputTF.setEnabled(false);
	mainPanel.add(inputTF);

	resultTF = new LabeledTextArea("Eredmény ékezet-predikció után:");
	resultTF.setPreferredSize(new Dimension(200, 100));
	resultTF.setEnabled(false);
	mainPanel.add(resultTF);

	convertBtn = new JButton("Ékezetek felismerése!");
	convertBtn.addActionListener(new ConvertBtnListener());

	JPanel toolsPanel = new JPanel(new FlowLayout());
	toolsPanel.add(convertBtn);

	add(mainPanel, BorderLayout.CENTER);
	add(toolsPanel, BorderLayout.SOUTH);
    }

    public AccentPredictionHandler getHandler() {
	return handler;
    }

    public void setHandler(AccentPredictionHandler handler) {
	this.handler = handler;
    }
}
