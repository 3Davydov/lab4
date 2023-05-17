package com.daniel.factory.view.UIComponents.providersView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.daniel.factory.architecture.providers.DetailProvider;

public class AccessoryProviderView implements DetailProviderView{

    private JPanel panel;
    private GridBagConstraints constraints;

    private JTextArea textField;
    private JSlider slider;

    private List<DetailProvider> providers;

    private int delay = 1;
    private int detailsCount = 0;

    public AccessoryProviderView() {

        providers = new ArrayList<>();

        panel = new JPanel();
        panel.setBackground(Color.ORANGE);
        panel.setLayout(new BorderLayout());
        
        JPanel suppPanel = new JPanel(new GridBagLayout());
        suppPanel.setOpaque(false);
        panel.add(suppPanel, BorderLayout.CENTER);

        GridBagConstraints suppConstraints = new GridBagConstraints();
        suppConstraints.fill = GridBagConstraints.BOTH;
        suppConstraints.weightx = 1;
        suppConstraints.weighty = 1;
        suppConstraints.gridx = 0;
        suppConstraints.gridy = 0;

        slider = new JSlider(0, 5, 1);

        textField = new JTextArea("Accessory Provider Delay : " + Integer.toString(slider.getValue()) + "\nDetails Provided : " + Integer.toString(detailsCount), 2, 1);
        textField.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        textField.setFont(new Font("TimesRoman", Font.BOLD, 20));
        textField.setEditable(false);
        textField.setOpaque(false);
        suppPanel.add(textField, suppConstraints);

        slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                delay = slider.getValue();
                textField.setText("Accessory Provider Delay : " + Integer.toString(slider.getValue()) + "\nDetails Provided : " + Integer.toString(detailsCount));
                changeProviderDelay(delay * 1000);
            }
            
        });
        suppConstraints.gridy = 1;
        panel.add(slider, BorderLayout.SOUTH);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1; 
        constraints.gridy   = 2;
        constraints.gridx = 0;
        constraints.ipady = 200;
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
        providers.add(newProvider);
        slider.setValue(providers.get(providers.size() - 1).getCurrentDelay() / 1000);
    }

    @Override
    public void changeProviderDelay(int newDelay) {
        for (DetailProvider p : providers) {
            p.setNewDelay(newDelay);
        }
    }

    @Override
    public synchronized void refreshDetailsCount() {
        detailsCount++;
        textField.setText("Accessory Provider Delay : " + Integer.toString(slider.getValue()) + "\nDetails Provided : " + Integer.toString(detailsCount));
    }

}
