#include <map>
#include <cmath>
#include <vector>
#include <iostream>
#include <algorithm>


template <typename _Set, typename _Label>
class KnnClassify {
    public:
        KnnClassify(std::vector<_Set> &data_set, std::vector<_Label> &labels, int k = 3);

    public:
        _Label classify(_Set &input);

    private:
        std::vector<_Set> _data_set;
        std::vector<_Label> _labels;
        int _k;
};


int main() {
    std::vector<std::vector<double>> data_set {
        {90, 2}, {88, 10}, {102, 15}, {78, 5},
        {10, 89}, {8, 97}, {15, 108}, {32, 75}
    };
    std::vector<char> labels{'A', 'A', 'A', 'A', 'B', 'B', 'B', 'B'};

    while (std::cin) {
        int x, y;

        std::cout << "Please Data: ";
        std::cin >> x >> y;

        std::vector<double> input{x, y};
        KnnClassify<std::vector<double>, char> knn(data_set, labels);

        char knn_classify_result = knn.classify(input);
        std::cout << std::endl << "Knn Classify Result: " << knn_classify_result << std::endl;
    }
    return 0;
}


template <typename _Set, typename _Label>
KnnClassify<_Set, _Label>::KnnClassify(std::vector<_Set> &data_set, std::vector<_Label> &labels, int k) :
    _data_set(data_set), _labels(labels), _k(k) {
    // Knn Classify Constructor
    if (data_set.size() != labels.size()) {
        throw std::out_of_range("size of the data set and label is inconsistent");
    }
}


template <typename _Set, typename _Label>
_Label KnnClassify<_Set, _Label>::classify(_Set &input) {
    std::vector<double> distances;

    for (auto data : _data_set) {
        double sum = 0;
        for (auto i = 0; i < data.size(); ++i) {
            sum += std::pow(data[i] - input[i], 2);
        }
        distances.push_back(std::pow(sum, 0.5));
    }

    std::vector<std::pair<int, double>> sorted_distances;
    for (auto index = 0; index < distances.size() - 1; ++index) {
        auto no = 0;
        auto seq_it = sorted_distances.cend() - 1;
        double seq_min_val = sorted_distances.size() ? (*seq_it).second : -1;
        double min_val_index = 0;

        for (; no < distances.size(); ++no) {
            if (distances[no] > seq_min_val && distances[no] < distances[min_val_index]) {
                min_val_index = no;
            }
        }

        sorted_distances.emplace_back(min_val_index, distances[min_val_index]);
    }

    auto max_val_index = 0;
    double max_val = distances[max_val_index];
    for (auto index = 1; index < distances.size(); ++index) {
        if (distances[index] > distances[max_val_index]) {
            max_val_index = index;
            max_val = distances[max_val_index];
        }
    }
    sorted_distances.emplace_back(max_val_index, max_val);

    std::map<_Label, int> label_mapping;
    for (auto index = 0; index < _k; ++index) {
        label_mapping[_labels[sorted_distances[index].first]] += 1;
    }

    auto max_item = label_mapping.begin();
    for (auto index = label_mapping.begin(); index != label_mapping.end(); ++index) {
        if ((*max_item).second < (*index).second) {
            max_item = index;
        }
    }
    return (*max_item).first;
}
