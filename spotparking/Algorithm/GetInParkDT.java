//package com.example.spotparking.Algorithm;
//
//class getInPark extends AGetInPark{
//
//
//
//    public getInPark(){super();}
//
//    public getInPark(double speed, int saidParked){
//        super(speed,saidParked);
//    }
//
//
//
//
//    @Override
//    int algorithm() {
//        if (saidParked == 2) {//case we are not sure he parked, main algorithm
//            if (blootoothUsed){
//                if(!blootoothOn){//blootooth disconnected
//                    if(stepsDoneRecently) {
//                        if(GPSspeed == 0.0){//if 0.0, and
//                            return 0;
//                        }
//                        else if(GPSspeed > 0.0){//if > 0.0,
//
//                        }
//                        else{//speed is -1.0 : underground, if he is walking there - maybe he parked underground - not good for us, did not park
//                            return 1;
//                        }
//                    }
//                    else{
//
//                    }
//                }
//                else{
//
//                }
//            }
//            else{
//
//            }
//        } else if (saidParked == 0) {//case said that he parked
//
//            //case said parked and maybe he is not:
//
//        } else if (saidParked == 1) {//case said that he not parked
//
//            //case said he not parked and maybe he is:
//
//        }
//    }
//
//}
