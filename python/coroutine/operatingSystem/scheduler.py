'''Operating System Scheduler

'''

from queue import Queue
from task import Task
from systemCall import *
import time
import select

class Scheduler(object):
    def __init__(self):
        self.ready   = Queue()
        self.taskMap = {}
        self.waitMap = {}
        self.waitrl  = {}
        self.waitwl  = {}

    def new(self, target):
        task = Task(target)
        self.taskMap[task.tid] = task

        self.schedule(task)
        return task.tid

    def schedule(self, task):
        self.ready.put(task)

    def terminate(self, task):
        task.terminate()
        if task.tid in self.taskMap:
            del self.taskMap[task.tid]
            print('[SCHEDULER STATE] tid = %s terminate.' % ( task.tid ))

        for wTask in self.waitMap.pop(task.tid, []):
            self.schedule(wTask)

    def waitForRead(self, fd, task):
        self.waitrl[fd] = task

    def waitForWrite(self, fd, task):
        self.waitwl[fd] = task

    def IOPoll(self, timeout = None):
        if self.waitrl or self.waitwl:
            rl, wl, el = select.select(self.waitrl, self.waitwl, [], timeout)
            for fd in rl:
                self.schedule(self.waitrl.pop(fd))
            for fd in wl:
                self.schedule(self.waitwl.pop(fd))

    def IOPollTask(self):
        print('[TASK IOPOLL] IO Polling Initialized')
        while True:
            if self.ready.empty():
                self.IOPoll(None)
            else:
                self.IOPoll(0)
            yield

    def wait(self, task, waitTaskId):
        if waitTaskId in self.taskMap:
            self.waitMap.setdefault(waitTaskId, []).append(task)
        else:
            return False
        return True

    def mainLoop(self):
        self.new(self.IOPollTask())
        while self.taskMap:
            task = self.ready.get()
            try:
                result = task.run()
                if isinstance(result, SystemCall):
                    result.task  = task
                    result.sched = self
                    result.handler()
                    continue
            except StopIteration:
                self.terminate(task)
                continue
            self.schedule(task)

if __name__ == '__main__':
    def t1():
        print('[TASK T1] Initialized')
        tid = yield GetTid()
        print('[TASK T1] step1, SystemCall(GetTid) = %s' % ( tid ))
        yield
        print('[TASK T1] step2, SystemCall(GetTid) = %s' % ( tid ))
        yield
        print('[TASK T1] step3, SystemCall(GetTid) = %s' % ( tid ))

    def t1c():
        print('[TASK T1C] Initialized')
        tid = yield GetTid()
        print('[TASK T1C] step1, SystemCall(GetTid) = %s' % ( tid ))
        yield
        print('[TASK T1C] step2, SystemCall(GetTid) = %s' % ( tid ))
        yield
        print('[TASK T1C] step3, SystemCall(GetTid) = %s' % ( tid ))

    def t2():
        print('[TASK T2] Initialized')
        tid = yield GetTid()
        print('[TASK T2] step1, SystemCall(GetTid) = %s' % ( tid ))
        tid = yield CreateTask(t1c())
        print('[TASK T2] step2, SystemCall(GetTid) = %s' % ( tid ))

    def t3():
        print('[TASK T3] Initialized')
        tid = yield GetTid()
        print('[TASK T3] step1, SystemCall(GetTid) = %s' % ( tid ))
        tid = yield GetTid()
        print('[TASK T3] step2, SystemCall(GetTid) = %s' % ( tid ))
        tid = yield KillTask(4)
        print('[TASK T3] step3, SystemCall(GetTid) = %s' % ( tid ))

    def t4():
        print('[TASK T4] Initialized')
        tid = yield GetTid()
        print('[TASK T4] step1, SystemCall(GetTid) = %s' % ( tid ))
        tid = yield WaitTask(2)
        print('[TASK T4] step2, SystemCall(GetTid) = %s' % ( tid ))
        tid = yield GetTid()
        print('[TASK T4] step3, SystemCall(GetTid) = %s' % ( tid ))
        tid = yield
        print('[TASK T4] step4, SystemCall(GetTid) = %s' % ( tid ))
        tid = yield
        print('[TASK T4] step5, SystemCall(GetTid) = %s' % ( tid ))


    scheduler = Scheduler()
    scheduler.new(t1())
    scheduler.new(t2())
    scheduler.new(t3())
    scheduler.new(t4())
    scheduler.mainLoop()
