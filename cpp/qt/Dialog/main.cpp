#include <QApplication>
#include "find.h"

int main(int argc, char *argv[]) {
    QApplication application(argc, argv);

    FindDialog *dialog = new FindDialog;

    dialog->show();

    return application.exec();
}
