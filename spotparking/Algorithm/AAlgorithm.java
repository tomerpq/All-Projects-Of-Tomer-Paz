package com.example.spotparking.Algorithm;

public abstract class AAlgorithm implements IAlgorithm{
    //    private boolean blootoothUsed = false;
//    private boolean blootoothOn = false;
//    private boolean stepsDoneRecently = false;
     public int timeCounter = 0;
     public int timeToEvacuate = 0;
     public boolean gotToHighSpeed = false;
     public double gpsSpeed = 0.0;//in KMH
     public double distToPark = 0;
//    private int saidParked = 2;//2 for not sure(no answer), 0 said that he parked, 1 said that he not parked

    public AAlgorithm(){}

 //   public AAlgorithm(double speed, int saidParked){
//        this.GPSspeed = speed;
//        this.saidParked = saidParked;
//    }



    protected boolean getOutParking(){return false;}
    protected boolean getInParking(){return false;}

    //    public void setSpeed(double s){GPSspeed = s;}
//    public void setSaidParked(int sign){saidParked = sign;}
//    public void setBlootoothUsed(boolean sign){
//        blootoothUsed = sign;
//    }
//    public void setStepsDone(boolean sign){
//        stepsDoneRecently = sign;
//    }
//    public void setBlootoothOn(boolean sign){
//        blootoothOn = sign;
//    }
//
}
