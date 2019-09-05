#include "ReservationImplementation.h"
ReservationImplementation::ReservationImplementation(Customer *customer, Flight *flight, Classes classs, int MaxBaggage,
                                                     string Identification) {
    m_customer = customer;
    m_flight = flight;
    m_class = classs;
    m_MaxBaggage = MaxBaggage;
    m_Identification = Identification;
}
