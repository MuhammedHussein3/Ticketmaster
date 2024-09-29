package com.ticket.service.config.threads;

import java.util.concurrent.ThreadFactory;

public class TicketThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread( Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(false);
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }
}
