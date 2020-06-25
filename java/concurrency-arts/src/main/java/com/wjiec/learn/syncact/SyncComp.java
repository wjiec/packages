package com.wjiec.learn.syncact;

public class SyncComp {

    synchronized void sync() { // flags: (0x0020) ACC_SYNCHRONIZED
        // monitorenter
        synchronized (SyncComp.class) {
        }
        // monitorexit [return]

        // monitorexit [exception]
    }

}
