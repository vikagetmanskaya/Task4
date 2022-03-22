package by.javacourse.task4.entity;

import by.javacourse.task4.util.IdGenerator;

public class Ship extends Thread {
    private long shipId;
    private State shipState;
    public enum State{
        NEW, RUNNABLE, TERMINATED
    }
    public Ship(){
        this.shipId = IdGenerator.generateShipId();
        this.shipState = State.NEW;
    }

    public long getShipId() {
        return shipId;
    }

    public State getShipState() {
        return shipState;
    }

    public void setShipState(State shipState) {
        this.shipState = shipState;
    }

    @Override
    public void run() {
        Port port = Port.getInstance();
        Pier pier = port.getPier();
        pier.process(this);
        port.releasePier(pier);
    }
    //equals and hashCode
}
