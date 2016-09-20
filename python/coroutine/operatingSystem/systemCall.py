'''Operating System Call Base

'''

class SystemCall(object):
    def __init__(self):
        pass

    def handler(self):
        pass

class GetTid(SystemCall):
    def __init__(self):
        super(GetTid, self).__init__()

    def handler(self):
        self.task.sendVal = "(Task ~%d In %#x)" % ( self.task.tid, id(self.task) )
        self.sched.schedule(self.task)

class CreateTask(SystemCall):
    def __init__(self, target):
        super(CreateTask, self).__init__()
        self.target = target

    def handler(self):
        tid = self.sched.new(self.target)
        self.task.sendVal = '(NewTask Child~%s Parent~%s)' % ( tid, self.task.tid )
        self.sched.schedule(self.task)

class KillTask(SystemCall):
    def __init__(self, tid):
        super(KillTask, self).__init__()
        self.killTaskId = tid

    def handler(self):
        task = self.sched.taskMap.get(self.killTaskId, None)
        if task:
            self.task.sendVal = '(CkillTask tid~%s self~%s)' % ( task.tid, self.task.tid )
            self.sched.terminate(task)
        else:
            self.task.sendVal = False

        self.sched.schedule(self.task)

class WaitTask(SystemCall):
    def __init__(self, tid):
        super(WaitTask, self).__init__()
        self.waitTaskId = tid

    def handler(self):
        result = self.sched.wait(self.task, self.waitTaskId)
        if result:
            self.task.sendVal = '(WaitTask tid = %s)' % ( self.waitTaskId )
        else:
            self.task.sendVal = '(WaitTask Error tid = %s not found)' % ( self.waitTaskId )
            self.sched.schedule(self.task)

class WaitRead(SystemCall):
    def __init__(self, fd):
        super(WaitRead, self).__init__()
        self.fd = fd

    def handler(self):
        fd = self.fd.fileno()
        self.sched.waitForRead(fd, self.task)

class WaitWrite(SystemCall):
    def __init__(self, fd):
        super(WaitWrite, self).__init__()
        self.fd = fd

    def handler(self):
        fd = self.fd.fileno()
        self.sched.waitForWrite(fd, self.task)

def sockAccept(sock):
    yield WaitRead(sock)
    yield sock.accept()

def sockRecv(sock, n):
    yield WaitRead(sock)
    yield sock.recv(n)

def sockWrite(sock, buffer):
    while buffer:
        yield WaitWrite(sock)
        length = sock.write(buffer)
        buffer = buffer[length:]
