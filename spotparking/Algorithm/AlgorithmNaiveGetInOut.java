package com.example.spotparking.Algorithm;

public class AlgorithmNaiveGetInOut extends AAlgorithm{

    public boolean getGotToHighSpeed(){
        return gotToHighSpeed;
    }

    @Override
    public int getTimeToEvacuate() {
        return timeToEvacuate;
    }

    @Override
    public void setTimeToEvacuate(int timeToEvacuate) {
        this.timeToEvacuate = timeToEvacuate;
    }

    public int getTimeCounter(){
        return timeCounter;
    }



    @Override
    public double getSpeed() {
        return gpsSpeed;
    }

    @Override
    public void setDistanceToPark(double distance) {
        distToPark = distance;
    }

    @Override
    public double getDistanceToPark() {
        return distToPark;
    }

    @Override
    public void setSpeed(double gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }



    @Override
    public boolean algorithm(boolean getInParking) {//getInParking true for it else false for getOutParking
        if(getInParking){
            return getInParking();
        }
        else{//getOutParking
            return getOutParking();
        }
    }


    @Override
    public boolean getInParking() {
        if(gpsSpeed >= 30.0){
            gotToHighSpeed = true;
        }
        if(gotToHighSpeed){
            if(gpsSpeed <= 2.0){//~stopped
                timeCounter += 5;//seconds
                if(timeCounter >= 45){//over 2 minutes in row
                    return true;
                }
            }
            else{
                timeCounter = 0;
            }
        }
        return false;
    }

    @Override
    public boolean getOutParking() {
        timeToEvacuate = (int)(distToPark/60.0);
        if(timeToEvacuate <= 1){
            return true;
        }
        return false;
    }
}
