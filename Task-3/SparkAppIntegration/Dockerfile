FROM bitnami/java:17.0.11-10

WORKDIR /app

COPY target/*.war app.war

ENV JAVA_HOME /opt/bitnami/java

ENV SERVER_PORT 8888

ENV JDK_JAVA_OPTIONS "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED"

ENTRYPOINT ["java", "-jar", "app.war"]
