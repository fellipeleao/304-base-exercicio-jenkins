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
		stage('Prepare'){
			steps {
				sh 'sh mvnw clean'
			}
		}
  		stage('Test Application') {
      			steps {
				script {
                                	checkout scm

                                	docker.image('mysql:5').withRun("-e 'MYSQL_ROOT_PASSWORD=mt123' -e 'MYSQL_MY_DATABASE=invest' -p $mySqlPort:3306") { c ->
                                        	/* Wait until mysql service is up */
                                        	sh 'while ! mysqladmin ping -h0.0.0.0 -P $mySqlPort --silent; do sleep 1; done'

						sh 'sh mvnw test'
                                	}
				}
      			}
    		}
                stage('Build Application') {
                        steps {
                                sh 'sh mvnw -B -DskipTests package'
                                sh "mv target/Api-Investimentos-0.0.1-SNAPSHOT.jar Api-Investimentos.jar"
                        }
                }
		stage('Build Docker Image'){
			steps {
				script {
					app = docker.build("registry-itau.mastertech.com.br/api-investimentos-fellipe")

                    			withCredentials([
                                		usernamePassword(
                                    			credentialsId: 'registry_credential',
                                    			passwordVariable: 'PASSWORD',
                                    			usernameVariable: 'USERNAME')
                                		]) {
                                    			sh "docker login -p $PASSWORD -u $USERNAME registry-itau.mastertech.com.br"
                                		}

                    			docker.withRegistry("https://registry-itau.mastertech.com.br", "registry_credential") {
                        			app.push("${env.BUILD_NUMBER}")
                        			app.push("latest")
                    			}  
				}
			}
		}
		stage('Deploy') {
			steps {
				sh "tar -czf Api-Investimentos.tar.gz Api-Investimentos.jar"
				sh "scp Api-Investimentos.tar.gz ubuntu@${env.HOST}:/home/ubuntu/"
			}
		}
  	}
}
