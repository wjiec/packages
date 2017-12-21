#include <Python/Python.h>
#include <CoreFoundation/CoreFoundation.h>
#include <CoreServices/CoreServices.h>


#define RETURN_NULL_IF_NULL(__EXP) do {\
    if (!(__EXP)) {\
        return NULL;\
    } \
} while (0)


typedef struct {
    PyObject *callback;
    FSEventStreamRef stream_ref;
    CFRunLoopRef run_loop_ref;
} StreamCallbackInfo;


static int  watchdog_test(const int a, const int b) {
    return a + b;
}


static void watchdog_fsevent_stream_callback(ConstFSEventStreamRef ref,
                                             StreamCallbackInfo *context,
                                             size_t num_events,
                                             const char *paths[],
                                             const FSEventStreamEventFlags event_flags[],
                                             const FSEventStreamEventId event_ids[]) {
    PyObject *py_event_paths = PyList_New(num_events);
    PyObject *py_event_flags = PyList_New(num_events);
    
    if (!(py_event_paths && py_event_flags)) {
        Py_DecRef(py_event_paths);
        Py_DecRef(py_event_flags);
        return;
    }
    
    PyObject *path = NULL;
    PyObject *flag = NULL;
    for (int i = 0; i < num_events; ++i) {
        path = PyString_FromString(paths[i]);
        flag = PyInt_FromLong(event_flags[i]);
        
        if (!(path && flag)) {
            Py_DecRef(py_event_paths);
            Py_DecRef(py_event_flags);
            return;
        }
        
        PyList_SET_ITEM(py_event_paths, i, path);
        PyList_SET_ITEM(py_event_flags, i, flag);
    }
    
    PyObject *callback_result = NULL;
    callback_result = PyObject_CallFunction(context->callback, "OO", py_event_paths, py_event_flags);
    if (callback_result == NULL) {
        if (PyErr_Occurred()) {
            PyErr_SetString(PyExc_ValueError, "Unable to call Python callback");
        }
        CFRunLoopStop(context->run_loop_ref);
    }
}


static FSEventStreamRef watchdog_create_fsevent_stream(char *path, StreamCallbackInfo *callback) {
    FSEventStreamRef stream_ref = NULL;
    CFAbsoluteTime stream_latency = 0.01;
    CFStringRef cf_string = NULL;
    CFMutableArrayRef paths = NULL;
    
    paths = CFArrayCreateMutable(kCFAllocatorDefault, 1, &kCFTypeArrayCallBacks);
    cf_string = CFStringCreateWithCString(kCFAllocatorDefault, path, kCFStringEncodingUTF8);
    CFArraySetValueAtIndex(paths, 0, cf_string);
    
    FSEventStreamContext context = {
        0, callback, NULL, NULL, NULL
    };
    
    stream_ref = FSEventStreamCreate(kCFAllocatorDefault,
                                     (FSEventStreamCallback)&watchdog_fsevent_stream_callback,
                                     &context,
                                     paths,
                                     kFSEventStreamEventIdSinceNow,
                                     stream_latency,
                                     kFSEventStreamCreateFlagFileEvents);

    CFRelease(cf_string);
    CFRelease(paths);

    return stream_ref;
}


/************************ Wrapper Function *******************************/
static PyObject *watchdog_add_watch_wrapper(PyObject *self, PyObject *args) {
    PyObject *path = NULL;
    PyObject *callback = NULL;
    RETURN_NULL_IF_NULL(PyArg_ParseTuple(args, "OO", &path, &callback));
    
    StreamCallbackInfo *callback_info = NULL;
    callback_info = PyMem_New(StreamCallbackInfo, 1);
    RETURN_NULL_IF_NULL(callback_info);
    
    char *c_string = PyString_AsString(path);
    FSEventStreamRef stream_ref = watchdog_create_fsevent_stream(c_string, callback_info);
    
    PyObject *value = NULL;
    value = PyCObject_FromVoidPtr(stream_ref, PyMem_Free);
    
    CFRunLoopRef run_loop_ref;
    run_loop_ref = CFRunLoopGetCurrent();
    FSEventStreamScheduleWithRunLoop(stream_ref, run_loop_ref, kCFRunLoopDefaultMode);
    
    callback_info->callback = callback;
    callback_info->stream_ref = stream_ref;
    callback_info->run_loop_ref = run_loop_ref;
    Py_IncRef(callback);
    
    if (!FSEventStreamStart(stream_ref)) {
        FSEventStreamInvalidate(stream_ref);
        FSEventStreamRelease(stream_ref);
        return NULL;
    }
    
    CFRunLoopRun();
    
    if (PyErr_Occurred()) {
        Py_DecRef(callback);
        return NULL;
    }
    
    Py_IncRef(Py_None);
    return Py_None;
}


static PyObject *watchdog_test_wrapper(PyObject *self, PyObject *args) {
    int number1;
    int number2;
    RETURN_NULL_IF_NULL(PyArg_ParseTuple(args, "ii", &number1, &number2));
    
    return PyLong_FromLong(watchdog_test(number1, number2));
}


static PyMethodDef watchdog_fsevents_methods[] = {
    {"test", watchdog_test_wrapper, METH_VARARGS, NULL},
    {"add_watch", watchdog_add_watch_wrapper, METH_VARARGS, NULL},
    {NULL, NULL, 0, NULL}
};


void initwatchdog_fsevents(void) {
    Py_InitModule("watchdog_fsevents", watchdog_fsevents_methods);
}
