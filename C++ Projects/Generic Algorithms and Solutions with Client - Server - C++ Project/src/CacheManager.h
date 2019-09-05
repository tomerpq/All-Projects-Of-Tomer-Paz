#ifndef MILESTONE2_CACHEMANAGER_H
#define MILESTONE2_CACHEMANAGER_H


#include "HashMapI.h"
/* our cache inteface*/
template<class T, class Answer>
class CacheManager{
    HashMapI<T>* myCacheManagerMap;
public:
    virtual Answer* getFromCache(T* t) = 0;
    virtual void addToCache(T* t, Answer*) = 0;
    virtual ~CacheManager() {};
};
#endif //MILESTONE2_CACHEMANAGER_H
