FROM bitnami/java:17.0.11-10

WORKDIR /app

COPY target/chatapp.war /app

ENV JAVA_HOME /opt/bitnami/java

CMD ["java", "-jar", "chatapp.war"]