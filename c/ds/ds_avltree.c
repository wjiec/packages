#include "ds.h"

struct _avl_tree_node_t {
    struct _avl_tree_node_t *left;
    struct _avl_tree_node_t *right;
};

struct _avl_tree_t {
    struct _avl_tree_node_t *root;
};

