package com.daniel.factory.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.daniel.factory.architecture.controller.Controller;
import com.daniel.factory.architecture.providers.AccessoryProviders;
import com.daniel.factory.architecture.providers.DetailProvider;
import com.daniel.factory.architecture.providers.SingleAccessoryProvider;
import com.daniel.factory.view.UIComponents.CarBuildView;
import com.daniel.factory.view.UIComponents.CarSaleView;
import com.daniel.factory.view.UIComponents.providersView.AccessoryProviderView;
import com.daniel.factory.view.UIComponents.providersView.BodyProviderView;
import com.daniel.factory.view.UIComponents.providersView.DetailProviderView;
import com.daniel.factory.view.UIComponents.providersView.EngineProviderView;
import com.daniel.factory.view.UIComponents.storesView.DetailStoreView;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainView {

    private JFrame mainWindow;
    private JPanel mainPanel;

    private EngineProviderView engineProviderView;
    private BodyProviderView bodyProviderView;
    private AccessoryProviderView accessoryProviderView;

    private DetailStoreView engineStoreView;
    private DetailStoreView bodyStoreView;
    private DetailStoreView accessoryStoreView;
    private DetailStoreView carStoreView;

    private CarBuildView carBuildView;
    private CarSaleView carSailView;

    private boolean isOpened = false;

    public synchronized void isClosed() {
        if (isOpened) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else notifyAll();

    }

    private JFrame getJFrame() {
        JFrame jframe = new JFrame() {};
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jframe.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public synchronized void windowClosing(WindowEvent e) {
                isOpened = false;
                isClosed();
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
            
        });

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dm = toolkit.getScreenSize();

        jframe.setLocation(0, 0);
        jframe.setSize(dm.width, dm.height);
        jframe.setTitle("FACTORY");
        return  jframe;
    }

    public MainView() {

        mainWindow = getJFrame();
        isOpened = true;
        mainPanel = new JPanel(new GridBagLayout());
        mainWindow.add(mainPanel);

        GridBagConstraints constraints = new GridBagConstraints(); 
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1; 

        engineProviderView = new EngineProviderView();
        mainPanel.add(engineProviderView.getPanel(), engineProviderView.getConstraints());

        bodyProviderView = new BodyProviderView();
        mainPanel.add(bodyProviderView.getPanel(), bodyProviderView.getConstraints());

        accessoryProviderView = new AccessoryProviderView();
        mainPanel.add(accessoryProviderView.getPanel(), accessoryProviderView.getConstraints());

        engineStoreView = new DetailStoreView("Engine Store : ", 1, 0, 1);
        mainPanel.add(engineStoreView.getPanel(), engineStoreView.getConstraints());

        bodyStoreView = new DetailStoreView("Body Store : ", 1, 1, 1);
        mainPanel.add(bodyStoreView.getPanel(), bodyStoreView.getConstraints());

        accessoryStoreView = new DetailStoreView("Accessory Store : ", 1, 2, 1);
        mainPanel.add(accessoryStoreView.getPanel(), accessoryStoreView.getConstraints());

        carBuildView = new CarBuildView();
        mainPanel.add(carBuildView.getPanel(), carBuildView.getConstraints());

        carStoreView = new DetailStoreView("Car Store : ", 3, 0, 3);
        mainPanel.add(carStoreView.getPanel(), carStoreView.getConstraints());

        carSailView = new CarSaleView();
        mainPanel.add(carSailView.getPanel(), carSailView.getConstraints());
        
        mainPanel.revalidate();
        mainWindow.revalidate();
        // mainWindow.setState(JFrame.ICONIFIED);
    }

    public DetailStoreView getEngineStoreView() {
        return engineStoreView;
    }

    public DetailStoreView getAccessoryStoreView() {
        return accessoryStoreView;
    }

    public DetailStoreView getBodyStoreView() {
        return bodyStoreView;
    }

    public DetailStoreView getCarStoreView() {
        return carStoreView;
    }

    public DetailProviderView getEngineProviderView() {
        return engineProviderView;
    }

    public DetailProviderView getBodyProviderView() {
        return bodyProviderView;
    }

    public DetailProviderView getAccessoryProviderView() {
        return accessoryProviderView;
    }

    public CarBuildView getFactoryView() {
        return carBuildView;
    }

    public void setEngineProviderRealization(DetailProvider realEngineProvider) {
        engineProviderView.setProvider(realEngineProvider);
    }

    public void setBodyProviderRealization(DetailProvider realBodyProvider) {
        bodyProviderView.setProvider(realBodyProvider);
    }

    public void setAccessoryProviderRealization(AccessoryProviders realAccessoryProviders) {
        for (SingleAccessoryProvider p : realAccessoryProviders.getAllSuppliers()) {
            accessoryProviderView.setProvider(p);
        }
    }

    public CarSaleView getSaleView() {
        return carSailView;
    }

    public void setFactory(Controller factoryController) {
        carBuildView.setFactory(factoryController);
    }
}
