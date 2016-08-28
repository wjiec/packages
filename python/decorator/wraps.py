def wraps(origin):
    def decorator(func):
        def wrapper(*key, **kwords):
            return func(*key, **kwords)
        wrapper.__name__ = origin.__name__
        return wrapper
    return decorator

def logging(module):
    def decorator(func):
        @wraps(func)
        def wrapper(*key, **kwords):
            print(module, "...", func.__name__, key, kwords)
            return func(*key, **kwords)
        return wrapper
    return decorator

@logging('Syntax')
def multi(x, y):
    return x * y
