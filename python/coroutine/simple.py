#!/usr/bin/env python3

def consumer():
    response = ''

    while True:
        message = yield response

        if not message:
            return

        print('[CONSUMER] Consumer <%s>...' % ( message ))
        response = '{CONSUMER OK}'

def product(consumer):
    r = consumer.send(None)# next() first, start generator
    assert(r == '', 'never')

    for require in range(1, 9):
        print('[PRODUCT]  Product <%s>...' % (require))
        response = consumer.send(require)
        print('[PRODUCT]  Consumer response <%s>' % response)

if __name__ == '__main__':
    print(type(consumer()))
    product(consumer())