#ifndef _TEST_H_
#define _TEST_H_

#include <QDialog>

class Test : public QDialog {
    Q_OBJECT

    public:
        Test(QWidget *parent = 0);
    signals:
        void signal1();
        void signal2();
        void signal3();
    public slots:
        void slot1();
        void slot2();
        void slot3();
};

#endif // TEST.H
