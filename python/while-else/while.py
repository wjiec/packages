#!/usr/bin/python35

index = 0
maxCnt = int(input("please input a max number: "))
while index < 8:
    print(index, "Line")
    
    if index == maxCnt:
        break;
    index += 1
else:
    print("while-else...")
