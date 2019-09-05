/** Tomer Paz 315311365*/

#include "threadPool.h"
#include <stdio.h>
//need comments!!
static pthread_mutex_t tmpLock = PTHREAD_MUTEX_INITIALIZER;
int i = 0;
int j= 0;

void printErr(){
    write(2,"Error in system call\n",sizeof("Error in system call\n")-1);
    exit(1);
}

void* funct(void* arg){
    ThreadPool* th = (ThreadPool*)(arg);//ref
    missionStruct* ms;
    missionStruct* msg;
    int tmp;
    int wait;
    int isEmpty = 2;
    void (*f)(void*);
    void (*g)(void*);
    void* param;
    while(1){//wait for tasks
        pthread_mutex_lock(&th->lock);
        tmp = th->alive;
        wait = th->wait;
        printf("Start of outer While(1), i = %d\tthreadId = %d\n",++i,(int)pthread_self());
        pthread_mutex_unlock(&th->lock);
        if(!tmp){//dying threadPool:
            while(1){
                pthread_mutex_lock(&th->lock);
                if(!th->empty){
                    isEmpty = osIsQueueEmpty(th->tasks);
                    if(isEmpty){
                        th->empty = 1;
                    }
                }
                pthread_mutex_unlock(&th->lock);
                pthread_mutex_lock(&th->lock);
                printf("Start of inner While(1), isEmpty = %d\tthreadId = %d\n",th->empty,(int)pthread_self());
                if(th->empty) {
                    if(th->destroyed) {
                        printf("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!BREEAKKKK = %d\n",++j);

                        pthread_mutex_unlock(&th->lock);
                        return(NULL);
                    }
                    printf("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!BREEAKKKK = %d\n",++j);

                    pthread_mutex_unlock(&th->lock);
                    break;
                }
                pthread_mutex_unlock(&th->lock);
                if(wait){
                    pthread_mutex_lock(&th->lock);
                    if(th->destroyed) {
                        pthread_mutex_unlock(&th->lock);
                        return(NULL);
                    }
                    msg = (missionStruct*)(osDequeue(th->tasks));
                    param = osDequeue(th->ParamsTasks);
                    pthread_mutex_unlock(&th->lock);
                    if((!msg) || (!(msg->f)))
                        break;
                    g = msg->f;
                    free(msg);
                    (*g)(param);
                    printf("IN WAIT, i = %d\tthreadId = %d\n",++i,(int)pthread_self());
                }
                else{//just for depleting the queues
                    pthread_mutex_lock(&th->lock);
                    if(th->destroyed) {
                        pthread_mutex_unlock(&th->lock);
                        return(NULL);
                    }
                    msg = (missionStruct*)(osDequeue(th->tasks));
                    osDequeue(th->ParamsTasks);
                    free(msg);
                    pthread_mutex_unlock(&th->lock);
                    printf("DEPLETING QUEUE - GETTING FUNCTIONS, i = %d\tthreadId = %d\n",++i,(int)pthread_self());
                }
            }
            pthread_mutex_lock(&th->lock);
            if(th->destroyed) {
                pthread_mutex_unlock(&th->lock);
                return(NULL);
            }
            pthread_mutex_unlock(&th->lock);
            pthread_mutex_lock(&th->lock);
            osDestroyQueue(th->tasks);
            osDestroyQueue(th->ParamsTasks);
            printf("DESTROYING QUEUES, i = %d\tthreadId = %d\n",++i,(int)pthread_self());
            th->destroyed = 1;
            pthread_mutex_unlock(&th->lock);
            return(NULL);
        }
        pthread_mutex_lock(&th->lock);
        while(th->alive && osIsQueueEmpty(th->tasks)){
            pthread_cond_wait(&th->cond,&th->lock);
        }
        tmp = th->alive;
        printf("AFTER COND, i = %d\tthreadId = %d\n",++i,(int)pthread_self());
        pthread_mutex_unlock(&th->lock);
        if(!tmp)
            continue;
        pthread_mutex_lock(&th->lock);
        ms = (missionStruct*)(osDequeue(th->tasks));
        param = osDequeue(th->ParamsTasks);
        printf("GETTING FUNCTION IN NORMAL PLACE, i = %d\tthreadId = %d\n",++i,(int)pthread_self());

        f = ms->f;
        free(ms);
        pthread_mutex_unlock(&th->lock);
        printf("BEFORE FUNCTION IN NORMAL PLACE, i = %d\tthreadId = %d\n",++i,(int)pthread_self());
        (*f)(param);
        printf("AFTER FUNCTION IN NORMAL PLACE, i = %d\tthreadId = %d\n",++i,(int)pthread_self());
    }
}

