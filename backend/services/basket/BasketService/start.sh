#!/bin/bash

#DHOST=docker.for.mac.localhost
DHOST=172.17.0.1

while ! curl -s $DHOST:8090 > /dev/null; do echo waiting 10 seconds for identity-service; sleep 10; done;
java -jar target/hldn-basket-0.0.1-SNAPSHOT.jar
