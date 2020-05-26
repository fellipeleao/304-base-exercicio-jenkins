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
				sh 'scp target/Api-Investimentos-0.0.1-SNAPSHOT.jar ubuntu@ec2-18-222-5-148.us-east-2.compute.amazonaws.com:/home/ubuntu/' 
			}
		}
  	}
}
