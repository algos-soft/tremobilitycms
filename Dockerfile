FROM tomcat:8

ARG version
ENV env_version=$version

RUN useradd -m -d /home/app -u 1100 app

RUN chown -R app /usr/local/tomcat/

ADD target/gertrud.war /usr/local/tomcat/webapps/
ADD enrypoint.sh /usr/local/tomcat/
RUN chmod 755 /usr/local/tomcat/enrypoint.sh

USER app

CMD ["/usr/local/tomcat/enrypoint.sh"]