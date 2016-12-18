#!/usr/bin/env python
#
# Copyright (C) 2016 ShadowMan
#
import sys
from PyQt5.QtWidgets import QApplication, QWidget
from PyQt5.QtGui import QIcon

class Sample(QWidget):

    def __init__(self):
        super().__init__(self)

        self.__init_window_ui()

    def __init_window_ui(self):
        # self.setGeometry(640, 480, 0, 0)
        self.setWindowTitle('PyQt5 - Sample Icon')
        self.setWindowIcon(QIcon('icon.png'))

if __name__ == '__main__':
    app = QApplication(sys.argv)

    window = Sample()
    window.show()

    exit(app.exec_())
