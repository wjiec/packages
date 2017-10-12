# -*- coding: UTF-8 -*-
#
# Copyright (C) 2017 DouLe
#
import Queue
import struct
import ctypes.wintypes


# Win32 API
CreateFileW = ctypes.windll.kernel32.CreateFileA
ReadDirectoryChangesW = ctypes.windll.kernel32.ReadDirectoryChangesW
GetLastError = ctypes.windll.kernel32.GetLastError


# Constant
FILE_LIST_DIRECTORY = 0x1
FILE_SHARE_READ = 0x01
FILE_SHARE_WRITE = 0x02
FILE_SHARE_DELETE = 0x04
OPEN_EXISTING = 3
FILE_FLAG_BACKUP_SEMANTICS = 0x02000000
INVALID_HANDLE_VALUE = ctypes.c_void_p(-1).value

FILE_NOTIFY_CHANGE_FILE_NAME = 0x01
FILE_NOTIFY_CHANGE_DIR_NAME = 0x02
FILE_NOTIFY_CHANGE_ATTRIBUTES = 0x04
FILE_NOTIFY_CHANGE_SIZE = 0x08
FILE_NOTIFY_CHANGE_LAST_WRITE = 0x010
FILE_NOTIFY_CHANGE_LAST_ACCESS = 0x020
FILE_NOTIFY_CHANGE_CREATION = 0x040
FILE_NOTIFY_CHANGE_SECURITY = 0x0100

# event buffer size
BUFFER_SIZE = 4096


class WatchDog(object):

    EVENT_CREATED = 1

    EVENT_DELETED = 2

    EVENT_UPDATED = 3

    EVENT_RENAME_FROM = 4

    EVENT_RENAME_TO = 5

    def __init__(self, directory):
        self._directory = directory
        self._result_queue = Queue.Queue()
        self._handle = self._create_file_handle()

    def _create_file_handle(self):
        handle = CreateFileW(
            str(self._directory),
            FILE_LIST_DIRECTORY,
            FILE_SHARE_READ | FILE_SHARE_WRITE | FILE_SHARE_DELETE,
            None,
            OPEN_EXISTING,
            FILE_FLAG_BACKUP_SEMANTICS,
            None
        )

        if ctypes.c_void_p(handle).value == INVALID_HANDLE_VALUE:
            print('<Invalid_Handler Handle={} LastError={}>'.format(ctypes.c_void_p(handle).value, GetLastError()))
            exit(1)
        return handle

    def watch(self, callback):
        event_buffer = ctypes.create_string_buffer(BUFFER_SIZE)
        byte_count = ctypes.wintypes.DWORD()

        while True:
            ReadDirectoryChangesW(
                self._handle,
                ctypes.byref(event_buffer),
                BUFFER_SIZE,
                True,
                FILE_NOTIFY_CHANGE_FILE_NAME |
                FILE_NOTIFY_CHANGE_DIR_NAME |
                FILE_NOTIFY_CHANGE_ATTRIBUTES |
                FILE_NOTIFY_CHANGE_SIZE |
                FILE_NOTIFY_CHANGE_LAST_WRITE |
                FILE_NOTIFY_CHANGE_SECURITY |
                FILE_NOTIFY_CHANGE_LAST_ACCESS |
                FILE_NOTIFY_CHANGE_CREATION,
                ctypes.byref(byte_count),
                None,
                None
            )

            self._result_queue.put(event_buffer.raw[:byte_count.value])
            self._parse_event_buffer(callback)

    def _parse_event_buffer(self, callback):
        event_buffer = self._result_queue.get(False)

        event_result = list()
        while len(event_buffer):
            try:
                next_entry_offset, action, filename_length = struct.unpack('III', event_buffer[:12])
                filename = event_buffer[12:12 + filename_length].decode('utf-16')

                event_result.append((action, self._directory, str(filename)))
                if next_entry_offset == 0:
                    break
                event_buffer = event_buffer[next_entry_offset:]
            except struct.error:
                print 'struct.error', event_buffer
        callback(event_result)
