FROM tomcat:8
MAINTAINER xyz

ADD target/tremobilitycms-${}.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]