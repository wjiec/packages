#include <QApplication>
#include <QHBoxLayout>
#include <QSize>
#include <QSpinBox>
#include <QSlider>
#include <QWidget>

int main(int argc, char *argv[]) {
    QApplication application(argc, argv);
    QWidget *window = new QWidget;

    window->setWindowTitle("My First GUI Application");
    window->setMinimumSize(QSize(640, 480));

    QSpinBox *spinBox = new QSpinBox;
    QSlider *slider = new QSlider(Qt::Horizontal);

    spinBox->setRange(0, 100);
    slider->setRange(0, 100);

    QObject::connect(spinBox, SIGNAL(valueChanged(int)), slider, SLOT(setValue(int)));
    QObject::connect(slider, SIGNAL(valueChanged(int)), spinBox, SLOT(setValue(int)));

    spinBox->setValue(18);

    QBoxLayout *layout = new QHBoxLayout;

    layout->addWidget(spinBox);
    layout->addWidget(slider);

    window->setLayout(layout);
    window->show();

    return application.exec();
}
