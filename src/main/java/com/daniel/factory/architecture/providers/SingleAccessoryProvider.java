package com.daniel.factory.architecture.providers;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

import com.daniel.factory.architecture.details.AccessoryDetail;
import com.daniel.factory.architecture.details.Detail;
import com.daniel.factory.architecture.store.DetailStore;
import com.daniel.factory.view.UIComponents.providersView.DetailProviderView;

public class SingleAccessoryProvider extends Thread implements DetailProvider{

    private AtomicInteger delay;
    private DetailStore accessoryStore;

    private DetailProviderView providerView = null;

    private int currentID = 0;

    public SingleAccessoryProvider(int baseDelay, int baseID){
        this.delay =  new AtomicInteger(baseDelay);
        this.currentID = baseID;
    }

    @Override
    public void setStore(DetailStore targetStore){
        if (targetStore != null) this.accessoryStore = targetStore;
    }

    @Override
    public void run() {
        try {
            sleep(delay.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long time_start = 0, time_end = 0;
        while (! this.isInterrupted()) {
            try {
                time_start = System.currentTimeMillis();
                provideNewDetail();
                if (providerView != null) providerView.refreshDetailsCount();
                time_end = System.currentTimeMillis();
                int delayNow = delay.get();
                if (delayNow - (time_end - time_start) > 0)
                    sleep(delayNow - (time_end - time_start));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + " is interrupted");
        return;
    }

    @Override
    public void provideNewDetail() throws InterruptedException {
        try {
            Detail d = null;
            try {
                d = (Detail) Class.forName(AccessoryDetail.class.getName()).getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException 
                        | SecurityException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            d.setID(currentID);
            currentID++;
            currentID %= Integer.MAX_VALUE;
            this.accessoryStore.putDetail(d);
        } catch (InterruptedException e) {
            throw e;
        }
    }

    @Override
    public void setNewDelay(int newDelay){
        if (newDelay >= 0) this.delay.set(newDelay);
    }

    @Override
    public void setProviderView(DetailProviderView providerView) {
        if (providerView != null)
            this.providerView = providerView;
    }

    @Override
    public int getCurrentDelay() {
        return delay.get();
    }

    @Override
    public boolean notInterrupted() {
        return (! this.isInterrupted());
    }
}
