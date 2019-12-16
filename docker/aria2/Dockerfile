FROM alpine:3.10

MAINTAINER Jayson Wang <jayson@laboys.org>

ENV ARIA2_VERSION=1.35.0
ENV ARIA2_HOME=/usr/local/aria2
ENV ARIA2_CONFIG=/var/aria2/config
ENV ARIA2_DOWNLOAD=/var/aria2/download


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
        make \
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
        --with-libuv \
    \
    && make -j $(nproc) \
    && make install \
    && make clean \
    \
    && cd "${ARIA2_HOME}" \
    && rm -rf /usr/src/* \
    \
    && { find . -type f -perm +0111 -exec strip --strip-all {} + || true; } \
    && ARIA2_RUNTIME_DEPS="$( \
        scanelf --needed --nobanner --format '%n#p' --recursive ${ARIA2_HOME} \
            | tr ',' '\n' \
            | sort -u \
            | awk 'system("[ -e /usr/local/lib" $1 " ]") == 0 { next } { print "so:" $1 }' \
        )" \
    && apk add --virtual .nginx-runtime-deps $ARIA2_RUNTIME_DEPS \
    && apk del .build-deps \
    \
    && rm -rf /var/cache/apk/* \
    && rm -rf /tmp/*

RUN set -ex \
    mkdir -p "${ARIA2_CONFIG}" \
    mkdir -p "${ARIA2_DOWNLOAD}" \
    touch "${ARIA2_CONFIG}/aria2.session"

ENV PATH "${ARIA2_HOME}/bin:${PATH}"
VOLUME ["/var/aria2"]

EXPOSE 6800

WORKDIR /var/aria2
CMD ["aria2c --conf-path=${ARIA2_CONFIG}/aria2.config"]
