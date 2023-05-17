package com.daniel.factory.architecture.sail;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;

import com.daniel.factory.architecture.details.ReadyCar;
import com.daniel.factory.architecture.store.ReadyCarStore;
import com.daniel.factory.view.UIComponents.CarSaleView;

public class Dealer extends Thread{

    private AtomicInteger delay;
    private ReadyCarStore carStore;
    private ReadyCar carToSail;

    private CarSaleView carSaleView = null;
    private int dealerNumber;

    private boolean logSale;

    private final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Dealer.class.getName());

    public Dealer(int newDelay, int currentDealerNumber, boolean logSaleFlag) throws SecurityException, IOException{
        this.delay = new AtomicInteger(newDelay);
        this.dealerNumber = currentDealerNumber;
        logSale = logSaleFlag;
        if (logSale) {
            LogManager.getLogManager().reset();
            java.util.logging.Logger globalLogger = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
            globalLogger.setLevel(Level.FINEST);

            FileHandler fh = new FileHandler("sail.log", 6000, 1, false);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.FINEST);
        }
    }

    public void setCarStore(ReadyCarStore targetStore) {
        if (targetStore != null) carStore = targetStore;
    }

    public void setSaleView(CarSaleView view) {
        if (view != null)
            this.carSaleView = view;
    }

    public void changeDelay(int newDelay) {
        delay.set(newDelay);
    }

    @Override
    public void run() {
        try {
            sleep(delay.get());
        } catch (InterruptedException e) {
            LogManager.getLogManager().reset();
            Thread.currentThread().interrupt();
        }
        while (! isInterrupted()) {
            try {
                carToSail = carStore.getCar();
                long timeStart = 0, timeEnd = 0;
                timeStart = System.currentTimeMillis();
                if (logSale) sailCar();
                if (carSaleView != null) carSaleView.refreshSoldCarsCount();
                timeEnd = System.currentTimeMillis();
                int delayNow = delay.get();
                if (delayNow > (timeEnd - timeStart)) {
                    sleep(delayNow - (timeEnd - timeStart));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + " is interrupted");
    }

    private void sailCar() {
        Date d = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("'Date' yyyy.MM.dd 'Time' hh:mm:ss a zzz");

        logger.info(formatForDateNow.format(d) + ":  Dealer " + Integer.toString(dealerNumber) + 
                                                 ":  Auto" + Integer.toString(carToSail.getSelfID()) + 
                                                 "(Body:  " + Integer.toString(carToSail.getBodyID()) + 
                                                 ", Motor:  " + Integer.toString(carToSail.getEngineID()) +
                                                 ", Accessory:  " + Integer.toString(carToSail.getAccessoryID()) + ")");
    }
}
