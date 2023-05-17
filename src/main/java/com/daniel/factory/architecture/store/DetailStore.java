package com.daniel.factory.architecture.store;

import java.util.LinkedList;
import java.util.Iterator;
import com.daniel.factory.architecture.details.Detail;
import com.daniel.factory.view.UIComponents.storesView.DetailStoreView;

public class DetailStore {

    private LinkedList<Detail> store;
    private final int size;
    private int currentSize = 0;
    
    private DetailStoreView detailStoreView = null;
    
    public DetailStore(int storeSize) {
        this.size = storeSize;
        store = new LinkedList<>();
    }

    public synchronized Detail getDetail() throws InterruptedException {
        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }
        Detail d;
        Iterator<Detail> iter = store.iterator();
        d = iter.next();
        iter.remove();
        currentSize--;
        if (detailStoreView != null) detailStoreView.setNewSize(currentSize);
        notify();
        return d;
    }

    public synchronized void putDetail(Detail newDetail) throws InterruptedException {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
        }
        if (newDetail != null) {
            store.add(newDetail);
            currentSize++;
            if (detailStoreView != null) detailStoreView.setNewSize(currentSize);
            notify();
        }
    }

    public void setStoreView(DetailStoreView newView) {
        detailStoreView = newView;
    }

    private boolean isFull() {
        if (store.size() >= this.size) return true;
        return false;
    }

    private boolean isEmpty(){
        if (store.size() == 0) return true;
        return false;
    }

}
