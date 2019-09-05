#include "PlaneImplementation.h"
PlaneImplementation::PlaneImplementation(int modelNumber, map<Jobs, int> CrewNeeded, int MaxFirstClass,
                                         int MaxEconomyClass, string Identification) {
    m_Identification = Identification;
    m_modelNumber = modelNumber;
    m_CrewNeeded = CrewNeeded;
    m_MaxEconomyClass = MaxEconomyClass;
    m_MaxFirstClass = MaxFirstClass;
}
