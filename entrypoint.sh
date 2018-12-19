#!/bin/sh

export _JAVA_OPTIONS="$_JAVA_OPTIONS -Dssl.keystore=${WIDI_TOMCAT_SSL_CERT}"

catalina.sh run

