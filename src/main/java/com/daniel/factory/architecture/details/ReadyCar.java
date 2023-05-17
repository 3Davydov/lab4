package com.daniel.factory.architecture.details;

public class ReadyCar {

    private Detail engine;
    private Detail body;
    private Detail accessory;

    private int ID;

    public ReadyCar(Detail readyEngine, Detail readyBody, Detail readyAccessory) {
        engine = readyEngine;
        body = readyBody;
        accessory = readyAccessory;
    }

    public int getEngineID() {
        return engine.getID();
    }

    public int getBodyID() {
        return body.getID();
    }

    public int getAccessoryID() {
        return accessory.getID();
    }

    public void setID(int newID) {
        ID = newID;
    }

    public int getSelfID() {
        return this.ID;
    }
}
