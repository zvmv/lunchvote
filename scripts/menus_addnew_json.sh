#!/bin/bash

if [ x$1 != x -a x$2 != x ]; then

  curl -s -D /dev/stderr -X POST -u $1 -H "Content-Type: application/json" -d $2 http://localhost:8080/menus|json_pp
else
	echo "Use $0 <email:password> <json>"
fi

