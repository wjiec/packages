import abc

class Base(metaclass = abc.ABCMeta):
    def __init__(self):
        print("Base::__init__()")

    @abc.abstractmethod
    def printer(self):
        print("Base::printer()")

class Child1(Base):
    def printer(self):
        print("Child1::printer")

class Child2(Base):
    def printer(self):
        print("Child2::printer")

if __name__ == '__main__':
    c1 = Child1()
    c2 = Child2()

    c1.printer()
    c2.printer()