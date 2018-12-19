FROM tomcat:8

ARG version
ENV env_version=$version
ADD target/tremobilitycms-${env_version}.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]

