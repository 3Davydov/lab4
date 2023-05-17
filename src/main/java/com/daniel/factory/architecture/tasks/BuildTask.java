package com.daniel.factory.architecture.tasks;

import com.daniel.factory.architecture.details.Detail;
import com.daniel.factory.architecture.details.ReadyCar;
import com.daniel.factory.architecture.store.DetailStore;
import com.daniel.factory.architecture.store.ReadyCarStore;
import com.daniel.factory.view.UIComponents.CarBuildView;
import com.daniel.threadpool.Task;

public class BuildTask implements Task {

    private DetailStore engineStore;
    private DetailStore bodyStore;
    private DetailStore accessoryStore;
    private ReadyCarStore carStore;

    private int nextCarID;
    private long workerDelay;

    private CarBuildView carBuildView;

    public BuildTask(DetailStore targetEngineStore, DetailStore targetBodyStore, DetailStore targetAccessoryStore, ReadyCarStore targetCarStore,
                        int nextCarID, long delay, CarBuildView view) {
        engineStore = targetEngineStore;
        bodyStore = targetBodyStore;
        accessoryStore = targetAccessoryStore;
        carStore = targetCarStore;
        this.nextCarID = nextCarID;
        workerDelay = delay;
        carBuildView = view;
    }

    @Override
    public void performWork() throws InterruptedException {
        try {
            Detail engine = engineStore.getDetail();
            Detail body = bodyStore.getDetail();
            Detail accessory = accessoryStore.getDetail();

            long timeStart = System.currentTimeMillis();
            ReadyCar car = new ReadyCar(engine, body, accessory);
            car.setID(nextCarID);
            long timeEnd = System.currentTimeMillis() - timeStart;

            if (workerDelay > timeEnd)
                Thread.sleep(workerDelay - timeEnd);
            else 
                Thread.sleep(workerDelay);
            if (carBuildView != null) {
                carBuildView.refreshBuiltCarsCount();
                carBuildView.decrementQueueSize();
            }
            carStore.putCar(car);            
        } catch (InterruptedException e) {
            throw e;
        }
    }
}
