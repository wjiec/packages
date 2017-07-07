Mysql Server Compile
--------------------

```
cmake -DWITH_BOOST=/usr/local/boost_1_59_0
```

or

```
cmake \
-DCMAKE_INSTALL_PREFIX=/usr/local/mysql \
-DMYSQL_DATADIR=/usr/local/mysql/data \
-DWITH_BOOST=/usr/local/boost_1_59_0 \
-DSYSCONFDIR=/usr/local/mysql/etc \
-DEFAULT_CHARSET=utf8mb4 \
-DDEFAULT_COLLATION=utf8mb4_general_ci \
-DENABLED_LOCAL_INFILE=1 \
-DEXTRA_CHARSETS=all
```

