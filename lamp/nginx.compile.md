nginx compile
-------------

```
./configure \
--prefix=/usr/local/nginx \
--sbin-path=/usr/sbin/nginx \
--conf-path=/etc/nginx/nginx.conf \
--error-log-path=/var/log/nginx/nginx_error.log \
--http-log-path=/var/log/nginx/access.log \
--pid-path=/vaar/run/nginx/nginx.pid \
--user=nginx \
--group=nginx \
--with-poll_module \
--with-file-aio \
--with-http_ssl_module \
--with-http_v2_module \
--with-http_dav_module \
--with-http_gzip_static_module \
--with-http_perl_module \
```
