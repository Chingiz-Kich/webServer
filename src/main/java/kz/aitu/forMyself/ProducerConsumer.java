package kz.aitu.forMyself;

import kz.aitu.Worker;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {
    private Queue<Worker> queue = new LinkedList();
    private final int LIMIT = 10;
    private final Object lock = new Object();

    public void produce(Worker worker) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() == LIMIT) {
                lock.wait();
            }

            queue.add(worker);
            System.out.println("Worker added id: " + worker.getId());
            lock.notify();
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait();
            }

            Worker polledWorker = queue.poll();
            System.out.println("Worker polled id: " + polledWorker.getId());
            lock.notify();
        }
    }
}
