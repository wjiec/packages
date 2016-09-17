'''Operating System Task

'''

class Task(object):
    taskId = 0

    def __init__(self, target):
        self.tid     = Task.taskId
        Task.taskId += 1
        self.target  = target
        self.sendVal = None
        self.name    = target.__name__

    def run(self):
        return self.target.send(self.sendVal)

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