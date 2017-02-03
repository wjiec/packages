#include <iostream>
#include <string>
#include <set>
#include <map>

using namespace std;

// pre-declaration
class Folder;

// message classes
class Message {
    friend class Folder;

    public:
        // constructor and operator override
        Message(string title, string text = string());
        Message(const Message &copy);
        Message &operator=(const Message &) = delete;
        // destructor
        ~Message();

        // public methods
        bool save(Folder *folder);
        bool remove(Folder *folder);
        void information(void);
    private:
        string *_message_title;
        string *_message_text;
        set<Folder*> *_folders;
};

class Folder {
    friend class Message;

    public:
        // constructor and operator override
        Folder(string name);
        Folder(const Folder &copy);
        Folder &operator=(const Folder &) = delete;
        // destructor
        ~Folder();

        // public methods
        bool save(Message *message);
        bool remove(Message *message);
        bool exists(Message *message);
        void information(void);

    private:
        string *_folder_name;
        set<Message*> *_messages;
};

int main(void) {
    Folder default_folder("default");

    Message msg_1("msg_title_1", "msg_text_1");
    msg_1.save(&default_folder);
    cout << "--msg_1.save(&default_folder) msg_1 information" << endl;
    msg_1.information();
    cout << "--msg_1.save(&default_folder) default-folder information" << endl;
    default_folder.information();

    Message msg_2(msg_1);
    cout << "--msg_2(msg_1) msg_2 information" << endl;
    msg_2.information();

    Folder new_folder("new_folder_1");
    msg_2.save(&new_folder);
    cout << "--msg_2.save(&new_folder) after msg_2 information" << endl;
    msg_2.information();

    cout << "--msg_2.save(&new_folder) after msg_1 information" << endl;
    msg_1.information();

    cout << "--msg_2.save(&new_folder) after new_folder information" << endl;
    new_folder.information();

    Folder copy_folder(new_folder);
    cout << "--Folder copy_folder(new_folder) after copy_folder information" << endl;
    copy_folder.information();
    msg_1.save(&copy_folder);
    cout << "--msg_1.save(&copy_folder) after copy_folder information" << endl;
    copy_folder.information();

    Message msg_3("msg-title-3", "message-text-3");
    cout << "--Message msg_3(\"msg-title-3\", \"message-text-3\") after msg_3 information" << endl;
    msg_3.information();

    msg_3.save(&copy_folder);
    cout << "--msg_3.save(&copy_folder); after msg_3 information" << endl;
    msg_3.information();
    cout << "--msg_3.save(&copy_folder); after copy_folder information" << endl;
    copy_folder.information();

    return 0;
}

// default constructor
Message::Message(string title, string text) :
    _message_title(new string(title)), _message_text(new string(text)), _folders(new set<Folder*>()) {

    // default constructor
}

// copy-constructor
Message::Message(const Message &copy) :
    _message_title(new string(*copy._message_title)),
    _message_text(new string(*copy._message_text)),
    _folders(new set<Folder*>(*copy._folders)) {


    // copy-constructor
    for (auto _folder : *_folders) {
        _folder->save(this);
    }
}

// public method: save
//      remove folder reference
bool Message::save(Folder *folder) {
    // current message folders reference
    _folders->insert(folder);

    // on folder add current message
    folder->save(this);

    // result
    return true;
}

// public method: remove folder
//      add new folder reference
bool Message::remove(Folder *folder) {
    auto folder_it = _folders->find(folder);

    // check folder exists
    if (folder_it == _folders->end()) {
        return false;
    }

    // check message exists on folder
    if (folder->exists(this) == false) {
        return false;
    }

    // erase message reference
    (*folder_it)->remove(this);

    // erase reference
    _folders->erase(folder_it);

    return true;
}

// destructor
Message::~Message() {
    // check reference-count
    if (_folders->size() == 0) {
        delete _message_text;
        delete _message_text;
        delete _folders;
    }
}


// Folder default-constructor
Folder::Folder(string name) :
    _folder_name(new string(name)), _messages(new set<Message*>()) {

    // default-constructor
}

// Folder copy-constructor
Folder::Folder(const Folder &copy) :
    _folder_name(new string(*copy._folder_name)), _messages(new set<Message*>(*copy._messages)) {

    // copy-constructor
    for (auto _message : *_messages) {
        _message->save(this);
    }
}

// destructor
Folder::~Folder() {
    if (_messages->size() == 0) {
        delete _folder_name;
        delete _messages;
    }
}

// public method: save
//      add new message reference
bool Folder::save(Message *message) {
    _messages->insert(message);

    return true;
}

// public method: remove
//      remove message reference
bool Folder::remove(Message *message) {
    auto message_it = _messages->find(message);

    // check exists
    if (message_it == _messages->end()) {
        return false;
    }

    // erase current folder reference
    (*message_it)->remove(this);

    // erase message
    _messages->erase(message_it);

    return true;
}

// public method: exists
//      check message exists
bool Folder::exists(Message *message) {
    return (_messages->find(message)) != (_messages->end());
}

void Message::information(void) {
    cout << endl;
    for (auto _folder : *_folders) {
        cout << "  " << *_folder->_folder_name << endl;
    }
    cout << endl;
}

void Folder::information(void) {
    cout << endl;
    for (auto _message : *_messages) {
        cout << "  " << *_message->_message_title << endl;
    }
    cout << endl;
}
