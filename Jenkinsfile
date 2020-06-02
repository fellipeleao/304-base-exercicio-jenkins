pipeline {
	agent any
	
	environment {
        	HOST = '18.222.5.148'	

                def mySqlPort = sh (
                	script: "awk -v min=64000 -v max=65000 'BEGIN{srand(); print int(min+rand()*(max-min+1))}'",
                        returnStdout: true
                ).trim()

		MYSQL_PORT = "$mySqlPort"
    	}
  	stages {
		stage('Clean'){
			steps {
				sh 'sh mvnw clean'
			}
		}
  		stage('Build') {
      			steps {
	  			sh 'sh mvnw -B -DskipTests package'
      			}
    		}
  		stage('Test') {
      			steps {
				script {
                                	checkout scm

					def mySqlPort = sh (
    						script: "awk -v min=64000 -v max=65000 'BEGIN{srand(); print int(min+rand()*(max-min+1))}'",
    						returnStdout: true
					).trim()

                                	docker.image('mysql:8').withRun("-e 'MYSQL_ROOT_PASSWORD=Horn1403*' -e 'MYSQL_MY_DATABASE=invest' -p $mySqlPort:3306") { c ->
                                        	/* Wait until mysql service is up */
                                        	sh 'while ! mysqladmin ping -h0.0.0.0 -P $mySqlPort --silent; do sleep 1; done'

						sh 'sh mvnw test'
                                	}
				}
      			}
    		}
		stage('Deploy') {
			steps {
				sh "mv target/Api-Investimentos-0.0.1-SNAPSHOT.jar Api-Investimentos.jar"
				sh "tar -czf Api-Investimentos.tar.gz Api-Investimentos.jar"
				sh "scp Api-Investimentos.tar.gz ubuntu@${env.HOST}:/home/ubuntu/"
			}
		}
  	}
}
