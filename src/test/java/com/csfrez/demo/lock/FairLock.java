package com.csfrez.demo.lock;

import java.util.concurrent.locks.ReentrantLock;

public class FairLock extends Thread {

    private ReentrantLock lock = new ReentrantLock(true);

    public void fairLock() {
        try {
            lock.lock();
            long random = 0; //Double.valueOf(Math.random()*1000).longValue();
            System.out.println(Thread.currentThread().getName() + "正在持有锁==>" + random);
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放了锁");
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        FairLock fairLock = new FairLock();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + "启动");
            fairLock.fairLock();
        };
        Thread[] thread = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }
}
