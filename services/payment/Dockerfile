FROM maven:openjdk

WORKDIR /opt/app
COPY . .
WORKDIR /opt/app/PaymentService

RUN mvn -DskipTests clean package

ENTRYPOINT ["/bin/bash"]
CMD ["-c", "./start.sh"]]
