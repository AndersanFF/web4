FROM jboss/wildfly:latest

WORKDIR /opt/jboss/wildfly/standalone/deployments/

COPY target/web4.war /opt/jboss/wildfly/standalone/deployments/ROOT.war
COPY ./postgres-ds.xml /opt/jboss/wildfly/standalone/configuration/
COPY ./lib/postgresql-42.6.2.jar /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]