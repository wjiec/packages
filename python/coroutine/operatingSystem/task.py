'''Operating System Task

'''
import types
from systemCall import *

class Task(object):
    taskId = 0

    def __init__(self, target):
        self.tid     = Task.taskId
        Task.taskId += 1
        self.target  = target
        self.sendVal = None
        self.name    = target.__name__
        self.stack   = []

    def run(self):
        while True:
            try:
                result = self.target.send(self.sendVal)
                if isinstance(result, SystemCall):
                    return result
                if isinstance(result, types.GeneratorType):
                    self.stack.append(self.target)
                    self.sendVal = None
                    self.target = result
                else:
                    print(self.stack)
                    if not self.stack:
                        return
                    self.sendVal = result
                    self.target  = self.stack.pop()
            except StopIteration:
                if not self.stack:
                    raise
                self.sendVal = None
                self.target  = self.stack.pop()

    def terminate(self):
        return self.target.close()

    def __str__(self):
        return '<Task name = %s %#x>' % (self.name, id(self))

if __name__ == '__main__':
    def simpleTask():
        print('[SIMPLE TASK]step 1')
        yield
        print('[SIMPLE TASK]step 2')
        yield

    task = Task(simpleTask())
    task.run()
    task.run()