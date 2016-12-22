#ifndef DS_H
#define DS_H

#include <stdio.h>
#include <stdbool.h>

/**
 * Base Data Structure: Stack 
 */
struct _stack_t;
typedef struct _stack_t Stack;

Stack *create_stack(size_t element_size, size_t stack_size);
void destroy_stack(Stack *stack);
bool push(Stack *stack, void *element);
void *pop(Stack *stack);
void *top(Stack *stack);

/**
 * Base Data Structure: List 
 */
struct _list_node_t;
struct _list_t;
typedef struct _list_node_t ListNode;
typedef struct _list_t List;

/**
* Base Data Structure: Queue
*/
struct _queue_node_t;
struct _queue_t;
typedef struct _queue_node_t QueueNode;
typedef struct _queue_t Queue;

/**
* Base Data Structure: Tree
*/
struct _tree_node_t;
struct _tree_t;
typedef struct _tree_node_t TreeNode;
typedef struct _tree_t Tree;

/**
* Extend Data Structure: Binary Tree
*/
struct _binary_tree_node_t;
struct _binary_tree_t;
typedef struct _binary_tree_node_t BinTreeNode;
typedef struct _binary_tree_t BinTree;

/**
* Extend Data Structure: AVL Tree
*/
struct _avl_tree_node_t;
struct _avl_tree_t;
typedef struct _avl_tree_node_t AvlTreeNode;
typedef struct _avl_tree_t AvlTree;


#endif // DS_H