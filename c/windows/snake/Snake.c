#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>
#include "Snake.h"

#define SCREEN_WIDTH 160
#define SCREENT_HEIGHT 50

HANDLE _stdOutputHandle = NULL;

static void initSnake(Snake *snake);
static void draw(const Snake *snake);
static void drawBorder();
static void drawGameInfo(const int score, const int level, const int length);
static void gotoXY(const int x, const int y);

void startGame(void) {
    Snake snake;

    _stdOutputHandle = GetStdHandle(STD_OUTPUT_HANDLE);
    
    initSnake(&snake);

    draw(&snake);


    while (1 == 1) {

    }
}

static void initSnake(Snake *snake) {
    COORD screenSize = { SCREEN_WIDTH, SCREENT_HEIGHT };
    SMALL_RECT consoleScreen = { 0, 0, SCREEN_WIDTH - 1, SCREENT_HEIGHT - 1 };
    CONSOLE_CURSOR_INFO cursor = { 1, 0 };

    SetConsoleCursorInfo(_stdOutputHandle, &cursor);
    SetConsoleTitle("Game: Snake - TEAM LABOYS");
    SetConsoleScreenBufferSize(_stdOutputHandle, screenSize);
    SetConsoleWindowInfo(_stdOutputHandle, TRUE, &consoleScreen);

    srand((unsigned)time(NULL));

    snake->header = malloc(sizeof(SnakeNode));
    snake->header->x = rand() % SCREEN_WIDTH;
    snake->header->y = rand() % SCREENT_HEIGHT;

    snake->next   = NULL;
    snake->length = 1;
}

static void draw(const Snake *snake) {
    drawBorder();
}

static void drawBorder() {
    static char wall[] = { 4, 4, 0 };

    // top wall
    for (int i = 0; i < SCREEN_WIDTH / 2; ++i) {
        printf(wall);
    }

    // center wall
    char space[(SCREEN_WIDTH - 2) + 1] = { 0 };
    memset(space, ' ', sizeof(char) * (SCREEN_WIDTH - 4));
    for (int i = 0; i < SCREENT_HEIGHT - 4; ++i) {
        printf("%s%s%s", wall, space, wall);
    }

    // bottom wall
    for (int i = 0; i < SCREEN_WIDTH / 2; ++i) {
        printf(wall);
    }

    drawGameInfo(0, 0, 1);
}

static void drawGameInfo(const int score, const int level, const int length) {
    SetConsoleTextAttribute(_stdOutputHandle, FOREGROUND_GREEN | FOREGROUND_INTENSITY);
    printf("Score: %03d   ", score);

    SetConsoleTextAttribute(_stdOutputHandle, FOREGROUND_BLUE | FOREGROUND_GREEN | FOREGROUND_INTENSITY);
    printf("Level: %03d   ", level);

    SetConsoleTextAttribute(_stdOutputHandle, FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_INTENSITY);
    printf("Length: %02d   ", length);

    SetConsoleTextAttribute(_stdOutputHandle, FOREGROUND_BLUE | FOREGROUND_RED);
    printf("\n%c Made By TEAM LABOYS", 1);

    SetConsoleTextAttribute(_stdOutputHandle, FOREGROUND_GREEN | FOREGROUND_BLUE | FOREGROUND_RED);
}

static void gotoXY(const int x, const int y) {
    COORD coord;

    if (x < SCREEN_WIDTH - 1 && y < SCREENT_HEIGHT - 1) {
        coord.X = x;
        coord.Y = y;
        SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), coord);
    }
}