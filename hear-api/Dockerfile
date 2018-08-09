FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./target/xhhear-server.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/xhhear-server.jar"]

EXPOSE 9300