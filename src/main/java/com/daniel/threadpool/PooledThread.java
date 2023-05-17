package com.daniel.threadpool;

import java.util.Queue;

public class PooledThread extends Thread {

    private Queue<ThreadPoolTask> taskQueue;

    public PooledThread(Queue<ThreadPoolTask> queue, int pid) {
        setName("PooledThread " + pid);
        taskQueue = queue;
    }

    @Override
    public void run() {
        while (! this.isInterrupted()) {
            try {
                ThreadPoolTask task = taskQueue.poll();
                if (task!= null) {
                    task.run();
                }
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() + " is interrupted");
    }
}
