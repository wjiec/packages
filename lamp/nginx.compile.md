nginx compile
-------------

```
./configure \
--prefix=/usr/local/nginx \
--sbin-path=/usr/local/nginx/bin/nginx \
--conf-path=/usr/local/nginx/etc/nginx.conf \
--error-log-path=/var/log/nginx/nginx_error.log \
--pid-path=/var/run/nginx/nginx.pid \
--lock-path=/var/lock/nginx/nginx.lock \
--user=nginx \
--group=nginx \
--with-poll_module \
--with-file-aio \
--with-http_ssl_module \
--with-http_v2_module \
--with-http_dav_module \
--with-http_gzip_static_module \
--with-http_perl_module
```
