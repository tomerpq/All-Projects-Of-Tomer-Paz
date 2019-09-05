

#ifndef RESERVATIONIMPLEMENTATION_H
#define RESERVATIONIMPLEMENTATION_H

#include "interface.h"

class ReservationImplementation : public Reservation{

    private:
        Customer* m_customer;
        Flight* m_flight;
        Classes m_class;
        int m_MaxBaggage;
        string m_Identification;

    public:
        ReservationImplementation(){}
        ReservationImplementation(Customer* customer,Flight* flight,Classes classs,int MaxBaggage,string Identification);
        Customer* getCustomer(){return m_customer;}
        Flight* getFlight(){return m_flight;}
        Classes getClass(){return m_class;}
        int getMaxBaggage(){return m_MaxBaggage;}
        string getID(){return m_Identification;}
};

#endif //RESERVATIONIMPLEMENTATION
