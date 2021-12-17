#!/bin/bash
curl -s -D /dev/stderr -X GET "http://127.0.0.1:8080/users" -u admin@yandex.ru:pass |json_pp
