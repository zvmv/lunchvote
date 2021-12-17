#!/bin/bash
 
if [ -n "$1" -a -n "$2" ]; then

  curl -s -D /dev/stderr -X POST -u $1 -H "Content-Type: application/json" -d "
	{\"dishes\":\"$2\",\"menudate\":\"$3\",\"price\":\"$4\",\"restaurant\":\"$5\"}" \
	http://localhost:8080/menus |json_pp
else
	echo "Use $0 <email:password> <dishes> <menudate> <price> <restaurant>"
fi

