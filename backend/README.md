0. set up https certs
-see backend/services/identity/https/README.md

1. init the db  

docker-compose up db  
mysql -h 127.0.0.1 -u root -p < services/identity/scripts/db-init.sql  
mysql -h 127.0.0.1 -u root -p < services/catalog/scripts/db-init.sql  
#mysql -h 127.0.0.1 -u root -p < services/basket/scripts/db-init.sql # note: basket uses redis now, not mysql  
mysql -h 127.0.0.1 -u root -p < services/order/scripts/db-init.sql  
mysql -h 127.0.0.1 -u root -p < services/payment/scripts/db-init.sql  

2. init the identity service

docker-compose up identity  
localhost:8090, login with admin:admin 
# note: for external deployment like aws, keycloak will require https. map 8443 to 8543 in docker-compose.yml, then use https://<ip-address>:8543 instead of localhost:8090
create a realm using services/identity/scripts/realm-import.json  
-TODO: make sure valid redirect url's are set up properly (needs localhost:3000 for local front end, needs * for mobile right now which needs to be fixed)
-TODO: add '+' to 'web origins' for CORS (which just enables all CORS stuff for the same urls as what is set in your valid redirect urls)
-TODO: realm settings -> security defenses -> set hsts max age to 0 so that it doesn't force https (do this for both master realm and the realm you create with realm-import)
 - otherwise you can't use http for other services located at same domain name
-TODO; clients -> client details -> settings -> client authentication -> turn this off to make it public access type, otherwise you'll hit issues with client secrets
#clients > spring-cloud-gateway-client > credentials > regenerate client secret, and then update that client secret in gateway > application.yml # not needed anymore
users > add user for the TestEShop users (alice@testeshop.ca/alice, admin@testeshop.ca/admin)
roles > add roles USER, ADMIN and give admin@testeshop.ca the ADMIN role

3. start up the rest of the services

docker-compose up  

4. test

currently, ESHOP_EXTERNAL_DNS_NAME_OR_IP in .env file should be set to docker.for.mac.localhost for backend tests
-TODO: need to change backend tests to work with domain name

cd test_eshop/TestEShop
mvn -DskipTests clean package
java -jar target/test-eshop-0.0.1-SNAPSHOT.jar
