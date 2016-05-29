#include <iostream>
#include <string>
#include <QLabel>
#include <QLineEdit>
#include <QCheckBox>
#include <QPushButton>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QString>
#include "rundialog.h"


RunDialog::RunDialog(QWidget *parent) : QDialog(parent) {
    description = new QLabel(tr("type the name of a program, floder, document, or Internet\n"
                                "resource, and Windows will open it for you"));
    adminTips = new QLabel(tr("This task will created with administration privileges"));

    openLabel = new QLabel(tr("&Open: "));
    command = new QLineEdit;
    openLabel->setBuddy(command);

    runButton = new QPushButton(tr("&Run"));
    cancelButton = new QPushButton(tr("&Cancel"));
    browserButton = new QPushButton(tr("&Browser..."));

    runButton->setDefault(true);
    runButton->setDisabled(true);

    connect(command, SIGNAL(textChanged(QString)), this, SLOT(enableCommandButton(const QString &)));
    connect(runButton, SIGNAL(clicked(bool)), this, SLOT(runCommandClicked()));
    connect(cancelButton, SIGNAL(clicked(bool)), this, SLOT(cancelClicked()));
    connect(browserButton, SIGNAL(clicked(bool)), this, SLOT(browserCliecked()));

    QHBoxLayout *topDescLayout = new QHBoxLayout;
    topDescLayout->addWidget(description);

    QHBoxLayout *centerLayout = new QHBoxLayout;
    centerLayout->addWidget(openLabel);
    centerLayout->addWidget(command);

    QHBoxLayout *tipsLayout = new QHBoxLayout;
    tipsLayout->addStretch();
    tipsLayout->addWidget(adminTips);

    QHBoxLayout *bottomLayout = new QHBoxLayout;
    bottomLayout->addStretch();
    bottomLayout->addWidget(runButton);
    bottomLayout->addWidget(cancelButton);
    bottomLayout->addWidget(browserButton);

    QVBoxLayout *mainLayout = new QVBoxLayout;
    mainLayout->addLayout(topDescLayout);
    mainLayout->addLayout(centerLayout);
    mainLayout->addLayout(tipsLayout);
    mainLayout->addLayout(bottomLayout);

    setLayout(mainLayout);
    setWindowTitle(tr("Run"));
    setFixedHeight(sizeHint().height());
}

void RunDialog::enableCommandButton(const QString &text) {
    runButton->setEnabled(!text.isEmpty());
}

void RunDialog::runCommandClicked() {
    QString text = command->text();

    std::cout << text.toStdString() << std::endl;
}

void RunDialog::cancelClicked() {
    QString text = command->text();

    if (text.isEmpty()) {
        close();
    } else {
        command->setText("");
    }
}

void RunDialog::browserCliecked() {
    std::cout << "Browser..." << std::endl;
}

