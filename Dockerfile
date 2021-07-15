#создаем образ на основе alpine linux с установленной jdk11
FROM adoptopenjdk/openjdk11:alpine-jre
#переменной JAR_FILE присваевается путь к jar-нику
ARG JAR_FILE=target/online-shop-0.0.1-SNAPSHOT.jar
#назначаем рабочую директорию в которой будут выполняться дальнейшие команды=> переходим в папку app
WORKDIR /opt/app
#наш jar-файл указанный в JAR_FILE копируется в папку app и копии задается имя onlineshop2021.jar
COPY ${JAR_FILE} onlineshop2021.jar
ENTRYPOINT ["java","-jar","onlineshop2021.jar"]



