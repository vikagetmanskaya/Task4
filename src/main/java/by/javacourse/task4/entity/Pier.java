package by.javacourse.task4.entity;

import by.javacourse.task4.util.IdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Pier {
    private static final Logger logger = LogManager.getLogger();
    private final long pierId;

    public Pier() {
        this.pierId = IdGenerator.generatePierId();
    }

    public long getPierId() {
        return pierId;
    }

    public void process(Ship ship){
        ship.setShipState(Ship.State.RUNNABLE);
        long shipId = ship.getShipId();
        int secondsCost = (int) ((Math.random() * 10) + 1);
        try {
            TimeUnit.SECONDS.sleep(secondsCost);
        } catch (InterruptedException e) {
            logger.error("Catch an exception in process with ship ", e);
            Thread.currentThread().interrupt();
        }
        ship.setShipState(Ship.State.TERMINATED);

    }
    //equals and hashCode
}
