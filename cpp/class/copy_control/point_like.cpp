#include <iostream>
#include <string>

using namespace std;

class SmartPoint {
    public:
        SmartPoint() : data(new string), use_count(new unsigned int(1)) {}
        SmartPoint(const string s) : data(new string(s)), use_count(new unsigned int (1)) {}
        SmartPoint(const SmartPoint &sp);
        SmartPoint &operator=(const SmartPoint &sp);
        ~SmartPoint();

        // check method
        unsigned int check_use_count(void);
        void check(void);
    private:
        string *data;
        unsigned int *use_count;
};

int main(void) {
    SmartPoint sp1("sp1");
    cout << "default reference-count" << endl;
    sp1.check();

    SmartPoint sp2(sp1);
    cout << "copy one-time reference-count" << endl;
    sp2.check();


    SmartPoint sp3("sp3");
    cout << "new instance reference-count" << endl;
    sp3.check();

    sp3 = sp1;
    sp3.check();

    return 0;
}

// copy-constructor
SmartPoint::SmartPoint(const SmartPoint &sp) :
    data(sp.data), use_count(sp.use_count) {


    ++(*use_count);
}

// assign-operator
SmartPoint &SmartPoint::operator=(const SmartPoint &sp) {
    // decrement its reference count
    // if left-value reference-count == 0
    // that execute destructor
    while (--(*use_count) == 0) {
        cout << "\t" << *data << " enter destructor" << endl;

        delete data;
        delete use_count;
    }

    // swap
    data = sp.data;
    use_count = sp.use_count;
    // increment right-value reference-count
    ++(*use_count);

    return *this;
}

unsigned int SmartPoint::check_use_count(void) {
    return *use_count;
}

void SmartPoint::check(void) {
    cout << "\tdata = \'" << *data << "\' use_count = " << *use_count << endl;
}

SmartPoint::~SmartPoint() {
    cout << "\t" << *data << " enter " << "SmartPoint::~SmartPoint" << endl;
    if (--(*use_count) == 0) {
        cout << "\tdata = " << *data << " reference count == 0" << endl;

        delete data;
        delete use_count;

        data = nullptr;
        use_count = nullptr;
    }
}
