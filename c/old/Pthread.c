#include "stdio.h"
#include "pthread.h"

void Func1(void * pv);
void Func2(void * pv);

int main(void)
{
	pthread_t p1;   // 声明线程ID
	pthread_t p2;
	
	// 创建线程
	pthread_create(&p1 ,NULL ,Func1 ,NULL);
	pthread_create(&p2 ,NULL ,Func2 ,NULL);
	
	pthread_join(p1 ,NULL);   // 等待线程
	pthread_join(p2 ,NULL);
	
	return 0;
}

void Func1(void * pv)
{
	int i;
	
	puts("This is Func1 process");
	scanf("%d" ,&i);
}

void Func2(void * pv)
{
	int i;
	
	puts("This is Func2 process");
	scanf("%d" ,&i);
}