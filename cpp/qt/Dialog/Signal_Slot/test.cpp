#include "test.h"
#include <iostream>
#include <QPushButton>
#include <QHBoxLayout>

Test::Test(QWidget *parent) : QDialog(parent) {
    QPushButton *button1 = new QPushButton(tr("Button1"));
    QPushButton *button2 = new QPushButton(tr("Button2"));
    QPushButton *button3 = new QPushButton(tr("Button3"));

    connect(button1, SIGNAL(clicked()), this, SIGNAL(signal1()));
    connect(this, SIGNAL(signal1()), this, SLOT(slot1()));

    connect(button2, SIGNAL(clicked()), this, SIGNAL(signal2()));
    connect(this, SIGNAL(signal2()), this, SLOT(slot2()));

    connect(button3, SIGNAL(clicked()), this, SIGNAL(signal3()));
    connect(this, SIGNAL(signal3()), this, SLOT(slot3()));

    QHBoxLayout *mainLayout = new QHBoxLayout;

    mainLayout->addWidget(button1);
    mainLayout->addWidget(button2);
    mainLayout->addWidget(button3);

    setLayout(mainLayout);
    setWindowTitle(tr("Test Signal & Slot"));
    setFixedWidth(sizeHint().width());
}

void Test::slot1() {
    std::cout << "slot1 trigged\n" << std::endl;
}

void Test::slot2() {
    std::cout << "slot2 trigged\n" << std::endl;
}

void Test::slot3() {
    std::cout << "slot3 trigged\n" << std::endl;
}
