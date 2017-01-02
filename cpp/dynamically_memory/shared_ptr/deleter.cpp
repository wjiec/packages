#include <iostream>
#include <memory>

using namespace std;

typedef struct _destination_t {
    char ip_addr;
    int port;
} Destination;

typedef struct _connection_t {
    int socket_fd;
} Connection;

Connection *connect(Destination *d);
void disconnect(Connection *conn);
void func(Destination &d);

int main(void) {
    Destination d = { 'A', 80 };

    try {
        func(d);
    } catch (exception &e) {
        cout << e.what() << endl;
    }
    

    return 0;
}

Connection *connect(Destination *d) {
    cout << "enter connect" << endl;

    Connection *conn = new Connection({d->port});
    cout<< "malloc new conn = " << conn << endl;

    return conn;
}

void disconnect(Connection *conn) {
    cout << "enter disconnect" << endl;

    delete conn;

    cout << "delete conn" << endl;
}

void func(Destination &d) {
    cout << "enter func" << endl;

    shared_ptr<Connection> sp = shared_ptr<Connection>(connect(&d), disconnect);
    cout << "smart pointer is " << sp << " use_count = " << sp.use_count() << endl;

    cout << sp->socket_fd << endl;
    
    // throw error
    throw exception();
}
