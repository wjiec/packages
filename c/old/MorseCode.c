#include "stdio.h"
#include "stdbool.h"

int main(void)
{
    int nNum;
    bool five;
    
    while (scanf("%d",&nNum))
    {
        int dex;
        int tmp;
        
        if (nNum <= 5)
        {
            tmp = nNum; five = true;
        }
        else
        {
            tmp = nNum - 5; five = false;
        }
        
        for (dex=tmp;dex>0;dex --)
            putchar((five) ? '*' : '_');
            
        for (dex=tmp;dex<5;dex ++)
            putchar((five) ? '_' : '*');
    }
    
    return 0;
}
