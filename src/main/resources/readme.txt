Tomcat configuration

brew install tomcat@8
cd /usr/local/Cellar/tomcat@8/8.5.38/libexec (tomcat home and basedir)

add tomcat as a configuration

application server configure: add tomcat home and basedir

before build add "Build Artifact", select Spring4Web.war exploded

adjust Java version to at least 8

add Spring Core to pom.xml (but it is s already there)