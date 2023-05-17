package com.daniel.factory.view.UIComponents.providersView;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import com.daniel.factory.architecture.providers.DetailProvider;

public interface DetailProviderView {

    public JPanel getPanel();

    public GridBagConstraints getConstraints();

    public void setProvider(DetailProvider newProvider);

    public void changeProviderDelay(int newDelay);

    public void refreshDetailsCount();
}
