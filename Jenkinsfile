pipeline {
	agent any
  	stages {
		stage('Prepare'){
			steps {
				sh 'mvn clean'
			}
		}
  		stage('Build') {
      			steps {
	  			sh 'mvn -B -DskipTests package'
      			}
    		}
  		stage('Test') {
      			steps {
          			sh 'mvn test'
      			}
    		}
		stage('Deploy') {
			steps {
				sh 'scp target/Api-Investimentos-0.0.1-SNAPSHOT.jar ubuntu@ubuntu@ec2-18-222-5-148.us-east-2.compute.amazonaws.com:/home/ubuntu/' 
			}
		}
  	}
}
