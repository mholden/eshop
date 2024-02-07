FROM maven:openjdk

WORKDIR /opt/app
COPY . .
WORKDIR /opt/app/Gateway

RUN mvn -DskipTests clean package

ENTRYPOINT ["/bin/bash"]
CMD ["-c", "./start.sh"]]
