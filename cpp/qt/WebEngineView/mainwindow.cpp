#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QWebChannel>
#include <QtWebEngineWidgets/QWebEngineView>


MainWindow::MainWindow(QWidget *parent):
    QMainWindow(parent), ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    ui->webEngineWidget->load(QUrl("qrc:/index.html"));
    QWebChannel *channel = new QWebChannel(ui->webEngineWidget->page());
    channel->registerObject("myObject", &obj);
    ui->webEngineWidget->page()->setWebChannel(channel);

    obj.setTimer(3500);
}

MainWindow::~MainWindow() {
    delete ui;
}
