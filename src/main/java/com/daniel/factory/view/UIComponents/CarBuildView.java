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

import com.daniel.factory.architecture.controller.Controller;

public class CarBuildView {

    private JPanel panel;
    private GridBagConstraints constraints;

    private JTextArea textField;
    private JSlider slider;

    private Controller factory;

    private int delay = 1;
    private int queueSize = 0;
    private int builtCarsCount = 0;

    public CarBuildView() {

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

        textField = new JTextArea("Workers Delay : " + Integer.toString(delay) + "\nCars Built : " + Integer.toString(builtCarsCount) + 
                                  '\n' + Integer.toString(queueSize) +" requests waiting in queue", 2, 1);
        textField.setFont(new Font("TimesRoman", Font.BOLD, 20));
        textField.setEditable(false);
        textField.setOpaque(false);
        suppPanel.add(textField, suppConstraints);

        slider = new JSlider(0, 5, 1);
        slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                delay = slider.getValue();
                textField.setText("Workers Delay : " + Integer.toString(delay) + "\nCars Built : " + Integer.toString(builtCarsCount) + 
                                  '\n' + Integer.toString(queueSize) +" requests waiting in queue");
                changeFactoryDelay();
            }
            
        });
        suppConstraints.gridy = 1;
        panel.add(slider, BorderLayout.SOUTH);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1; 
        constraints.gridy   = 0;
        constraints.gridx = 2;
        constraints.gridheight = 3;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public GridBagConstraints getConstraints() {
        return this.constraints;
    }

    public void setFactory(Controller factory) {
        this.factory = factory;
        slider.setValue(factory.getWorkerDelay() / 1000);
    }

    private synchronized void changeFactoryDelay() {
        this.factory.changeDelay(delay * 1000);
    }

    public synchronized void refreshBuiltCarsCount() {
        builtCarsCount++;
        textField.setText("Workers Delay : " + Integer.toString(delay) + "\nCars Built : " + Integer.toString(builtCarsCount) + 
                                '\n' + Integer.toString(queueSize) +" requests waiting in queue");
    }

    public synchronized void refreshQueueSize(int newSize) {
            try {
            queueSize = newSize;
            textField.setText("Workers Delay : " + Integer.toString(delay) + "\nCars Built : " + Integer.toString(builtCarsCount) + 
                                  '\n' + Integer.toString(queueSize) +" requests waiting in queue");}
                                  catch(Exception e){}
    }

    public synchronized void decrementQueueSize() {
            try{
            queueSize--;
            textField.setText("Workers Delay : " + Integer.toString(delay) + "\nCars Built : " + Integer.toString(builtCarsCount) + 
                                  '\n' + Integer.toString(queueSize) +" requests waiting in queue");} catch (Exception e) {}
    }
}
