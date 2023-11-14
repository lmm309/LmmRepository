FROM java:8
MAINTAINER LIUMIN
ADD springclouddemo.tar.gz /
WORKDIR /springclouddemo/
VOLUME /myVolume
ENV HOST_Q=$(hostname)
RUN chmod +x -R /springclouddemo
#设置时区，否则容器内比宿主机时间少8小时
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
CMD ["sh","/springclouddemo/startup.sh"]
