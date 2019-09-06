FROM payara/server-web
COPY target/jsfApp.war $DEPLOY_DIR
