package by.javacourse.task4.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final Logger logger = LogManager.getLogger();
    private static final String FILE_PATH = "src/main/resources/port.properties";
    private static final String AMOUNT_OF_PIERS_PROPERTY = "amountOfPiers";
    private int amountOfPiers;
    private final Lock acquireLock = new ReentrantLock();
    private final ArrayDeque<Pier> availablePiers;
    private final ArrayDeque<Condition> waitingQueue = new ArrayDeque<>();
    private Port(){
    FileInputStream fileInputStream = null;
    try {
        fileInputStream = new FileInputStream(FILE_PATH);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    Properties properties = new Properties();
    try {
        properties.load(fileInputStream);
    } catch (IOException e) {
        //logger
    }
    amountOfPiers = Integer.parseInt(properties.getProperty(AMOUNT_OF_PIERS_PROPERTY));
        availablePiers = new ArrayDeque<>(amountOfPiers);
        for (int i = 0; i < amountOfPiers; i++) {
            availablePiers.add(new Pier());
        }

}
    private static class PortHolder {
        public static final Port INSTANCE = new Port();
    }

    public static Port getInstance()  {
       return PortHolder.INSTANCE;
    }
    public Pier getPier(){
        acquireLock.lock();
        if(availablePiers.isEmpty()){
            Condition condition = acquireLock.newCondition();
            waitingQueue.addLast(condition);
            try {
                condition.await();
            } catch (InterruptedException e) {
                logger.error("Catch an exception in getting pier ", e);
                Thread.currentThread().interrupt();
            }
        }
        Pier pier = availablePiers.removeFirst();
        acquireLock.unlock();
        return pier;
    }
    public void releasePier(Pier pier){
        acquireLock.lock();
        if(availablePiers.size() <= amountOfPiers){
            availablePiers.push(pier);
        }
        Condition condition = waitingQueue.pollFirst();
        if(condition != null){
            condition.signal();
        }
        acquireLock.unlock();
    }
    //equals and hashCode
}
