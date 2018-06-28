#include "stdio.h"

int main(void)
{
	int nCount ,nMax ,nMin ,nTemp;
	
	scanf("%d" ,&nCount);
	
	scanf("%d" ,&nTemp);
	nMin = nMax = nTemp;
	nCount -= 1;
	
	while (nCount --)
	{
		scanf("%d" ,&nTemp);
		
		if (nMax <= nTemp)
		    nMax = nTemp;
		
		if (nMin >= nTemp)
		    nMin = nTemp;
	}
	
	printf("%d %d" ,nMin ,nMax);
	
	return 0;
}
