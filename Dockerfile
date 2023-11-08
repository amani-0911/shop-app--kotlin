
FROM adoptopenjdk:11-jre-hotspot


WORKDIR /shop-application


COPY build/libs/shop-*.jar shop-application.jar

EXPOSE 8080


CMD ["java", "-jar", "shop-application.jar"]
