#ifndef OBJECT_H
#define OBJECT_H

#include <QObject>
#include <QStringList>

class Object : public QObject {
    Q_OBJECT
    Q_PROPERTY(QString info MEMBER m_info READ getInfo WRITE setInfo NOTIFY infoChanged)

    public:
        explicit Object(QObject *parent = 0);

        QString getInfo() const;
        void setInfo(QString inf);

    signals:
        void infoChanged(QString);

    public slots:
        void callFromJS(QString info);

    private:
        QString info;
};


#endif // OBJECT_H
