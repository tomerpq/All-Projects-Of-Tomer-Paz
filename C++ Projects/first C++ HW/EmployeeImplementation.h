
#ifndef EMPLOYEEIMPLEMENTATION_H
#define EMPLOYEEIMPLEMENTATION_H

#include "interface.h"
class EmployeeImplementation : public Employee {
    private:
        string m_Identification;
        int m_Seniority;
        int m_BirthYear;
        Employee* m_Employer;
        Jobs m_Title;

    public:

        EmployeeImplementation(string Identification, int Seniority,int BirthYear,Employee* Employer,Jobs Title);

        EmployeeImplementation(){};
        int getSeniority(){return m_Seniority;}
        int getBirthYear(){return m_BirthYear;}
        string getID(){return m_Identification;}

        Employee *getEmployer(){return m_Employer;}


        Jobs getTitle(){return m_Title;}

};

#endif//EMPLOYEEIMPLEMENTATION_H
