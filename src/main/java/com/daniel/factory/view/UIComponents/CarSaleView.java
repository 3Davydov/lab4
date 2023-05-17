package com.daniel.factory.view.UIComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.daniel.factory.architecture.sail.CarSale;

public class CarSaleView {

    private JPanel panel;
    private GridBagConstraints constraints;

    private JTextArea textField;
    private JSlider slider;

    private int delay = 1;
    private int soldCarsCounter = 0;

    private CarSale dealers;

    public CarSaleView() {

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

        textField = new JTextArea("Dealers Delay : " + Integer.toString(delay) + "\nCars sold : " + Integer.toString(soldCarsCounter), 2, 1);
        textField.setFont(new Font("TimesRoman", Font.BOLD, 20));
        textField.setEditable(false);
        textField.setOpaque(false);
        suppPanel.add(textField, suppConstraints);

        slider = new JSlider(0, 10, 1);
        slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                delay = slider.getValue();
                textField.setText("Dealers Delay : " + Integer.toString(delay) + "\nCars sold : " + Integer.toString(soldCarsCounter));
                dealers.changeDelay(delay * 1000);
            }
            
        });
        suppConstraints.gridy = 1;
        panel.add(slider, BorderLayout.SOUTH);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1; 
        constraints.gridy   = 0;
        constraints.gridx = 4;
        constraints.gridheight = 3;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public GridBagConstraints getConstraints() {
        return this.constraints;
    }

    public void setDealers(CarSale newDealers) {
        dealers = newDealers;
        slider.setValue(dealers.getCurrentDelay() / 1000);
    }

    public synchronized void refreshSoldCarsCount() {
        soldCarsCounter++;
        textField.setText("Dealers Delay : " + Integer.toString(delay) + "\nCars sold : " + Integer.toString(soldCarsCounter));
    }

}
