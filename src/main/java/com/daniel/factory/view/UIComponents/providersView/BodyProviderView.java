package com.daniel.factory.view.UIComponents.providersView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.daniel.factory.architecture.providers.DetailProvider;

public class BodyProviderView implements DetailProviderView{

    private JPanel panel;
    private GridBagConstraints constraints;

    private JTextArea textField;
    private JSlider slider;

    private DetailProvider provider;

    private int delay = 1;
    private int detailsCount = 0;

    public BodyProviderView() {

        panel = new JPanel();
        panel.setBackground(Color.ORANGE);
        panel.setLayout(new BorderLayout());

        slider = new JSlider(0, 5, 1);

        textField = new JTextArea("Body Provider Delay : " + Integer.toString(slider.getValue()) + "\nDetails Provided : " + Integer.toString(detailsCount), 2, 1);
        textField.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        textField.setFont(new Font("TimesRoman", Font.BOLD, 20));
        textField.setEditable(false);
        textField.setOpaque(false);
        panel.add(textField, BorderLayout.CENTER);

        slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                textField.setText("Body Provider Delay : " + Integer.toString(slider.getValue()) + "\nDetails Provided : " + Integer.toString(detailsCount));
                delay = slider.getValue();
                changeProviderDelay(delay * 1000);
            }
            
        });
        panel.add(slider, BorderLayout.SOUTH);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1; 
        constraints.gridy   = 1;
        constraints.gridx = 0;
    }

    @Override
    public JPanel getPanel() {
        return this.panel;
    }

    @Override
    public GridBagConstraints getConstraints() {
        return this.constraints;
    }

    @Override
    public void setProvider(DetailProvider newProvider) {
        provider = newProvider;
        slider.setValue(provider.getCurrentDelay() / 1000);
    }

    @Override
    public void changeProviderDelay(int newDelay) {
        provider.setNewDelay(newDelay);
    }

    @Override
    public void refreshDetailsCount() {
        detailsCount++;
        textField.setText("Body Provider Delay : " + Integer.toString(slider.getValue()) + "\nDetails Provided : " + Integer.toString(detailsCount));
    }

}
