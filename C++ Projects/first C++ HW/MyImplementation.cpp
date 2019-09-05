
#include "MyImplementation.h"
MyImplementation::~MyImplementation() {
    flightList.clear();
    planeList.clear();
    customerList.clear();
    reservationList.clear();
    employeeList.clear();
}
void MyImplementation::exit() {
    ofstream myfile ("Employee.txt");
    if (myfile.is_open())
    {
        for(std::list<std::string>::const_iterator i = employeeList.begin(); i != employeeList.end(); ++i)
        {
            myfile << *i + "\n";
        }
        myfile.close();
    }
    else cout << "Unable to open file";
    ofstream myfile2 ("Plane.txt");
    if (myfile2.is_open())
    {
        for(std::list<std::string>::const_iterator i = planeList.begin(); i != planeList.end(); ++i)
        {
            myfile2 << *i + "\n";
        }
        myfile2.close();
    }
    else cout << "Unable to open file";
    ofstream myfile3 ("Reservation.txt");
    if (myfile3.is_open())
    {
        for(std::list<std::string>::const_iterator i = reservationList.begin(); i != reservationList.end(); ++i)
        {
            myfile3 << *i + "\n";
        }
        myfile3.close();
    }
    else cout << "Unable to open file";
    ofstream myfile4 ("Customer.txt");
    if (myfile4.is_open())
    {
        for(std::list<std::string>::const_iterator i = customerList.begin(); i != customerList.end(); ++i)
        {
            myfile4 << *i + "\n";
        }
        myfile4.close();
    }
    else cout << "Unable to open file";
    ofstream myfile5 ("Flight.txt");
    if (myfile5.is_open())
    {
        for(std::list<std::string>::const_iterator i = flightList.begin(); i != flightList.end(); ++i)
        {
            myfile5 << *i + "\n";
        }
        myfile5.close();
    }
    else cout << "Unable to open file";

}
//getters: we use seperator "-" in the file. i have my order of reading == order of writing.
Employee* MyImplementation::getEmployee(string id) {
    string line,tmp;
    bool found = false;
    int lineNum = 0;
    ifstream myfile("Employee.txt");
    if (myfile.is_open())
    {
        while (getline(myfile,line))
        {
            lineNum++;
        }
        myfile.close();
    }
    else cout << "Unable to open file";
    string info[lineNum];
    lineNum = 0;
    ifstream myfile2("Employee.txt");
    if (myfile2.is_open())
    {
        while (getline(myfile2,line))
        {
            info[lineNum] = line;
            if(line.substr(0,line.find("-")).compare(id) == 0) {
                tmp = line;
                found = true;
            }
            lineNum++;
        }
        myfile2.close();
    }
    else cout << "Unable to open file";
    if(!found) {
        cout << "didn't find the employee!";
        return NULL;
    }
    tmp = tmp.substr(tmp.find("-")+1);
    int seniority = tmp[0] -'0';
    tmp = tmp.substr(tmp.find("-")+1);
    int BirthYear = atoi(tmp.substr(0,tmp.find("-")).c_str());
    tmp = tmp.substr(tmp.find("-")+1);
    string employerId = tmp.substr(0,id.size());
    tmp = tmp.substr(tmp.find("-")+1);
    Jobs job;
    if(tmp.compare("MANAGER") == 0)
        job = MANAGER;
    else if(tmp.compare("NAVIGATOR") == 0)
        job = NAVIGATOR;
    else if(tmp.compare("FLY_ATTENDANT") == 0)
        job = FLY_ATTENDANT;
    else if(tmp.compare("PILOT") == 0)
        job = PILOT;
    else
        job = OTHER;
    return new EmployeeImplementation(id,seniority,BirthYear,recAddEmployee(info,employerId),job);
}
Employee* MyImplementation::recAddEmployee(string info[],string id) {
    if(id.compare("~") == 0)
        return NULL;
    int i;
    string line;
    for(i = 0; i < info->size(); i++){
        line = info[i];
        if(line.substr(0,line.find("-")).compare(id) == 0){
            string tmp(line);
            tmp = tmp.substr(tmp.find("-")+1);
            int seniority = tmp[0] -'0';
            tmp = tmp.substr(tmp.find("-")+1);
            int BirthYear = atoi(tmp.substr(0,tmp.find("-")).c_str());
            tmp = tmp.substr(tmp.find("-")+1);
            string employerId = tmp.substr(0,id.size());
            tmp = tmp.substr(tmp.find("-")+1);
            Jobs job;
            if(tmp.compare("MANAGER") == 0)
                job = MANAGER;
            else if(tmp.compare("NAVIGATOR") == 0)
                job = NAVIGATOR;
            else if(tmp.compare("FLY_ATTENDANT") == 0)
                job = FLY_ATTENDANT;
            else if(tmp.compare("PILOT") == 0)
                job = PILOT;
            else
                job = OTHER;
            return new EmployeeImplementation(id,seniority,BirthYear,recAddEmployee(info,employerId),job);//for adding employers!
        }
    }
}
Plane* MyImplementation::getPlane(string id) {
    string line,tmp;
    bool found = false;
    ifstream myfile("Plane.txt");
    if (myfile.is_open())
    {
        while (getline(myfile,line))
        {
            if(line.substr(0,line.find("-")).compare(id) == 0) {
                tmp = line;
                found = true;
            }
        }
        myfile.close();
    }
    else cout << "Unable to open file";
    if(!found){
        cout << "didn't find the plane!";
        return NULL;
    }//the file info getting:
    tmp = tmp.substr(tmp.find("-")+1);
    int MaxFirst = atoi(tmp.substr(0,tmp.find("-")).c_str());
    tmp = tmp.substr(tmp.find("-")+1);
    int MaxSecond = atoi(tmp.substr(0,tmp.find("-")).c_str());
    tmp = tmp.substr(tmp.find("-")+1);
    int modelNum = atoi(tmp.substr(0,tmp.find("-")).c_str());
    tmp = tmp.substr(tmp.find("-")+1);
    int numManager = atoi(tmp.substr(0,tmp.find("-")).c_str());
    tmp = tmp.substr(tmp.find("-")+1);
    int numNavigator = atoi(tmp.substr(0,tmp.find("-")).c_str());
    tmp = tmp.substr(tmp.find("-")+1);
    int numFly_Attendant = atoi(tmp.substr(0,tmp.find("-")).c_str());
    tmp = tmp.substr(tmp.find("-")+1);
    int numPilot = atoi(tmp.substr(0,tmp.find("-")).c_str());
    tmp = tmp.substr(tmp.find("-")+1);
    int numOther = atoi(tmp.c_str());
    map<Jobs,int> crewMembers;
    crewMembers.insert(std::pair<Jobs,int>(MANAGER,numManager));
    crewMembers.insert(std::pair<Jobs,int>(NAVIGATOR,numNavigator));
    crewMembers.insert(std::pair<Jobs,int>(FLY_ATTENDANT,numFly_Attendant));
    crewMembers.insert(std::pair<Jobs,int>(PILOT,numPilot));
    crewMembers.insert(std::pair<Jobs,int>(OTHER,numOther));
    return new PlaneImplementation(modelNum,crewMembers,MaxFirst,MaxSecond,id);
}

