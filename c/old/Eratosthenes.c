#include "stdio.h"
#define LEN 1000

int nNum[LEN] = { 0 };

int main(void)
{
    int index;
    int t;
    
    for (index = 2;index < LEN;index++)
        nNum[index] = 1;
    
    for (index = 2;index < LEN;index++)
        if (nNum[index])       
            for(t = 2;index * t <= LEN;t ++)
                nNum[index * t] = 0;
    
    for (index = 2;index < LEN;index++)
       if (nNum[index])
            printf("%-5d",index);
    
    return 0;
}
            