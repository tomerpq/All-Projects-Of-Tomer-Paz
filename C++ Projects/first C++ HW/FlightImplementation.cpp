#include "FlightImplementation.h"

FlightImplementation::FlightImplementation(string Identification, int ModelNumber, list<Reservation *> Reservations,
                                           list<Employee *> AssignedCrew, Date* date, string Source,
                                           string Destination) {
    m_Destination = Destination;
    m_Source = Source;
    m_AssignedCrew = AssignedCrew;
    m_Reservations = Reservations;
    m_Identification = Identification;
    m_Date = date;
    m_ModelNumber = ModelNumber;
}
FlightImplementation::~FlightImplementation() {
    delete m_Date;
}