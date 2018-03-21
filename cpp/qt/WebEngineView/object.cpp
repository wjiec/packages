#include "object.h"
#include <QDebug>
#include <QString>
#include <QVector>


Object::Object(QObject *parent): QObject(parent) {
    info = "initializing";
}

QString Object::getInfo() const {
    return info;
}

void Object::setInfo(QString info) {
    this->info = info;
}

void Object::callFromJS(QString info) {
    this->info = info;
    qDebug() << "From Js" << this->info;
}
