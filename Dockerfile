FROM tomcat:8

ADD target/tremobilitycms-${CI_BUILD_REF_NAME}.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]

