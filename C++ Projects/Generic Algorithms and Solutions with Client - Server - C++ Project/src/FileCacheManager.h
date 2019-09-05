#ifndef MILESTONE2_FILECACHEMANAGER_H
#define MILESTONE2_FILECACHEMANAGER_H

#include <fstream>
#include "FastestPath.h"
#include "CacheManager.h"
#include <limits>
#include <mutex>
/*our cache, that works with files */
template<class T, class Answer>
class FileCacheManager : public CacheManager<T,Answer>{
    HashMapI<T> myCacheManagerMap;
    std::string FileName;
    std::ifstream& GotoLine(std::ifstream& file, unsigned int num);
public:
    FileCacheManager(const std::string FileName);

    Answer *getFromCache(T *t) override;

    void addToCache(T* t, Answer *answer) override;

    ~FileCacheManager() override;

    static void filePutContents(const std::string& name, const std::string& content, bool append);
};
//append content to the file method
template<class T, class Answer>
void FileCacheManager<T, Answer>::filePutContents(const std::string &name, const std::string &content, bool append) {
    std::ofstream outfile;
    if (append)
        outfile.open(name, std::ios_base::app);
    else
        outfile.open(name);
    outfile << content;
}
//method to move the stream sto the correct line in the file to get the information from
template<class T, class Answer>
std::ifstream& FileCacheManager<T, Answer>::GotoLine(std::ifstream& file, unsigned int num){
    file.seekg(std::ios::beg);
    for(int i=0; i < num - 1; ++i){
        file.ignore(std::numeric_limits<std::streamsize>::max(),'\n');
    }
    return file;
}
//getting the infromation from cache (file in our case)
template<class T, class Answer>
Answer *FileCacheManager<T, Answer>::getFromCache(T *t) {
    //getting the place in cache the file is
    int place = myCacheManagerMap.contains(t);
    //if we didnt get -1 we send the infromation back
    if(place != -1){
        std::ifstream file(FileName);
        GotoLine(file, place);
        std::string* line = new std::string();
        std::getline(file, *line);
        file.close();
        return line;
    }else
        //if the map returnes -1 it means the answer is not in cache so we return null pointer
        return nullptr;

}

//appends the answer to the end of cache and updates the map at what line the answer for this problem is
template<class T, class Answer>
void FileCacheManager<T, Answer>::addToCache(T* t, Answer *answer) {
    myCacheManagerMap.insertToMap(t);
    filePutContents(FileName,(*answer + "\n"),true);
}

template<class T, class Answer>
FileCacheManager<T, Answer>::~FileCacheManager() {
}

//deleting the pointer we  got and creating the file in our repository
template<class T, class Answer>
FileCacheManager<T, Answer>::FileCacheManager(const std::string FileName) {
    const char* FN = FileName.c_str();
    //deleting file from old runs
    remove(FN);
    this->FileName = FileName;
    std::ofstream o(FileName);
    o.close();
}

#endif //MILESTONE2_FILECACHEMANAGER_H
