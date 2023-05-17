package com.daniel.factory.architecture;

import java.io.IOException;

import com.daniel.factory.architecture.controller.Controller;
import com.daniel.factory.architecture.providers.AccessoryProviders;
import com.daniel.factory.architecture.providers.BodyProvider;
import com.daniel.factory.architecture.providers.EngineProvider;
import com.daniel.factory.architecture.sail.CarSale;
import com.daniel.factory.architecture.store.DetailStore;
import com.daniel.factory.architecture.store.ReadyCarStore;
import com.daniel.factory.view.MainView;
import com.daniel.initialization.PropertiesReader;

public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();

        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.getAllProperties("/config.properties");

        DetailStore engineStore = new DetailStore(propertiesReader.getEngineStoreSize());
        engineStore.setStoreView(mainView.getEngineStoreView());

        DetailStore bodyStore = new DetailStore(propertiesReader.getBodyStoreSize());
        bodyStore.setStoreView(mainView.getBodyStoreView());

        DetailStore accessoryStore = new DetailStore(propertiesReader.getAccessoryStoreSize());
        accessoryStore.setStoreView(mainView.getAccessoryStoreView());

        ReadyCarStore readyCarStore = new ReadyCarStore(propertiesReader.getCarStoreSize());
        readyCarStore.setCarStoreView(mainView.getCarStoreView());

        EngineProvider engineProvider = new EngineProvider(propertiesReader.getEngineProviderDelay());
        engineProvider.setStore(engineStore);
        engineProvider.setName("Engine Provider");
        engineProvider.setProviderView(mainView.getEngineProviderView());

        BodyProvider bodyProvider = new BodyProvider(propertiesReader.getBodyProviderDelay());
        bodyProvider.setStore(bodyStore);
        bodyProvider.setName("Body Provider");
        bodyProvider.setProviderView(mainView.getBodyProviderView());

        AccessoryProviders accessoryProviders = new AccessoryProviders(propertiesReader.getAccessoryProvidersNum(), propertiesReader.getAccessoryProviderDelay());
        accessoryProviders.setStore(accessoryStore);
        accessoryProviders.setAccessoryProvidersView(mainView.getAccessoryProviderView());

        Controller controller = new Controller(propertiesReader.getWorkersNum(), propertiesReader.getDealersNum(), propertiesReader.getWorkerDelay());
        controller.setEngineStore(engineStore);
        controller.setBodyStore(bodyStore);
        controller.setAccessoryStore(accessoryStore);
        controller.setFactoryView(mainView.getFactoryView());
        controller.setCarStore(readyCarStore);

        CarSale carSale = null;
        try {
            carSale = new CarSale(propertiesReader.getDealersNum(), propertiesReader.getDealerDelay(), propertiesReader.getLogFlag());
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        carSale.setCarStore(readyCarStore);
        carSale.setSaleView(mainView.getSaleView());

        mainView.setEngineProviderRealization(engineProvider);
        mainView.setBodyProviderRealization(bodyProvider);
        mainView.setAccessoryProviderRealization(accessoryProviders);
        mainView.setFactory(controller);
        mainView.getSaleView().setDealers(carSale);

        controller.start();
        carSale.start();
        engineProvider.start();
        bodyProvider.start();
        accessoryProviders.start();
        
        mainView.isClosed();

        synchronized (engineProvider) {
            engineProvider.interrupt();
        }
        synchronized (bodyProvider) {
            bodyProvider.interrupt();
        }
        synchronized (accessoryProviders) {
            accessoryProviders.interrupt();
        }
        synchronized (controller) {
            controller.interrupt();
        }
        synchronized (carSale) {
            carSale.interrupt();
        }
    }
}
