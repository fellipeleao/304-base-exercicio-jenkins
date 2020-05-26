pipeline {
	agent any
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
				sh 'tar -czf api-investimentos.tar.gz --directory=target/ Api-Investimentos-0.0.1-SNAPSHOT.jar'
				sh 'scp api-investimentos.tar.gz ubuntu@18.222.5.148:/home/ubuntu/' 
				sh 'ssh -t ubuntu@18.222.5.148 'sh /home/ubuntu/projetos/build/build-ex-jenkins.sh'
			}
		}
  	}
}
