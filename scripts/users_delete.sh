#!/bin/bash

if [ -n "$1" -a -n "$2" ]; then
  curl -s -D /dev/stderr -X DELETE "http://127.0.0.1:8080/users/$2" -u $1 |json_pp
else
	echo "Use $0 <email:password> <id>" 
fi
