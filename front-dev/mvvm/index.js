function One (options) {
    if (!(this instanceof One)) {
        console.warn('One is a constructor and should be called with the `new` keyword')
    }

    this._init(options)
}

One.prototype._init = function (options) {
    this.$options = options

    this._initData()
};

One.prototype._initData = function() {
    this.$data = this.$options.data

    var self = this
    Object.keys(this.$data).forEach(function (key) {
        self._proxy(key)
    })
};

One.prototype._proxy = function (key) {
    var self = this
    Object.defineProperty(self, key, {
        configurable: true,
        enumerable: true,
        get: function proxyGetter() {
            return self.$data[key]
        },
        set: function proxySetter(value) {
            self.$data[key] = value
        }
    })
};
