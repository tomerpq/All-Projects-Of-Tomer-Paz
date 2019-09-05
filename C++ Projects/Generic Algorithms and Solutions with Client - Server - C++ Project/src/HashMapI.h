#ifndef MILESTONE2_HMI_H
#define MILESTONE2_HMI_H
/*hashMap that works with some type T. to be implemented */

#include <string>
#include <unordered_map>
#include <bits/unordered_map.h>

template<class I>
class HashMapI{
    std::unordered_map<std::string,int> hashMap;//my map
    int locationIndicator;
public:
    virtual ~HashMapI() {}

    HashMapI() {locationIndicator = 1;}

    int contains(I* i){// the int here symbols the place in our cache
        std::string s = i->to_string();
        auto got = hashMap.find (s);
        if(got == hashMap.end())//we didn't find the value
            return -1;
        else
            return got->second;
    }

    void insertToMap(I* i){
        std::string s = i->to_string();
        auto got = hashMap.find(s);
        if(got == hashMap.end()) {//we didn't find the value so we add to map
            hashMap.insert(std::pair<std::string,int>(s, locationIndicator));
            locationIndicator++;
        }
    }
};

#endif