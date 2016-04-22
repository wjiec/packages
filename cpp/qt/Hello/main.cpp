#include <QApplication>
#include <QTextCodec>
#include <QWidget>
#include <QLabel>

int main(int argc, char * argv[]) {
    QApplication app(argc, argv);
    QTextCodec::setCodecForLocale(QTextCodec::codecForName("UTF-8"));
    QWidget * widget = new QWidget;
    QLabel label(widget);

    label.setText(QObject::tr("453123"));
    widget->setMinimumWidth(640);
    widget->setMinimumHeight(480);

    widget->show();
    return app.exec();
}
