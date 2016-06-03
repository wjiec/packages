#include <QApplication>
#include "test.h"

int main(int argc, char *argv[]) {
    QApplication application(argc, argv);
    Test *t = new Test;

    t->show();

    return application.exec();
}
