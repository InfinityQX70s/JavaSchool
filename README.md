1) mvn tomcat7:redeploy если выдает ошибку 
2) mvn tomcat7:deploy
3) mvn clean install

DB logiweb user:logiweb pass:secret
jdbc:mysql://localhost:3306/logiweb

Need to dump db city table. 


1) запустить сонар сервер

mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install

mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Pcoverage-per-test

mvn sonar:sonar      you can see covarage of your code

3)http://localhost:9000/
