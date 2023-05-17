package com.daniel.factory.architecture.controller;

import com.daniel.factory.architecture.store.DetailStore;
import com.daniel.factory.architecture.store.ReadyCarStore;
import com.daniel.factory.architecture.tasks.BuildTask;
import com.daniel.factory.view.UIComponents.CarBuildView;
import com.daniel.threadpool.ThreadPool;

public class Controller extends Thread {
    
    private DetailStore engineStore;
    private DetailStore bodyStore;
    private DetailStore accessoryStore;
    private ReadyCarStore carStore;

    private ThreadPool threadPool;

    private int nextCarID;
    private int workerDelay;
    private int carStoreSize;

    private CarBuildView carBuildView = null;

    public Controller(int workersNum, int dealersNum, int workerDelay) {
        setName("Controller");
        threadPool = new ThreadPool(workersNum);
        this.workerDelay = workerDelay;
    }

    @Override
    public void run() {
        try {
            sleep(workerDelay);
        } catch (InterruptedException e) {
            System.out.println("Controller is interrupted");
            threadPool.interrupt();
            this.interrupt();
            return;
        }
        
        while (! this.isInterrupted()) {
            try {
                int currentStoreSize = carStore.checkSize();
                int currentQueueSize = threadPool.getQueueSize();
                int runningTasksCount = threadPool.getRunningTasksCount();
                if (currentQueueSize + runningTasksCount < (carStoreSize - currentStoreSize) / 2) {
                    int diff = carStoreSize - currentStoreSize - currentQueueSize - runningTasksCount;
                    carBuildView.refreshQueueSize(diff + currentQueueSize);
                    threadPool.addTask(new BuildTask(engineStore, bodyStore, accessoryStore, carStore, nextCarID, workerDelay, carBuildView), diff);
                    // TODO add next carID generator
                }
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
        System.out.println("Controller is interrupted");
        threadPool.interrupt();
        return;
    }

    public void setEngineStore(DetailStore targetEngineStore) {
        engineStore = targetEngineStore;
    }

    public void setBodyStore(DetailStore targetBodyStore) {
        bodyStore = targetBodyStore;
    }

    public void setAccessoryStore(DetailStore targetAccessoryStore) {
        accessoryStore = targetAccessoryStore;
    }

    public void setCarStore(ReadyCarStore targetCarStore) {
        carStore = targetCarStore;
        carStoreSize = carStore.getSummarySize();
    }

    public void setFactoryView(CarBuildView targetView) {
        carBuildView = targetView;
    }

    public int getWorkerDelay() {
        return workerDelay;
    }

    public void changeDelay(int newDelay) {
        workerDelay = newDelay;
    }
}
