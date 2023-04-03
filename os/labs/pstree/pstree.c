#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdarg.h>
#include <stdlib.h>
#include <ctype.h>

#include <unistd.h>
#include <dirent.h>

#define ARG_VERSION         "version"
#define ARG_SHOW_PIDS       "show-pids"
#define ARG_NUMERIC_SORT    "numeric-sort"

#ifndef FILENAME_MAX
#define FILENAME_MAX        1024
#endif

#define BUFFER_MAX          256
#define PROC_MAX            65536

bool show_pids = false;
bool numeric_sort = false;
bool show_version = false;

static void log_debug(char *format, ...) {
#ifdef BUILD_DEBUG
    va_list args;
    va_start(args, format);
    vfprintf(stderr, format, args);
    fprintf(stderr, "\n");
    va_end(args);
#endif
}

static bool parse_flags(int argc, char *argv[]) {
    for (int i = 1; i < argc; i++) {
        const char *arg = argv[i];
        if (!arg || arg[0] != '-') {
            return false;
        }

        if (arg[1] == '-') {
            if (strcmp(arg + 2, ARG_SHOW_PIDS) == 0) {
                show_pids = true;
            } else if (strcmp(arg + 2, ARG_NUMERIC_SORT) == 0) {
                numeric_sort = true;
            } else if (strcmp(arg + 2, ARG_VERSION) == 0) {
                show_version = true;
            } else {
                fprintf(stderr, "unknown option: %s\n\n", arg + 2);
                return false;
            }
        } else {
            for (int j = 1; arg[j] != '\0'; j++) {
                switch (arg[j]) {
                    case 'p':
                        show_pids = true;
                        break;
                    case 'n':
                        numeric_sort = true;
                        break;
                    case 'V':
                        show_version = true;
                        break;
                    default:
                        fprintf(stderr, "unknown option: %c\n\n", arg[j]);
                        return false;
                }
            }
        }
    }

    return true;
}

static void print_usage(const char *cmd) {
    fprintf(stderr, "usage: %s [-p, --show-pids] [-n, --numeric-sort] [-V, --version]\n", cmd);
}

static void print_version() {
    fprintf(stderr, "pstree (OSLAB) FAMILY\n");
    fprintf(stderr, "Copyright (C) 2023 Jayson Wang\n");
}

static bool is_digits(const char *buf) {
    for (int i = 0; buf && buf[i] != '\0'; i++) {
        if (!isdigit(buf[i])) return false;
    }
    return true;
}

typedef struct snapshot_t *Snapshot;

static Snapshot take_snapshot();
static void print_snapshot(Snapshot snapshot);
static void free_snapshot(Snapshot *snapshot);

int main(int argc, char *argv[]) {
    if (!parse_flags(argc, argv)) {
        print_usage(argv[0]);
        return 2;
    }

    log_debug("arg.show-pids: %d", show_pids);
    log_debug("arg.numeric-sort: %d", numeric_sort);
    log_debug("arg.show_version: %d", show_version);

    if (show_version) {
        print_version();
        return 0;
    }

    Snapshot snapshot = take_snapshot();
    if (snapshot == NULL) return 1;

    print_snapshot(snapshot);
    free_snapshot(&snapshot);

    return 0;
}

typedef struct process_t *Process;

static void free_process(Process *proc);
static Process load_process(int pid);

typedef struct snapshot_node_t *SnapshotNode;

struct snapshot_t {
    Process proc;

    SnapshotNode brother;
    SnapshotNode children;
};

extern Snapshot take_snapshot() {
    DIR *root = opendir("/proc");
    if (!root) return NULL;

    int pc = 0;
    char *end = NULL;
    struct dirent *curr;
    Process processes[PROC_MAX];
    while ((curr = readdir(root))) {
        if ((curr->d_type & DT_DIR) != 0 && is_digits(curr->d_name)) {
            int pid = (int) strtol(curr->d_name, &end, 10);
            Process proc = load_process(pid);
            if (!proc) return NULL;
            processes[pc++] = proc;
        }
    }

    return NULL;
}

extern void print_snapshot(Snapshot snapshot) {
}

extern void free_snapshot(Snapshot *snapshot) {
    if (snapshot && *snapshot) {
        free_process(&(*snapshot)->proc);
        if ((*snapshot)->brother) {
            free_snapshot((Snapshot *) &(*snapshot)->brother);
        }
        if ((*snapshot)->children) {
            free_snapshot((Snapshot *) &(*snapshot)->children);
        }
        *snapshot = NULL;
    }
}

struct process_t {
    int pid;
    int ppid;
    char *name;
};

extern Process load_process(int pid) {
    char filename[FILENAME_MAX];
    snprintf(filename, FILENAME_MAX, "/proc/%d/stat", pid);

    Process proc = (Process) malloc(sizeof(struct process_t));
    if (!proc) return NULL;

    FILE *fp = fopen(filename, "r");
    if (!fp) return NULL;

    char *end;
    char buffer[BUFFER_MAX];

    // pid
    fscanf(fp, "%255s", buffer);
    proc->pid = (int) strtol(buffer, &end, 10);
    log_debug("proc.%d.pid: %d", pid, proc->pid);

    // name
    fscanf(fp, "%255s", buffer);
    // the filename of the executable, in parentheses
    size_t name_length = sizeof(char) * strlen(buffer) - 2;
    proc->name = malloc(name_length + 1); // padding \0 in suffix
    memset(proc->name, 0, name_length);
    strncpy(proc->name, buffer + 1, name_length);
    log_debug("proc.%d.pid: %s", pid, proc->name);

    // state(discard)
    fscanf(fp, "%1s", buffer);
    log_debug("proc.%d.state: %c", pid, buffer[0]);

    // ppid
    fscanf(fp, "%255s", buffer);
    proc->ppid = (int) strtol(buffer, &end, 10);
    log_debug("proc.%d.ppid: %d", pid, proc->ppid);

    fclose(fp);
    return proc;
}

extern void free_process(Process *proc) {
    if (proc && *proc) {
        free((*proc)->name);
        free(*proc);
        *proc = NULL;
    }
}
