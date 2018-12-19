FROM tomcat:8

ARG version
ENV env_version=$version

RUN useradd -m -d /home/app -u 1100 app

RUN chown -R app /usr/local/tomcat/

ADD target/gertrud.war /usr/local/tomcat/webapps/
ADD entrypoint.sh /usr/local/tomcat/
RUN chmod 755 /usr/local/tomcat/entrypoint.sh
ADD tomcat/server.xml /usr/local/tomcat/conf/
COPY libs/* /usr/local/tomcat/lib/
ADD prometheus/metrics.war /usr/local/tomcat/webapps/

USER app

CMD ["/usr/local/tomcat/entrypoint.sh"]