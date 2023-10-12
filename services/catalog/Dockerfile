FROM maven:openjdk

WORKDIR /opt/app
COPY . .
WORKDIR /opt/app/CatalogService

RUN mvn -DskipTests clean package

#ENTRYPOINT ["java"]
#CMD ["-jar", "target/hldn-catalog-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["/bin/bash"]
CMD ["-c", "./start.sh"]]
