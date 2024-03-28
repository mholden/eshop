#!/bin/bash

while ! curl -s docker.for.mac.localhost:8090 > /dev/null; do echo waiting 10 seconds for identity-service; sleep 10; done;
java -jar target/hldn-gateway-0.0.1-SNAPSHOT.jar 
