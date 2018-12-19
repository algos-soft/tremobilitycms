FROM tomcat:8

ARG version
ENV env_version=$version

RUN useradd -m -d /home/app -u 1100 app

RUN chown -R app /usr/local/tomcat/

ADD target/tremobilitycms.war /usr/local/tomcat/webapps/

USER app

CMD ["catalina.sh", "run"]

