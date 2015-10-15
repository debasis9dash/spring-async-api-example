#!/usr/bin/env bash

sleep $1
curl -H "Content-Type: application/json" -X POST -d "$2" http://10.0.2.2:8080/callback.service/method
