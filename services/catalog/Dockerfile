FROM maven:openjdk

WORKDIR /opt/app
COPY . .
WORKDIR /opt/app/hldn-catalog

RUN mvn clean package

ENTRYPOINT ["java"]
CMD ["-jar", "target/hldn-catalog.jar"]
