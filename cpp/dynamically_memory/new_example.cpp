#include <iostream>
#include <vector>
#include <new>

using namespace std;

vector<int> *factory(void);
void input(vector<int> *&p);
void output(vector<int> *&p);

int main(void) {
    vector<int> *p = factory();
    input(p);
    output(p);
    delete p;

    return 0;
}

vector<int> *factory(void) {
    return new vector<int>;
}

void input(vector<int> *&p) {
    for (int tmp; cin >> tmp;) {
        p->push_back(tmp);
    }
}

void output(vector<int> *&p) {
    for (auto &el : *p) {
        cout << el << " ";
    }
    cout << endl;
}
