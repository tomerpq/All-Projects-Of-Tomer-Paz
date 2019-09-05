#ifndef MILESTONE2_HASHSETI_H
#define MILESTONE2_HASHSETI_H
#include <string>
#include <unordered_set>

/*hashSet to hashes some type T. to be implemented */
template<class I>
class HashSetI {
private:
    std::unordered_set<std::string> hashSet;//
public:
    virtual ~HashSetI(){}
    void insertToHashSet(I* i){
      std::string s = to_string(i);
      hashSet.insert(s);
    }
    bool contains(I* i){
      std::string s = to_string(i);
      return (!(hashSet.find(s) == hashSet.end()));
    }
    void clearSet(){
        hashSet.clear();
    }
    int getSize(){
      return hashSet.size();
    }
protected:
    virtual std::string to_string(I* i) = 0;//must be 1-1 func of i object to string
};

#endif

