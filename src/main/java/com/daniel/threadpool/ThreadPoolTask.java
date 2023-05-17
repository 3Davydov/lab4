package com.daniel.threadpool;

public class ThreadPoolTask {
    
    private TaskListener listener;
    private Task task;

    public ThreadPoolTask(TaskListener listener, Task task) {
        this.listener = listener;
        this.task = task;
    }

    public void prepare() {
        listener.taskStarted();
    }

    public void finish() {
        listener.taskFinished();
    }

    public void run() throws InterruptedException {
        try {
            prepare();
            task.performWork(); 
            finish();
        } catch (InterruptedException e) {
            throw e;
        }
    }

}
