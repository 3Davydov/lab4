package com.daniel.factory.view.UIComponents.storesView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DetailStoreView {

    private JPanel panel;
    private GridBagConstraints constraints;

    private JTextArea textField;
    private int currentSize = 0;
    private String text;

    public DetailStoreView(String text, int gridX, int gridY, int gridHeight) {

        panel = new JPanel();
        panel.setBackground(Color.CYAN);
        panel.setLayout(new BorderLayout());

        this.text = text;
        textField = new JTextArea(text + '\n' + Integer.toString(currentSize), 2, 1);
        textField.setFont(new Font("TimesRoman", Font.BOLD, 20));
        textField.setEditable(false);
        textField.setOpaque(false);
        panel.add(textField, BorderLayout.CENTER);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridheight = gridHeight;
        constraints.gridy   = gridY;
        constraints.gridx = gridX;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public GridBagConstraints getConstraints() {
        return this.constraints;
    }

    public synchronized void setNewSize(int newSize) {
        currentSize = newSize;
        textField.setText(text + '\n' + Integer.toString(currentSize));
    }

}
