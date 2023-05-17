package com.daniel.factory.architecture.sail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.daniel.factory.architecture.store.ReadyCarStore;
import com.daniel.factory.view.UIComponents.CarSaleView;

public class CarSale {
    
    private List<Dealer> dealers;
    private int currentDelay;
    
    public CarSale(int dealersNum, int baseDelay, boolean logSale) throws SecurityException, IOException {
        currentDelay = baseDelay;
        dealers = new ArrayList<>();
        for (int i = 0; i < dealersNum; i++) {
            Dealer d = new Dealer(baseDelay, i, logSale);
            d.setName("Dealer " + Integer.toString(i));
            dealers.add(d);
        }
    }

    

    public void setCarStore(ReadyCarStore targetStore) {
        if (targetStore != null) {
            for (Dealer d : dealers) {
                d.setCarStore(targetStore);
            }
        }
    }

    public void setSaleView(CarSaleView view) {
        if (view != null)
            for (Dealer d : dealers) {
                d.setSaleView(view);
            }
    }

    public void start() {
        for (Dealer d : dealers) {
            d.start();
        }
    }

    public void changeDelay(int newDelay) {
        for (Dealer d : dealers) {
            d.changeDelay(newDelay);
        }
    }

    public int getCurrentDelay() {
        return currentDelay;
    }

    public void interrupt() {
        for (Dealer d : dealers) {
            d.interrupt();
        }
    }
}
