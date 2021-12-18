#!/bin/bash
 
if [ -n "$1" -a -n "$2" ]; then

  curl -s -D /dev/stderr -X POST -u $1 -H "Content-Type: application/json" http://localhost:8080/votes?id=$2 |json_pp
else
	echo "Use $0 <email:password> <menuId>"
fi

