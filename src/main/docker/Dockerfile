# 基镜像
FROM java:8
# 定义匿名卷
VOLUME /tmp
# 复制文件重命名
ADD marcher-blog-1.0.0.jar app.jar
# 暴露端口
EXPOSE 8879
# 入口点
ENTRYPOINT ["java", "-jar", "app.jar"]

