#include <iostream>
#include <iterator>
#include <sstream>
#include <fstream>
#include <memory>
#include <vector>
#include <string>
#include <map>
#include <set>

using namespace std;

// pre-definition
class QueryResult;
// TextQuery Classes
class TextQuery {
    public:
        // from file getting lines
        TextQuery(ifstream &in_file);
        // query method
        QueryResult query(string q);
    private:
        string query_text;
        vector<string> lines;
        map<string, set<int>> word_mapping;
};

class QueryResult {
    public:
        // default construct
        QueryResult() = default;
        // normal construct
        QueryResult(string query_text, shared_ptr<vector<string>> ls, shared_ptr<set<int>> m)
            : query_text(query_text), lines(ls), mapping(m) {};
        // print methods
        void print(void);
        // check available
        bool available(void);
    private:
        string query_text;
        shared_ptr<vector<string>> lines;
        shared_ptr<set<int>> mapping;
};

int main(void) {
    // file handler
    ifstream in_file("text_query.cpp", ios::in);
    // text query instance
    TextQuery text_query(in_file);

    // query
    for (string text; cin >> text; ) {
        // query result
        auto rst = text_query.query(text);
        // if available
        if (rst.available()) {
            // print that
            rst.print();
        }
    }

    return 0;
}

TextQuery::TextQuery(ifstream &in_file) {
    string s;
    istream_iterator<string> eof;
    // loop process each lines
    // line number is start in 1
    for (int line_number = 1; getline(in_file, s); ++line_number) {
        // Save each lines into the container
        lines.push_back(s);
        // string-stream
        stringstream ss(s);
        // line-iterator
        istream_iterator<string> line_it(ss), eof;
        // loop process each word
        for (auto curr_word = line_it; curr_word != eof; ++curr_word) {
            // the word non-exists
            if (word_mapping.find(*curr_word) == word_mapping.end()) {
                // insert new word-mapping
                word_mapping.insert(pair<string, set<int>>(*curr_word, set<int>{line_number}));
            } else {
                // the word exists in mapping
                word_mapping[*curr_word].insert(line_number);
            }
        }
    }

    // test lines
    // for (auto s : lines) {
    //     cout << s << endl;
    // }
    // cout << endl;

    // test word_mapping
    // for (auto p : word_mapping) {
    //     // pair<string, set<int>>
    //     cout << p.first << ' ';
    //     for (auto ln : p.second) {
    //         cout << ln << ' ';
    //     }
    //     cout << endl;
    // }
}

QueryResult TextQuery::query(string q) {
    if (word_mapping.find(q) == word_mapping.end()) {
        cout << "\tThis word not found on the file." << endl;
        return QueryResult();
    } else {
        // QueryResult(string query_text, shared_ptr<vector<string>> ls, shared_ptr<set<int>> m)
        return QueryResult(query_text, make_shared<vector<string>>(lines), make_shared<set<int>>(word_mapping[q]));
    }
}

void QueryResult::print(void) {
    // first line
    cout << query_text << " occurs " << mapping->size() << " times." << endl;
    // occurs lines
    for (auto ln : *mapping) {
        // the line-number is start in 1
        cout << '\t' << (*lines)[ln - 1] << endl;
    }
    cout << endl;
}

bool QueryResult::available(void) {
    return lines && mapping;
}

