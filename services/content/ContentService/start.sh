#!/bin/bash

while ! curl -s identity-service:8180 > /dev/null; do echo waiting 10 seconds for identity-service; sleep 10; done;
#while ! curl -s localhost:8180 > /dev/null; do echo waiting for identity-service; sleep 3; done;
java -jar target/hldn-content-0.0.1-SNAPSHOT.jar 
