

#ifndef CUSTOMERIMPLEMENTATION_H
#define CUSTOMERIMPLEMENTATION_H

#include "interface.h"

class CustomerImplementation : public Customer{

    private:
        string m_FullName;
        int m_Priority;
        list<Reservation*> m_Reservations;
        string m_Identification;
    public:

        CustomerImplementation(){}
        CustomerImplementation(string FullName,int Priority,list<Reservation*> Reservations,string Identification);
        string getFullName(){return m_FullName;}
        int getPriority(){return m_Priority;}
        list<Reservation*> getReservations(){return m_Reservations;}
        string getID(){return m_Identification;}
};


#endif //CUSTOMERIMPLEMENTATION
