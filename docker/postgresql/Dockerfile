FROM postgres:13.3
LABEL maintainer="ram0973@gmail.com"
RUN echo "ru_RU.UTF-8 UTF-8" >> /etc/locale.gen && locale-gen && \
    localedef -i ru_RU -c -f UTF-8 -A /usr/share/locale/locale.alias ru_RU.UTF-8
ENV LANG ru_RU.utf8
ENV TZ UTC
RUN echo "$TZ" > /etc/timezone && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime
VOLUME  ["/var/lib/postgresql/data"]
EXPOSE 5432
