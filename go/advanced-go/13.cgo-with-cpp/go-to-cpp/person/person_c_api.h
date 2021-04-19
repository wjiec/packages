#ifndef __PERSON_C_API_INCLUDED__
#define __PERSON_C_API_INCLUDED__

#include "../mem/mem_c_api.h"

// export from go
extern RefAddr cNewPerson(char*, int);
extern void cDeletePerson(RefAddr);
extern char *cPersonGetName(RefAddr);
extern int cPersonGetAge(RefAddr);
extern void cPersonSetName(RefAddr, char*);
extern void cPersonSetAge(RefAddr, int);

// import to go
void testPerson1();
void testPerson2();
void testPerson3();
void testPerson4();


#endif // __PERSON_C_API_INCLUDED__
