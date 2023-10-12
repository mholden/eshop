#!/bin/bash

while ! curl -s identity-service:8180 > /dev/null; do echo waiting 3 seconds for identity-service; sleep 3; done;
#while ! curl -s localhost:8180 > /dev/null; do echo waiting for identity-service; sleep 3; done;
java -jar target/hldn-basket-0.0.1-SNAPSHOT.jar
