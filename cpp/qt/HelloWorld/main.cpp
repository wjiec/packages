#include <QApplication>
#include <QLabel>

int main(int argc, char *argv[]) {
    QApplication window(argc, argv);
    QLabel *label = new QLabel("Hello World!");

    label->show();
    return window.exec();
}
