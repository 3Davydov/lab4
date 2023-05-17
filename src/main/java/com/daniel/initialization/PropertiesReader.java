package com.daniel.initialization;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    Properties prop;

    public PropertiesReader() {
        prop = new Properties();
    }

    public void getAllProperties(String propFileName) {

        InputStream inputPropFile;
        inputPropFile = PropertiesReader.class.getResourceAsStream(propFileName);
        try{
            prop.load(inputPropFile);
        }
        catch (IOException err) {
            System.out.println("ERROR DURING LOADING FILE " + inputPropFile.toString());
        }
    }

    public int getBodyStoreSize() {
        return Integer.valueOf(prop.getProperty("bodyStorageSize"));
    }

    public int getAccessoryStoreSize() {
        return Integer.valueOf(prop.getProperty("accessoryStorageSize"));
    }

    public int getEngineStoreSize() {
        return Integer.valueOf(prop.getProperty("engineStorageSize"));
    }

    public int getAccessoryProvidersNum() {
        return Integer.valueOf(prop.getProperty("accessoryProvidersNum"));
    }

    public int getCarStoreSize() {
        return Integer.valueOf(prop.getProperty("carStorageSize"));
    }

    public int getWorkersNum() {
        return Integer.valueOf(prop.getProperty("workers"));
    }

    public int getDealersNum() {
        return Integer.valueOf(prop.getProperty("dealers"));
    }

    public int getEngineProviderDelay() {
        return Integer.valueOf(prop.getProperty("engineProviderDelay"));
    }

    public int getBodyProviderDelay() {
        return Integer.valueOf(prop.getProperty("bodyProviderDelay"));
    }

    public int getAccessoryProviderDelay() {
        return Integer.valueOf(prop.getProperty("accessoryProviderDelay"));
    }

    public int getWorkerDelay() {
        return Integer.valueOf(prop.getProperty("workerDelay"));
    }

    public int getDealerDelay() {
        return Integer.valueOf(prop.getProperty("dealerDelay"));
    }

    public boolean getLogFlag() {
        return Boolean.valueOf(prop.getProperty("logSale"));
    }
}
