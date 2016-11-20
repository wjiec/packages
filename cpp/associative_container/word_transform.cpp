#include <iostream>
#include <fstream>
#include <iterator>
#include <string>
#include <sstream>
#include <map>

using namespace std;

void word_transform(ifstream &mapRuleFile, ifstream &inFile, string &out);
map<string, string> build_map(ifstream &mapRuleFile);


int main(void) {
    ifstream mapFile("map_rule.txt");
    ifstream inFile("in.txt");
    string out;

    word_transform(mapFile, inFile, out);
    cout << out << endl;

    return 0;
}

void word_transform(ifstream &mapRuleFile, ifstream &inFile, string &out) {
    map<string, string> rule = build_map(mapRuleFile);
    string line;

    while (getline(inFile, line)) {
        istringstream in(line);
        istream_iterator<string> iit(in), eof;

        while (iit != eof) {
            if (rule.find(*iit) != rule.end()) {
                out += rule[*iit++];
            } else {
                out += *iit++;
            }
            out += ' ';
        }
        out += '\n';
    }
}

map<string, string> build_map(ifstream &mapRuleFile) {
    map<string, string> m;
    string key, value;

    while (mapRuleFile >> key && getline(mapRuleFile, value)) {
        if (!key.length() || !value.length()) {
            break;
        }
        value.erase(value.begin());
        m[key] = value;
    }

    return m;
}
