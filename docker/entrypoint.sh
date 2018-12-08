#!/bin/bash -e

jar_file='/opt/aimoney/aimoney.jar'
profile_active=${ENVIRONMENT:-stage}

if [[ -z $1 ]] || [[ ${1:0:1} == '-' ]] ; then
	exec java $JVM_OPTS -jar $jar_file "$@"
else
  exec "$@"
fi
