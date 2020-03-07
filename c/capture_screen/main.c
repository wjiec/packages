#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>

static bool capture(const char *filename);


int main(int argc, char *argv[]) {
    char filename[PATH_MAX] = { 0 };
    for (int i = 0; i < time(NULL); i++) {
        memset(filename, 0, MAX_PATH);

        time_t timestamp = time(NULL);
        struct tm localTime;
        localtime_s(&localTime, &timestamp);
        sprintf(filename, "%d-%d-%d-%d-%d-%d.bmp",
            localTime.tm_year + 1900, localTime.tm_mon + 1, localTime.tm_mday,
            localTime.tm_hour, localTime.tm_min, localTime.tm_sec
        );

        capture(filename);
        Sleep(10000);
    }
}

static bool capture(const char *filename) {
    int width = GetSystemMetrics(SM_CXSCREEN);
    int height = GetSystemMetrics(SM_CYSCREEN);

    HDC desktopDeviceCtx = GetDC(NULL);
    HDC memDeviceCtx = CreateCompatibleDC(desktopDeviceCtx);
    if (!memDeviceCtx) {
        DeleteObject(memDeviceCtx);
        ReleaseDC(NULL, desktopDeviceCtx);
        return false;
    }

    HBITMAP bmp =  CreateCompatibleBitmap(desktopDeviceCtx, width, height);
    if (!bmp) {
        DeleteObject(bmp);
        DeleteObject(memDeviceCtx);
        ReleaseDC(NULL, desktopDeviceCtx);
        return false;
    }

    SelectObject(memDeviceCtx, bmp);
    if (!BitBlt(memDeviceCtx, 0, 0, width, height, desktopDeviceCtx, 0, 0, SRCCOPY)) {
        DeleteObject(bmp);
        DeleteObject(memDeviceCtx);
        ReleaseDC(NULL, desktopDeviceCtx);
    }

    BITMAP screen;
    GetObject(bmp, sizeof(BITMAP), &screen);
    BITMAPINFOHEADER bmpInfoHeader = {
        .biSize = sizeof(BITMAPINFOHEADER),
        .biWidth = screen.bmWidth,
        .biHeight = screen.bmHeight,
        .biPlanes = 1,
        .biBitCount = 32,
        .biCompression = BI_RGB,
        .biSizeImage = 0,
        .biXPelsPerMeter = 0,
        .biYPelsPerMeter = 0,
        .biClrUsed = 0,
        .biClrImportant = 0,
    };

    DWORD bmpSize = ((screen.bmWidth * bmpInfoHeader.biBitCount + 31) / 32) * 4 * screen.bmHeight;
    HANDLE memBuffer = GlobalAlloc(GHND, bmpSize);
    CHAR *bmpBuffer = (CHAR*)GlobalLock(memBuffer);
    GetDIBits(desktopDeviceCtx, bmp, 0, screen.bmHeight, bmpBuffer, (BITMAPINFO*)&bmpInfoHeader, DIB_RGB_COLORS);

    HANDLE file = CreateFile(filename, GENERIC_WRITE, 0, NULL, CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, NULL);
    BITMAPFILEHEADER bmpFileHeader = {
        .bfOffBits = sizeof(BITMAPFILEHEADER) + sizeof(BITMAPINFOHEADER),
        .bfSize = bmpSize + sizeof(BITMAPFILEHEADER) + sizeof(BITMAPINFOHEADER),
        .bfType = 0x4d42,
    };

    DWORD writtenOffset = 0;
    WriteFile(file, (LPSTR)&bmpFileHeader, sizeof(BITMAPFILEHEADER), &writtenOffset, NULL);
    WriteFile(file, (LPSTR)&bmpInfoHeader, sizeof(BITMAPINFOHEADER), &writtenOffset, NULL);
    WriteFile(file, (LPSTR)bmpBuffer, bmpSize, &writtenOffset, NULL);

    GlobalUnlock(memBuffer);
    GlobalFree(memBuffer);
    CloseHandle(file);

    DeleteObject(bmp);
    DeleteObject(memDeviceCtx);
    ReleaseDC(NULL, desktopDeviceCtx);

    return true;
}
