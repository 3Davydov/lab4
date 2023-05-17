package com.daniel.factory.architecture.details;

public class BodyDetail implements Detail{

    private int ID = -1;
    private final String type = "BodyDetail";

    public BodyDetail() {}

    @Override
    public void setID(int newID) {
        if (this.ID == -1 && newID != -1) this.ID =  newID;
    }

    @Override
    public int getID(){
        return this.ID;
    }

    @Override 
    public String getType(){
        return this.type;
    }
    
}
