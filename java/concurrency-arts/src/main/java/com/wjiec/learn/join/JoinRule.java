package com.wjiec.learn.join;

public class JoinRule implements Runnable {

    private SharedObj obj;

    public JoinRule(SharedObj obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        try {
            int v = obj.get();
            System.out.printf("read from shared: %d\n", v);
            obj.set(obj.get() + 1);
            // 10s
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getId());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