ThreadPool* tpCreate(int numOfThreads){
    int i = 0;
    int j;
    int err;
    ThreadPool* th = malloc(sizeof(ThreadPool));
    if(!th){
        printErr();
    }
    th->numOfThreads = numOfThreads;
    th->alive = 1;
    th->empty = 0;
    th->destroyed = 0;
    th->tasks = osCreateQueue();
    if(!th->tasks){
        free(th);
        printErr();
    }
    th->ParamsTasks = osCreateQueue();
    if(!th->ParamsTasks){
        free(th->tasks);
        free(th);
        printErr();
    }
    if(pthread_mutex_init(&th->lock,NULL) != 0){
        free(th->tasks);
        free(th->ParamsTasks);
        free(th);
        printErr();
    }
    if(pthread_cond_init(&th->cond,NULL) != 0){
        free(th->tasks);
        free(th->ParamsTasks);
        pthread_mutex_destroy(&th->lock);
        free(th);
        printErr();
    }
    th->threads = malloc(sizeof(pthread_t)*numOfThreads);
    if(!th->threads){
        free(th->tasks);
        free(th->ParamsTasks);
        pthread_mutex_destroy(&th->lock);
        pthread_cond_destroy(&th->cond);
        free(th);
        printErr();
    }
    while(i < numOfThreads)
    {

        err = pthread_create(&th->threads[i], NULL, funct, (void*)(th));
        if (err != 0){
            if(th){
                if(th->tasks)
                    osDestroyQueue(th->tasks);
                if(th->ParamsTasks)
                    osDestroyQueue(th->ParamsTasks);
                for(j = 0; j < i; j++)
                    pthread_cancel(th->threads[j]);
                if(th->threads)
                    free(th->threads);
                pthread_mutex_destroy(&th->lock);
                pthread_cond_destroy(&th->cond);
                free(th);
            }
            printErr();
        }
        i++;
    }
    return th;
}

void tpDestroy(ThreadPool* threadPool, int shouldWaitForTasks){
    int tmp;
    int i;
    pthread_mutex_lock(&tmpLock);
    if(threadPool)
        tmp = 1;
    else
        tmp = 0;
    pthread_mutex_unlock(&tmpLock);
    if(!tmp){
        return;
    }
    pthread_mutex_lock(&threadPool->lock);
    tmp = threadPool->alive;
    pthread_mutex_unlock(&threadPool->lock);
    if(!tmp){
        return;
    }
    //make threadPool start dying:
    pthread_mutex_lock(&threadPool->lock);
    threadPool->alive = 0;
    if(shouldWaitForTasks != 0)
        threadPool->wait = 1;
    else
        threadPool->wait = 0;
    pthread_mutex_unlock(&threadPool->lock);
    pthread_cond_signal(&threadPool->cond);//so it starts dying.
    pthread_mutex_lock(&tmpLock);
    for (i = 0; i < threadPool->numOfThreads; i++)
        pthread_join(threadPool->threads[i], NULL);
    //mem clean:
    if(threadPool->threads)
        free(threadPool->threads);
    pthread_mutex_destroy(&threadPool->lock);
    pthread_cond_destroy(&threadPool->cond);
    free(threadPool);
    pthread_mutex_unlock(&tmpLock);

}

int tpInsertTask(ThreadPool* threadPool, void (*computeFunc) (void *), void* param){
    int tmp;
    missionStruct* ms;
    pthread_mutex_lock(&threadPool->lock);
    tmp = (threadPool->alive);
    pthread_mutex_unlock(&threadPool->lock);
    if(!tmp){
        return -1;
    }
    else{
        ms = malloc(sizeof(missionStruct));
        pthread_mutex_lock(&threadPool->lock);
        ms->f = computeFunc;
        osEnqueue(threadPool->tasks,(void*)(ms));
        osEnqueue(threadPool->ParamsTasks,param);
        pthread_mutex_unlock(&threadPool->lock);
        pthread_cond_signal(&threadPool->cond);
        return 0;
    }
}

