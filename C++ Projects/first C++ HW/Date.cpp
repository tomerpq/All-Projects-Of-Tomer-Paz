#include "interface.h"
#include <stdexcept>

Date::Date(string date):date{date} {
    if(date.size() != 10)
        throw std::invalid_argument("string size is not valid!");
    int x1 = date[0] - '0';
    int x2 = date[1] - '0';
    int x3 = date[2] - '0';
    int x4 = date[3] - '0';
    int x5 = date[5] - '0';
    int x6 = date[6] - '0';
    int x7 = date[8] - '0';
    int x8 = date[9] - '0';
    if((!(x1 >= 0 && x1 <= 9)) || (!(x2 >= 0 && x2 <= 9)) || (!(x3 >= 0 && x3 <= 9)) || (!(x4 >= 0 && x4 <= 9)))
        throw std::invalid_argument("date string is not valid!");
    if(x5 == 1){
        if((!(x6 >= 0 && x2 <= 2)))
            throw std::invalid_argument("date string is not valid!");
    }
    else if(x5 == 0){
        if((!(x6 >= 1 && x2 <= 9)))
            throw std::invalid_argument("date string is not valid!");
    }
    else
        throw std::invalid_argument("date string is not valid!");
    if(x7 == 0){
        if((!(x8 >= 1 && x8 <= 9)))
            throw std::invalid_argument("date string is not valid!");
    }
    else if(x7 == 1 || x7 == 2){
        if((!(x8 >= 0  && x8 <= 9)))
            throw std::invalid_argument("date string is not valid!");
    }
    else if(x7 == 3){
        if((!(x8 >= 0  && x8 <= 1)))
            throw std::invalid_argument("date string is not valid!");
    }
    else
        throw std::invalid_argument("date string is not valid!");
}

bool Date::operator<(const Date& d) const{
    int x;
    int y;
    x = this->getDate()[0] -'0';
    y = d.getDate()[0] -'0';
    if(x < y)
        return true;
    x = this->getDate()[1] -'0';
    y = d.getDate()[1] -'0';
    if(x < y)
        return true;
    x = this->getDate()[2] -'0';
    y = d.getDate()[2] -'0';
    if(x < y)
        return true;
    x = this->getDate()[3] -'0';
    y = d.getDate()[3] -'0';
    if(x < y)
        return true;
    x = this->getDate()[5] -'0';
    y = d.getDate()[5] -'0';
    if(x < y)
        return true;
    x = this->getDate()[6] -'0';
    y = d.getDate()[6] -'0';
    if(x < y)
        return true;
    x = this->getDate()[8] -'0';
    y = d.getDate()[8] -'0';
    if(x < y)
        return true;
    x = this->getDate()[9] -'0';
    y = d.getDate()[9] -'0';
    if(x < y)
        return true;
    return false;
}
bool Date::operator>(const Date& d) const{
    return (d < *this);
}
bool Date::operator==(const Date& d) const{
    return (!(*this < d) && !(*this > d));
}