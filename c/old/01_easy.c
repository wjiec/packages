#include "stdio.h"
#include "stdlib.h"
#include "time.h"

void initArray(int * pi ,int nCount);
void printArray(int * pi ,int nCount);

int main(void) {
	int * pArray;
	int nCount ,nIndex;
	
	scanf("%d %d" ,&nCount ,&nIndex);
	
	initArray(pArray ,nCount);
	
	printArray(pArray ,nCount);
}

void initArray(int * pi ,int nCount) {
	int i;
	
	srand(time(NULL));
	
	pi = (int *)malloc(nCount * sizeof(int));
	
	if (pi == NULL) {
		sprintf(stderr ,"malloc error");
	}
	
	for (i = 0;i < nCount;i ++) {
		* (pi + i) = rand() % 100;
	}
}

void printArray(int * pi ,int nCount) {
	int i;
	
	for (i = 0;i < nCount;i ++) {
		printf("%d " ,* pi);
	}
	putchar('\n');
}