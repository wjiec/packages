#include "object.h"
#include <QDebug>
#include <QString>
#include <QTimer>


Object::Object(QObject *parent): QObject(parent) {
    m_info = "initializing";
}

QString Object::getInfo() const {
    return m_info;
}

void Object::setInfo(QString info) {
    setProperty("info", info);
}

void Object::callFromJS(QString info) {
    setInfo("From Js: " + info);
    qDebug() << this->m_info;
}

void Object::setTimer(const int msec) const {
    qDebug() << "Start Timer: " << msec;

    QTimer *timer = new QTimer();
    timer->connect(timer, SIGNAL(timeout()), this, SLOT(callFromTimer()));
    timer->start(msec);
}

void Object::callFromTimer() {
    setInfo("From Timer: xxx");
}
