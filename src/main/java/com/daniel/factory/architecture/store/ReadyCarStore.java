package com.daniel.factory.architecture.store;

import java.util.LinkedList;
import java.util.Iterator;
import com.daniel.factory.architecture.details.ReadyCar;
import com.daniel.factory.view.UIComponents.storesView.DetailStoreView;

public class ReadyCarStore {

    private LinkedList<ReadyCar> store;
    private final int size;
    
    private DetailStoreView carStoreView = null;

    public ReadyCarStore(int storeSize) {
        this.size = storeSize;
        store = new LinkedList<>();
    }

    public synchronized ReadyCar getCar() throws InterruptedException {
        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw e;
            }
        }
        ReadyCar d;
        Iterator<ReadyCar> iter = store.iterator();
        d = iter.next();
        iter.remove();
        if (carStoreView != null) carStoreView.setNewSize(store.size());
        notifyAll();
        return d;
    }

    public synchronized void putCar(ReadyCar newDetail) throws InterruptedException {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw e;
            }
        }
        if (newDetail != null) {
            int storeSize;
            store.add(newDetail);
            storeSize = store.size();
            if (carStoreView != null) carStoreView.setNewSize(storeSize);
            notifyAll();
        }
    }

    public boolean isFull() {
        if (store.size() >= this.size) return true;
        return false;
    }

    public boolean isEmpty(){
        if (this.store.size() == 0) return true;
        return false;
    }

    public synchronized int checkSize() throws InterruptedException {
        while (store.size() == size) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw  e;
            }
        }
        return store.size();
    }

    public void setCarStoreView(DetailStoreView newCarStoreView) {
        carStoreView = newCarStoreView;
    }

    public int getSummarySize() {
        return size;
    }

}
