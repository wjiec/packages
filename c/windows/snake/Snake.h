#ifndef _SNAKE_H_
#define _SNAKE_H_

typedef struct _snake_node_t {
    int x;
    int y;
} SnakeNode;

typedef struct _snake_t {
    SnakeNode *header;
    SnakeNode *next;
    int length;
} Snake;

void startGame(void);

#endif // SHAKE.H
