#include <QApplication>
#include "rundialog.h"

int main(int argc, char *argv[]) {
    QApplication application(argc, argv);
    RunDialog *command = new RunDialog;

    command->show();

    return application.exec();
}
