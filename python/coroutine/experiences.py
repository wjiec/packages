from itertools import count

index = count(1)

def coroutine():
    r0 = yield 0 # ==> return 0, stop(wait recv from send) --------------------------------------------------- [3]
    
    print(next(index), '[COROUTINE] recv message[%s]-0' % ( r0 )) # recv data, value is `r0` ----------------- [6]
    r1 = yield 1 # ==> return 1, stop(wait recv from send) --------------------------------------------------- [7]
    
    print(next(index), '[COROUTINE] recv message[%s]-1' % ( r1 )) # ------------------------------------------ [10]
    r2 = yield 2 # ------------------------------------------------------------------------------------------- [11]
    
    print(next(index), '[COROUTINE] recv message[%s]-2' % ( r2 )) # ------------------------------------------ [14]
    r3 = yield 3 # ------------------------------------------------------------------------------------------- [15]
    
    print(next(index), '[COROUTINE] recv message[%s]-3' % ( r3 )) # NEVER EXECUTE!

if __name__ == '__main__':
    c = coroutine() # generator instance --------------------------------------------------------------------- [1]
    
    v = c.send(None) # v = 0 --------------------------------------------------------------------------------- [2]
    print(next(index), '[MAIN] from coroutine return value[%s]-A' % ( v )) # --------------------------------- [4]
    
    v = c.send('A') # v = 1, r0 = 'A' ------------------------------------------------------------------------ [5]
    print(next(index), '[MAIN] from coroutine return value[%s]-B' % ( v )) # --------------------------------- [8]
    
    v = c.send('B') # v = 2, r1 = 'B' ------------------------------------------------------------------------ [9]
    print(next(index), '[MAIN] from coroutine return value[%s]-C' % ( v )) # --------------------------------- [12]
    
    v = c.send('C') # v = 3, r1 = 'C' # ---------------------------------------------------------------------- [13]
    print(next(index), '[MAIN] from coroutine return value[%s]-D' % ( v )) # ----------------------------------[16]
    
    try:
        v = c.send('D') # StopIteration
    except StopIteration:
        print(next(index), 'StopIteration')