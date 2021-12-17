#!/bin/bash

if [ x$1 != x ]; then
  curl -s -D /dev/stderr -X GET "http://127.0.0.1:8080/menus/date/$1" |json_pp
else
	echo "Use $0 <yyyy-mm-dd>" 
fi
