FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="mydemo"
ENV TZ=Asia/Shanghai
ENV SPRING_PROFILES_ACTIVE=dev
ENV JAVA_OPTS=""
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9005
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.profiles.active=$SPRING_PROFILES_ACTIVE"]
