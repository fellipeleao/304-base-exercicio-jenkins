pipeline {
	agent any
	
	environment {
        	HOST = '18.222.5.148'	
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
          			sh 'sh mvnw test'
      			}
    		}
		stage('Deploy') {
			steps {
				sh 'tar -czf ${env.BUILD_TAG}.tar.gz --directory=target/ ${env.BUILD_TAG}.jar'
				sh 'scp ${env.BUILD_TAG}.tar.gz ubuntu@${env.HOST}:/home/ubuntu/' 
				sh 'ssh -t ubuntu@${env.HOST} "sh /home/ubuntu/projetos/build/build-ex-jenkins-2.sh ${env.BUILD_TAG} api-investimentos"'
			}
		}
  	}
}
