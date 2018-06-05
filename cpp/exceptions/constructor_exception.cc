#include <iostream>
#include <vector>
#include <memory>


template <typename Type>
class Apple {
    public:
        Apple(std::initializer_list<Type> list) try :
            _list(std::make_shared(list)) {
        } catch (std::bad_alloc &e) {
            std::cout << e.what() << std::endl;
        }

    private:
        std::shared_ptr<std::vector<Type>> _list;
};


int main(int argc, char *argv[]) {
    auto apple = new Apple<int>({1, 2, 3});
    return 0;
}
