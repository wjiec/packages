#include <iostream>
#include <unordered_map>
#include <string>

using namespace std;

int main(void) {
    unordered_map<string, int> um{ {"a1", 2}, {"b2", 3}, {"c3", 4}, {"d4", 5}, {"f5", 6} };

    cout << "um.bucket_count() = " << um.bucket_count() << endl;
    cout << "um.max_bucket_count() = " << um.max_bucket_count() << endl;
    for (unsigned index = 0; index < um.bucket_count(); ++index) {
        cout << "um.bucket_size(" << index + 1 << ") = " << um.bucket_size(index + 1) << endl;
    }
    for (auto &w : um) {
        cout << "pair<int, int>(" << w.first << ", " << w.second << ") in bucket(" << um.bucket(w.first) << ")" << endl;
    }

    return 0;
}