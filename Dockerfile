FROM java:8
VOLUME /tmp
RUN echo "Asia/shanghai" > /etc/timezone;
ADD target/xxw-v1.jar api.jar
RUN bash -c 'touch /api.jar'
EXPOSE 10001
ENTRYPOINT ["java","-jar","/api.jar"]
