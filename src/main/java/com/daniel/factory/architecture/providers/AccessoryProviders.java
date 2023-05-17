package com.daniel.factory.architecture.providers;

import java.util.ArrayList;
import java.util.List;

import com.daniel.factory.architecture.store.DetailStore;
import com.daniel.factory.view.UIComponents.providersView.DetailProviderView;

public class AccessoryProviders {
    
    private List<SingleAccessoryProvider> providers;
    private int delay;

    public AccessoryProviders(int providersNum, int baseDelay) {
        this.providers = new ArrayList<>();
        this.delay = baseDelay;
        for (int i = 0; i < providersNum; i++) {
            SingleAccessoryProvider newProv = new SingleAccessoryProvider(this.delay, i * 1000);
            newProv.setName("Accessory Provider " + i);
            providers.add(newProv);
        }
    }

    public void setStore(DetailStore targetStore) {
        for (SingleAccessoryProvider e : providers) {
            e.setStore(targetStore);
        }
    }

    public void start() {
        for (SingleAccessoryProvider e : providers) {
            e.start();
        }
    }

    public void setNewDelay(int newDelay) {
        for (SingleAccessoryProvider e : providers) {
            if (e.notInterrupted())
                e.setNewDelay(newDelay);
        }
    }

    public void setAccessoryProvidersView(DetailProviderView newProvider) {
        if (newProvider != null) {
            for (SingleAccessoryProvider e : providers) {
                e.setProviderView(newProvider);
            }
        }
    }

    public List<SingleAccessoryProvider> getAllSuppliers() {
        return providers;
    }

    public void interrupt() {
        for (SingleAccessoryProvider e : providers) {
            e.interrupt();
        }
    }
}
