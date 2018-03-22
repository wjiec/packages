#ifndef OBJECT_H
#define OBJECT_H

#include <QObject>
#include <QStringList>

class Object : public QObject {
    Q_OBJECT
    Q_PROPERTY(QString info MEMBER m_info NOTIFY infoChanged)

    public:
        explicit Object(QObject *parent = 0);

        QString getInfo() const;
        void setInfo(QString inf);
        void setTimer(const int msec) const;

    signals:
        void infoChanged(QString);

    public slots:
        void callFromTimer();
        void callFromJS(QString info);

    private:
        QString m_info;
};


#endif // OBJECT_H
