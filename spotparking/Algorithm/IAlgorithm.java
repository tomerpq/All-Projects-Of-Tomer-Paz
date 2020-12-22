package com.example.spotparking.Algorithm;

public interface IAlgorithm {
    public void setSpeed(double gpsSpeed);
    public double getSpeed();
    public void setDistanceToPark(double distance);
    public double getDistanceToPark();
    public boolean getGotToHighSpeed();
    public int getTimeToEvacuate();
    public void setTimeToEvacuate(int timeToEvacuate);
    public int getTimeCounter();
        public boolean algorithm(boolean getInParking);
//    public void setBlootoothUsed(boolean sign);
//    public void setStepsDone(boolean sign);
//    public void setBlootoothOn();
}
