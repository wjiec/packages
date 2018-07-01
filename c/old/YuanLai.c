#include "stdio.h"
#include "string.h"
#define T_PART    0
#define T_SUBASSY 1

typedef struct partinfo {
	int cost;
	int supplier;
} PartInfo;

typedef struct subassyinfo {
	int nParts;
	
	struct {
		char PartNo[10];
		int  Quan;
	} Parts[16];
} SubassyInfo;

typedef struct invrec {
	char strName[16];
	
	enum { PART ,SUBASSY } type;
	
	union {
		PartInfo part;
		SubassyInfo subassy;
	} info;
} Invrec;

int main(void)
{
	Invrec Rec;
	
	printf("%zd" ,sizeof(Rec));
	
	return 0;
}