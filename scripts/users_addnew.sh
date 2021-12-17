#!/bin/bash
 
if [ -n "$1" -a -n "$2" ]; then

  curl -s -D /dev/stderr -X POST -u $1 -H "Content-Type: application/json" -d "
	{\"email\":\"$2\",\"password\":\"$3\",\"name\":\"$4\",\"enabled\":\"$5\",\"admin\":\"$6\"}" \
	http://localhost:8080/users |json_pp
else
	echo "Use $0 <email:password> <email> <password> <name> <enabled(true/false)> <admin(true/false)>"
fi

