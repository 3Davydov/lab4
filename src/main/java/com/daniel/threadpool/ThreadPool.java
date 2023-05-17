package com.daniel.threadpool;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool implements TaskListener {

    private final Set<PooledThread> availableThreads = new HashSet<>();
    private final Queue<ThreadPoolTask> taskQueue = new ConcurrentLinkedQueue<>();

    private AtomicInteger queueSize = new AtomicInteger(0);
    private AtomicInteger runningTasksCount = new AtomicInteger(0);

    public ThreadPool(int numOfThreads) {
        for (int i = 0; i < numOfThreads; i++) {
            PooledThread pooledThread = new PooledThread(taskQueue, i);
            availableThreads.add(pooledThread);
            pooledThread.start();
        }
    }

    @Override
    public void taskStarted() {
        queueSize.decrementAndGet();
        runningTasksCount.incrementAndGet();
    }

    @Override
    public synchronized void taskFinished() {
        runningTasksCount.decrementAndGet();
        notify();
    }

    public synchronized void addTask(Task task, int numOfTasks) throws InterruptedException {
        for (int i = 0; i < numOfTasks; i++) {
            taskQueue.add(new ThreadPoolTask(this, task));
            queueSize.incrementAndGet();
        }
        try {
            while (taskQueue.size() >= numOfTasks / 2) wait();
        } catch (InterruptedException e) {
            throw e;
        }
    }

    public int getQueueSize() {
        return queueSize.get();
    }

    public int getRunningTasksCount() {
        return runningTasksCount.get();
    }

    public void interrupt() {
        for (PooledThread thread : availableThreads) {
            thread.interrupt();
        }
    }
}
