pipeline {
	agent any
	
	environment {
        	HOST = '18.222.5.148'	
    	}
  	stages {
		stage('Prepare'){
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
          			sh 'sh mvnw test'
      			}
    		}
		stage('Deploy') {
			steps {
				sh "mv target/Api-Investimentos-0.0.1-SNAPSHOT.jar Api-Investimentos.jar"
				sh "tar -czf Api-Investimentos.tar.gz Api-Investimentos.jar"
				sh "scp Api-Investimentos.tar.gz ubuntu@${env.HOST}:/home/ubuntu/"
				sh "ssh -t ubuntu@${env.HOST} 'sh /home/ubuntu/projetos/build/build-ex-jenkins-2.sh Api-Investimentos api-investimentos'"
			}
		}
  	}
}
