#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <stdarg.h>
#include <stdlib.h>
#include <ctype.h>
#include <dirent.h>
#include <math.h>

#define ARG_VERSION         "version"
#define ARG_SHOW_PIDS       "show-pids"
#define ARG_NUMERIC_SORT    "numeric-sort"

#define BUFFER_MAX          256

// ---------------------- Globals ----------------------
bool show_pids = false;
bool numeric_sort = false;
bool show_version = false;

// ---------------------- Utility ----------------------
static void log_debug(char *format, ...) {
#ifdef BUILD_DEBUG
    va_list args;
    va_start(args, format);
    vfprintf(stderr, format, args);
    fprintf(stderr, "\n");
    va_end(args);
#endif
}

static bool is_digits(const char *buf) {
    for (int i = 0; buf && buf[i] != '\0'; i++) {
        if (!isdigit(buf[i])) return false;
    }
    return true;
}

static void *zero_malloc(size_t size) {
    void *mem = malloc(size);
    if (mem) memset(mem, 0, size);
    return mem;
}

// ---------------------- Flags ----------------------
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

// ---------------------- Process ----------------------
typedef struct process_t {
    int pid;
    int ppid;
    char *name;
} *Process;

static Process load_process(int pid) {
    char filename[FILENAME_MAX];
    snprintf(filename, FILENAME_MAX, "/proc/%d/stat", pid);

    Process proc = (Process) zero_malloc(sizeof(struct process_t));
    if (!proc) return NULL;

    FILE *fp = fopen(filename, "r");
    if (!fp) return NULL;

    char *end;
    char buffer[BUFFER_MAX];

    // pid
    if (fscanf(fp, "%255s", buffer) <= 0) return NULL;
    proc->pid = (int) strtol(buffer, &end, 10);
    log_debug("proc.%d.pid: %d", pid, proc->pid);

    // name
    if (fscanf(fp, "%255s", buffer) <= 0) return NULL;
    // the filename of the executable, in parentheses
    size_t name_length = sizeof(char) * strlen(buffer) - 2;
    proc->name = zero_malloc(name_length + 1); // padding \0 in suffix
    memset(proc->name, 0, name_length);
    strncpy(proc->name, buffer + 1, name_length);
    log_debug("proc.%d.pid: %s", pid, proc->name);

    // state(discard)
    if (fscanf(fp, "%1s", buffer) <= 0) return NULL;
    log_debug("proc.%d.state: %c", pid, buffer[0]);

    // ppid
    if (fscanf(fp, "%255s", buffer) <= 0) return NULL;
    proc->ppid = (int) strtol(buffer, &end, 10);
    log_debug("proc.%d.ppid: %d", pid, proc->ppid);

    fclose(fp);
    return proc;
}

static void free_process(Process *proc) {
    if (proc && *proc) {
        free((*proc)->name);
        free(*proc);
        *proc = NULL;
    }
}

// ---------------------- Snapshot ----------------------
typedef struct snapshot_t {
    Process proc;

    struct snapshot_t *next;
} *Snapshot;

static Snapshot create_snapshot(Process proc) {
    Snapshot snap = (Snapshot) zero_malloc(sizeof(struct snapshot_t));
    if (!snap) return NULL;

    snap->proc = proc;
    return snap;
}

static void free_snapshot(Snapshot *snap) {
    if (snap && *snap) {
        free_process(&(*snap)->proc);
        free_snapshot(&(*snap)->next);

        free(*snap);
        *snap = NULL;
    }
}

static Snapshot merge_snapshot(Snapshot a, Snapshot b) {
    Snapshot dummy = create_snapshot(NULL), curr = dummy;
    while (a && b) {
        if (a->proc->pid < b->proc->pid) {
            curr->next = a;
            a = a->next;
        } else {
            curr->next = b;
            b = b->next;
        }
        curr = curr->next;
    }
    if (a != NULL) curr->next = a;
    if (b != NULL) curr->next = b;

    Snapshot sorted = dummy->next;
    dummy->next = NULL;
    free_snapshot(&dummy);

    return sorted;
}

static Snapshot sort_snapshot(Snapshot start, Snapshot end) {
    if (!start) return NULL;
    if (start->next == end) {
        start->next = NULL;
        return start;
    }

    Snapshot slow = start, fast = start;
    while (fast != end) {
        slow = slow->next;
        fast = fast->next;
        if (fast != end) fast = fast->next;
    }

    return merge_snapshot(sort_snapshot(start, slow), sort_snapshot(slow, end));
}

static Snapshot take_snapshots() {
    DIR *root = opendir("/proc");
    if (!root) return NULL;

    char *end = NULL;
    struct dirent *entry;
    Snapshot snap = create_snapshot(NULL), curr = snap;
    while ((entry = readdir(root))) {
        if ((entry->d_type & DT_DIR) == DT_DIR && is_digits(entry->d_name)) {
            int pid = (int) strtol(entry->d_name, &end, 10);

            Process proc = load_process(pid);
            if (!proc) return NULL;

            curr->next = create_snapshot(proc);
            if (!curr->next) return NULL;

            curr = curr->next;
        }
    }
    closedir(root);

    if (numeric_sort) {
        snap = sort_snapshot(snap->next, NULL);
    }
    return snap;
}

static int print_snapshot(Snapshot snap, int indent, int parent) {
    int children_count = 0, curr_children = 0;
    for (Snapshot curr = snap; curr != NULL; curr = curr->next) {
        if (curr->proc && curr->proc->ppid == parent) children_count++;
    }

    for (Snapshot curr = snap; curr != NULL; curr = curr->next) {
        if (curr->proc && curr->proc->ppid == parent) {
            // 0: idle, 1: init, 2: kthreadd
            // list processes on userland only
            if (curr->proc->pid == 2) continue;
            int indent_step = (int) strlen(curr->proc->name);

            if (curr_children++ == 0) {
                if (indent != 0) {
                    indent_step += 3;
                    if (children_count == 1) printf("---");
                    else printf("-+-");
                }
            } else {
                if (indent != 0) {
                    indent_step += 3;
                    printf("%*s %c-", indent, "", curr_children == children_count ? '`' : '|');
                }
            }

            if (show_pids) {
                indent_step += (int) (log(curr->proc->pid) / log(10)) + 3;
                printf("%s(%d)", curr->proc->name, curr->proc->pid);
            } else printf("%s", curr->proc->name);

            print_snapshot(snap, indent + indent_step, curr->proc->pid);
        }
    }
    if (children_count == 0) printf("\n");
    return children_count;
}

int main(int argc, char *argv[]) {
    if (!parse_flags(argc, argv)) {
        print_usage(argv[0]);
        return EXIT_FAILURE;
    }

    log_debug("arg.show-pids: %d", show_pids);
    log_debug("arg.numeric-sort: %d", numeric_sort);
    log_debug("arg.show_version: %d", show_version);

    if (show_version) {
        print_version();
        return EXIT_SUCCESS;
    }

    Snapshot snap = take_snapshots();
    if (snap == NULL) return EXIT_FAILURE;

    print_snapshot(snap, 0, 0);
    free_snapshot(&snap);

    return EXIT_SUCCESS;
}
