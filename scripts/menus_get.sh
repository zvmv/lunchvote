#!/bin/bash

if [ -n $1 ]; then
  curl -s -D /dev/stderr -X GET "http://127.0.0.1:8080/menus/$1" |json_pp
else
	echo "Use $0 <menuId>" 
fi
