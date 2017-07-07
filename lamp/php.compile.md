PHP7.1.6 Compile
----------------

```
./configure \
--prefix=/usr/local/php7 \
--exec-prefix=/usr/local/php7 \
--bindir=/usr/local/php7/bin \
--sbindir=/usr/local/php7/sbin \
--includedir=/usr/local/php7/include \
--libdir=/usr/local/php7/lib/php \
--with-config-file-path=/usr/local/php7/etc \
--with-mysql-sock=/tmp/mysql.sock \
--with-mcrypt=/usr/include \
--with-mhash \
--with-openssl \
--with-mysqli=shared,mysqlnd \
--with-pdo-mysql=shared,mysqlnd \
--with-iconv \
--with-zlib \
--enable-zip \
--enable-inline-optimization \
--disable-debug \
--disable-rpath \
--enable-shared \
--enable-xml \
--enable-bcmath \
--enable-shmop \
--enable-sysvsem \
--enable-mbregex \
--enable-mbstring \
--enable-ftp \
--enable-gd-native-ttf \
--enable-pcntl \
--enable-sockets \
--with-xmlrpc \
--enable-soap \
--without-pear \
--with-gettext \
--enable-session \
--with-curl \
--with-jpeg-dir \
--with-freetype-dir \
--enable-opcache \
--enable-fpm \
--with-fpm-user=nginx \
--with-fpm-group=nginx \
--without-gdbm \
--with-mcrypt=/usr/local/related/libmcrypt \
--disable-fileinfo

```