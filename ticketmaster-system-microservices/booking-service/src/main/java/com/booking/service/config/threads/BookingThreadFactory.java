package com.booking.service.config.threads;

import java.util.concurrent.ThreadFactory;

public class BookingThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread( Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(false);
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }
}
