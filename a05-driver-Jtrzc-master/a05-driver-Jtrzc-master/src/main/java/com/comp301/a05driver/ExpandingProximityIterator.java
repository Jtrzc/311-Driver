package com.comp301.a05driver;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ExpandingProximityIterator implements Iterator<Driver> {

    private int expansionStep;
    private Position clientPosition;
    private Iterable<Driver> driverPoolOG;
    private Iterator<Driver> driverPoolList;
    private Iterator<Driver> driverPool2;
    private Iterator<Driver> driverPoolAmount;
    private int counter;
    private int counter2;
    private boolean flag = false;
    private boolean hasNextHold = false;
    private Driver nextDriver;
    private Driver driverHolder;
    private int stepMultiplier = 0;
    private int upperBounds =0;
    private int lowerBounds;
    public ExpandingProximityIterator(Iterable<Driver> driverPool, Position clientPosition, int expansionStep){
        if(driverPool == null){
            throw new IllegalArgumentException();
        }
        if(clientPosition == null){
            throw new IllegalArgumentException();
        }
        if(expansionStep < 0){
            throw new IllegalArgumentException();
        }
        nextDriver = null;
        driverPoolOG = driverPool;
        driverPoolList = driverPoolOG.iterator();
        driverPool2 = driverPoolOG.iterator();
        driverPoolAmount = driverPoolOG.iterator();
        while(driverPoolAmount.hasNext()){
            counter++;
            driverPoolAmount.next();
        }
        this.expansionStep = expansionStep;
        this.clientPosition = clientPosition;

    }

    private void nextDriver(){

        if(nextDriver == null){
            while(driverPoolList.hasNext()){
                int holder = (clientPosition.getManhattanDistanceTo(driverPoolList.next().getVehicle().getPosition()));

                lowerBounds = (expansionStep*stepMultiplier)+1;
                if( (holder<= lowerBounds)  && (holder > upperBounds )){
                    nextDriver = driverPool2.next();
                    hasNextHold = true;
                    counter2++;
                    if(!driverPool2.hasNext()){
                        stepMultiplier = stepMultiplier+1;
                        driverPoolList = driverPoolOG.iterator();
                        driverPool2 = driverPoolOG.iterator();
                        upperBounds = lowerBounds;
                    }
                    break;
                }else{
                    flag = true;
                    driverPool2.next();
                }
                if((!driverPool2.hasNext() && (flag))){
                    stepMultiplier = stepMultiplier+1;
                    driverPoolList = driverPoolOG.iterator();
                    driverPool2 = driverPoolOG.iterator();
                    flag = false;
                    upperBounds = lowerBounds;
                }if(counter2 == counter){
                    flag = false;
                    nextDriver = null;
                    break;
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
