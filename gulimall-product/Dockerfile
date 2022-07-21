#运行环境
FROM java:8
#暴露端口
EXPOSE 8080
#容器内挂载目录
VOLUME /tmp
#添加renren-fast.jar到根目录命名为app.jar
ADD target/*.jar  /app.jar
RUN bash -c 'touch /app.jar'
#启动命令java -jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=prod"]
