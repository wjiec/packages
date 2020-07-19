package com.wjiec.learn.threading;

public class ThreadPriorityTest {

    private static volatile boolean isStart = false;

    public static void main(String[] args) throws InterruptedException {
        Job[] jobs = new Job[10];
        for (int i = 0; i < jobs.length; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            jobs[i] = new Job(priority);

            Thread thread = new Thread(jobs[i], "Thread-" + i);
            thread.setPriority(priority);
            thread.start();
        }

        isStart = true;
        Thread.sleep(1000);
        isStart = false;

        for (var job : jobs) {
            System.out.printf("job priority: %d, count: %d\n", job.priority, job.jobCount);
        }
    }

    static class Job implements Runnable {

        private int priority;

        private long jobCount;

        public Job(int priority) {
            this.priority = priority;
            this.jobCount = 0;
        }

        @Override
        public void run() {
            while (!isStart) {
                Thread.yield();
            }

            while (isStart) {
                jobCount += 1;
                Thread.yield();
            }
        }
    }

}

