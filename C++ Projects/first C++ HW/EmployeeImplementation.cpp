#include "EmployeeImplementation.h"


    EmployeeImplementation::EmployeeImplementation(string Identification, int Seniority, int BirthYear,
                                                   Employee *Employer,
                                                   Jobs Title) {
        m_Identification = Identification;
        m_Seniority = Seniority;
        m_BirthYear = BirthYear;
        m_Employer = Employer;
        m_Title = Title;
    }