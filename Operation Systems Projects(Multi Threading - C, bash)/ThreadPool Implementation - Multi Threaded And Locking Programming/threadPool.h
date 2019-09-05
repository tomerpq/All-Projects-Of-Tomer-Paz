/** Tomer Paz 315311365*/

#ifndef EX4_THREADPOOL_H
#define EX4_THREADPOOL_H

#include <unistd.h>
#include <stdlib.h>
#include "osqueue.h"
#include "pthread.h"

typedef struct thread_pool{
    int numOfThreads;
    int alive;
    int wait;
    int empty;
    int destroyed;
    OSQueue* tasks;//
    OSQueue* ParamsTasks;//
    pthread_t* threads;//
    pthread_mutex_t lock;//
    pthread_cond_t cond;

}ThreadPool;

typedef struct mission{
    void (*f)(void*);
}missionStruct;

ThreadPool* tpCreate(int numOfThreads);

void tpDestroy(ThreadPool* threadPool, int shouldWaitForTasks);

int tpInsertTask(ThreadPool* threadPool, void (*computeFunc) (void *), void* param);

#endif
