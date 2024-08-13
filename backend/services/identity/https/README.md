
sudo yum install certbot
then did what they did here: https://www.youtube.com/watch?v=Kk9kuf6D8so 
-copy the fullchain cert (from /etc/letsencrypt/live/) into tls.crt and the privkey into tls.key in identity/https
- and make sure the volume is set up in docker-compose
-change the permissions on those files chmod 655
-docker rm identity-service (for some reason? seems like identity service is caching their https certs after initial start up..)
- i’ve been docker rm’ing everything. db included so identitydb gets wiped too
-re-init identity service (init db, start up, realm import)
 - so really it would be better to do this before any db init
-THEN it finally works
