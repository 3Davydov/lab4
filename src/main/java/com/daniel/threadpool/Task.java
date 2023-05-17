package com.daniel.threadpool;

public interface Task {

    public void performWork() throws InterruptedException;

}
