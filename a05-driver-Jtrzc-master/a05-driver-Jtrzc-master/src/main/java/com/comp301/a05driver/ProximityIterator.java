package com.comp301.a05driver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ProximityIterator implements Iterator<Driver> {
    private Position clientPosition;
    private int proximityRange;
    private Iterator<Driver> driverPool1;
    private Iterator<Driver> driverPool2;
    private Driver nextDriver;
    private Driver driverHolder;
    public ProximityIterator(Iterable<Driver> driverPool, Position clientPosition, int proximityRange){
        if(driverPool == null){
            throw new IllegalArgumentException();
        }
        if(clientPosition == null){
            throw new IllegalArgumentException();
        }
        this.driverPool1 = driverPool.iterator();
        driverPool2 = driverPool.iterator();
        this.clientPosition = clientPosition;
        this.proximityRange = proximityRange;
        nextDriver = null;
    }
    private void nextDriver(){
        if(driverPool1 == null){
            return;
        }
        if(nextDriver == null){
            while(driverPool1.hasNext()){
                if(clientPosition.getManhattanDistanceTo(driverPool1.next().getVehicle().getPosition()) <= proximityRange){
                    nextDriver = driverPool2.next();
                    break;
                }else{
                    driverPool2.next();
                }
            }
        }
    }
    @Override
    public boolean hasNext() {
        nextDriver();
        return nextDriver != null;
    }

    @Override
    public Driver next() {
        if(!hasNext()){
            throw new NoSuchElementException();
        }else if(hasNext()){
            driverHolder = nextDriver;
            nextDriver = null;
            return driverHolder;
        }
        return null;
    }

}