/*didn't manage to finish  in time :( */
Flight* MyImplementation::getFlight(string id) {
    string line,tmp;
    bool found = false;
    ifstream myfile("Flight.txt");
    if (myfile.is_open())
    {
        while (getline(myfile,line))
        {
            if(line.substr(0,line.find("-")).compare(id) == 0) {
                tmp = line;
                found = true;
            }
        }
        myfile.close();
    }
    else cout << "Unable to open file";
    if(!found){
        cout << "didn't find the flight!";
        return NULL;
    }
    tmp = tmp.substr(tmp.find("-")+1);
    int modelNumber = atoi(tmp.substr(0,tmp.find("-")).c_str());
    tmp = tmp.substr(tmp.find("-")+1);
    list<Reservation*> res;
    std::list<Reservation*>::iterator it1;
    ifstream myfile2("Reservation.txt");
    string tmp2;
    if (myfile2.is_open()) {
        while (getline(myfile2, line)) {
            tmp2 =  line;
            string idres = tmp2.substr(line.find("-")+1);
            line = line.substr(line.find("-")+1);
            line = line.substr(line.find("-")+1);
            line = line.substr(line.find("-")+1);
                tmp2 = tmp2.substr(tmp2.find("-")+1);
                int MaxBaggage = atoi(tmp2.substr(0,tmp2.find("-")).c_str());
                tmp2 = tmp2.substr(tmp2.find("-")+1);
                int classes = atoi(tmp2.substr(0,tmp2.find("-")).c_str());
                tmp2 = tmp2.substr(tmp2.find("-")+1);
                string thisFlight = tmp2.substr(0,tmp2.find("-"));
                tmp2 = tmp2.substr(tmp2.find("-")+1);
                string thisCustomer = tmp2;
                if(line.substr(0,line.find("-")).compare(id) == 0){
                    if(classes == 1){
                        res.insert(it1,new ReservationImplementation(getCustomer(thisCustomer),getFlight(thisFlight),FIRST_CLASS,MaxBaggage,idres));
                    }
                    else
                        res.insert(it1,new ReservationImplementation(getCustomer(thisCustomer),getFlight(thisFlight),SECOND_CLASS,MaxBaggage,idres));
                }

        }
        myfile2.close();
    }
    else cout << "Unable to open file";
    list<Employee*> emp;
    Date* date = new Date(tmp.substr(0,tmp.find("-")));
    tmp = tmp.substr(tmp.find("-")+1);
    string source = tmp.substr(0,tmp.find("-"));
    tmp = tmp.substr(tmp.find("-")+1);
    string dest = tmp;
    return new FlightImplementation(id,modelNumber,res,emp,date,source,dest);

}

