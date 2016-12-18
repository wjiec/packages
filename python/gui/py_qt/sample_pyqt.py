#!/usr/bin/env python
#
# Copyright (C) 2016 ShadowMan
#
import sys
from PyQt5.QtWidgets import QApplication, QWidget

if __name__ == '__main__':
    app = QApplication(sys.argv)

    # A widget with no parent is called a window.
    widget = QWidget()

    widget.resize(640, 480)
    widget.move(0, 0)
    widget.setWindowTitle('PyQt5 - Simple')
    widget.show()

    sys.exit(app.exec_())