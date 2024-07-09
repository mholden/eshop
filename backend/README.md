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
create a realm using services/identity/scripts/realm-import.json  
clients > spring-cloud-gateway-client > credentials > regenerate client secret, and then update that client secret in gateway > application.yml
users > add user for the TestEShop users (alice@testeshop.ca/alice, admin@testeshop.ca/admin)
roles > add roles USER, ADMIN and give admin@testeshop.ca the ADMIN role

3. start up the rest of the services

docker-compose up  

4. test

currently, ESHOP_EXTERNAL_DNS_NAME_OR_IP in .env file should be set to docker.for.mac.localhost for backend tests

cd test_eshop/TestEShop
mvn -DskipTests clean package
java -jar target/test-eshop-0.0.1-SNAPSHOT.jar
