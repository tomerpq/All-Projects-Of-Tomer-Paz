

#ifndef MYIMPLEMENTATION_H
#define MYIMPLEMENTATION_H

#include "interface.h"
#include "CustomerImplementation.h"
#include "EmployeeImplementation.h"
#include "FlightImplementation.h"
#include "PlaneImplementation.h"
#include "ReservationImplementation.h"
#include <iostream>
#include <fstream>
#include <stdexcept>
#include <vector>


class MyImplementation : public Ex2,public CustomerImplementation,public EmployeeImplementation,public FlightImplementation
        ,public PlaneImplementation,public ReservationImplementation{

private:
    list<string> employeeList;
    list<string> planeList;
    list<string> flightList;
    list<string> customerList;
    list<string> reservationList;
    std::list<string>::iterator it;
    Employee* recAddEmployee(string info[],string id);
    int IDGENERATOR = 0;
public:
    MyImplementation(){};
    Employee* addEmployee(
            int seniority,
            int birth_year,
            string employer_id,
            Jobs title);

    Employee* getEmployee(string id);

    Plane* addPlane(
            int model_number,
            map<Jobs, int> crew_needed,
            map<Classes, int> max_passangers);

    Plane* getPlane(string id);

    Flight* addFlight(
            int model_number,
            Date date,
            string source,
            string destination);

    Flight* getFlight(string id);

    Customer* addCustomer(
            string full_name,
            int priority);

    Customer* getCustomer(string id);

    Reservation* addResevation(
            string customerId,
            string flightId,
            Classes cls,
            int max_baggage);

    Reservation* getReservation(string id);

    void exit();

    ~MyImplementation();
};

#endif //TARGIL2_MYIMPLEMENTATION_H
