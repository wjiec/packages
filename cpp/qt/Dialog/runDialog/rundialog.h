#ifndef RUNDIALOG_H
#define RUNDIALOG_H

#include <QDialog>

class QLabel;
class QLineEdit;
class QPushButton;

class RunDialog : public QDialog {
        Q_OBJECT
    public:
        explicit RunDialog(QWidget *parent = 0);

    signals: // unuseful?
//        void runCommand(const QString &str, Qt::CaseSensitivity cs);
//        void cancel(const QString &str, Qt::CaseSensitivity cs);
//        void browser(const QString &str, Qt::CaseSensitivity cs);
    public slots:
        void enableCommandButton(const QString &text);
        void runCommandClicked();
        void cancelClicked();
        void browserCliecked();
    private:
        QLabel *description;
        QLabel *openLabel;
        QLabel *adminTips;

        QLineEdit *commandLineEdit;

        QPushButton *runButton;
        QPushButton *cancelButton;
        QPushButton *browserButton;
};

#endif // RUNDIALOG_H
