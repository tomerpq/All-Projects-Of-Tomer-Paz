

#ifndef FLIGHTIMPLEMENTATION_H
#define FLIGHTIMPLEMENTATION_H
#include "interface.h"
class FlightImplementation : public Flight {
private:
    string m_Identification;
    int m_ModelNumber;
    list<Reservation*> m_Reservations;
    list<Employee*> m_AssignedCrew;
    Date* m_Date;
    string m_Source;
    string m_Destination;

public:
    FlightImplementation(){}
    FlightImplementation(string Identification,int ModelNumber,
    list<Reservation*> Reservations,
    list<Employee*> AssignedCrew,
    Date* date,
    string Source,
    string Destination);

    int getModelNumber(){
        return m_ModelNumber;
    }
    list<Reservation*> getReservations(){
        return m_Reservations;
    }
    list<Employee*> getAssignedCrew(){
        return m_AssignedCrew;
    }
    Date getDate(){
        return *m_Date;
    }
    string getSource(){
        return m_Source;
    }
    string getDestination(){
        return m_Destination;
    }
    string getID(){
        return m_Identification;
    }
    ~FlightImplementation();

};

#endif //FLIGHTIMPLEMENTATION
