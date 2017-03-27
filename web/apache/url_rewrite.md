# Enable rewrite module

```
LoadModule rewrite_module modules/mod_rewrite.so
```

# Enable options

```
AllowOverride all
Options Indexes FollowSymLinks
```

# vhost example

```
<VirtualHost *:80>
    DocumentRoot "/opt/www"
    ServerName localhost

    <Directory "/opt/www">
        Options Indexes FollowSymLinks 
        AllowOverride all 
    </Directory>
</VirtualHost>
```
