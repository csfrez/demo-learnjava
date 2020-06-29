package com.csfrez.demo.lock;

import java.util.concurrent.atomic.AtomicInteger;


public class AtomicTest {

    final static int LOOP = 10000;

    public static void main(String[] args) throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();
        AtomicProducer producer = new AtomicProducer(counter);
        AtomicConsumer consumer = new AtomicConsumer(counter);
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println(counter.getInteger());
    }
}

class AtomicCounter {

    private AtomicInteger integer = new AtomicInteger();

    public AtomicInteger getInteger() {
        return integer;
    }

    public void setInteger(AtomicInteger integer) {
        this.integer = integer;
    }

    public void increment() {
        integer.incrementAndGet();
    }

    public void decrement() {
        integer.decrementAndGet();
    }
}

class AtomicProducer extends Thread {
    private AtomicCounter atomicCounter;

    public AtomicProducer(AtomicCounter atomicCounter) {
        this.atomicCounter = atomicCounter;
    }

    @Override
    public void run() {
        for (int j = 0; j < AtomicTest.LOOP; j++) {
            System.out.println(Thread.currentThread().getName() + "==>producer : " + atomicCounter.getInteger());
            atomicCounter.increment();
        }
    }
}

class AtomicConsumer extends Thread {
    private AtomicCounter atomicCounter;

    public AtomicConsumer(AtomicCounter atomicCounter) {
        this.atomicCounter = atomicCounter;
    }

    @Override
    public void run() {
        for (int j = 0; j < AtomicTest.LOOP; j++) {
            System.out.println(Thread.currentThread().getName() + "==>consumer : " + atomicCounter.getInteger());
            atomicCounter.decrement();
        }
    }
}
