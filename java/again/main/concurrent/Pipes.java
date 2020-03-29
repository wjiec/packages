package main.concurrent;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Pipes {

    ReentrantLock lock;

    Condition check;

    HashMap<Integer, String> buffers;

    public Pipes(int count) {
        lock = new ReentrantLock();
        check = lock.newCondition();

        buffers = new HashMap<>();
    }

    public void write(int to, String message) {
        lock.lock();
        try {
            while (buffers.get(to) != null && !buffers.get(to).isEmpty()) {
                check.await();
            }

            buffers.put(to, message);
            check.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public String read(int from) {
        lock.lock();
        try {
            while (buffers.get(from) == null || buffers.get(from).isEmpty()) {
                check.await();
            }

            String message = buffers.put(from, "");
            check.signalAll();
            return message;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return "";
    }

}
