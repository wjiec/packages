from __future__ import print_function

class Chain(object):

    def __init__(self, path = '', protocol = None, domain = None):
        if protocol is not None and domain is not None:
            self.__path = protocol + '://' + domain
        else:
            self.__path = path

    def __getattr__(self, attr):
        return Chain("%s/%s" % ( self.__path, attr ))

    def __str__(self):
        return self.__path

    def __repr__(self):
        return self.__path

    def __call__(self, *key):
        return self.__getattr__(*key)

if __name__ == '__main__':
    print(Chain(protocol = 'http', domain = 'api.sina.com').status.user.timeline.list)
    print(Chain(protocol = 'http', domain = 'api.sina.com').status.user('ShadowMan').timeline.list)
