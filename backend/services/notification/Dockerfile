FROM maven:openjdk

WORKDIR /opt/app
COPY . .
WORKDIR /opt/app/NotificationService

RUN mvn -DskipTests clean package

ENTRYPOINT ["/bin/bash"]
CMD ["-c", "./start.sh"]]