Customer* MyImplementation::getCustomer(string id) {
    string line,tmp;
    bool found = false;
    ifstream myfile("Customer.txt");
    if (myfile.is_open())
    {
        while (getline(myfile,line))
        {
            if(line.substr(0,line.find("-")).compare(id) == 0) {
                tmp = line;
                found = true;
            }
        }
        myfile.close();
    }
    else cout << "Unable to open file";
    if(!found){
        cout << "didn't find the customer!";
        return NULL;
    }
    tmp = tmp.substr(tmp.find("-")+1);
    string fullName = tmp.substr(0,tmp.find("-"));
    tmp = tmp.substr(tmp.find("-")+1);
    list<Reservation*> res;
    std::list<Reservation*>::iterator it1;
    ifstream myfile2("Reservation.txt");
    string tmp2;
    if (myfile2.is_open()) {
        while (getline(myfile2, line)) {
            tmp2 =  line;
            string idres = tmp2.substr(line.find("-")+1);
            line = line.substr(line.find("-")+1);
            line = line.substr(line.find("-")+1);
            line = line.substr(line.find("-")+1);
            line = line.substr(line.find("-")+1);
            tmp2 = tmp2.substr(tmp2.find("-")+1);
            int MaxBaggage = atoi(tmp2.substr(0,tmp2.find("-")).c_str());
            tmp2 = tmp2.substr(tmp2.find("-")+1);
            int classes = atoi(tmp2.substr(0,tmp2.find("-")).c_str());
            tmp2 = tmp2.substr(tmp2.find("-")+1);
            string thisFlight = tmp2.substr(0,tmp2.find("-"));
            tmp2 = tmp2.substr(tmp2.find("-")+1);
            string thisCustomer = tmp2;
            if(line.compare(id) == 0){
                if(classes == 1){
                    res.insert(it1,new ReservationImplementation(getCustomer(thisCustomer),getFlight(thisFlight),FIRST_CLASS,MaxBaggage,idres));
                }
                else
                    res.insert(it1,new ReservationImplementation(getCustomer(thisCustomer),getFlight(thisFlight),SECOND_CLASS,MaxBaggage,idres));
            }

        }
        myfile2.close();
    }
    else cout << "Unable to open file";
    int priority = atoi(tmp.c_str());
    return new CustomerImplementation(fullName,priority,res,id);
}
Reservation* MyImplementation::getReservation(string id) {
    string line,tmp;
    bool found = false;
    ifstream myfile("Reservation.txt");
    if (myfile.is_open())
    {
        while (getline(myfile,line))
        {
            if(line.substr(0,line.find("-")).compare(id) == 0) {
                tmp = line;
                found = true;
            }
        }
        myfile.close();
    }
    else cout << "Unable to open file";
    if(!found){
        cout << "didn't find the Resevations!";
        return NULL;
    }
    tmp = tmp.substr(tmp.find("-")+1);
    int MaxBaggage = atoi(tmp.substr(0,tmp.find("-")).c_str());
    tmp = tmp.substr(tmp.find("-")+1);
    int classes = atoi(tmp.substr(0,tmp.find("-")).c_str());
    tmp = tmp.substr(tmp.find("-")+1);
    string thisFlight = tmp.substr(0,tmp.find("-"));
    tmp = tmp.substr(tmp.find("-")+1);
    string thisCustomer = tmp;
    if(classes == 1){
        return new ReservationImplementation(getCustomer(thisCustomer),getFlight(thisFlight),FIRST_CLASS,MaxBaggage,id);
    }
    else
        return new ReservationImplementation(getCustomer(thisCustomer),getFlight(thisFlight),SECOND_CLASS,MaxBaggage,id);
}


