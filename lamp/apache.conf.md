Apache with PHP configure on Windows
------------------------------------


```
LoadModule php7_module D:/usr/php7/php7apache2_4.dll
PHPInidir "D:/usr/php7"
AddType application/x-httpd-php .php .phtml
```


Apache generic configure
------------------------


#### mod_rewrite

```
LoadModule rewrite_module modules/mod_rewrite.so
```


#### mod_gzip

```
LoadModule filter_module modules/mod_filter.so
LoadModule headers_module modules/mod_headers.so
LoadModule deflate_module modules/mod_deflate.so

<IfModule mod_deflate.c>
    SetOutputFilter DEFLATE

    SetEnvIfNoCase Request_URL .(?:gif|jpg?g|png)$ no-gzip dont-vary
    SetEnvIfNocase Request_URI .(?:exe|t?gz|zip|bz2|sit|rar)$ no-gzip dont-vary
    SetEnvIfNocase Request_URI .(?:pdf|mov|avi|mp3|mp4|rm)$ no-gzip dont-vary

    AddOutputFilterByType DEFLATE text/html text/plain text/xml
    AddOutputFilterByType DEFLATE application/ms* application/vnd* application/postscript application/javascript application/x-javascript
    AddOutputFilterByType DEFLATE application/x-httpd-php application/x-httpd-fastphp
    
    BrowserMatch ^Mozilla/4 gzip-only-text/html
    BrowserMatch ^Mozilla/4.0[678] no-gzip
    BrowserMatch \bMSIE !no-gzip !gzip-only-text/html

    DeflateCompressionLevel 9
</IfModule>
```


#### expires_module

```
<IfModule expires_module>
    ExpiresActive on

    ExpiresByType text/css A2592000

    ExpiresByType application/x-javascript A2592000
    ExpiresByType application/javascript A2592000

    ExpiresByType image/jpeg A2592000
    ExpiresByType image/gif A2592000
    ExpiresByType image/png A2592000
    ExpiresByType image/x-icon A2592000
</IfModule>
```


#### Apache Token

```
ServerTokens Prod
ServerSignature Off
```