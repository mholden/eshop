1. init the db

docker-compose up db
mysql -h 127.0.0.1 -u root -p < services/identity/scripts/db-init.sql 
mysql -h 127.0.0.1 -u root -p < services/catalog/scripts/db-init.sql 
#mysql -h 127.0.0.1 -u root -p < services/basket/scripts/db-init.sql # note: basket uses redis now, not mysql
mysql -h 127.0.0.1 -u root -p < services/order/scripts/db-init.sql
mysql -h 127.0.0.1 -u root -p < services/payment/scripts/db-init.sql

2. init the identity service

docker-compose up identity
localhost:8180, login with admin:admin
create a realm using services/identity/scripts/realm-import.json

3. start up the rest of the services

docker-compose up