//adders::
Employee* MyImplementation::addEmployee(int seniority, int birth_year, string employer_id, Jobs title) {
    if(!(birth_year >= 0 && birth_year <= 9999))
        throw std::invalid_argument("problem adding employee");
    string s = std::to_string(IDGENERATOR) + "-" + std::to_string(seniority) + "-" + std::to_string(birth_year) + "-";
    if(employer_id.compare("") == 0)
        s = s + "~";
    else
        s = s + employer_id;
    s = s + "-";
    s = s + std::to_string(title);
    it = employeeList.end();
    employeeList.insert(it,s);
    IDGENERATOR++;//for next builds
}
Plane* MyImplementation::addPlane(int model_number, map<Jobs, int> crew_needed, map<Classes, int> max_passangers) {
    string s = std::to_string(IDGENERATOR) + "-" + std::to_string(max_passangers.at(FIRST_CLASS)) + "-" +
               std::to_string(max_passangers.at(SECOND_CLASS)) + "-" + std::to_string(model_number) + "-" +
               std::to_string(crew_needed.at(MANAGER)) + "-" + std::to_string(crew_needed.at(NAVIGATOR)) + "-" +
               std::to_string(crew_needed.at(FLY_ATTENDANT)) + "-" + std::to_string(crew_needed.at(PILOT)) + "-" +
               std::to_string(crew_needed.at(OTHER));
    it = planeList.end();
    planeList.insert(it, s);
    IDGENERATOR++;//for next builds
}
Flight* MyImplementation::addFlight(int model_number, Date date, string source, string destination) {
    string s = std::to_string(IDGENERATOR) + "-" + std::to_string(model_number) + "-" + date.getDate() + "-" +
            source + "-" + destination;
    it = flightList.end();
    flightList.insert(it, s);
    IDGENERATOR++;//for next builds
}
Customer* MyImplementation::addCustomer(string full_name, int priority) {
    string s = std::to_string(IDGENERATOR) + "-" + full_name + "-" + std::to_string(priority);
    it = customerList.end();
    customerList.insert(it, s);
    IDGENERATOR++;//for next builds
}
Reservation* MyImplementation::addResevation(string customerId, string flightId, Classes cls, int max_baggage) {
    if(flightList.empty())
        throw std::invalid_argument("problem adding reservation");
    string s = std::to_string(IDGENERATOR) + "-" + std::to_string(max_baggage) + "-";
    if(cls == FIRST_CLASS)
        s = s + "1";
    else
        s = s + "2";
    s = s + "-";
    s = s + flightId + "-";
    s = s + customerId;
    it = reservationList.end();
    reservationList.insert(it, s);
    IDGENERATOR++;//for next builds
}
