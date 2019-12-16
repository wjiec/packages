FROM alpine:3.10

MAINTAINER Jayson Wang <jayson@laboys.org>

ENV ARIA2_VERSION=1.35.0
ENV ARIA2_HOME=/usr/local/aria2


RUN set -ex \
    && apk update \
    && apk add --virtual .build-deps \
        autoconf \
        automake \
        c-ares-dev \
        ca-certificates \
        curl \
        dpkg \
        dpkg-dev \
        expat-dev \
        file \
        gcc \
        gnutls-dev \
        g++ \
        libgcrypt-dev \
        libgpg-error-dev \
        libssh2-dev \
        linux-headers \
        libuv-dev \
        libxml2-dev \
        openssl-dev \
        sqlite-dev \
        zlib-dev \
    && mkdir -p "/usr/src" \
    && curl -SL "https://github.com/aria2/aria2/releases/download/release-${ARIA2_VERSION}/aria2-${ARIA2_VERSION}.tar.gz" -o aria2.tar.gz \
    \
    && tar xvf aria2.tar.gz -C /usr/src \
    && rm -rf aria2.tar.gz* \
    \
    && mkdir -p "${ARIA2_HOME}" \
    && apk add --virtual .runtime-deps \
        openssl \
    \
    && cd "/usr/src/aria2-${ARIA2_VERSION}" \
    && ./configure \
        --prefix="${ARIA2_HOME}" \
        --with-libuv
