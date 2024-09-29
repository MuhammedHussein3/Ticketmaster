//package com.booking.service.service.locker;
//
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class DistributedLockService {
//    private final RedissonClient redissonClient;
//
//    public DistributedLockService(RedissonClient redissonClient) {
//        this.redissonClient = redissonClient;
//    }
//
//    public void executeWithLock(String lockKey, Runnable task) {
//        RLock lock = redissonClient.getLock(lockKey);
//        try {
//            // حاول تأمين القفل مع وقت انتظار ووقت انتهاء
//            if (lock.tryLock(10, 30, TimeUnit.SECONDS)) {
//                try {
//                    task.run(); // تنفيذ المهمة أثناء تأمين القفل
//                } finally {
//                    lock.unlock(); // افتح القفل دائماً في كتلة finally
//                }
//            } else {
//                // التعامل مع حالة عدم إمكانية تأمين القفل
//                throw new IllegalStateException("Could not acquire lock on key: " + lockKey);
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt(); // استعادة حالة الانقطاع
//        }
//    }
//
//}
