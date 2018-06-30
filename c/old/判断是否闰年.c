#include "stdio.h"
int main(void)
{
    int year;
    printf("请输入年份:");
    while (scanf("%d",&year) == 1)
    {
        if (year % 100 == 0)
        {
            if (year % 400 == 0)
                printf("是闰年.");
            else
                printf("不是闰年.");
        }
        else
        {
            if(year%4==0&&year%100!=0)
                printf("是闰年.");
            else
                printf("不是闰年.");
        }
        printf("\n请输入年份:");
    }
    printf("Done.\n");
    return 0;
}
            
    
    