#!/bin/bash

if [ x$1 != x -a x$2 != x ]; then

  curl -s -D /dev/stderr -X PUT -u $1 -H "Content-Type: application/json" -d $3 http://localhost:8080/menus/$2 |json_pp
else
	echo "Use $0 <email:password> <useriD> <json>"
fi

