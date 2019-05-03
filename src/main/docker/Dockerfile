# 基镜像: 表示使用 Jdk8 环境 为基础镜像，如果镜像不是本地的会从 DockerHub 进行下载
FROM java:8
# 指明作者
MAINTAINER marcher
# 定义匿名卷: VOLUME 指向了一个/tmp的目录，由于 Spring Boot 使用内置的Tomcat容器，
# Tomcat 默认使用/tmp作为工作目录。这个命令的效果是：在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到容器中的/tmp目录
VOLUME /tmp
# 复制文件重命名
ADD marcher-blog-1.0.0.jar app.jar
# 暴露端口
EXPOSE 8879
# 入口点
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]

