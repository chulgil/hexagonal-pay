package me.chulgil.msa.common;

import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

@Configuration
public class CountDownLatchManager {

    private final Map<String, CountDownLatch> countDownLatchMap;

    private final Map<String, String> stringMap;

    public CountDownLatchManager() {
        countDownLatchMap = new ConcurrentHashMap<>();
        stringMap = new ConcurrentHashMap<>();

//        CountDownLatch latch = new CountDownLatch(1);
//        latch.countDown();
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

    }

    public void addCountDownLatch(String key) {
        this.countDownLatchMap.put(key, new CountDownLatch(1));
    }

    public void setDataForKey(String key, String data){
        this.stringMap.put(key, data);
    }

    public String getDataForKey(String key){
        return this.stringMap.get(key);
    }

    public CountDownLatch getCountDownLatch(String key) {
        return this.countDownLatchMap.get(key);
    }

}
