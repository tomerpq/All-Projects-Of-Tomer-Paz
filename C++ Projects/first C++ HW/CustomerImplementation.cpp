#include "CustomerImplementation.h"
CustomerImplementation::CustomerImplementation(string FullName, int Priority, list<Reservation *> Reservations,
                                               string Identification) {
    m_FullName = FullName;
    m_Priority = Priority;
    m_Reservations = Reservations;
    m_Identification = Identification;
}