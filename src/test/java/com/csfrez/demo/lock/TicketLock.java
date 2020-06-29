package com.csfrez.demo.lock;

import java.util.concurrent.atomic.AtomicInteger;

public class TicketLock {

    // 队列票据(当前排队号码)
    private AtomicInteger queueNum = new AtomicInteger();
    // 出队票据(当前需等待号码)
    private AtomicInteger dueueNum = new AtomicInteger();
    private ThreadLocal<Integer> ticketLocal = new ThreadLocal<>();

    public void lock() {
        int currentTicketNum = dueueNum.incrementAndGet();
        // 获取锁的时候，将当前线程的排队号保存起来
        ticketLocal.set(currentTicketNum);
        while (currentTicketNum != queueNum.get()) {
            // doSomething...
        }
    }

    // 释放锁：从排队缓冲池中取
    public void unLock() {
        Integer currentTicket = ticketLocal.get();
        queueNum.compareAndSet(currentTicket, currentTicket + 1);
    }
}
