

#ifndef PLANEIMPLEMENTATION_H
#define PLANEIMPLEMENTATION_H

#include "interface.h"

class PlaneImplementation : public  Plane{

    private:
        int m_modelNumber;
        map<Jobs,int> m_CrewNeeded;
        int m_MaxFirstClass;
        int m_MaxEconomyClass;
        string m_Identification;
    public:
        PlaneImplementation(){}
        PlaneImplementation(int modelNumber,map<Jobs,int> CrewNeeded,int MaxFirstClass,int MaxEconomyClass,string Identification);
        int getModelNumber(){return m_modelNumber;}
        map<Jobs, int> getCrewNeeded(){return m_CrewNeeded;}
        int getMaxFirstClass(){return m_MaxFirstClass;}
        int getMaxEconomyClass(){return m_MaxEconomyClass;}
        string getID(){return m_Identification;}
};

#endif //PLANEIMPLEMENTATION
